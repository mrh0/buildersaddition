package com.mrh0.buildersaddition.blocks;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mrh0.buildersaddition.Index;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public class CopperVerticalSlab extends VerticalSlab implements WeatheringCopper {
	private final WeatheringCopper.WeatherState weatherState;

	static Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() -> {
		return ImmutableBiMap.<Block, Block>builder()
				.put(Index.CUT_COPPER_VERTICAL_SLAB.get(), Index.EXPOSED_CUT_COPPER_VERTICAL_SLAB.get())
				.put(Index.EXPOSED_CUT_COPPER_VERTICAL_SLAB.get(), Index.WEATHERED_CUT_COPPER_VERTICAL_SLAB.get())
				.put(Index.WEATHERED_CUT_COPPER_VERTICAL_SLAB.get(), Index.OXIDIZED_CUT_COPPER_VERTICAL_SLAB.get()).build();
	});

	static Optional<Block> getNext(Block block) {
		return Optional.ofNullable(NEXT_BY_BLOCK.get().get(block));
	}

	public CopperVerticalSlab(String name, Block source, WeatheringCopper.WeatherState state) {
		super(name, source);
		this.weatherState = state;
	}

	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		this.randomTick(state, level, pos, random);
		//this.onRandomTick(state, level, pos, random);
	}

	public boolean isRandomlyTicking(BlockState state) {
		return CopperVerticalSlab.getNext(state.getBlock()).isPresent();
	}

	public Optional<BlockState> getNext(BlockState state) {
		return getNext(state.getBlock()).map((m) -> {
			return m.withPropertiesOf(state);
		});
	}

	public WeatheringCopper.WeatherState getAge() {
		return this.weatherState;
	}
}
