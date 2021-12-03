package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.container.ShelfContainer;
import com.mrh0.buildersaddition.inventory.ModInventory;
import com.mrh0.buildersaddition.util.IComparetorOverride;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;


public class ShelfTileEntity extends RandomizableContainerBlockEntity implements MenuProvider, IComparetorOverride {

	public ModInventory handler;
	
	public ShelfTileEntity(BlockPos pos, BlockState state) {
		super(Index.SHELF_TILE_ENTITY_TYPE, pos, state);
		handler = new ModInventory(6, this::changed);
	}
	
	@Override
	protected AbstractContainerMenu createMenu(int windowId, Inventory inv) {
		return ShelfContainer.create(windowId, inv, this.getBlockPos(), this.handler);
	}
	
	@Override
	public void load(CompoundTag nbt) {
		this.handler.deserializeNBT(nbt.getCompound("ItemStackHandler"));
		super.load(nbt);
	}
	
	@Override
	public CompoundTag save(CompoundTag nbt) {
		return super.save(nbt);
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt) {
		nbt.put("ItemStackHandler", this.handler.serializeNBT());
		super.saveAdditional(nbt);
	}
	
	public void changed(int slot) {
		this.setChanged();
	}
	
	

	/*@Override
	public int getSizeInventory() {
		return handler.getSlots();
	}*/

	@Override
	public boolean isEmpty() {
		for(int i = 0; i < handler.getSlots(); i++) {
			if(handler.getStackInSlot(i).getCount() > 0)
				return false;
		}
		return true;
	}

	/*@Override
	public ItemStack getStackInSlot(int index) {
		return handler.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return handler.extractItem(index, count, false);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return handler.extractItem(index, handler.getStackInSlot(index).getCount(), false);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		handler.setStackInSlot(index, stack);
		this.setChanged();
	}*/
	
	@Override
	public boolean canOpen(Player player) {
		return !player.isSpectator();
	}
	@Override
	public void clearContent() {
		for(int i = 0; i < handler.getSlots(); i++) {
			handler.setStackInSlot(i, ItemStack.EMPTY);
		}
		this.setChanged();
	}

	@Override
	protected Component getDefaultName() {
		return new TranslatableComponent("container.buildersaddition.shelf");
	}

	/*@Override
	protected Container createMenu(int id, Inventory player) {
		return createMenu(id, player, player.player);
	}*/

	@Override
	protected NonNullList<ItemStack> getItems() {
		NonNullList<ItemStack> items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
		for(int i = 0; i < getContainerSize(); i++) {
			items.set(i, handler.getStackInSlot(i));
		}
		return items;
	}

	/*@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		for(int i = 0; i < getSizeInventory(); i++) {
			handler.setStackInSlot(i, itemsIn.get(i));
		}
		this.setChanged();
	}
	*/
	
	/*@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		CompoundTag update = getUpdateTag();
        int data = 0;
        return ClientboundBlockEntityDataPacket.m_195640_(this);
        //return new ClientboundBlockEntityDataPacket(this.getBlockPos(), Index.SHELF_TILE_ENTITY_TYPE, update);
	}*/
	
	
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		CompoundTag update = pkt.getTag();
        handleUpdateTag(update);
	}
	
	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag nbt = new CompoundTag();
		save(nbt);
        return nbt;
	}
	
	@Override
	public void handleUpdateTag(CompoundTag nbt) {
		load(nbt);
	}
	
	@Override
	public int getComparetorOverride() {
		return AbstractContainerMenu.getRedstoneSignalFromContainer(this);
	}

	@Override
	public int getContainerSize() {
		return 0;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> p_59625_) {
	}
}
