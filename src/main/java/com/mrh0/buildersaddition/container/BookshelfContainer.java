package com.mrh0.buildersaddition.container;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.container.base.BaseContainer;
import com.mrh0.buildersaddition.inventory.slot.BookSlot;
import com.mrh0.buildersaddition.tileentity.BookshelfTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class BookshelfContainer extends BaseContainer {

	//public BookshelfTileEntity te;
	private ItemStackHandler handler;
	public static final int SLOTS = 18;
	
	protected BookshelfContainer(MenuType<?> type, int id) {
		super(type, id);
	}

	public static BookshelfContainer create(int windowId, Inventory playerInventory, BlockPos pos, ItemStackHandler inv) {
		return new BookshelfContainer(playerInventory, pos, windowId, inv);
	}

	public static BookshelfContainer create(int windowId, Inventory playerInventory, FriendlyByteBuf buf) {
		BlockPos pos = buf.readBlockPos();
		return new BookshelfContainer(playerInventory, pos, windowId, ((BookshelfTileEntity) Minecraft.getInstance().level.getBlockEntity(pos)).handler);
	}

	public BookshelfContainer(Inventory playerInv, BlockPos pos, int window, ItemStackHandler inv){
		super(Index.BOOKSHELF_CONTAINER, window);
		//this.te = (BookshelfTileEntity) te.getWorld().getTileEntity(pos);
		this.handler = inv;
		
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
	public ItemStack quickMoveStack(Player player, int fromSlot) {
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = (Slot) this.slots.get(fromSlot);

		if (slot != null && slot.hasItem()) {
			ItemStack current = slot.getItem();
			previous = current.copy();

			if (fromSlot < this.handler.getSlots()) {
				// From the container inventory to the player's inventory
				if (!this.moveItemStackTo(current, this.handler.getSlots(), this.handler.getSlots() + 36, true))
					return ItemStack.EMPTY;
			} else {
				// From the player's inventory to the container inventory
				if (!this.moveItemStackTo(current, 0, 18, false))
					return ItemStack.EMPTY;
			}

			if (current.getCount() == 0)
				slot.set(ItemStack.EMPTY);
			else
				slot.setChanged();

			if (current.getCount() == previous.getCount())
				return ItemStack.EMPTY;
			slot.onTake(player, current);
		}
		return previous;
	}
}
