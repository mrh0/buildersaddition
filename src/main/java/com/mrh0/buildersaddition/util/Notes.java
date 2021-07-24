package com.mrh0.buildersaddition.util;

import com.mrh0.buildersaddition.config.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;

public class Notes {
	public static int getNoteFromKey(int key) {
		switch(key) {
			case 65:
				return 1;
			case 90:
				return 3;
			case 83:
				return 5;
			case 88:
				return 6;
			case 68:
				return 8;
			case 67:
				return 10;
			case 70:
				return 11;
			case 86:
				return 13;
			case 71:
				return 15;
			case 66:
				return 17;
			case 72:
				return 18;
			case 78:
				return 20;
			case 74:
				return 22;
			case 77:
				return 23;//
			case 81:
				return 0;
			case 87:
				return 2;
			case 69:
				return 4;
			case 82:
				return 7;
			case 84:
				return 9;
			case 89:
				return 12;
			case 85:
				return 14;
			case 73:
				return 16;
			case 79:
				return 19;
			case 80:
				return 21;
			case 75:
				return 24;
			default:
				return -1;
		}
	}
	
	/*public static void playClientNote(SoundEvent evt, int note) {
		if(note < 0 && note >= 48)
			return;
		float f = (float)Math.pow(2.0D, (double)(note - 12) / 12.0D);
		Minecraft.getInstance().getSoundManager().play(SimpleSound.master(evt, f));
	}*/
	
	public static final String[] instrumentNames = {"bass", "snare", "hat", "basedrum", "bell", "flute", "chime", "guitar", 
			"xylophone", "iron_xylophone", "cow_bell", "didgeridoo", "bit", "banjo", "pling", "harp"};
	
	public static final String[] octaveNames = {"1-3", "1-3", "1-3", "1-3", "5-7", "4-6", "5-7", "2-4", 
			"5-7", "3-5", "4-6", "1-3", "3-5", "3-5", "3-5", "3-5"};
	
	public static final String[] noteKeys = {"F# [Q]", "G [A]", "G# [W]", "A [Z]", "A# [E]", "B [S]", "C [X]", "C# [R]", "D [D]", "D# [T]", "E [C]",
			"F [F]", "F# [Y]", "G [V]", "G# [U]", "A [G]", "A# [I]", "B [B]", "C [H]", "C# [O]", "D [N]", "D# [P]", "E [J]", "F [M]", "F# [K]"};
}
