package com.mrh0.buildersaddition.blocks;

import javax.annotation.Nullable;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.base.BaseBlock;
import com.mrh0.buildersaddition.event.opts.BlockOptions;
import com.mrh0.buildersaddition.event.opts.ItemOptions;
import com.mrh0.buildersaddition.tileentity.ArcadeTileEntity;
import com.mrh0.buildersaddition.tileentity.CounterTileEntity;
import com.mrh0.buildersaddition.util.Util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.nio.channels.NetworkChannel;

public class Arcade extends BaseBlock implements EntityBlock {
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	
	private static VoxelShape SHAPE_NORTH_LOWER = Block.box(0d, 0d, 1d, 16d, 32d, 16d);
	private static VoxelShape SHAPE_EAST_LOWER = Block.box(0d, 0d, 0d, 15d, 32d, 16d);
	private static VoxelShape SHAPE_SOUTH_LOWER = Block.box(0d, 0d, 0d, 16d, 32d, 15d);
	private static VoxelShape SHAPE_WEST_LOWER = Block.box(1d, 0d, 0d, 16d, 32d, 16d);
	
	private static VoxelShape SHAPE_NORTH_UPPER = Block.box(0d, -16d, 1d, 16d, 16d, 16d);
	private static VoxelShape SHAPE_EAST_UPPER = Block.box(0d, -16d, 0d, 15d, 16d, 16d);
	private static VoxelShape SHAPE_SOUTH_UPPER = Block.box(0d, -16d, 0d, 16d, 16d, 15d);
	private static VoxelShape SHAPE_WEST_UPPER = Block.box(1d, -16d, 0d, 16d, 16d, 16d);

	public Arcade() {
		super("arcade", Properties.copy(Blocks.IRON_BLOCK)
				.isSuffocating(Util::isntSolid).isValidSpawn(Util::isntSolid).isViewBlocking(Util::isntSolid), new BlockOptions().setItemOptions(new ItemOptions()));
		
		this.registerDefaultState(this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, Direction.NORTH));
	}

	public Arcade(String name) {
		super("arcade_" + name, Properties.copy(Blocks.OAK_PLANKS)
				.isValidSpawn(Util::isntSolid).isSuffocating(Util::isntSolid).isViewBlocking(Util::isntSolid));
		this.registerDefaultState(this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, Direction.NORTH));
	}
	
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return world.isClientSide() ? Util.createTickerHelper(type, Index.ARCADE_TILE_ENTITY_TYPE.get(), ArcadeTileEntity::tick) : null;
	}
	
	@Override
	public PushReaction getPistonPushReaction(BlockState p_60584_) {
		return PushReaction.BLOCK;
	}
	
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos blockpos = context.getClickedPos();
		if (blockpos.getY() < 255 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context)) {
			Level world = context.getLevel();
			boolean flag = world.hasNeighborSignal(blockpos) || world.hasNeighborSignal(blockpos.above());
			return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
					.setValue(HALF, DoubleBlockHalf.LOWER);
		} else {
			return null;
		}
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity ent,
			ItemStack p_49851_) {
		world.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
	}
	
	public boolean isValidPosition(BlockState state, BlockGetter worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		return state.getValue(HALF) == DoubleBlockHalf.LOWER ? true : blockstate.is(this);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
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
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return getShapeForDirection(state.getValue(FACING), state.getValue(HALF) == DoubleBlockHalf.UPPER);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		if (player.isSpectator()) {
            return InteractionResult.PASS;
        }
    	if (world.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
    	
    	ArcadeTileEntity mte = getTE(state, world, pos);
		player.openMenu(mte);
		/*NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider) mte, extraData -> {
            extraData.writeBlockPos(pos); //mte.pos
        });*/
    	return InteractionResult.SUCCESS;
	}

	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			ArcadeTileEntity tileentity = getTE(state, worldIn, pos);
			
			if(state.getValue(HALF) == DoubleBlockHalf.LOWER)
				worldIn.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
			else
				worldIn.setBlockAndUpdate(pos.below(), Blocks.AIR.defaultBlockState());
			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}
	
	
	private ArcadeTileEntity getTE(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileentity;
		if(state.getValue(HALF) == DoubleBlockHalf.LOWER)
			tileentity = worldIn.getBlockEntity(pos);
		else
			tileentity = worldIn.getBlockEntity(pos.below());
		if (tileentity != null && tileentity instanceof ArcadeTileEntity)
			return (ArcadeTileEntity) tileentity;
		return null;
	}

	@Override
	public RenderShape getRenderShape(BlockState p_60550_) {
		return RenderShape.MODEL;
	}
	
	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		return tileentity == null ? false : tileentity.triggerEvent(id, param);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ArcadeTileEntity(pos, state);
	}
}
