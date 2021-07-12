package com.mrh0.buildersaddition.blocks.base;
/*
import com.mrh0.buildersaddition.entity.SeatEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BaseStool extends BaseDerivativeBlock implements ISeat {

	//private static AxisAlignedBB shape = new AxisAlignedBB(2D/16D, 0D/16D, 2D/16D, 14D/16D, 9D/16D, 14D/16D);
	private static VoxelShape SHAPE = Block.makeCuboidShape(3d, 0d, 3d, 13d, 9d, 13d);
	private static VoxelShape SHAPE_TWO = Block.makeCuboidShape(2d, 6d, 2d, 14d, 8d, 14d);
	
	public BaseStool(String name, Block source) {
		super(name, source);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		return SeatEntity.createSeat(worldIn, pos, player, SoundEvents.BLOCK_WOOL_HIT);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.or(SHAPE, SHAPE_TWO);
	}
}
*/