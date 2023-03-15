package com.mrh0.buildersaddition.blocks.base;

import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.event.BlockRegistry;
import com.mrh0.buildersaddition.event.opts.BlockOptions;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BaseBlock extends Block {

	public BaseBlock(String name, Properties block) {
		super(block);
		//Index.BLOCKS.register(name, () -> this);
		//Index.ITEMS.register(name, () -> new BlockItem(this, new Item.Properties().tab(ModGroup.MAIN)));
		//this.setRegistryName(name);
		//BlockRegistry.instance.register(this, new BlockOptions());
	}
	
	public BaseBlock(String name, Properties block, BlockOptions opts) {
		super(block);
		//Index.BLOCKS.register(name, () -> this);
		//Index.ITEMS.register(name, () -> new BlockItem(this, new Item.Properties().tab(ModGroup.MAIN)));
		//this.setRegistryName(name);
		//BlockRegistry.instance.register(this, opts);
	}
}