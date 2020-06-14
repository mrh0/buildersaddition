package com.mrh0.buildersaddition;

import com.mrh0.buildersaddition.event.BlockRegistry;
import com.mrh0.buildersaddition.event.ItemRegistry;
import com.mrh0.buildersaddition.itemgroup.ModGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


/*
 * Author: MRH0 (aka MRH, MRHminer & hminer)
 * Builders Crafts & Additions
 * MIT License
 */
@Mod(BuildersAddition.MODID)
public class BuildersAddition {
	public static final String MODID = "buildersaddition";
	
	public BuildersAddition() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::postInit);
    	MinecraftForge.EVENT_BUS.addListener(this::serverevt);
    	MinecraftForge.EVENT_BUS.register(this);
    	
    	new ModGroup("builders_addition_group");
    	
    	new BlockRegistry();
    	new ItemRegistry();
    	
    	new Index();
	}

    private void setup(final FMLCommonSetupEvent evt) {

    }

    public void postInit(FMLLoadCompleteEvent evt) {
    	System.out.println("Builders Addition Initialized!");
    }
    
    public void serverevt(FMLServerStartingEvent evt) {
    	
    }
}
