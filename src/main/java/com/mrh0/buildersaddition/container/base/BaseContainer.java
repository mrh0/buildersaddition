package com.mrh0.buildersaddition.container.base;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class BaseContainer extends AbstractContainerMenu {

	protected BaseContainer(MenuType<?> type, int id) {
		super(type, id);
	}

	@Override
	public boolean stillValid(Player player) {
		return !player.isSpectator();
	}
}
