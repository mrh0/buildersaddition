package com.mrh0.buildersaddition;

import com.mrh0.buildersaddition.event.BlockRegistry;
import com.mrh0.buildersaddition.event.ClientEventHandler;
import com.mrh0.buildersaddition.event.ContainerRegistry;
import com.mrh0.buildersaddition.event.ItemRegistry;
import com.mrh0.buildersaddition.event.TileEntityRegistry;
import com.mrh0.buildersaddition.itemgroup.ModGroup;
import com.mrh0.buildersaddition.proxy.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
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
	
	public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	
	public BuildersAddition() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::postInit);
    	MinecraftForge.EVENT_BUS.addListener(this::serverevt);
    	MinecraftForge.EVENT_BUS.register(this);
    	
    	new ModGroup("builders_addition_group");
    	
    	new BlockRegistry();
    	new ItemRegistry();
    	new TileEntityRegistry();
    	new ContainerRegistry();
    	
    	new Index();
	}

    private void setup(final FMLCommonSetupEvent evt) {
    	proxy.init(evt);
    	DistExecutor.runWhenOn(Dist.CLIENT, () -> ClientEventHandler::clientRegistry);
    }

    public void postInit(FMLLoadCompleteEvent evt) {
    	System.out.println("Builders Addition Initialized!");
    }
    
    public void serverevt(FMLServerStartingEvent evt) {
    	
    }
}
