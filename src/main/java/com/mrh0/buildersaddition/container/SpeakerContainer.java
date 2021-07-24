package com.mrh0.buildersaddition.container;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.container.base.BaseContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class SpeakerContainer extends BaseContainer {
	
	public final BlockPos pos;
	
	protected SpeakerContainer(int id, BlockPos pos) {
		super(Index.SPEAKER_CONTAINER, id);
		this.pos = pos;
	}
	
	public static SpeakerContainer create(int windowId, Inventory playerInventory, BlockPos pos) {
		return new SpeakerContainer(windowId, pos);
	}

	public static SpeakerContainer create(int windowId, Inventory playerInventory, FriendlyByteBuf buf) {
		return new SpeakerContainer(windowId, buf.readBlockPos());
	}
}
