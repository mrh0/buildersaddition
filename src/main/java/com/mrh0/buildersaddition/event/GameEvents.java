package com.mrh0.buildersaddition.event;


import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.config.Config;
import com.mrh0.buildersaddition.util.Notes;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class GameEvents {
	
	@SubscribeEvent
    public static void interact(PlayerInteractEvent.RightClickBlock evt) {
        if(evt.getItemStack().getItem() instanceof ShovelItem) {
        	BlockState stateClicked = evt.getWorld().getBlockState(evt.getPos());
        	if(stateClicked.isIn(Blocks.GRAVEL)) {
        		BlockState stateAbove = evt.getWorld().getBlockState(evt.getPos().up());
    			if(!stateAbove.getMaterial().isSolid() || stateAbove.getBlock() instanceof FenceGateBlock) {
	        		if(!evt.getWorld().isRemote()) {
	        			evt.getWorld().setBlockState(evt.getPos(), Index.GRAVEL_PATH.getDefaultState());
	        			evt.getItemStack().damageItem(1, evt.getPlayer(), (PlayerEntity e) -> {});
	        			evt.setCancellationResult(ActionResultType.SUCCESS);
	                	evt.setCanceled(true);
	        		}
	        		else {
	        			evt.getPlayer().playSound(SoundEvents.BLOCK_GRAVEL_BREAK, 1, 1);
	        		}
    			}
        	}
        }
    }
	
	@SubscribeEvent
	public static void tickEvent(TickEvent.ClientTickEvent evt) {
		Notes.latestNotes -= Config.MIDI_NOTES_PER_SECOND.get()/20;
		if(Notes.latestNotes < 0)
			Notes.latestNotes = 0;
	}
	
	/*@SubscribeEvent
	public static void torchEvent(PlayerInteractEvent.RightClickBlock evt) {
		BlockPos pos = evt.getPos();
		if(evt.getItemStack().getItem() == Items.TORCH) {
			if(evt.getFace() == Direction.UP) {
				BlockState state = evt.getWorld().getBlockState(evt.getPos());
				if(state.getBlock() instanceof SlabBlock) {
					if(state.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
						evt.getWorld().setBlockState(evt.getPos().up(), Index.SLAB_TORCH.getDefaultState());
						evt.getWorld().playSound(null, pos.getX(), pos.getY(), pos.getZ(), Blocks.TORCH.getSoundType(evt.getWorld().getBlockState(pos)).getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
						evt.getPlayer().swingArm(evt.getHand());
						if(!evt.getPlayer().isCreative())
							evt.getItemStack().shrink(1);
					}
				}
			}
		}
	}*/
}
