package com.mrh0.buildersaddition.event;

import java.util.ArrayList;
import com.mrh0.buildersaddition.BuildersAddition;
import com.mrh0.buildersaddition.event.opts.TileEntityOptions;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.IForgeRegistry;

public class TileEntityRegistry {
	
	public static TileEntityRegistry instance;
	
	protected ArrayList<BlockEntityType<? extends BlockEntity>> objs;
	
	public TileEntityRegistry() {
		objs = new ArrayList<BlockEntityType<? extends BlockEntity>>();
		instance = this;
	}
	
	public <T extends BlockEntity> BlockEntityType<T> register(BlockEntityType.BlockEntitySupplier<T> obj, TileEntityOptions opts) {
		BlockEntityType<T> type = new BlockEntityType<T>(obj, opts.isUsedByBlocks, null);
		type.setRegistryName(BuildersAddition.MODID+":"+opts.regName);
		if(opts.isEnabled) {
			objs.add(type);
		}
		return type;
	}
	
	public void initAll(IForgeRegistry<BlockEntityType<?>> reg) {
		for(BlockEntityType<? extends BlockEntity> supp : objs) {
			init(reg, supp);
		}
	}

	protected void init(IForgeRegistry<BlockEntityType<?>> reg, BlockEntityType<?> type) {
		reg.register(type);
	}
}
