package com.mrh0.buildersaddition.inventory.slot;

import com.mrh0.buildersaddition.util.Util;

import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.KnowledgeBookItem;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BookSlot extends SlotItemHandler {

	public BookSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return Util.isBook(stack) ? 64 : 0;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 64;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return Util.isBook(stack);
	}
}
