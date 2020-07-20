package com.mrh0.buildersaddition.event;


import com.mrh0.buildersaddition.Index;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
}
