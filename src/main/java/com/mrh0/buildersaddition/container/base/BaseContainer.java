package com.mrh0.buildersaddition.container.base;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

public class BaseContainer extends Container {

	protected BaseContainer(ContainerType<?> type, int id) {
		super(type, id);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return !playerIn.isSpectator();
	}
}
