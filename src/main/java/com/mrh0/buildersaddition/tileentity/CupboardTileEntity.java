package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.Counter;
import com.mrh0.buildersaddition.blocks.Cupboard;
import com.mrh0.buildersaddition.tileentity.base.BaseChestTileEntity;
import com.mrh0.buildersaddition.util.IComparetorOverride;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import ITextComponent;

public class CupboardTileEntity extends BaseChestTileEntity implements IComparetorOverride {
	public CupboardTileEntity() {
		super(Index.CUPBOARD_TILE_ENTITY_TYPE);
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.buildersaddition.cupboard");
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
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(
			net.minecraftforge.common.capabilities.Capability<T> cap, Direction side) {
		if (!this.removed && cap == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (this.invHandler == null) {
				if(isLower()) {
					this.invHandler = net.minecraftforge.common.util.LazyOptional.of(this::createHandler);
				}
				else {
					TileEntity te = world.getTileEntity(pos.down());
					if(te != null && te instanceof CupboardTileEntity) {
						CupboardTileEntity cte = (CupboardTileEntity)te;
						this.invHandler = net.minecraftforge.common.util.LazyOptional.of(cte::createHandler);
					}
					else {
						return net.minecraftforge.common.util.LazyOptional.empty();
					}
				}
			}
			//if(this.invHandler != null)
			//	return this.invHandler.cast();
		}
		return super.getCapability(cap, side);
	}
	
	@Override
	public net.minecraftforge.items.IItemHandlerModifiable createHandler() {
		return new net.minecraftforge.items.wrapper.InvWrapper(this);
	}
	
	public boolean isLower() {
		BlockState state = this.world.getBlockState(pos);
		return state.get(Cupboard.HALF) == DoubleBlockHalf.LOWER;
	}
	
	@Override
	public int getSizeInventory() {
		return 54;
	}
	
	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return ChestContainer.createGeneric9X6(id, player, this);
	}
	
	@Override
	public int getComparetorOverride() {
		return Container.calcRedstone(this);
	}
}
