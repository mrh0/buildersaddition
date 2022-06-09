package com.mrh0.buildersaddition.items.base;

import com.mrh0.buildersaddition.event.opts.ItemOptions;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BaseBlockItem extends BlockItem {

	public BaseBlockItem(Block block, ItemOptions opts) {
		super(block,  new Properties().tab(opts.group));
		//ForgeRegistries.ITEMS.register(ForgeRegistries.BLOCKS.getKey(block), this);
		//this.setRegistryName(block.getRegistryName());
	}
	
	public BaseBlockItem(Block block, CreativeModeTab group) {
		super(block,  new Properties().tab(group));
		//ForgeRegistries.ITEMS.register(ForgeRegistries.BLOCKS.getKey(block), this);
		//this.setRegistryName(block.getRegistryName());
	}
}
