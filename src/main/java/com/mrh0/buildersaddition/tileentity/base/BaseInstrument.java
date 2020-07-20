package com.mrh0.buildersaddition.tileentity.base;

import com.mrh0.buildersaddition.BuildersAddition;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class BaseInstrument extends TileEntity {
	public BaseInstrument(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public abstract void playNote(int note);
	
	@Override
	public void remove() {
		if(BuildersAddition.midi != null)
			if(BuildersAddition.midi.midiEvent == this)
				BuildersAddition.midi.midiEvent = null;
		super.remove();
	}
	
	@Override
	public void onChunkUnloaded() {
		super.onChunkUnloaded();
		if(BuildersAddition.midi != null)
			if(BuildersAddition.midi.midiEvent == this)
				BuildersAddition.midi.midiEvent = null;
	}
}
