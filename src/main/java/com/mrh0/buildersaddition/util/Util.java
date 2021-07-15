package com.mrh0.buildersaddition.util;

import com.mrh0.buildersaddition.config.Config;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.KnowledgeBookItem;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class Util {
	private static final ITag<Item> FORGE_BOOKS_TAG = ItemTags.makeWrapperTag(new ResourceLocation("forge", "books").toString());

	public static boolean isBook(ItemStack stack) {
		Item i = stack.getItem();
		String n = i.getRegistryName().getPath();
		
		return (i instanceof EnchantedBookItem) || (i instanceof KnowledgeBookItem) || i == Items.BOOK || i == Items.WRITABLE_BOOK || i == Items.WRITTEN_BOOK 
				|| n.endsWith("book") || n.endsWith("manual") || n.endsWith("journal") || n.endsWith("tome")  || n.startsWith("tome") || n.endsWith("lexicon")  || n.endsWith("codex")
				|| n.endsWith("guide") || n.startsWith("guide") || n.startsWith("handbook") || n.endsWith("chronicle") || n.endsWith("companion") || n.endsWith("binder") || n.endsWith("nomicon")
				|| n.endsWith("dictionary") || n.startsWith("dictionary") || n.endsWith("materials_and_you") || n.endsWith("binder") || n.startsWith("binder")
				|| i.isIn(FORGE_BOOKS_TAG);
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
	
	public static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
		return false;
	}
	
	public static boolean accessCheck(World world, BlockPos pos, Direction facing) {
		if(!Config.INVENTORY_ACCESS_BLOCK_CHECK.get())
			return true;
		BlockState front = world.getBlockState(pos.offset(facing));
		return !(front.isSolidSide(world, pos.offset(facing), facing.getOpposite()) || front.isSolidSide(world, pos.offset(facing), Direction.UP));
	}
}
