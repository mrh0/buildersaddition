package com.mrh0.buildersaddition.arcade.games;

import com.mrh0.buildersaddition.arcade.ArcadeGame;
import com.mrh0.buildersaddition.arcade.ArcadeScreen;
import com.mrh0.buildersaddition.tileentity.ArcadeTileEntity;

public class ArcadeCredits extends ArcadeGame {

	public ArcadeCredits(ArcadeScreen s, ArcadeTileEntity te) {
		super(s, te);
	}

	@Override
	public void start() {
		super.start();
		s.setColors(0x0, 0xf);
		s.clear();
		
		int ln = 1;
		s.print(1, ln++, "Builders Crafts & Additions");
		ln++;
		s.print(1, ln++, "Programming: MRH0");
		s.print(1, ln++, "Models: MRH0");
		s.print(1, ln++, "Textures: MRH0");
		s.print(1, ln++, "Translation: Mikeliro, Pancakes0228,");
		s.print(1, ln++, "Yupoman, alierenreis, spiderfromi");
		ln++;
		s.print(1, ln++, "Source:");
		s.print(1, ln++, "github.com/mrh0/buildersaddition/");
	}
}
