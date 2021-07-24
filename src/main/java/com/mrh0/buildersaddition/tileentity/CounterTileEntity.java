package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.Counter;
import com.mrh0.buildersaddition.tileentity.base.BaseChestTileEntity;
import com.mrh0.buildersaddition.util.IComparetorOverride;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import ITextComponent;

public class CounterTileEntity extends BaseChestTileEntity implements IComparetorOverride {
	public CounterTileEntity() {
		super(Index.COUNTER_TILE_ENTITY_TYPE);
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.buildersaddition.counter");
	}
	
	protected void playSound(BlockState state, SoundEvent evt) {
		Vector3i vector3i = state.get(Counter.FACING).getDirectionVec();
		double d0 = (double) this.pos.getX() + 0.5D + (double) vector3i.getX() / 2.0D;
		double d1 = (double) this.pos.getY() + 0.5D + (double) vector3i.getY() / 2.0D;
		double d2 = (double) this.pos.getZ() + 0.5D + (double) vector3i.getZ() / 2.0D;
		this.world.playSound((PlayerEntity) null, d0, d1, d2, evt, SoundCategory.BLOCKS, 0.5F,
				this.world.rand.nextFloat() * 0.1F + 0.9F);
	}
	
	@Override
	public int getComparetorOverride() {
		return Container.calcRedstone(this);
	}
}
