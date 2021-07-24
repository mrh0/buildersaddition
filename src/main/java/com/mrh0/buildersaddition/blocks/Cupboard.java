package com.mrh0.buildersaddition.blocks;

import java.util.Random;
import javax.annotation.Nullable;
import com.mrh0.buildersaddition.blocks.base.BaseBlock;
import com.mrh0.buildersaddition.tileentity.CounterTileEntity;
import com.mrh0.buildersaddition.tileentity.CupboardTileEntity;
import com.mrh0.buildersaddition.util.IComparetorOverride;
import com.mrh0.buildersaddition.util.Util;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class Cupboard extends BaseBlock implements IWaterLoggable, ITileEntityProvider {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final BooleanProperty MIRROR = BooleanProperty.create("mirror");
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	private static VoxelShape SHAPE_NORTH_LOWER = Block.makeCuboidShape(0d, 0d, 1d, 16d, 32d, 16d);
	private static VoxelShape SHAPE_EAST_LOWER = Block.makeCuboidShape(0d, 0d, 0d, 15d, 32d, 16d);
	private static VoxelShape SHAPE_SOUTH_LOWER = Block.makeCuboidShape(0d, 0d, 0d, 16d, 32d, 15d);
	private static VoxelShape SHAPE_WEST_LOWER = Block.makeCuboidShape(1d, 0d, 0d, 16d, 32d, 16d);
	
	private static VoxelShape SHAPE_NORTH_UPPER = Block.makeCuboidShape(0d, -16d, 1d, 16d, 16d, 16d);
	private static VoxelShape SHAPE_EAST_UPPER = Block.makeCuboidShape(0d, -16d, 0d, 15d, 16d, 16d);
	private static VoxelShape SHAPE_SOUTH_UPPER = Block.makeCuboidShape(0d, -16d, 0d, 16d, 16d, 15d);
	private static VoxelShape SHAPE_WEST_UPPER = Block.makeCuboidShape(1d, -16d, 0d, 16d, 16d, 16d);

	public Cupboard(String name, Block source) {
		super("cupboard_" + name, Properties.from(source));
		this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(HALF, DoubleBlockHalf.LOWER).with(FACING, Direction.NORTH));
	}
	
	@Override
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}
	
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos blockpos = context.getPos();
		if (blockpos.getY() < 255 && context.getWorld().getBlockState(blockpos.up()).isReplaceable(context)) {
			World world = context.getWorld();
			boolean flag = world.isBlockPowered(blockpos) || world.isBlockPowered(blockpos.up());
			return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite())
					.with(HALF, DoubleBlockHalf.LOWER).with(MIRROR, context.hasSecondaryUseForPlayer())
					.with(WATERLOGGED, Boolean.valueOf(context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER));
		} 
		return null;
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, Boolean.valueOf(worldIn.getFluidState(pos.up()).getFluid() == Fluids.WATER)), 3);
	}
	
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.down();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		return state.get(HALF) == DoubleBlockHalf.LOWER ? true : blockstate.isIn(this);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED, HALF, MIRROR);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidStateIn) {
		return SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluidStateIn);
	}

	@Override
	public boolean canPlaceLiquid(BlockGetter world, BlockPos pos, BlockState state, Fluid fluidIn) {
		return SimpleWaterloggedBlock.super.canPlaceLiquid(world, pos, state, fluidIn);
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
		switch (type) {
		case LAND:
			return false;
		case WATER:
			return world.getFluidState(pos).is(FluidTags.WATER);
		case AIR:
			return false;
		default:
			return false;
		}
	}

	private VoxelShape getShapeForDirection(Direction d, boolean b) {
		switch (d) {
			case NORTH:
				return b ? SHAPE_NORTH_UPPER : SHAPE_NORTH_LOWER;
			case SOUTH:
				return b ? SHAPE_SOUTH_UPPER : SHAPE_SOUTH_LOWER;
			case EAST:
				return b ? SHAPE_EAST_UPPER : SHAPE_EAST_LOWER;
			case WEST:
				return b ? SHAPE_WEST_UPPER : SHAPE_WEST_LOWER;
			default:
				break;
		}
		return SHAPE_NORTH_LOWER;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return getShapeForDirection(state.get(FACING), state.get(HALF) == DoubleBlockHalf.UPPER);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			if(state.get(HALF) == DoubleBlockHalf.LOWER) {
				//BlockState front1 = worldIn.getBlockState(pos.offset(state.get(FACING)));
				//BlockState front2 = worldIn.getBlockState(pos.up().offset(state.get(FACING)));
				//if(front1.isSolid() || front2.isSolid())
				if(!Util.accessCheck(worldIn, pos, state.get(FACING)) || !Util.accessCheck(worldIn, pos.up(), state.get(FACING)))
					return ActionResultType.CONSUME;
				TileEntity tileentity = worldIn.getTileEntity(pos);
				if (tileentity instanceof CupboardTileEntity) {
					player.openContainer((CupboardTileEntity) tileentity);
					PiglinTasks.func_234478_a_(player, true);
				}
			}
			else {
				//BlockState front1 = worldIn.getBlockState(pos.offset(state.get(FACING)));
				//BlockState front2 = worldIn.getBlockState(pos.down().offset(state.get(FACING)));
				//if(front1.isSolid() || front2.isSolid())
				if(!Util.accessCheck(worldIn, pos, state.get(FACING)) || !Util.accessCheck(worldIn, pos.down(), state.get(FACING)))
					return ActionResultType.CONSUME;
				TileEntity tileentity = worldIn.getTileEntity(pos.down());
				if (tileentity instanceof CupboardTileEntity) {
					player.openContainer((CupboardTileEntity) tileentity);
					PiglinTasks.func_234478_a_(player, true);
				}
			}
			return ActionResultType.CONSUME;
		}
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.isIn(newState.getBlock())) {
			CupboardTileEntity tileentity = getTE(state, worldIn, pos);
			
			if(state.get(HALF) == DoubleBlockHalf.LOWER) {
				BlockState upper = worldIn.getBlockState(pos.up());
				if(upper.getBlock() == this)
					worldIn.setBlockState(pos.up(), getFluidState(upper).getFluid() == Fluids.WATER ? getFluidState(upper).getBlockState() : Blocks.AIR.getDefaultState(), 35);
				if (tileentity != null) {
					InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
					worldIn.updateComparatorOutputLevel(pos, this);
					worldIn.updateComparatorOutputLevel(pos.up(), this);
				}
			}
			else {
				BlockState lower = worldIn.getBlockState(pos.down());
				if(lower.getBlock() == this)
					worldIn.setBlockState(pos.down(), getFluidState(lower).getFluid() == Fluids.WATER ? getFluidState(lower).getBlockState() : Blocks.AIR.getDefaultState(), 35);
				if (tileentity != null) {
					InventoryHelper.dropInventoryItems(worldIn, pos.down(), (IInventory) tileentity);
					worldIn.updateComparatorOutputLevel(pos, this);
					worldIn.updateComparatorOutputLevel(pos.down(), this);
				}
			}
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if(state.get(HALF) == DoubleBlockHalf.UPPER)
			return;
		CupboardTileEntity tileentity = getTE(state, worldIn, pos);
		if (tileentity != null) {
			tileentity.invTick();
		}

	}
	
	private CupboardTileEntity getTE(BlockState state, World worldIn, BlockPos pos) {
		TileEntity tileentity;
		if(state.get(HALF) == DoubleBlockHalf.LOWER)
			tileentity = worldIn.getTileEntity(pos);
		else
			tileentity = worldIn.getTileEntity(pos.down());
		if (tileentity != null && tileentity instanceof CupboardTileEntity)
			return (CupboardTileEntity) tileentity;
		return null;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
		super.eventReceived(state, worldIn, pos, id, param);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}

	@Override
	@Nullable
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity instanceof INamedContainerProvider ? (INamedContainerProvider) tileentity : null;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;//state.get(HALF) == DoubleBlockHalf.LOWER;
	}
	
	@Override
	@Nullable
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new CupboardTileEntity();
	}
	
	@Override
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		if(blockState.get(HALF) == DoubleBlockHalf.LOWER)
			return IComparetorOverride.getComparetorOverride(worldIn, pos);
		else
			return IComparetorOverride.getComparetorOverride(worldIn, pos.down());
	}
}

