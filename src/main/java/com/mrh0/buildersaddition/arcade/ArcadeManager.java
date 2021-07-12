package com.mrh0.buildersaddition.arcade;

import java.util.ArrayList;
import java.util.List;

import com.mrh0.buildersaddition.arcade.games.ArcadeBreakout;
import com.mrh0.buildersaddition.arcade.games.ArcadeCredits;
import com.mrh0.buildersaddition.arcade.games.ArcadeSnake;
import com.mrh0.buildersaddition.tileentity.ArcadeTileEntity;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ArcadeManager {
	public interface GameConstructor {
		public ArcadeGame construct(ArcadeScreen s, ArcadeTileEntity te);
	}
	
	public static ArcadeManager instance = null;
	private final ArrayList<GameConstructor> games;
	private final ArrayList<ITextComponent> names;
	
	public ArcadeManager() {
		if(instance == null)
			instance = this;
		games = new ArrayList<GameConstructor>();
		names = new ArrayList<ITextComponent>();
	}
	
	public void add(GameConstructor game, String key) {
		games.add(game);
		names.add(new TranslationTextComponent("arcade.buildersaddition.game." + key));
	}
	
	public List<GameConstructor> getGames() {
		return this.games;
	}
	
	public GameConstructor getGame(int index) {
		return this.games.get(index);
	}
	
	public String getGameName(int index) {
		return this.names.get(index).getString();
	}
	
	public static void init() {
		new ArcadeManager();
		instance.add(ArcadeSnake::new, "snake");
		//instance.add(ArcadeBreakout::new, "breakout");
		instance.add(ArcadeCredits::new, "credits");
	}
}