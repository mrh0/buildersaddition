package com.mrh0.buildersaddition.event.opts;

import net.minecraft.world.item.CreativeModeTab;

public class ItemOptions extends RegOptions<ItemOptions> {
	public boolean addTooltip = false;
	public boolean hidden = false;
	public ItemOptions showTooltip() {
		addTooltip = true;
		return this;
	}
	public ItemOptions hide(boolean b) {
		hidden = b;
		return this;
	}
	
	public ItemOptions hide() {
		hide(true);
		return this;
	}
}
