package com.mrh0.buildersaddition.tileentity.base;

import com.mrh0.buildersaddition.util.IComparetorOverride;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public abstract class BaseChestTileEntity extends RandomizableContainerBlockEntity
		implements IComparetorOverride, ICapabilityProvider {
	private NonNullList<ItemStack> inv = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
	private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
		protected void onOpen(Level world, BlockPos pos, BlockState state) {
			playSound(state, SoundEvents.BARREL_OPEN);
			//updateBlockState(state, true);
		}

		protected void onClose(Level world, BlockPos pos, BlockState state) {
			playSound(state, SoundEvents.BARREL_CLOSE);
			//updateBlockState(state, false);
		}

		protected void openerCountChanged(Level world, BlockPos pos, BlockState state, int a, int b) {
			
		}

		protected boolean isOwnContainer(Player player) {
			if (player.containerMenu instanceof ChestMenu) {
				Container container = ((ChestMenu) player.containerMenu).getContainer();
				return container == this;
			} else {
				return false;
			}
		}
	};

	public BaseChestTileEntity(BlockEntityType<?> tet, BlockPos pos, BlockState state) {
		super(tet, pos, state);
	}

	protected LazyOptional<IItemHandlerModifiable> chestHandler;

	@Override
	public void setBlockState(BlockState p_155251_) {
		super.setBlockState(p_155251_);
		if (this.chestHandler != null) {
			LazyOptional<?> oldHandler = this.chestHandler;
			this.chestHandler = null;
			oldHandler.invalidate();
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(
			Capability<T> cap, Direction side) {
		if (!this.remove && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (this.chestHandler == null)
				this.chestHandler = LazyOptional.of(this::createHandler);
			return this.chestHandler.cast();
		}
		return super.getCapability(cap, side);
	}

	protected IItemHandlerModifiable createHandler() {
		BlockState state = this.getBlockState();
		if (!(state.getBlock() instanceof ChestBlock)) {
			return new InvWrapper(this);
		}
		Container inv = ChestBlock.getContainer((ChestBlock) state.getBlock(), state, getLevel(), getBlockPos(), true);
		return new InvWrapper(inv == null ? this : inv);
	}

	@Override
	protected void invalidateCaps() {
		super.invalidateCaps();
		if (chestHandler != null)
			chestHandler.invalidate();
	}

	/*public net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandlerModifiable> invHandler;

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		if (this.invHandler != null) {
			this.invHandler.invalidate();
			this.invHandler = null;
		}
	}

	@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(
			net.minecraftforge.common.capabilities.Capability<T> cap, Direction side) {
		if (!this.isRemoved() && cap == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (this.invHandler == null)
				this.invHandler = getInvHandler();
			return this.invHandler.cast();
		}
		return super.getCapability(cap, side);
	}
	
	@Override
	protected IItemHandler createUnSidedHandler() {
		return super.createUnSidedHandler();
	}
	
	@Override
	public net.minecraftforge.items.IItemHandlerModifiable createHandler() {
		BlockState state = this.getBlockState();
		if (!(state.getBlock() instanceof ChestBlock)) {
			return new net.minecraftforge.items.wrapper.InvWrapper(this);
		}
		Container inv = ChestBlock.getContainer((ChestBlock) state.getBlock(), state, getLevel(), getBlockPos(), true);
		return new net.minecraftforge.items.wrapper.InvWrapper(inv == null ? this : inv);
	}
	
	public net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandlerModifiable> getInvHandler() {
		return invHandler;
	}*/

	@Override
	public void clearRemoved() {
		super.clearRemoved();
		if (chestHandler != null)
			chestHandler.invalidate();
	}

	@Override
	public CompoundTag serializeNBT() {
		return super.serializeNBT();
	}

	@Override
	public void load(CompoundTag p_155349_) {
		super.load(p_155349_);
		this.inv = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(p_155349_)) {
			ContainerHelper.loadAllItems(p_155349_, this.inv);
		}

	}

	@Override
	public CompoundTag save(CompoundTag p_59112_) {
		super.save(p_59112_);
		if (!this.trySaveLootTable(p_59112_)) {
			ContainerHelper.saveAllItems(p_59112_, this.inv);
		}

		return p_59112_;
	}
	
	/*@Override
	public int getContainerSize() {
		return 27;
	}*/

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.inv;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.inv = itemsIn;
	}
	
	/*@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
		return ChestMenu.sixRows(id, inv, this);
	}*/

	public void startOpen(Player p_58616_) {
		if (!this.remove && !p_58616_.isSpectator()) {
			this.openersCounter.incrementOpeners(p_58616_, this.getLevel(), this.getBlockPos(), this.getBlockState());
		}

	}

	public void stopOpen(Player p_58614_) {
		if (!this.remove && !p_58614_.isSpectator()) {
			this.openersCounter.decrementOpeners(p_58614_, this.getLevel(), this.getBlockPos(), this.getBlockState());
		}

	}

	public void recheckOpen() {
		if (!this.remove) {
			this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
		}

	}

	/*void updateBlockState(BlockState p_58607_, boolean p_58608_) {
		this.level.setBlock(this.getBlockPos(), p_58607_.setValue(BarrelBlock.OPEN, Boolean.valueOf(p_58608_)), 3);
	}

	private void scheduleTick() {
		this.level.getBlockTicks().scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 5);
	}*/

	/*public void invTick() {
		this.numPlayersUsing = ChestBlockEntity.getOpenCount(this.level, this.getBlockPos());
		if (this.numPlayersUsing > 0) {
			this.scheduleTick();
		} 
		else {
			BlockState blockstate = this.getBlockState();
			if (!(blockstate.hasBlockEntity())) {
				this.setRemoved();
				return;
			}
			this.playSound(blockstate, SoundEvents.BARREL_CLOSE);
		}
	}*/

	protected abstract void playSound(BlockState state, SoundEvent evt);

	/*
	 * public int calcRedstoneFromInventory() { if (inv == null) { return 0; } else
	 * { int i = 0; float f = 0.0F;
	 * 
	 * for(int j = 0; j < inv.size(); ++j) { ItemStack itemstack = inv.get(j); if
	 * (!itemstack.isEmpty()) { f += (float)itemstack.getCount() /
	 * (float)Math.min(getInventoryStackLimit(), itemstack.getMaxStackSize()); ++i;
	 * } }
	 * 
	 * f = f / (float)inv.getSizeInventory(); return MathHelper.floor(f * 14.0F) +
	 * (i > 0 ? 1 : 0); } }
	 */

	@Override
	public int getComparetorOverride() {
		return AbstractContainerMenu.getRedstoneSignalFromContainer(this);
	}
}
