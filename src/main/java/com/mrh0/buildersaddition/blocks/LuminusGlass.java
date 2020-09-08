package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.event.BlockRegistry;
import com.mrh0.buildersaddition.event.opts.BlockOptions;
import net.minecraft.block.StainedGlassBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;

public class LuminusGlass extends StainedGlassBlock {

	public LuminusGlass(String name, DyeColor color) {
		super(color, Properties.create(Material.GLASS)
				.setSuffocates((s,w,p) -> false).setLightLevel((state) -> 15));
		this.setRegistryName("luminus_glass" + (name.length() > 0 ? "_" + color : ""));
		BlockRegistry.instance.register(this, new BlockOptions());
	}
}
