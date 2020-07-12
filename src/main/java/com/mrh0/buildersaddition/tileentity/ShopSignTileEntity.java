package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

public class ShopSignTileEntity extends TileEntity {

	private ItemStack item;
	
	public ShopSignTileEntity() {
		super(Index.SHOP_SIGN_TILE_ENTITY_TYPE);
		item = ItemStack.EMPTY;
	}
	
	public ItemStack getDisplayItem() {
		return item.copy();
	}
	
	public void setDisplayItem(ItemStack item) {
		if(item == null)
			item = ItemStack.EMPTY;
		this.item = item.copy();
		world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 0);
		this.markDirty();
		
	}
	
	public boolean hasDisplayItem() {
		return item != ItemStack.EMPTY && item.getItem() != Items.AIR;
	}
	
	@Override
	public void func_230337_a_(BlockState state, CompoundNBT nbt) {
		item = ItemStack.read(nbt.getCompound("item"));
		if(item == null)
			item = ItemStack.EMPTY;
		super.func_230337_a_(state, nbt);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		nbt.put("item", item.write(new CompoundNBT()));
		return super.write(nbt);
	}
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT update = getUpdateTag();
        int data = 0;
        return new SUpdateTileEntityPacket(this.pos, data, update);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		CompoundNBT update = pkt.getNbtCompound();
        handleUpdateTag(this.getBlockState(), update);
	}
	
	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT nbt = new CompoundNBT();
		write(nbt);
        return nbt;
	}
	
	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
		func_230337_a_(state, nbt);
	}
}
