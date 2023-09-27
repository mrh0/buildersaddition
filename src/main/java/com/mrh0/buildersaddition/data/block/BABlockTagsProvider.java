package com.mrh0.buildersaddition.data.block;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mrh0.buildersaddition.Index;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BABlockTagsProvider extends TagsProvider<Block> {
	
	public static List<ResourceKey<Block>> woodenBlocks = new ArrayList<>();
	public static List<ResourceKey<Block>> stoneBlocks = new ArrayList<>();
	public static List<ResourceKey<Block>> ironBlocks = new ArrayList<>();
	public static List<ResourceKey<Block>> leavesBlocks = new ArrayList<>();
	public static List<ResourceKey<Block>> earthBlocks = new ArrayList<>();
	public static List<ResourceKey<Block>> circuitBlocks = new ArrayList<>();
	
	public BABlockTagsProvider(DataGenerator gen, String modId, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper existingFileHelper) {
		super(gen.getPackOutput(), ForgeRegistries.Keys.BLOCKS, provider, modId,  existingFileHelper);
	}

	protected Path getPath(ResourceLocation rl) {
		return this.pathProvider.json(rl); //Path.of("data", rl.getNamespace(), "/tags/blocks/" + rl.getPath() + ".json");
				//.resolve("data/" + rl.getNamespace() + "/tags/blocks/" + rl.getPath() + ".json");
	}

	public String getName() {
		return "BA Block Tags";
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(BlockTags.MINEABLE_WITH_AXE).add(woodenBlocks.toArray(new ResourceKey[0]));
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(stoneBlocks.toArray(new ResourceKey[0]));
		this.tag(BlockTags.NEEDS_STONE_TOOL).add(ironBlocks.toArray(new ResourceKey[0]));
		this.tag(BlockTags.LEAVES).add(leavesBlocks.toArray(new ResourceKey[0]));
		this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(earthBlocks.toArray(new ResourceKey[0]));
	}
}