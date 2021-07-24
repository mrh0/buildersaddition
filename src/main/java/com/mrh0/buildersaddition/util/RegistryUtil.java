package com.mrh0.buildersaddition.util;

import com.mrh0.buildersaddition.BuildersAddition;
import net.minecraft.world.entity.decoration.Motive;


public class RegistryUtil {
	public static Motive createPainting(String name, int w, int h) {
		Motive p = new Motive(16*w, 16*h);
		p.setRegistryName(BuildersAddition.MODID, name);
		return p;
	}
}
