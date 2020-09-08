package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.Bookshelf;
import com.mrh0.buildersaddition.container.BookshelfContainer;
import com.mrh0.buildersaddition.inventory.ModInventory;
import com.mrh0.buildersaddition.util.IComparetorOverride;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BookshelfTileEntity extends LockableLootTileEntity implements INamedContainerProvider, IComparetorOverride {

	public ModInventory handler;
	
	public BookshelfTileEntity() {
		super(Index.BOOKSHELF_TILE_ENTITY_TYPE);
		handler = new ModInventory(18, this::changed);
	}
	
	@Override
	public Container createMenu(int windowId, PlayerInventory inv, PlayerEntity player) {
		return BookshelfContainer.create(windowId, inv, this.getPos(), this.handler);
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		this.handler.deserializeNBT(nbt.getCompound("ItemStackHandler"));
		super.read(state, nbt);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		nbt.put("ItemStackHandler", this.handler.serializeNBT());
		return super.write(nbt);
	}
	
	public void changed(int slot) {
		BlockState bs = world.getBlockState(pos);
		if(bs.getBlock() instanceof Bookshelf)
			world.setBlockState(pos, Bookshelf.getState(bs, hasIn(0), hasIn(1), hasIn(2), hasIn(3), hasIn(4), hasIn(5), hasIn(6), hasIn(7)));
	}
	
	private boolean hasIn(int i) {
		if(i == 0)
			return handler.getStackInSlot(0).getCount() > 0 || handler.getStackInSlot(1).getCount() > 0 || handler.getStackInSlot(2).getCount() > 0;
		if(i == 7)
			return handler.getStackInSlot(15).getCount() > 0 || handler.getStackInSlot(16).getCount() > 0 || handler.getStackInSlot(17).getCount() > 0;
		return handler.getStackInSlot(i*2+1).getCount() > 0 || handler.getStackInSlot(i*2+2).getCount() > 0;
	}

	@Override
	public int getSizeInventory() {
		return handler.getSlots();
	}

	@Override
	public boolean isEmpty() {
		for(int i = 0; i < handler.getSlots(); i++) {
			if(handler.getStackInSlot(i).getCount() > 0)
				return false;
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return handler.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		//int n = Math.min(count, handler.getStackInSlot(index).getCount());
		//handler.setStackInSlot(index, new ItemStack(handler.get));
		return handler.extractItem(index, count, false);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return handler.extractItem(index, handler.getStackInSlot(index).getCount(), false);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		handler.setStackInSlot(index, stack);
		this.markDirty();
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return !player.isSpectator();
	}

	@Override
	public void clear() {
		for(int i = 0; i < handler.getSlots(); i++) {
			handler.setStackInSlot(i, ItemStack.EMPTY);
		}
		this.markDirty();
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.buildersaddition.bookshelf");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return createMenu(id, player, player.player);
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		NonNullList<ItemStack> items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		for(int i = 0; i < getSizeInventory(); i++) {
			items.set(i, handler.getStackInSlot(i));
		}
		return items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		for(int i = 0; i < getSizeInventory(); i++) {
			handler.setStackInSlot(i, itemsIn.get(i));
		}
		this.markDirty();
	}
	
	@Override
	public int getComparetorOverride() {
		return Container.calcRedstone(this);
	}
}
