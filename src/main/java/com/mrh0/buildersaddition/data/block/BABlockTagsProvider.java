package com.mrh0.buildersaddition.data.block;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BABlockTagsProvider extends TagsProvider<Block> {
	
	public static List<Block> woodenBlocks = new ArrayList<>();
	public static List<Block> stoneBlocks = new ArrayList<>();
	public static List<Block> ironBlocks = new ArrayList<>();
	public static List<Block> leavesBlocks = new ArrayList<>();
	public static List<Block> earthBlocks = new ArrayList<>();
	public static List<Block> circuitBlocks = new ArrayList<>();
	
	public BABlockTagsProvider(DataGenerator gen, String modId, ExistingFileHelper existingFileHelper) {
		super(gen, Registry.BLOCK, modId, existingFileHelper);
	}

	protected Path getPath(ResourceLocation rl) {
		return this.generator.getOutputFolder()
				.resolve("data/" + rl.getNamespace() + "/tags/blocks/" + rl.getPath() + ".json");
	}

	public String getName() {
		return "BA Block Tags";
	}

	@Override
	protected void addTags() {
		this.tag(BlockTags.MINEABLE_WITH_AXE).add(woodenBlocks.toArray(new Block[0]));
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(stoneBlocks.toArray(new Block[0]));
		this.tag(BlockTags.NEEDS_STONE_TOOL).add(ironBlocks.toArray(new Block[0]));
		this.tag(BlockTags.LEAVES).add(leavesBlocks.toArray(new Block[0]));
		this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(earthBlocks.toArray(new Block[0]));
	}
}