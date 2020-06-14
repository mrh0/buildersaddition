package com.mrh0.buildersaddition.itemgroup;

import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.Index;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModGroup extends ItemGroup{
	public static ModGroup MAIN;;
	
	public ModGroup(String name) {
		super(BuildersAddition.MODID+":"+name);
		MAIN = this;
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(Index.CUT_QUARTZ_PILLAR);
	}
}
