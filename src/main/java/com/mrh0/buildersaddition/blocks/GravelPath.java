package com.mrh0.buildersaddition.blocks;

import java.util.Random;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GravelPath extends BaseDerivativeBlock {

	private final static VoxelShape SHAPE = Block.box(0, 0, 0, 16, 15, 16);

	public GravelPath() {
		super("gravel_path", Blocks.GRAVEL);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	public boolean isTransparent(BlockState state) {
		return true;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return !isValidPosition(this.defaultBlockState(), context.getLevel(), context.getClickedPos())
				? Block.pushEntitiesUp(this.defaultBlockState(), Blocks.GRAVEL.defaultBlockState(),
						context.getLevel(), context.getClickedPos())
				: super.getStateForPlacement(context);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState otherState, LevelAccessor world,
			BlockPos pos, BlockPos otherPos) {
		if (facing == Direction.UP && !isValidPosition(state, world, pos)) {
			world.m_186460_(pos, this, 1); //Other?
		}
		return super.updateShape(state, facing, otherState, world, pos, otherPos);
	}

	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
		worldIn.setBlockAndUpdate(pos, pushEntitiesUp(state, Blocks.GRAVEL.defaultBlockState(), worldIn, pos));
	}

	public boolean isValidPosition(BlockState state, LevelAccessor worldIn, BlockPos pos) {
		BlockState blockstate = worldIn.getBlockState(pos.above());
		return !blockstate.getMaterial().isSolid() || blockstate.getBlock() instanceof FenceGateBlock;
	}
}
