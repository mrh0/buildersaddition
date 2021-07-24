package com.mrh0.buildersaddition.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IConnects {
	public boolean connect(BlockState state, Block source);
}
