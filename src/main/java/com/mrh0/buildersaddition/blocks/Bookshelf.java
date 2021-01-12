package com.mrh0.buildersaddition.blocks;


import javax.annotation.Nullable;
import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.tileentity.BookshelfTileEntity;
import com.mrh0.buildersaddition.util.IComparetorOverride;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
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

public class Bookshelf extends BaseDerivativeBlock {

	public static final BooleanProperty BOOK0 = BooleanProperty.create("book0");
	public static final BooleanProperty BOOK1 = BooleanProperty.create("book1");
	public static final BooleanProperty BOOK2 = BooleanProperty.create("book2");
	public static final BooleanProperty BOOK3 = BooleanProperty.create("book3");
	public static final BooleanProperty BOOK4 = BooleanProperty.create("book4");
	public static final BooleanProperty BOOK5 = BooleanProperty.create("book5");
	public static final BooleanProperty BOOK6 = BooleanProperty.create("book6");
	public static final BooleanProperty BOOK7 = BooleanProperty.create("book7");
	
	public static final DirectionProperty FACING = DirectionProperty.create("facing",
			p -> p.getIndex() > 1 && p.getIndex() < Direction.values().length);
	
	protected static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 8D, 16D, 16D, 16D);
	protected static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
	protected static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16D, 16D, 8D);
	protected static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(8D, 0.0D, 0.0D, 16D, 16.0D, 16.0D);
	
	public Bookshelf(String name) {
		super("bookshelf_" + name, Blocks.OAK_PLANKS);
		setDefaultState(getDefaultState().with(FACING, Direction.NORTH)
			.with(BOOK0, false).with(BOOK1, false).with(BOOK2, false).with(BOOK3, false)
			.with(BOOK4, false).with(BOOK5, false).with(BOOK6, false).with(BOOK7, false));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING, BOOK0, BOOK1, BOOK2, BOOK3, BOOK4, BOOK5, BOOK6, BOOK7);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		return this.getDefaultState().with(FACING, c.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new BookshelfTileEntity();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(FACING)) {
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
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (player.isSpectator()) {
            return ActionResultType.PASS;
        }
    	if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }
    	
    	BookshelfTileEntity mte = (BookshelfTileEntity) worldIn.getTileEntity(pos);
		NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) mte, extraData -> {
            extraData.writeBlockPos(mte.getPos());
        });
    	return ActionResultType.CONSUME;
    }
	
	@Override
	public float getEnchantPowerBonus(BlockState state, IWorldReader world, BlockPos pos) {
		return Math.min((float)getBookSum(state, world, pos)/3f, 6);
	}
	
	public static BlockState getState(BlockState state, boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6, boolean b7, boolean b8) {
		return state
				.with(BOOK0, b1).with(BOOK1, b2).with(BOOK2, b3).with(BOOK3, b4)
				.with(BOOK4, b5).with(BOOK5, b6).with(BOOK6, b7).with(BOOK7, b8);
	}
	
	public int getBookSum(BlockState state, IWorldReader world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
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
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.isIn(newState.getBlock())) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof IInventory) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}

			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
			ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof BookshelfTileEntity) {
				((BookshelfTileEntity) tileentity).setCustomName(stack.getDisplayName());
			}
		}

	}
	
	@Override
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		return IComparetorOverride.getComparetorOverride(worldIn, pos);
	}
}
