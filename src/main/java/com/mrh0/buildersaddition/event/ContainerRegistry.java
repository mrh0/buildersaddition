package com.mrh0.buildersaddition.event;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.event.opts.ContainerOptions;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.IForgeRegistry;

public class ContainerRegistry extends GenericRegistry<MenuType<?>, ContainerOptions>{
	
	public static ContainerRegistry instance;
	
	public ContainerRegistry() {
		super();
		instance = this;
	}

	protected void init(IForgeRegistry<MenuType<?>> reg, MenuType<?> obj) {
		//reg.register(obj);
	}
}
