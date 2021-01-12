package com.mrh0.buildersaddition.network;

import java.util.function.Supplier;
import com.mrh0.buildersaddition.config.Config;
import com.mrh0.buildersaddition.tileentity.base.BaseInstrument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class PlayNotePacket {
	
	private BlockPos pos;
	private int note;
	
	public PlayNotePacket(BlockPos pos, int note) {
		this.pos = pos;
		this.note = note;
	}
	
	public static void encode(PlayNotePacket packet, PacketBuffer tag) {
        tag.writeBlockPos(packet.pos);
        tag.writeInt(packet.note);
    }
	
	public static PlayNotePacket decode(PacketBuffer buf) {
		PlayNotePacket scp = new PlayNotePacket(buf.readBlockPos(), buf.readInt());
        return scp;
    }
	
	public static void handle(PlayNotePacket pkt, Supplier<NetworkEvent.Context> ctx) {
		//System.out.println("Sending Note");
		ctx.get().enqueueWork(() -> {
			try {
				ServerPlayerEntity player = ctx.get().getSender();
			
			if (player != null) {
				sendUpdate(pkt.pos, player, pkt.note);
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		ctx.get().setPacketHandled(true);
		
		}
	private static void sendUpdate(BlockPos pos, ServerPlayerEntity player, int note) {
		if(!Config.MIDI_ENABLED.get())
			return;
		BaseInstrument te = (BaseInstrument) player.world.getTileEntity(pos);
        if (te != null) {
        	te.playNote(note);
            SUpdateTileEntityPacket supdatetileentitypacket = te.getUpdatePacket();
            if (supdatetileentitypacket != null) {
                player.connection.sendPacket(supdatetileentitypacket);
            }
        }
    }
}
