package com.mrh0.buildersaddition.network;

import java.util.function.Supplier;
import com.mrh0.buildersaddition.config.Config;
import com.mrh0.buildersaddition.tileentity.base.BaseInstrument;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class PlayNotePacket {
	
	private BlockPos pos;
	private int note;
	
	public PlayNotePacket(BlockPos pos, int note) {
		this.pos = pos;
		this.note = note;
	}
	
	public static void encode(PlayNotePacket packet, FriendlyByteBuf tag) {
        tag.writeBlockPos(packet.pos);
        tag.writeInt(packet.note);
    }
	
	public static PlayNotePacket decode(FriendlyByteBuf buf) {
		PlayNotePacket scp = new PlayNotePacket(buf.readBlockPos(), buf.readInt());
        return scp;
    }
	
	public static void handle(PlayNotePacket pkt, Supplier<NetworkEvent.Context> ctx) {
		//System.out.println("Sending Note");
		ctx.get().enqueueWork(() -> {
			try {
				ServerPlayer player = ctx.get().getSender();
			
			if (player != null) {
				sendUpdate(pkt.pos, player, pkt.note);
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		ctx.get().setPacketHandled(true);
		
		}
	private static void sendUpdate(BlockPos pos, ServerPlayer player, int note) {
		if(!Config.MIDI_ENABLED.get())
			return;
		BaseInstrument te = (BaseInstrument) player.level.getBlockEntity(pos);
        if (te != null) {
        	te.playNote(note);
        	Packet<ClientGamePacketListener> supdatetileentitypacket = te.getUpdatePacket();
            if (supdatetileentitypacket != null) {
                player.connection.send(supdatetileentitypacket);
            }
        }
    }
}
