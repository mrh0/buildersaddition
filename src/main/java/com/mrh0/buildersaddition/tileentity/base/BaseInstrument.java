package com.mrh0.buildersaddition.tileentity.base;

import com.mrh0.buildersaddition.BuildersAddition;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseInstrument extends BlockEntity {
	public BaseInstrument(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
		super(tileEntityTypeIn, pos, state);
	}

	public abstract void playNote(int note);
	
	@Override
	public void clearRemoved() {
		if(BuildersAddition.midi != null)
			if(BuildersAddition.midi.midiEvent == this)
				BuildersAddition.midi.midiEvent = null;
		super.clearRemoved();
	}
	
	@Override
	public void onChunkUnloaded() {
		super.onChunkUnloaded();
		if(BuildersAddition.midi != null)
			if(BuildersAddition.midi.midiEvent == this)
				BuildersAddition.midi.midiEvent = null;
	}
}
