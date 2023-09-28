package com.mrh0.buildersaddition;

import com.mrh0.buildersaddition.arcade.ArcadeManager;
import com.mrh0.buildersaddition.config.Config;
import com.mrh0.buildersaddition.event.ClientEventHandler;
import com.mrh0.buildersaddition.event.CreativeModeTabRegistry;
import com.mrh0.buildersaddition.event.EventHandler;
import com.mrh0.buildersaddition.midi.MidiHandler;
import com.mrh0.buildersaddition.network.PlayNotePacket;
import com.mrh0.buildersaddition.network.UpdateDataPacket;
import com.mrh0.buildersaddition.proxy.*;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;


/*
 * Author: MRH0 (aka MRH, MRHminer & hminer)
 * Builders Crafts & Additions
 * MIT License
 */
@Mod(BuildersAddition.MODID)
public class BuildersAddition {
	public static final String MODID = "buildersaddition";
	public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	private static final int PROTOCOL = 1;
	public static MidiHandler midi = null;
	public static boolean BOP_ACTIVE = false;
	
	public static final SimpleChannel Network = ChannelBuilder.named(new ResourceLocation(MODID, "main"))
            .clientAcceptedVersions(Channel.VersionTest.exact(PROTOCOL))
            .serverAcceptedVersions(Channel.VersionTest.exact(PROTOCOL))
            .networkProtocolVersion(PROTOCOL)
            .simpleChannel();
	
	public BuildersAddition() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		//new ModGroup("builders_addition_group");
		
		Index.register(eventBus);
		CreativeModeTabRegistry.register(eventBus);
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::postInit);
    	//MinecraftForge.EVENT_BUS.addListener(this::serverevt);
    	MinecraftForge.EVENT_BUS.register(this);
		eventBus.addListener(this::addCreative);
    	
    	ArcadeManager.init();
    	
    	//new BlockRegistry();
    	//new ItemRegistry();
    	//new TileEntityRegistry();
    	//new ContainerRegistry();
    	
    	BOP_ACTIVE = ModList.get().isLoaded("biomesoplenty");
    	
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
    	Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("buildersaddition-common.toml"));
	}

    private void setup(final FMLCommonSetupEvent evt) {
    	proxy.init(evt);
    	//DistExecutor.runWhenOn(Dist.CLIENT, () -> ClientEventHandler::clientRegistry);
    }

    private void setupClient(final FMLClientSetupEvent evt) {
    	ClientEventHandler.clientRegistry();
    }
    
    public void postInit(FMLLoadCompleteEvent evt) {
    	int i = 0;
		Network.messageBuilder(PlayNotePacket.class, i++)
				.encoder(PlayNotePacket::encode)
				.decoder(PlayNotePacket::decode)
				.consumerMainThread(PlayNotePacket::handle);
		Network.messageBuilder(UpdateDataPacket.class, i++)
				.encoder(UpdateDataPacket::encode)
				.decoder(UpdateDataPacket::decode)
				.consumerMainThread(UpdateDataPacket::handle);
        //Network.registerMessage(i++, PlayNotePacket.class, PlayNotePacket::encode, PlayNotePacket::decode, PlayNotePacket::handle);
        //Network.registerMessage(i++, UpdateDataPacket.class, UpdateDataPacket::encode, UpdateDataPacket::decode, UpdateDataPacket::handle);
    	System.out.println("Builders Addition Initialized!");
    }

	public void addCreative(BuildCreativeModeTabContentsEvent event) {
		if(event.getTabKey() != CreativeModeTabRegistry.MAIN_TAB.getKey()) return;

		Index.ITEMS.getEntries().forEach(event::accept);
	}
}
