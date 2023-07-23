package com.mrh0.buildersaddition.network;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

public class UpdateDataPacket {
	
	private BlockPos pos;
	private int data;
	
	public UpdateDataPacket(BlockPos pos, int data) {
		this.pos = pos;
		this.data = data;
	}
	
	public static void encode(UpdateDataPacket packet, FriendlyByteBuf tag) {
        tag.writeBlockPos(packet.pos);
        tag.writeInt(packet.data);
    }
	
	public static UpdateDataPacket decode(FriendlyByteBuf buf) {
		UpdateDataPacket scp = new UpdateDataPacket(buf.readBlockPos(), buf.readInt());
        return scp;
    }
	
	public static void handle(UpdateDataPacket pkt, Supplier<NetworkEvent.Context> ctx) {
		//System.out.println("Sending Update");
		ctx.get().enqueueWork(() -> {
			try {
				ServerPlayer player = ctx.get().getSender();
			
			if (player != null) {
				sendUpdate(pkt.pos, player, pkt.data);
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		ctx.get().setPacketHandled(true);
		
		}
	private static void sendUpdate(BlockPos pos, ServerPlayer player, int data) {
		BlockEntity te = (BlockEntity) player.level().getBlockEntity(pos);
		
	    if (te != null) {
	        if(te instanceof IIntData) {
	        	((IIntData)te).updateData(data);
	        	Packet<ClientGamePacketListener> supdatetileentitypacket = te.getUpdatePacket();
	            if (supdatetileentitypacket != null) {
	                player.connection.send(supdatetileentitypacket);
	            }
	        }
		}
    }
}
