package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class SlabTorch extends BaseDerivativeBlock {
	
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(6.0D, -8.0D, 6.0D, 10.0D, 2.0D, 10.0D);
	
	public SlabTorch() {
		super("slab_torch", Blocks.TORCH);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos,
			PlayerEntity player) {
		return new ItemStack(Items.TORCH);
	}
}
