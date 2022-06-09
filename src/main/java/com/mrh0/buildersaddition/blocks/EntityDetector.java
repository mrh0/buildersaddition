package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.base.BaseBlock;
import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.tileentity.ArcadeTileEntity;
import com.mrh0.buildersaddition.tileentity.EntityDetectorTileEntity;
import com.mrh0.buildersaddition.util.Util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class EntityDetector extends BaseDerivativeBlock implements EntityBlock {
	public static final IntegerProperty POWER = IntegerProperty.create("power", 0, 15);
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	
	public EntityDetector() {
		super("entity_detector", Blocks.OBSERVER);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWER, 0));
	}

	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return !world.isClientSide() ? Util.createTickerHelper(type, Index.ENTITY_DETECTOR_TILE_ENTITY_TYPE.get(), EntityDetectorTileEntity::tick) : null;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, POWER);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		return this.defaultBlockState().setValue(FACING, c.isSecondaryUseActive() ? c.getNearestLookingDirection().getOpposite() : c.getNearestLookingDirection());
	}
	
	@Override
	public boolean isSignalSource(BlockState p_60571_) {
		return true;
	}
	
	@Override
	public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
		if(side == world.getBlockState(pos).getValue(FACING))
			return state.getValue(POWER);
		return 0;
	}
	
	@Override
	public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
		if(side == world.getBlockState(pos).getValue(FACING)){
			return state.getValue(POWER);
		}
		return 0;
	}
	
	/*@Override
	public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
		return side == state.getValue(FACING);
	}
	
	@Override
	public int getWeakPower(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
		if(side == blockAccess.getBlockState(pos).get(FACING)){
			return state.getValue(POWER);
		}
		return 0;
	}
	
	@Override
	public int getStrongPower(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
		if(side == blockAccess.getBlockState(pos).get(FACING))
			return state.getValue(POWER);
		return 0;
	}
	
	@Override
	public boolean canProvidePower(BlockState state) {
		return true;
	}*/

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new EntityDetectorTileEntity(pos, state);
	}
}
