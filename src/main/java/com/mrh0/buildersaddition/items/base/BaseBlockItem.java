package com.mrh0.buildersaddition.items.base;

import com.mrh0.buildersaddition.event.opts.ItemOptions;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;

public class BaseBlockItem extends BlockItem {

	public BaseBlockItem(Block block, ItemOptions opts) {
		super(block,  new Properties().tab(opts.group));
		this.setRegistryName(block.getRegistryName());
	}
	
	public BaseBlockItem(Block block, CreativeModeTab group) {
		super(block,  new Properties().tab(group));
		this.setRegistryName(block.getRegistryName());
	}
	
}
