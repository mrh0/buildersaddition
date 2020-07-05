package com.mrh0.buildersaddition.container;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.container.base.BaseContainer;
import com.mrh0.buildersaddition.inventory.slot.BookSlot;
import com.mrh0.buildersaddition.tileentity.BookshelfTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;

public class BookshelfContainer extends BaseContainer {

	public BookshelfTileEntity te;
	private ItemStackHandler handler;
	public static final int SLOTS = 18;
	
	protected BookshelfContainer(ContainerType<?> type, int id) {
		super(type, id);
	}

	public static BookshelfContainer create(int windowId, PlayerInventory playerInventory, BlockPos pos, ItemStackHandler inv) {
		return new BookshelfContainer(playerInventory, pos, windowId, inv);
	}

	public static BookshelfContainer create(int windowId, PlayerInventory playerInventory, PacketBuffer buf) {
		return new BookshelfContainer(playerInventory, buf.readBlockPos(), windowId, null);
	}

	public BookshelfContainer(PlayerInventory playerInv, BlockPos pos, int window, ItemStackHandler inv){
		super(Index.BOOKSHELF_CONTAINER, window);
		this.te = (BookshelfTileEntity) Minecraft.getInstance().world.getTileEntity(pos);
		this.handler = inv;
		if(this.handler == null)
			this.handler = te.handler;
		
		int xPos = 8;
		int yPos = 18;
		
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlot(new BookSlot(handler, x + y * 9, xPos + x * 18, yPos + y * 18));
			}
		}
		
		xPos = 8;
		yPos = 66;
				
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlot(new Slot(playerInv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}
				
		for (int x = 0; x < 9; x++) {
			this.addSlot(new Slot(playerInv, x, xPos + x * 18, yPos + 58));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int fromSlot) {
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < this.handler.getSlots()) {
				// From the container inventory to the player's inventory
				if (!this.mergeItemStack(current, this.handler.getSlots(), this.handler.getSlots() + 36, true))
					return ItemStack.EMPTY;
			} else {
				// From the player's inventory to the container inventory
				if (!this.mergeItemStack(current, 0, 4, false))
					return ItemStack.EMPTY;
			}

			if (current.getCount() == 0)
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();

			if (current.getCount() == previous.getCount())
				return ItemStack.EMPTY;
			slot.onTake(playerIn, current);
		}
		return previous;
	}
}
