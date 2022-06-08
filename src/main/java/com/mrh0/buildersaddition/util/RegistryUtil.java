package com.mrh0.buildersaddition.util;

import com.mrh0.buildersaddition.BuildersAddition;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.ForgeRegistries;


public class RegistryUtil {
	public static PaintingVariant createPainting(String name, int w, int h) {
		PaintingVariant p = new PaintingVariant(16*w, 16*h);
		//p.setRegistryName(BuildersAddition.MODID, name);
		ForgeRegistries.PAINTING_VARIANTS.register(name, p);
		return p;
	}
}
