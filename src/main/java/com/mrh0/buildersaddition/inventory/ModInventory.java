package com.mrh0.buildersaddition.inventory;

import net.minecraftforge.items.ItemStackHandler;

public class ModInventory extends ItemStackHandler{
	private IChanged change = null;
	public ModInventory(int size, IChanged change) {
		super(size);
		this.change = change;
	}
	
	public ModInventory(int size) {
		super(size);
	}
	
	@Override
	protected void onContentsChanged(int slot) {
		super.onContentsChanged(slot);
		if(change != null)
			change.changed(slot);
	}
	
	public interface IChanged {
		public void changed(int slot);
	}
}
