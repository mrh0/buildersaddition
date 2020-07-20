package com.mrh0.buildersaddition.network;

import java.util.function.Supplier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class UpdateDataPacket {
	
	private BlockPos pos;
	private int data;
	
	public UpdateDataPacket(BlockPos pos, int data) {
		this.pos = pos;
		this.data = data;
	}
	
	public static void encode(UpdateDataPacket packet, PacketBuffer tag) {
        tag.writeBlockPos(packet.pos);
        tag.writeInt(packet.data);
    }
	
	public static UpdateDataPacket decode(PacketBuffer buf) {
		UpdateDataPacket scp = new UpdateDataPacket(buf.readBlockPos(), buf.readInt());
        return scp;
    }
	
	public static void handle(UpdateDataPacket pkt, Supplier<NetworkEvent.Context> ctx) {
		//System.out.println("Sending Update");
		ctx.get().enqueueWork(() -> {
			try {
				ServerPlayerEntity player = ctx.get().getSender();
			
			if (player != null) {
				sendUpdate(pkt.pos, player, pkt.data);
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		ctx.get().setPacketHandled(true);
		
		}
	private static void sendUpdate(BlockPos pos, ServerPlayerEntity player, int data) {
		TileEntity te = (TileEntity) player.world.getTileEntity(pos);
		
	    if (te != null) {
	        if(te instanceof IIntData) {
	        	((IIntData)te).updateData(data);
	            SUpdateTileEntityPacket supdatetileentitypacket = te.getUpdatePacket();
	            if (supdatetileentitypacket != null) {
	                player.connection.sendPacket(supdatetileentitypacket);
	            }
	        }
		}
    }
}
