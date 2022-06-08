package com.mrh0.buildersaddition.container;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.container.base.BaseContainer;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ArcadeContainer extends BaseContainer {
	
	public final BlockPos pos;
	
	protected ArcadeContainer(int id, BlockPos pos) {
		super(Index.ARCADE_CONTAINER, id);
		this.pos = pos;
	}
	
	public static ArcadeContainer create(int windowId, Inventory playerInventory, BlockPos pos) {
		return new ArcadeContainer(windowId, pos);
	}

	public static ArcadeContainer create(int windowId, Inventory playerInventory, FriendlyByteBuf buf) {
		return new ArcadeContainer(windowId, buf.readBlockPos());
	}
}
