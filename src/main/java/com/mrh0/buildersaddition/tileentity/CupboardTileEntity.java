package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.Counter;
import com.mrh0.buildersaddition.blocks.Cupboard;
import com.mrh0.buildersaddition.tileentity.base.BaseChestTileEntity;
import com.mrh0.buildersaddition.util.IComparetorOverride;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class CupboardTileEntity extends BaseChestTileEntity implements IComparetorOverride {
	
	public CupboardTileEntity(BlockPos pos, BlockState state) {
		super(Index.CUPBOARD_TILE_ENTITY_TYPE.get(), pos, state);
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("container.buildersaddition.cupboard");
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
	public <T> LazyOptional<T> getCapability(
			Capability<T> cap, Direction side) {
		if (!this.isRemoved() && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (this.chestHandler == null) {
				if(isLower()) {
					this.chestHandler = LazyOptional.of(this::createHandler);//net.minecraftforge.common.util.LazyOptional.of(this::createHandler);
				}
				else {
					BlockEntity te = level.getBlockEntity(getBlockPos().below());
					if(te != null && (te instanceof CupboardTileEntity)) {
						CupboardTileEntity cte = (CupboardTileEntity)te;
						this.chestHandler = LazyOptional.of(cte::createHandler);// net.minecraftforge.common.util.LazyOptional.of(cte::createHandler);
					}
					else {
						return LazyOptional.empty();
					}
				}
			}
			//if(this.invHandler != null)
			//	return this.invHandler.cast();
		}
		return super.getCapability(cap, side);
	}
	
	/*@Override
	public net.minecraftforge.items.IItemHandlerModifiable createHandler() {
		return new net.minecraftforge.items.wrapper.InvWrapper(this);
	}*/
	
	public boolean isLower() {
		BlockState state = this.level.getBlockState(this.getBlockPos());
		return state.getValue(Cupboard.HALF) == DoubleBlockHalf.LOWER;
	}
	
	@Override
	public int getContainerSize() {
		return 54;
	}
	
	@Override
	public int getComparetorOverride() {
		return AbstractContainerMenu.getRedstoneSignalFromContainer(this);
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inv) {
		return ChestMenu.sixRows(id, inv, this);
	}
}
