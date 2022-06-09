package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.event.BlockRegistry;
import com.mrh0.buildersaddition.event.opts.BlockOptions;
import com.mrh0.buildersaddition.itemgroup.ModGroup;

import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class IronFence extends FenceBlock {

	public IronFence(String name) {
		super(Properties.copy(Blocks.IRON_BLOCK));

		BaseDerivativeBlock.blockTagSort(this, Blocks.IRON_BLOCK);
	}
	
	
	
	@Override
	public boolean connectsTo(BlockState state, boolean solid, Direction direction) {
		boolean flag = this.isIronFence(state.getBlock());
		//boolean flag1 = block instanceof FenceGateBlock && FenceGateBlock.isParallel(p_220111_1_, p_220111_3_);
		return !isExceptionForConnection(state) && solid || flag;// || flag1;
	}
	
	private boolean isIronFence(Block block) {
		return block instanceof IronFence;
	}
}