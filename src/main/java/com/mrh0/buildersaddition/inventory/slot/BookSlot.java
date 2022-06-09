package com.mrh0.buildersaddition.inventory.slot;

import com.mrh0.buildersaddition.util.Util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BookSlot extends SlotItemHandler {

	public BookSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		return Util.isBook(stack);
	}
}
