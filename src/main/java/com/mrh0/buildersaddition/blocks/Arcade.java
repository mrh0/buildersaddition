package com.mrh0.buildersaddition.blocks;

import javax.annotation.Nullable;
import com.mrh0.buildersaddition.blocks.base.BaseBlock;
import com.mrh0.buildersaddition.tileentity.ArcadeTileEntity;
import com.mrh0.buildersaddition.tileentity.CounterTileEntity;
import com.mrh0.buildersaddition.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class Arcade extends BaseBlock {
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	
	private static VoxelShape SHAPE_NORTH_LOWER = Block.makeCuboidShape(0d, 0d, 1d, 16d, 32d, 16d);
	private static VoxelShape SHAPE_EAST_LOWER = Block.makeCuboidShape(0d, 0d, 0d, 15d, 32d, 16d);
	private static VoxelShape SHAPE_SOUTH_LOWER = Block.makeCuboidShape(0d, 0d, 0d, 16d, 32d, 15d);
	private static VoxelShape SHAPE_WEST_LOWER = Block.makeCuboidShape(1d, 0d, 0d, 16d, 32d, 16d);
	
	private static VoxelShape SHAPE_NORTH_UPPER = Block.makeCuboidShape(0d, -16d, 1d, 16d, 16d, 16d);
	private static VoxelShape SHAPE_EAST_UPPER = Block.makeCuboidShape(0d, -16d, 0d, 15d, 16d, 16d);
	private static VoxelShape SHAPE_SOUTH_UPPER = Block.makeCuboidShape(0d, -16d, 0d, 16d, 16d, 15d);
	private static VoxelShape SHAPE_WEST_UPPER = Block.makeCuboidShape(1d, -16d, 0d, 16d, 16d, 16d);


	public Arcade() {
		super("arcade", Properties.from(Blocks.IRON_BLOCK)
				.notSolid().setAllowsSpawn((BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) -> false)
				.setOpaque(Util::isntSolid).setSuffocates(Util::isntSolid).setBlocksVision(Util::isntSolid));
		this.setDefaultState(this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(FACING, Direction.NORTH));
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
					.with(HALF, DoubleBlockHalf.LOWER);
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
		builder.add(FACING, HALF);
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
		if (player.isSpectator()) {
            return ActionResultType.PASS;
        }
    	if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }
    	
    	ArcadeTileEntity mte = getTE(state, worldIn, pos);
		NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) mte, extraData -> {
            extraData.writeBlockPos(mte.getPos());
        });
    	return ActionResultType.SUCCESS;
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.isIn(newState.getBlock())) {
			ArcadeTileEntity tileentity = getTE(state, worldIn, pos);
			
			if(state.get(HALF) == DoubleBlockHalf.LOWER)
				worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
			else
				worldIn.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}
	
	private ArcadeTileEntity getTE(BlockState state, World worldIn, BlockPos pos) {
		TileEntity tileentity;
		if(state.get(HALF) == DoubleBlockHalf.LOWER)
			tileentity = worldIn.getTileEntity(pos);
		else
			tileentity = worldIn.getTileEntity(pos.down());
		if (tileentity != null && tileentity instanceof ArcadeTileEntity)
			return (ArcadeTileEntity) tileentity;
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

	/*@Override
	@Nullable
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity instanceof INamedContainerProvider ? (INamedContainerProvider) tileentity : null;
	}*/
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return state.get(HALF) == DoubleBlockHalf.LOWER;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ArcadeTileEntity();
	}
}
