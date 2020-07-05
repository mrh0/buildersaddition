package com.mrh0.buildersaddition.tileentity.base;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public abstract class BaseChestTileEntity extends LockableLootTileEntity
		implements ICapabilityProvider {
	private NonNullList<ItemStack> inv = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
	private int numPlayersUsing;

	public BaseChestTileEntity(TileEntityType<?> tet) {
		super(tet);
	}

	private net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandlerModifiable> invHandler;

	@Override
	public void updateContainingBlockInfo() {
		super.updateContainingBlockInfo();
		if (this.invHandler != null) {
			this.invHandler.invalidate();
			this.invHandler = null;
		}
	}

	@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(
			net.minecraftforge.common.capabilities.Capability<T> cap, Direction side) {
		if (!this.removed && cap == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (this.invHandler == null)
				this.invHandler = net.minecraftforge.common.util.LazyOptional.of(this::createHandler);
			return this.invHandler.cast();
		}
		return super.getCapability(cap, side);
	}

	private net.minecraftforge.items.IItemHandlerModifiable createHandler() {
		BlockState state = this.getBlockState();
		if (!(state.getBlock() instanceof ChestBlock)) {
			return new net.minecraftforge.items.wrapper.InvWrapper(this);
		}
		IInventory inv = ChestBlock.func_226916_a_((ChestBlock) state.getBlock(), state, getWorld(), getPos(), true);
		return new net.minecraftforge.items.wrapper.InvWrapper(inv == null ? this : inv);
	}

	@Override
	public void remove() {
		super.remove();
		if (invHandler != null)
			invHandler.invalidate();
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		super.write(nbt);
		if (!this.checkLootAndWrite(nbt)) {
			ItemStackHelper.saveAllItems(nbt, this.inv);
		}
		return nbt;
	}

	@Override
	public void func_230337_a_(BlockState state, CompoundNBT nbt) {
		super.func_230337_a_(state, nbt);
		this.inv = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if (!this.checkLootAndRead(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.inv);
		}
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.inv;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.inv = itemsIn;
	}

	@Override
	protected abstract ITextComponent getDefaultName();

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return ChestContainer.createGeneric9X3(id, player, this);
	}

	@Override
	public void openInventory(PlayerEntity player) {
		if (!player.isSpectator()) {
			if (this.numPlayersUsing < 0) {
				this.numPlayersUsing = 0;
			}

			++this.numPlayersUsing;
			BlockState blockstate = this.getBlockState();
			this.playSound(blockstate, SoundEvents.BLOCK_BARREL_OPEN);
			this.scheduleTick();
		}

	}

	private void scheduleTick() {
		this.world.getPendingBlockTicks().scheduleTick(this.getPos(), this.getBlockState().getBlock(), 5);
	}

	public void invTick() {
		int i = this.pos.getX();
		int j = this.pos.getY();
		int k = this.pos.getZ();
		this.numPlayersUsing = ChestTileEntity.calculatePlayersUsing(this.world, this, i, j, k);
		if (this.numPlayersUsing > 0) {
			this.scheduleTick();
		} else {
			BlockState blockstate = this.getBlockState();
			if (!(blockstate.getBlock().hasTileEntity(blockstate))) {
				this.remove();
				return;
			}

			this.playSound(blockstate, SoundEvents.BLOCK_BARREL_CLOSE);
		}

	}

	@Override
	public void closeInventory(PlayerEntity player) {
		if (!player.isSpectator()) {
			--this.numPlayersUsing;
		}
	}

	protected abstract void playSound(BlockState state, SoundEvent evt);
}
