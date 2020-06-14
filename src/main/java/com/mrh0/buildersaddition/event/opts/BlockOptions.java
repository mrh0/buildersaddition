package com.mrh0.buildersaddition.event.opts;

public class BlockOptions extends RegOptions<BlockOptions>{
	public boolean makeItem = true;
	public ItemOptions itemOpts = new ItemOptions();
	public BlockOptions hasItem(boolean b) {
		makeItem = b;
		return this;
	}
	
	public BlockOptions setItemOptions(ItemOptions itemOpts) {
		this.itemOpts = itemOpts;
		return this;
	}
}
