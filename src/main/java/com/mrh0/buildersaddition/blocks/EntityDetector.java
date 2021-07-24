package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseBlock;
import com.mrh0.buildersaddition.tileentity.EntityDetectorTileEntity;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class EntityDetector extends BaseBlock {
	public static final IntegerProperty POWER = IntegerProperty.create("power", 0, 15);
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	
	public EntityDetector() {
		super("entity_detector", Properties.from(Blocks.OBSERVER));
		this.setDefaultState(this.defaultBlockState().with(FACING, Direction.NORTH).with(POWER, 0));
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
