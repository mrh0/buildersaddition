package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.Bookshelf;
import com.mrh0.buildersaddition.container.BookshelfContainer;
import com.mrh0.buildersaddition.inventory.ModInventory;
import com.mrh0.buildersaddition.util.IComparetorOverride;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BookshelfTileEntity extends RandomizableContainerBlockEntity implements MenuProvider, IComparetorOverride {

	public ModInventory handler;
	
	public BookshelfTileEntity(BlockPos pos, BlockState state) {
		super(Index.BOOKSHELF_TILE_ENTITY_TYPE, pos, state);
		handler = new ModInventory(18, this::changed);
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
	
	@Override
	public void load(CompoundTag nbt) {
		this.handler.deserializeNBT(nbt.getCompound("ItemStackHandler"));
		super.load(nbt);
	}
	
	public void changed(int slot) {
		BlockState bs = level.getBlockState(this.getBlockPos());
		if(bs.getBlock() instanceof Bookshelf)
			level.setBlockAndUpdate(this.getBlockPos(), Bookshelf.getState(bs, hasIn(0), hasIn(1), hasIn(2), hasIn(3), hasIn(4), hasIn(5), hasIn(6), hasIn(7)));
	}
	
	private boolean hasIn(int i) {
		if(i == 0)
			return handler.getStackInSlot(0).getCount() > 0 || handler.getStackInSlot(1).getCount() > 0 || handler.getStackInSlot(2).getCount() > 0;
		if(i == 7)
			return handler.getStackInSlot(15).getCount() > 0 || handler.getStackInSlot(16).getCount() > 0 || handler.getStackInSlot(17).getCount() > 0;
		return handler.getStackInSlot(i*2+1).getCount() > 0 || handler.getStackInSlot(i*2+2).getCount() > 0;
	}

	@Override
	public int getContainerSize() {
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

	
	
	/*@Override
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
	}*/

	/*@Override
	public boolean isUsableByPlayer(Player player) {
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
	}*/


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

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		for(int i = 0; i < getContainerSize(); i++) {
			handler.setStackInSlot(i, itemsIn.get(i));
		}
		this.setChanged();
	}
	
	@Override
	public int getComparetorOverride() {
		return AbstractContainerMenu.getRedstoneSignalFromContainer(this);
	}

	@Override
	protected Component getDefaultName() {
		return new TranslatableComponent("container.buildersaddition.bookshelf");
	}

	@Override
	protected AbstractContainerMenu createMenu(int windowId, Inventory inv) {
		return BookshelfContainer.create(windowId, inv, this.getBlockPos(), this.handler);
	}
}
