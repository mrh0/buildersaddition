package com.mrh0.buildersaddition.tileentity.base;

import com.mrh0.buildersaddition.util.IComparetorOverride;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;

public abstract class BaseChestTileEntity extends RandomizableContainerBlockEntity
		implements IComparetorOverride, ICapabilityProvider {
	private NonNullList<ItemStack> inv = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
	private int numPlayersUsing;

	public BaseChestTileEntity(BlockEntityType<?> tet, BlockPos pos, BlockState state) {
		super(tet, pos, state);
	}

	public net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandlerModifiable> invHandler;

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
				this.invHandler = net.minecraftforge.common.util.LazyOptional.of(this::createHandler);
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
		Inventory inv = ChestBlock..getChestInventory((ChestBlock) state.getBlock(), state, getLevel(), getBlockPos(), true);
		return new net.minecraftforge.items.wrapper.InvWrapper(inv == null ? this : inv);
	}

	@Override
	public void clearRemoved() {
		super.clearRemoved();
		if (invHandler != null)
			invHandler.invalidate();
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
		return Container.calcRedstoneFromInventory(this);
	}
}
