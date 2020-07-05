package com.mrh0.buildersaddition.event;

import com.mrh0.buildersaddition.event.opts.ContainerOptions;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.IForgeRegistry;

public class ContainerRegistry extends GenericRegistry<ContainerType<?>, ContainerOptions>{
	
	public static ContainerRegistry instance;
	
	public ContainerRegistry() {
		super();
		instance = this;
	}

	protected void init(IForgeRegistry<ContainerType<?>> reg, ContainerType<?> obj) {
		reg.register(obj);
	}
}
