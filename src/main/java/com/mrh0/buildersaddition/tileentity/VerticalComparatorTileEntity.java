package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.VerticalComparatorBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class VerticalComparatorTileEntity extends BlockEntity {

	private int cache;

	public VerticalComparatorTileEntity(BlockPos pos, BlockState state) {
		super(Index.VERTICAL_COMPARATOR_TILE_ENTITY_TYPE, pos, state);
	}

	public static void tick(Level world, BlockPos pos, BlockState state, VerticalComparatorTileEntity te) {
		VerticalComparatorBlock b = ((VerticalComparatorBlock) Index.VERTICAL_COMPARATOR);
		int n = b.getInputSignal(world, pos, state);
		if(te.cache != n) {
			b.checkTickOnNeighbor(world, pos, state);
			te.cache  = n;
		}
		/*BlockState is = world
				.getBlockState(pos.relative(state.getValue(VerticalComparatorBlock.VERTICAL_FACING)));
		if (!is.hasAnalogOutputSignal()) {
			te.cache = 0;
			return;
		}
		int n = is.getAnalogOutputSignal(world, pos);
		System.out.println("CompTick " + te.cache + ":" + n);
		if (te.cache != n) {
			System.out.println("Ref");
			((VerticalComparatorBlock) Index.VERTICAL_COMPARATOR).refreshOutputState(world, pos, state);
		}
		te.cache = n;*/
	}

	private int output;

	public CompoundTag save(CompoundTag p_59181_) {
		super.save(p_59181_);
		p_59181_.putInt("OutputSignal", this.output);
		return p_59181_;
	}

	public void load(CompoundTag p_155389_) {
		super.load(p_155389_);
		this.output = p_155389_.getInt("OutputSignal");
	}

	public int getOutputSignal() {
		return this.output;
	}

	public void setOutputSignal(int p_59176_) {
		this.output = p_59176_;
	}
}
