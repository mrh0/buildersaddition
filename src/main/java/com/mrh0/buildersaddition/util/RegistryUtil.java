package com.mrh0.buildersaddition.util;

import com.mrh0.buildersaddition.BuildersAddition;

import net.minecraft.entity.item.PaintingType;

public class RegistryUtil {
	public static PaintingType createPainting(String name, int w, int h) {
		PaintingType p = new PaintingType(16*w, 16*h);
		p.setRegistryName(BuildersAddition.MODID, name);
		return p;
	}
}
