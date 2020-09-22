package com.mrh0.buildersaddition.container;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.container.base.BaseContainer;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class ArcadeContainer  extends BaseContainer {
	
	public final BlockPos pos;
	
	protected ArcadeContainer(int id, BlockPos pos) {
		super(Index.ARCADE_CONTAINER, id);
		this.pos = pos;
	}
	
	public static ArcadeContainer create(int windowId, PlayerInventory playerInventory, BlockPos pos) {
		return new ArcadeContainer(windowId, pos);
	}

	public static ArcadeContainer create(int windowId, PlayerInventory playerInventory, PacketBuffer buf) {
		return new ArcadeContainer(windowId, buf.readBlockPos());
	}
}
