package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.event.BlockRegistry;
import com.mrh0.buildersaddition.event.opts.BlockOptions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.util.Direction;

public class IronFence extends FenceBlock {

	public IronFence(String name) {
		super(Properties.from(Blocks.IRON_BLOCK));
		this.setRegistryName(name);
		BlockRegistry.instance.register(this, new BlockOptions());
	}
	
	@Override
	public boolean canConnect(BlockState state, boolean solid, Direction direction) {
		Block block = state.getBlock();
		boolean flag = this.isIronFence(block);
		//boolean flag1 = block instanceof FenceGateBlock && FenceGateBlock.isParallel(p_220111_1_, p_220111_3_);
		return !cannotAttach(block) && solid || flag;// || flag1;
	}
	
	private boolean isIronFence(Block block) {
		return block instanceof IronFence;
	}
}
