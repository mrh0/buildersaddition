package com.mrh0.buildersaddition.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.KnowledgeBookItem;

public class Util {
	public static boolean isBook(ItemStack stack) {
		Item i = stack.getItem();
		String n = i.getRegistryName().getPath();
		
		return (i instanceof EnchantedBookItem) || (i instanceof KnowledgeBookItem) || i == Items.BOOK || i == Items.WRITABLE_BOOK || i == Items.WRITTEN_BOOK 
				|| n.endsWith("book") || n.endsWith("manual") || n.endsWith("journal") || n.endsWith("tome")  || n.startsWith("tome") || n.endsWith("lexicon")  || n.endsWith("codex")
				|| n.endsWith("guide") || n.startsWith("guide") || n.startsWith("handbook") || n.endsWith("chronicle") || n.endsWith("companion") || n.endsWith("binder");
	}
	
	public static BlockState crackedState(BlockState cur) {
		if(cur.isIn(Blocks.STONE_BRICKS))
			return Blocks.CRACKED_STONE_BRICKS.getDefaultState();
		else if(cur.isIn(Blocks.NETHER_BRICKS))
			return Blocks.CRACKED_NETHER_BRICKS.getDefaultState();
		else if(cur.isIn(Blocks.POLISHED_BLACKSTONE_BRICKS))
			return Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.getDefaultState();
		else
			return null;
	}
}
