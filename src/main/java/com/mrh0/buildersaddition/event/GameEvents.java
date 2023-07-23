package com.mrh0.buildersaddition.event;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.VerticalComparatorBlock;
import com.mrh0.buildersaddition.blocks.VerticalRepeaterBlock;
import com.mrh0.buildersaddition.config.Config;
import com.mrh0.buildersaddition.tileentity.EntityDetectorTileEntity;
import com.mrh0.buildersaddition.tileentity.SpeakerTileEntity;
import com.mrh0.buildersaddition.util.Notes;
import com.mrh0.buildersaddition.util.Util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class GameEvents {
	@SuppressWarnings("deprecation")
	@SubscribeEvent//(priority = EventPriority.LOWEST)
    public static void interact(PlayerInteractEvent.RightClickBlock evt) {
		
		Item item = evt.getItemStack().getItem();
        if(item instanceof ShovelItem) {
        	if(!Config.PATHBLOCK_CREATION_ENABLED.get())
        		return;
        	BlockState stateClicked = evt.getLevel().getBlockState(evt.getPos());
        	if(stateClicked.is(Blocks.GRAVEL)) {
        		BlockState stateAbove = evt.getLevel().getBlockState(evt.getPos().above());
    			if(stateAbove.isAir() || stateAbove.getBlock() instanceof FenceGateBlock) {
	        		if(!evt.getLevel().isClientSide()) {
	        			evt.getLevel().setBlockAndUpdate(evt.getPos(), Index.GRAVEL_PATH.get().defaultBlockState());
	        			evt.getItemStack().hurtAndBreak(1, evt.getEntity(), (Player e) -> {});
	        		}
	        		else {
	        			evt.getEntity().playSound(SoundEvents.GRAVEL_BREAK, 1, 1);
	        		}
	        		evt.setCancellationResult(InteractionResult.SUCCESS);
                	evt.setCanceled(true);
    			}
        	}
        }
        else if(item instanceof PickaxeItem) {
        	if(!Config.CRACKED_CREATION_ENABLED.get())
        		return;
        	BlockState stateClicked = evt.getLevel().getBlockState(evt.getPos());
        	BlockState next = Util.crackedState(stateClicked);
    		if(next != null) {
    			if(!evt.getLevel().isClientSide()) {
        			evt.getLevel().setBlockAndUpdate(evt.getPos(), next);
        			evt.getItemStack().hurtAndBreak(1, evt.getEntity(), (Player e) -> {});
                	return;
    			}
    			evt.setCancellationResult(InteractionResult.SUCCESS);
            	evt.setCanceled(true);
        		evt.getEntity().playSound(SoundEvents.UI_STONECUTTER_TAKE_RESULT, 1, 1);
    		}	
        }
        /*else if(item == Items.REPEATER) {
        	if(evt.getFace().getAxis() != Axis.Y) {
        		BlockPos pos = evt.getPos().relative(evt.getFace());
        		if(evt.getLevel().getBlockState(pos).isAir()) {
        			
        			// Ugly Work-around
        			if(!evt.getEntity().isCrouching()) {
	        			if(evt.getLevel().getBlockState(evt.getPos()).use(evt.getLevel(), evt.getEntity(), evt.getHand(), evt.getHitVec()) != InteractionResult.PASS) {
	        				evt.setCancellationResult(InteractionResult.SUCCESS);
	                    	evt.setCanceled(true);
	                    	return;
	        			}
        			}
        				
        			boolean flag = evt.getHitVec().getLocation().y - (double) evt.getPos().getY() - .5d < 0;
        			BlockState state = Index.VERTICAL_REPEATER.get().defaultBlockState().setValue(VerticalRepeaterBlock.HORIZONTAL_FACING, evt.getFace().getOpposite()).setValue(VerticalRepeaterBlock.VERTICAL_FACING, flag ? Direction.UP : Direction.DOWN);
        			
        			if(!evt.getLevel().getBlockState(evt.getPos()).isFaceSturdy(evt.getLevel(), evt.getPos(), evt.getFace()))
        				return;
        			
        			evt.getLevel().setBlockAndUpdate(pos, state);
        			Index.VERTICAL_REPEATER.get().setPlacedBy(evt.getLevel(), pos, state, evt.getEntity(), evt.getItemStack());
        			if(!evt.getEntity().isCreative())
        				evt.getItemStack().shrink(1);
        			
        			evt.setCancellationResult(InteractionResult.SUCCESS);
                	evt.setCanceled(true);
                	SoundType snd = Blocks.REPEATER.getSoundType(state, evt.getLevel(), pos, evt.getEntity());
            		evt.getEntity().playSound(snd.getPlaceSound(), snd.getVolume(), snd.getPitch());
        		}
        	}
        }
        else if(item == Items.COMPARATOR) {
        	if(evt.getFace().getAxis() != Axis.Y) {
        		BlockPos pos = evt.getPos().relative(evt.getFace());
        		if(evt.getLevel().getBlockState(pos).isAir()) {
        			
        			// Ugly Work-around
        			if(!evt.getEntity().isCrouching()) {
	        			if(evt.getLevel().getBlockState(evt.getPos()).use(evt.getLevel(), evt.getEntity(), evt.getHand(), evt.getHitVec()) != InteractionResult.PASS) {
	        				evt.setCancellationResult(InteractionResult.SUCCESS);
	                    	evt.setCanceled(true);
	                    	return;
	        			}
        			}
        			
        			boolean flag = evt.getHitVec().getLocation().y - (double) evt.getPos().getY() - .5d < 0;
        			BlockState state = Index.VERTICAL_COMPARATOR.get().defaultBlockState().setValue(VerticalComparatorBlock.HORIZONTAL_FACING, evt.getFace().getOpposite()).setValue(VerticalComparatorBlock.VERTICAL_FACING, flag ? Direction.UP : Direction.DOWN);
        			
        			if(!evt.getLevel().getBlockState(evt.getPos()).isFaceSturdy(evt.getLevel(), evt.getPos(), evt.getFace()))
        				return;
        			
        			evt.getLevel().setBlockAndUpdate(pos, state);
        			Index.VERTICAL_COMPARATOR.get().setPlacedBy(evt.getLevel(), pos, state, evt.getEntity(), evt.getItemStack());
        			if(!evt.getEntity().isCreative())
        				evt.getItemStack().shrink(1);
        			
        			evt.setCancellationResult(InteractionResult.SUCCESS);
                	evt.setCanceled(true);
                	SoundType snd = Blocks.COMPARATOR.getSoundType(state, evt.getLevel(), pos, evt.getEntity());
            		evt.getEntity().playSound(snd.getPlaceSound(), snd.getVolume(), snd.getPitch());
        		}
        	}
        }*/
        //add event: When r-clicking a piston face with slime turn into a sticky-piston
    }
	
	@SubscribeEvent
	public static void tickEvent(TickEvent.ServerTickEvent evt) {
		SpeakerTileEntity.latestNotes -= Config.MIDI_NOTES_PER_SECOND.get()/20;
		if(SpeakerTileEntity.latestNotes < 0)
			SpeakerTileEntity.latestNotes = 0;
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
	/*@SubscribeEvent
    public static void place( evt) {
		
	}*/
}
