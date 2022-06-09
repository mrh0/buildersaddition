package com.mrh0.buildersaddition.tileentity;

import javax.annotation.Nonnull;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.Bookshelf;
import com.mrh0.buildersaddition.container.BookshelfContainer;
import com.mrh0.buildersaddition.inventory.ModInventory;
import com.mrh0.buildersaddition.util.IComparetorOverride;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BookshelfTileEntity extends BlockEntity implements MenuProvider, IComparetorOverride {

	public static int SLOTS = 18;
	
	public final ItemStackHandler handler = new ItemStackHandler(SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
        	changed(slot);
            setChanged();
        }
    };
	private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
	
	public BookshelfTileEntity(BlockPos pos, BlockState state) {
		super(Index.BOOKSHELF_TILE_ENTITY_TYPE.get(), pos, state);
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
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> handler);
    }
	
	@Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }
	
	@Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }
	
	@Override
	public int getComparetorOverride() {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(this);
	}


	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
		return new BookshelfContainer(id, inv, this, new SimpleContainerData(0));
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("container.buildersaddition.bookshelf");
	}
}
