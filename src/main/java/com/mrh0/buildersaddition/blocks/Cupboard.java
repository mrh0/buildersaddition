package com.mrh0.buildersaddition.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.mrh0.buildersaddition.blocks.base.BaseBlock;
import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.tileentity.CounterTileEntity;
import com.mrh0.buildersaddition.tileentity.CupboardTileEntity;
import com.mrh0.buildersaddition.util.IComparetorOverride;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class Cupboard extends BaseBlock implements IWaterLoggable, ITileEntityProvider {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final BooleanProperty MIRROR = BooleanProperty.create("mirror");
	public static final DirectionProperty FACING = DirectionProperty.create("facing",
			p -> p.getIndex() > 1 && p.getIndex() < Direction.values().length);

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
					.with(HALF, DoubleBlockHalf.LOWER).with(MIRROR, context.func_225518_g_())
					.with(WATERLOGGED, Boolean.valueOf(context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER));
		} else {
			return null;
		}
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof CounterTileEntity) {
				((CounterTileEntity) tileentity).setCustomName(stack.getDisplayName());
			}
		}
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
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
		return IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn);
	}

	@Override
	public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
		return IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn);
	}

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		switch (type) {
		case LAND:
			return false;
		case WATER:
			return worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
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
				BlockState front1 = worldIn.getBlockState(pos.offset(state.get(FACING)));
				BlockState front2 = worldIn.getBlockState(pos.up().offset(state.get(FACING)));
				if(front1.isSolid() || front2.isSolid())
					return ActionResultType.CONSUME;
				TileEntity tileentity = worldIn.getTileEntity(pos);
				if (tileentity instanceof CupboardTileEntity) {
					player.openContainer((CupboardTileEntity) tileentity);
					PiglinTasks.func_234478_a_(player, true);
				}
			}
			else {
				BlockState front1 = worldIn.getBlockState(pos.offset(state.get(FACING)));
				BlockState front2 = worldIn.getBlockState(pos.down().offset(state.get(FACING)));
				if(front1.isSolid() || front2.isSolid())
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
				worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
				if (tileentity != null) {
					InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
					worldIn.updateComparatorOutputLevel(pos, this);
					worldIn.updateComparatorOutputLevel(pos.up(), this);
				}
			}
			else {
				worldIn.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
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
		return state.get(HALF) == DoubleBlockHalf.LOWER;
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

