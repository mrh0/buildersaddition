package com.mrh0.buildersaddition.event.opts;

import com.mrh0.buildersaddition.itemgroup.ModGroup;

import net.minecraft.item.ItemGroup;

public class ItemOptions extends RegOptions<ItemOptions> {
	public boolean addTooltip = false;
	public boolean hidden = false;
	public ItemGroup group = ModGroup.MAIN;
	public ItemOptions showTooltip() {
		addTooltip = true;
		return this;
	}
	public ItemOptions hide(boolean b) {
		hidden = b;
		return this;
	}
	
	public ItemOptions group(ItemGroup group) {
		this.group = group;
		return this;
	}
	
	public ItemOptions hide() {
		hide(true);
		return this;
	}
}
