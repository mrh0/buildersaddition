package com.mrh0.buildersaddition.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public interface IConnects {
	public boolean connect(BlockState state, Block source);
}
