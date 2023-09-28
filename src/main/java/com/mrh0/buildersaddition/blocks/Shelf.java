package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.tileentity.ShelfTileEntity;
import com.mrh0.buildersaddition.util.IComparetorOverride;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Shelf extends BaseDerivativeBlock implements EntityBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	protected static final VoxelShape NORTH_SHAPE = Block.box(0.0D, 0.0D, 8D, 16D, 16D, 16D);
	protected static final VoxelShape EAST_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
	protected static final VoxelShape SOUTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16D, 16D, 8D);
	protected static final VoxelShape WEST_SHAPE = Block.box(8D, 0.0D, 0.0D, 16D, 16.0D, 16.0D);

	public Shelf(String name) {
		super("shelf_" + name, Blocks.OAK_PLANKS);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		return this.defaultBlockState().setValue(FACING, c.getHorizontalDirection().getOpposite());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext c) {
		switch (state.getValue(FACING)) {
		case NORTH:
			return NORTH_SHAPE;
		case EAST:
			return EAST_SHAPE;
		case SOUTH:
			return SOUTH_SHAPE;
		case WEST:
			return WEST_SHAPE;
		default:
			return NORTH_SHAPE;
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult hit) {
		if (player.isSpectator()) {
			return InteractionResult.PASS;
		}
		if (world.isClientSide()) {
			return InteractionResult.SUCCESS;
		}

		ShelfTileEntity mte = (ShelfTileEntity) world.getBlockEntity(pos);
		player.openMenu((MenuProvider) mte);
		/*NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider) mte, extraData -> {
			extraData.writeBlockPos(mte.getBlockPos());
		});*/
		return InteractionResult.CONSUME;
	}

	public int getBookSum(BlockState state, LevelReader world, BlockPos pos) {
		BlockEntity te = world.getBlockEntity(pos);
		if (te != null) {
			if (te instanceof ShelfTileEntity) {
				ShelfTileEntity bte = (ShelfTileEntity) te;
				int sum = 0;
				for (int i = 0; i < bte.handler.getSlots(); i++) {
					if (bte.handler.getStackInSlot(i).getCount() > 0)
						sum++;
				}
				return sum;
			}
		}
		return 0;
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			BlockEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof ShelfTileEntity) {
				Containers.dropContents(world, pos, (ShelfTileEntity) tileentity);
				world.updateNeighborsAt(pos, this); // Comparators
			}
			super.onRemove(state, world, pos, newState, isMoving);
		}
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity ent, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			BlockEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof ShelfTileEntity) {
				((ShelfTileEntity) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
		return IComparetorOverride.getComparetorOverride(worldIn, pos);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ShelfTileEntity(pos, state);
	}

	/*@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152755_, BlockState p_152756_,
			BlockEntityType<T> p_152757_) {
		if (p_152755_.isClientSide)
			return Util.createTickerHelper(p_152757_, Index.SHELF_TILE_ENTITY_TYPE, ShelfTileEntity::ticker);
		return null;
	}*/
}
