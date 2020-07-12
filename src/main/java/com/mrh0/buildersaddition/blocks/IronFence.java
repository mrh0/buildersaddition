package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.event.BlockRegistry;
import com.mrh0.buildersaddition.event.opts.BlockOptions;

import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;

public class IronFence extends FenceBlock {

	public IronFence() {
		super(Properties.from(Blocks.IRON_BLOCK));
		this.setRegistryName("iron_fence");
		BlockRegistry.instance.register(this, new BlockOptions());
	}

}
