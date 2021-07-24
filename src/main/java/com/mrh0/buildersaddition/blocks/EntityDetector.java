package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseBlock;
import com.mrh0.buildersaddition.tileentity.EntityDetectorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import IntegerProperty;

public class EntityDetector extends BaseBlock {
	public static final IntegerProperty POWER = IntegerProperty.create("power", 0, 15);
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	
	public EntityDetector() {
		super("entity_detector", Properties.from(Blocks.OBSERVER));
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(POWER, 0));
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		return;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new EntityDetectorTileEntity();
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(POWER);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		return this.getDefaultState().with(FACING, c.getNearestLookingDirection().getOpposite().getOpposite());
	}
	
	@Override
	public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, Direction side) {
		return side == state.get(FACING);
	}
	
	@Override
	public int getWeakPower(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
		if(side == blockAccess.getBlockState(pos).get(FACING)){
			return state.get(POWER);
		}
		return 0;
	}
	
	@Override
	public int getStrongPower(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
		if(side == blockAccess.getBlockState(pos).get(FACING))
			return state.get(POWER);
		return 0;
	}
	
	@Override
	public boolean canProvidePower(BlockState state) {
		return true;
	}
}
