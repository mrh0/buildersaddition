package com.mrh0.buildersaddition.blocks;


import javax.annotation.Nullable;
import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.tileentity.BookshelfTileEntity;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class Bookshelf extends BaseDerivativeBlock implements EntityBlock {

	public static final BooleanProperty BOOK0 = BooleanProperty.create("book0");
	public static final BooleanProperty BOOK1 = BooleanProperty.create("book1");
	public static final BooleanProperty BOOK2 = BooleanProperty.create("book2");
	public static final BooleanProperty BOOK3 = BooleanProperty.create("book3");
	public static final BooleanProperty BOOK4 = BooleanProperty.create("book4");
	public static final BooleanProperty BOOK5 = BooleanProperty.create("book5");
	public static final BooleanProperty BOOK6 = BooleanProperty.create("book6");
	public static final BooleanProperty BOOK7 = BooleanProperty.create("book7");
	
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	
	protected static final VoxelShape NORTH_SHAPE = Block.box(0.0D, 0.0D, 8D, 16D, 16D, 16D);
	protected static final VoxelShape EAST_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
	protected static final VoxelShape SOUTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16D, 16D, 8D);
	protected static final VoxelShape WEST_SHAPE = Block.box(8D, 0.0D, 0.0D, 16D, 16.0D, 16.0D);
	
	public Bookshelf(String name) {
		super("bookshelf_" + name, Blocks.OAK_PLANKS);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH)
			.setValue(BOOK0, false).setValue(BOOK1, false).setValue(BOOK2, false).setValue(BOOK3, false)
			.setValue(BOOK4, false).setValue(BOOK5, false).setValue(BOOK6, false).setValue(BOOK7, false));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, BOOK0, BOOK1, BOOK2, BOOK3, BOOK4, BOOK5, BOOK6, BOOK7);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		return this.defaultBlockState().setValue(FACING, c.getHorizontalDirection().getOpposite());
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return  new BookshelfTileEntity(pos, state);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
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
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		if (player.isSpectator()) {
            return InteractionResult.PASS;
        }
    	if (world.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
    	
    	BookshelfTileEntity mte = (BookshelfTileEntity) world.getBlockEntity(pos);
		NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) mte, extraData -> {
            extraData.writeBlockPos(pos);
        });
    	return InteractionResult.CONSUME;
	}
	
	@Override
	public float getEnchantPowerBonus(BlockState state, LevelReader world, BlockPos pos) {
		return Math.min((float)getBookSum(state, world, pos)/3f, 6);
	}
	
	public static BlockState getState(BlockState state, boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6, boolean b7, boolean b8) {
		return state
				.setValue(BOOK0, b1).setValue(BOOK1, b2).setValue(BOOK2, b3).setValue(BOOK3, b4)
				.setValue(BOOK4, b5).setValue(BOOK5, b6).setValue(BOOK6, b7).setValue(BOOK7, b8);
	}
	
	public int getBookSum(BlockState state, LevelReader world, BlockPos pos) {
		BlockEntity te = world.getBlockEntity(pos);
		if(te != null) {
			if(te instanceof BookshelfTileEntity) {
				BookshelfTileEntity bte = (BookshelfTileEntity)te;
				int sum = 0;
				for(int i = 0; i < bte.handler.getSlots(); i++) {
					if(bte.handler.getStackInSlot(i).getCount() > 0)
						sum++;
				}
				return sum;
			}
		}
		return 0;
	}
	
	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState,
			boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			BlockEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof Container) {
				Containers.dropContents(world, pos, (Container) tileentity);
				world.updateNeighborsAt(pos, this);
			}
		}
		super.onRemove(state, world, pos, newState, isMoving);
	}
	
	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity ent, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			BlockEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof BookshelfTileEntity) {
				((BookshelfTileEntity) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState sate, Level world, BlockPos pos) {
		return IComparetorOverride.getComparetorOverride(world, pos);
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState p_60457_) {
		return true;
	}
}
