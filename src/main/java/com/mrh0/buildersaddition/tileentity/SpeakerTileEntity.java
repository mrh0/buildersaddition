package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.config.Config;
import com.mrh0.buildersaddition.container.SpeakerContainer;
import com.mrh0.buildersaddition.network.IIntData;
import com.mrh0.buildersaddition.tileentity.base.BaseInstrument;

import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.state.BlockState;

public class SpeakerTileEntity extends BaseInstrument implements MenuProvider, IIntData {

	private int instruments;
	
	public SpeakerTileEntity(BlockPos pos, BlockState state) {
		super(Index.SPEAKER_TILE_ENTITY_TYPE, pos, state);
	}
	
	@Override
	public Container createMenu(int windowId, PlayerInventory inv, PlayerEntity player) {
		return SpeakerContainer.create(windowId, inv, this.getPos());
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.buildersaddition.speaker");
	}
	
	@Override
	public void playNote(int note) {
		if(!Config.MIDI_ENABLED.get() || !Config.MIDI_SOUND_ENABLED.get())
			return;
		
		if(note > 0 && note <= 24) {
			if(isInstrumentActive(0))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_BASS, note);
			if(isInstrumentActive(1))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_SNARE, note);
			if(isInstrumentActive(2))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_HAT, note);
			if(isInstrumentActive(3))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM, note);
			if(isInstrumentActive(11))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO, note);
		}
		if(note > 12 && note <= 36) {
			if(isInstrumentActive(7))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_GUITAR, note - 12);
		}
		if(note > 24 && note <= 48) {
			if(isInstrumentActive(6))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_CHIME, note - 24);
			if(isInstrumentActive(9))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, note - 24);
			if(isInstrumentActive(12))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_BIT, note - 24);
			if(isInstrumentActive(13))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_BANJO, note - 24);
			if(isInstrumentActive(14))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_PLING, note - 24);
			if(isInstrumentActive(15))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_HARP, note - 24);
		}
		if(note > 36 && note <= 60) {
			if(isInstrumentActive(5))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_FLUTE, note - 36);
			if(isInstrumentActive(10))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_COW_BELL, note - 36);
		}
		if(note > 48 ) {//&& note <= 72
			if(isInstrumentActive(4))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_BELL, note - 48);
			if(isInstrumentActive(8))
				playNote(world, pos, SoundEvents.BLOCK_NOTE_BLOCK_XYLOPHONE, note - 48);
		}
	}
	
	public static int latestNotes = 0;
	
	public static void playNote(World world, BlockPos pos, SoundEvent evt, int note) {
		//if(!world.isRemote())
		//	return;
		if(note < 0)// || note > 24)
			return;
		float f = (float)Math.pow(2.0D, (double)(note - 12) / 12.0D);
		if(latestNotes < Config.MIDI_NOTES_PER_SECOND.get()) {
			world.playSound((PlayerEntity)null, pos, evt, SoundCategory.RECORDS, 3.0F, f);
			latestNotes++;
		}
		//world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5D, (double)pos.getY() + 1.2D, (double)pos.getZ() + 0.5D, (double)note / 24.0D, 0.0D, 0.0D);
	}
	
	@Override
	public void updateData(int encoded) {
		instruments = encoded;
	}
	
	//Read
	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		instruments = nbt.getInt("instruments");
		super.read(state, nbt);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		nbt.putInt("instruments", instruments);
		return super.write(nbt);
	}
	
	private int getBit(int n, int k) {
	    return (n >> k) & 1;
	}
	
	public boolean isInstrumentActive(int index) {
		return getBit(instruments, index) > 0;
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
		this.read(state, nbt);
	}
}
