package com.mrh0.buildersaddition.arcade.games;

import com.mrh0.buildersaddition.arcade.ArcadeGame;
import com.mrh0.buildersaddition.arcade.ArcadeManager;
import com.mrh0.buildersaddition.arcade.ArcadeScreen;
import com.mrh0.buildersaddition.gui.ArcadeGui;
import com.mrh0.buildersaddition.tileentity.ArcadeTileEntity;

import net.minecraft.util.text.TranslationTextComponent;

public class ArcadeMenu extends ArcadeGame {

	public ArcadeGui gui = null;
	public ArcadeMenu(ArcadeScreen s, ArcadeTileEntity te) {
		super(s, te);
	}
	
	int selected;
	int size = ArcadeManager.instance.getGames().size();
	
	@Override
	public void start() {
		super.start();
		selected = 0;
		render();
	}
	
	private void logo() {
		String l1 = "  ___                    _      "; 
		String l2 = " / _ \\                  | |     "; 
		String l3 = "/ /_\\ \\_ __ ___ __ _  __| | ___ ";
		String l4 = "|  _  | '__/ __/ _` |/ _` |/ _ \\"; 
		String l5 = "| | | | | | (_| (_| | (_| |  __/";
		String l6 = "\\_| |_/_|  \\___\\__,_|\\__,_|\\___|";
		
		int x = s.width / 2 - (l4.length() / 2);
		
		s.print(x, 0, l1);
		s.print(x, 1, l2);
		s.print(x, 2, l3);
		s.print(x, 3, l4);
		s.print(x, 4, l5);
		s.print(x, 5, l6);
		
		s.print(1, s.height-3, new TranslationTextComponent("arcade.buildersaddition.menu.select")); //"Select a game with [UP] and [DOWN]"
		s.print(1, s.height-2, new TranslationTextComponent("arcade.buildersaddition.menu.start")); //"Start the game with [ENTER] or [SPACE]"
	}
	
	private void render() {
		s.setColors(0x0, 0xf);
		s.clear();
		logo();
		for(int i = 0; i < size; i++) {
			s.setColors(0x0, 0xf);
			if(selected == i)
				s.setColors(0x0, 0x6);
			s.print(1, i + 8, (selected == i ? ">":" ") + ArcadeManager.instance.getGameName(i));
		}
	}
	
	@Override
	public void onKeyPressed(int key) {
		super.onKeyPressed(key);
		if(isUp(key) && selected > 0)
			selected--;
		if(isDown(key) && selected < size-1)
			selected++;
		render();
		if(isEnter(key) || isSpace(key)) {
			System.out.println();
			if(gui != null)
				gui.setGame(ArcadeManager.instance.getGame(selected));
		}
	}
}
