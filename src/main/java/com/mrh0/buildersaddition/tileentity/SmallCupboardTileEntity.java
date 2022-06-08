package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.Counter;
import com.mrh0.buildersaddition.tileentity.base.BaseChestTileEntity;
import com.mrh0.buildersaddition.util.IComparetorOverride;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.state.BlockState;

public class SmallCupboardTileEntity extends BaseChestTileEntity implements IComparetorOverride {
	public SmallCupboardTileEntity(BlockPos pos, BlockState state) {
		super(Index.SMALL_CUPBOARD_TILE_ENTITY_TYPE, pos, state);
	}
	
	protected void playSound(BlockState state, SoundEvent evt) {
		Vec3i vector3i = state.getValue(Counter.FACING).getNormal();
		double d0 = (double) this.getBlockPos().getX() + 0.5D + (double) vector3i.getX() / 2.0D;
		double d1 = (double) this.getBlockPos().getY() + 0.5D + (double) vector3i.getY() / 2.0D;
		double d2 = (double) this.getBlockPos().getZ() + 0.5D + (double) vector3i.getZ() / 2.0D;
		this.level.playSound((Player) null, d0, d1, d2, evt, SoundSource.BLOCKS, 0.5F,
				this.level.random.nextFloat() * 0.1F + 0.9F);
	}
	
	@Override
	public int getComparetorOverride() {
		return AbstractContainerMenu.getRedstoneSignalFromContainer(this);
	}

	@Override
	public int getContainerSize() {
		return 27;
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inv) {
		return ChestMenu.threeRows(id, inv, this);//new ChestMenu(MenuType.GENERIC_9x3, id, inv, this, 2);
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("container.buildersaddition.small_cupboard");
	}
}