package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.config.Config;
import com.mrh0.buildersaddition.container.SpeakerContainer;
import com.mrh0.buildersaddition.network.IIntData;
import com.mrh0.buildersaddition.tileentity.base.BaseInstrument;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SpeakerTileEntity extends BaseInstrument implements MenuProvider, IIntData {

	private int instruments;
	
	public SpeakerTileEntity(BlockPos pos, BlockState state) {
		super(Index.SPEAKER_TILE_ENTITY_TYPE.get(), pos, state);
	}
	@Override
	public Component getDisplayName() {
		return Component.translatable("container.buildersaddition.speaker");
	}
	
	@Override
	public void playNote(int note) {
		if(!Config.MIDI_ENABLED.get() || !Config.MIDI_SOUND_ENABLED.get())
			return;
		Level world = getLevel();
		BlockPos pos = getBlockPos();
		if(note > 0 && note <= 24) {
			if(isInstrumentActive(0))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_BASS.get(), note);
			if(isInstrumentActive(1))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_SNARE.get(), note);
			if(isInstrumentActive(2))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_HAT.get(), note);
			if(isInstrumentActive(3))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_BASEDRUM.get(), note);
			if(isInstrumentActive(11))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_DIDGERIDOO.get(), note);
		}
		if(note > 12 && note <= 36) {
			if(isInstrumentActive(7))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_GUITAR.get(), note - 12);
		}
		if(note > 24 && note <= 48) {
			if(isInstrumentActive(6))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_CHIME.get(), note - 24);
			if(isInstrumentActive(9))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_IRON_XYLOPHONE.get(), note - 24);
			if(isInstrumentActive(12))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_BIT.get(), note - 24);
			if(isInstrumentActive(13))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_BANJO.get(), note - 24);
			if(isInstrumentActive(14))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_PLING.get(), note - 24);
			if(isInstrumentActive(15))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_HARP.get(), note - 24);
		}
		if(note > 36 && note <= 60) {
			if(isInstrumentActive(5))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_FLUTE.get(), note - 36);
			if(isInstrumentActive(10))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_COW_BELL.get(), note - 36);
		}
		if(note > 48 ) {//&& note <= 72
			if(isInstrumentActive(4))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_BELL.get(), note - 48);
			if(isInstrumentActive(8))
				playNote(world, pos, SoundEvents.NOTE_BLOCK_XYLOPHONE.get(), note - 48);
		}
	}
	
	public static int latestNotes = 0;
	
	public static void playNote(Level world, BlockPos pos, SoundEvent evt, int note) {
		//if(!world.isRemote())
		//	return;
		if(note < 0)// || note > 24)
			return;
		float f = (float)Math.pow(2.0D, (double)(note - 12) / 12.0D);
		if(latestNotes < Config.MIDI_NOTES_PER_SECOND.get()) {
			world.playSound((Player)null, pos, evt, SoundSource.RECORDS, 3.0F, f);
			latestNotes++;
		}
		//world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5D, (double)pos.getY() + 1.2D, (double)pos.getZ() + 0.5D, (double)note / 24.0D, 0.0D, 0.0D);
	}
	
	@Override
	public void updateData(int encoded) {
		instruments = encoded;
		this.setChanged();
	}
	
	//Read
	@Override
	public void load(CompoundTag nbt) {
		instruments = nbt.getInt("instruments");
		super.load(nbt);
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt) {
		nbt.putInt("instruments", instruments);
		super.saveAdditional(nbt);
	}
	
	private int getBit(int n, int k) {
	    return (n >> k) & 1;
	}
	
	public boolean isInstrumentActive(int index) {
		return getBit(instruments, index) > 0;
	}

	/*@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		CompoundTag update = getUpdateTag();
        int data = 0;
        return new ClientboundBlockEntityDataPacket(this.getBlockPos(), data, update);
	}*/
	
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		CompoundTag update = pkt.getTag();
        handleUpdateTag(update);
	}
	
	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag nbt = new CompoundTag();
        //save(nbt);
        saveAdditional(nbt);
        return nbt;
	}
	
	@Override
	public void handleUpdateTag(CompoundTag nbt) {
		this.load(nbt);
	}

	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
		return SpeakerContainer.create(windowId, inv, this.getBlockPos());
	}
}
