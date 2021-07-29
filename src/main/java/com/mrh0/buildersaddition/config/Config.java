package com.mrh0.buildersaddition.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class Config {
	public static final String CATAGORY_GENERAL = "general";
	public static final String CATAGORY_MIDI = "midi";
	
	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	
	public static ForgeConfigSpec COMMON_CONFIG;
	
	public static ForgeConfigSpec.BooleanValue MIDI_ENABLED;
	public static ForgeConfigSpec.BooleanValue MIDI_INPUT_ENABLED;
	public static ForgeConfigSpec.BooleanValue MIDI_SOUND_ENABLED;
	public static ForgeConfigSpec.IntValue MIDI_NOTES_PER_SECOND;
	
	public static ForgeConfigSpec.BooleanValue INVENTORY_ACCESS_BLOCK_CHECK;
	public static ForgeConfigSpec.BooleanValue REQUIRE_FLINT_AND_STEEL;
	
	public static ForgeConfigSpec.BooleanValue PATHBLOCK_CREATION_ENABLED;
	public static ForgeConfigSpec.BooleanValue CRACKED_CREATION_ENABLED;
	
	public static ForgeConfigSpec.BooleanValue PILLARS_CONNECT_ALL;
	
	static {
		
		/*COMMON_BUILDER.comment("General Settings").push(CATAGORY_GENERAL);
		MOD_ENABLE = COMMON_BUILDER.comment("Enable or disable Musicians Instruments & Additions")
				.define("mod_enable", true);
		COMMON_BUILDER.pop();*/
		
		COMMON_BUILDER.comment("General Settings").push(CATAGORY_GENERAL);
		
		INVENTORY_ACCESS_BLOCK_CHECK = COMMON_BUILDER.comment("Enable storage checking for blocked access.")
				.define("inv_access_block_check", true);
		
		REQUIRE_FLINT_AND_STEEL = COMMON_BUILDER.comment("Whether Flint & Steel is required to light a candle.")
				.define("require_flint_and_steel", true);
		
		PATHBLOCK_CREATION_ENABLED = COMMON_BUILDER.comment("Whether the creation of pathblocks (added by this mod) using a shovel is enabled.")
				.define("pathblock_creation_enabled", true);
		
		CRACKED_CREATION_ENABLED = COMMON_BUILDER.comment("Whether the creation of cracked block variants using a pickaxe is enabled.")
				.define("cracked_creation_enabled", true);
		
		PILLARS_CONNECT_ALL = COMMON_BUILDER.comment("If set true, pillars will connecto to all other pillar types.")
				.define("pillars_connect_all", false);

		COMMON_BUILDER.pop();
		
		COMMON_BUILDER.comment("Midi Settings").push(CATAGORY_MIDI);
		
		MIDI_ENABLED = COMMON_BUILDER.comment("Enable server MIDI.")
				.define("midi_enable", true);
		
		MIDI_INPUT_ENABLED = COMMON_BUILDER.comment("Enable MIDI input.")
				.define("midi_input_enable", false);
		
		MIDI_SOUND_ENABLED = COMMON_BUILDER.comment("Enable MIDI client sound.")
				.define("midi_sound_enable", true);
		
		MIDI_NOTES_PER_SECOND = COMMON_BUILDER.comment("Max number of notes played per 20 ticks.")
				.defineInRange("midi_note_limit", 75, 0, 200);
		
		COMMON_BUILDER.pop();
		
		COMMON_CONFIG = COMMON_BUILDER.build();
	}
	
	public static void loadConfig(ForgeConfigSpec spec, java.nio.file.Path path) {
		final CommentedFileConfig configData = CommentedFileConfig.builder(path)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();
		configData.load();
		spec.setConfig(configData);
	}
}
