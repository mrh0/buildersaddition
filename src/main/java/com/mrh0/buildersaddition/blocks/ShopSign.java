package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.state.FullDirectionalState;
import com.mrh0.buildersaddition.tileentity.ShopSignTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ShopSign extends BaseDerivativeBlock {

	public static final EnumProperty<FullDirectionalState> STATE = EnumProperty.<FullDirectionalState>create("state", FullDirectionalState.class);
	
	private static VoxelShape SHAPE_DOWN_X = Block.makeCuboidShape(1d, 0d, 7d, 15d, 14d, 9d);
	private static VoxelShape SHAPE_DOWN_Z = Block.makeCuboidShape(7d, 0d, 1d, 9d, 14d, 15d);
	
	private static VoxelShape SHAPE_UP_X = Block.makeCuboidShape(1d, 2d, 7d, 15d, 16d, 9d);
	private static VoxelShape SHAPE_UP_Z = Block.makeCuboidShape(7d, 2d, 1d, 9d, 16d, 15d);
	
	public ShopSign(String name) {
		super("shop_sign_" + name, Blocks.OAK_WOOD);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ShopSignTileEntity();
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(STATE);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		return getDefaultState().with(STATE, FullDirectionalState.getFor(c.getFace(), c.getPlacementHorizontalFacing()));
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return getShape(state.get(STATE));
	}
	
	public VoxelShape getShape(FullDirectionalState state) {
		switch(state) {
			case Up_X:
				return SHAPE_UP_X;
			case Up_Z:
				return SHAPE_UP_Z;
			case Down_X:
				return SHAPE_DOWN_X;
			case Down_Z:
				return SHAPE_DOWN_Z;
		}
		return SHAPE_UP_Z;
	}
}
