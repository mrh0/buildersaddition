package com.mrh0.buildersaddition.blocks.base;

import com.mrh0.buildersaddition.entity.SeatEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BaseStool extends BaseDerivativeBlock implements ISeat {

	private static AxisAlignedBB shape = new AxisAlignedBB(2D/16D, 0D/16D, 2D/16D, 14D/16D, 9D/16D, 14D/16D);
	
	public BaseStool(String name, Block source) {
		super(name, source);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		return SeatEntity.createSeat(worldIn, pos, player) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.create(shape);
	}
}
