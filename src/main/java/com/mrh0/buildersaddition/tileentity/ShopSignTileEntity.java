package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ShopSignTileEntity extends BlockEntity {

	private ItemStack item;
	
	public ShopSignTileEntity(BlockPos pos, BlockState state) {
		super(Index.SHOP_SIGN_TILE_ENTITY_TYPE, pos, state);
		item = ItemStack.EMPTY;
	}
	
	public ItemStack getDisplayItem() {
		return item.copy();
	}
	
	public void setDisplayItem(ItemStack item) {
		if(item == null)
			item = ItemStack.EMPTY;
		this.item = item.copy();
		this.item.setCount(1);
		level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 0);
		this.setChanged();
		
	}
	
	public boolean hasDisplayItem() {
		return item != ItemStack.EMPTY && item.getItem() != Items.AIR;
	}
	
	@Override
	public CompoundTag save(CompoundTag nbt) {
		
		return super.save(nbt);
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt) {
		nbt.put("item", item.save(new CompoundTag()));
		super.saveAdditional(nbt);
	}
	
	@Override
	public void load(CompoundTag nbt) {
		item = ItemStack.of(nbt.getCompound("item"));
		if(item == null)
			item = ItemStack.EMPTY;
		super.load(nbt);
	}
	
	
	
	/*@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		CompoundTag update = getUpdateTag();
        int data = 0;
        return new ClientboundBlockEntityDataPacket(this.getBlockPos(), data, update);
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
}
