package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.event.BlockRegistry;
import com.mrh0.buildersaddition.event.opts.BlockOptions;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class IronFence extends FenceBlock {

	public IronFence(String name) {
		super(Properties.copy(Blocks.IRON_BLOCK));
		this.setRegistryName(name);
		BlockRegistry.instance.register(this, new BlockOptions());
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