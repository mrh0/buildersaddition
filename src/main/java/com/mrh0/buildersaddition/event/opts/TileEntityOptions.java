package com.mrh0.buildersaddition.event.opts;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;

public class TileEntityOptions extends RegOptions<TileEntityOptions> {
	public Set<Block> isUsedByBlocks;
	public String regName;
	
	public TileEntityOptions(String name, Block...set) {
		isUsedByBlocks = new HashSet<Block>();
		for(Block b : set) {
			isUsedByBlocks.add(b);
		}
		regName = name;
	}
}
