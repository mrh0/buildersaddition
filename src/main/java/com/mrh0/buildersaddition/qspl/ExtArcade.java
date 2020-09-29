package com.mrh0.buildersaddition.qspl;

import com.mrh0.qspl.type.TString;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.func.Arguments;
import com.mrh0.qspl.type.func.IFunc;
import com.mrh0.qspl.type.func.InternalFunc;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.vm.VM;
import com.mrh0.qspl.vm.module.Module;
import com.mrh0.qspl.vm.module.ModuleScope;

public class ExtArcade implements Module {

	private QSPLArcadeGame game;
	
	public ExtArcade(QSPLArcadeGame game) {
		this.game = game;
	}
	
	@Override
	public String getName() {
		return "arcade";
	}

	@Override
	public String getAuthor() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public void extend(ModuleScope ext) {
		ext.export("VERSION", new TNumber(0.1d));
		
		ext.export("WIDTH", new TNumber(game.s.width));
		ext.export("HEIGHT", new TNumber(game.s.height));
		
		ext.export("NOTE_BASS", new TNumber(0));
		ext.export("NOTE_HAT", new TNumber(2));
		ext.export("NOTE_BIT", new TNumber(12));
		
		ext.export("BLACK", new TNumber(0));
		ext.export("DARK_BLUE", new TNumber(1));
		ext.export("GREEN", new TNumber(2));
		ext.export("CYAN", new TNumber(3));
		ext.export("RED", new TNumber(4));
		ext.export("PURPLE", new TNumber(5));
		ext.export("ORANGE", new TNumber(6));
		ext.export("LIGHT_GRAY", new TNumber(7));
		ext.export("DARK_GRAY", new TNumber(8));
		ext.export("LIGHT_BLUE", new TNumber(9));
		ext.export("LIME", new TNumber(10));
		ext.export("AQUA", new TNumber(11));
		ext.export("PINK", new TNumber(12));
		ext.export("MAGENTA", new TNumber(13));
		ext.export("YELLOW", new TNumber(14));
		ext.export("WHITE", new TNumber(15));
		
		IFunc f;
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() < 3)
				return TUndefined.getInstance();
			game.s.print(TNumber.integer(args.get(0)), TNumber.integer(args.get(1)), TString.string(args.get(2)));
			return TUndefined.getInstance();
		};
		ext.export("print", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() < 2)
				return TUndefined.getInstance();
			return TNumber.create(game.s.getBackground(TNumber.integer(args.get(0)), TNumber.integer(args.get(1))));
		};
		ext.export("getBg", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() < 2)
				return TUndefined.getInstance();
			return TNumber.create(game.s.getForeground(TNumber.integer(args.get(0)), TNumber.integer(args.get(1))));
		};
		ext.export("getFg", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() < 1)
				return TUndefined.getInstance();
			game.s.setBg(TNumber.integer(args.get(0)));
			return TUndefined.getInstance();
		};
		ext.export("setBg", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() < 1)
				return TUndefined.getInstance();
			game.s.setFg(TNumber.integer(args.get(0)));
			return TUndefined.getInstance();
		};
		ext.export("setFg", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() < 2)
				return TUndefined.getInstance();
			return new TString(game.s.getChar(TNumber.integer(args.get(0)), TNumber.integer(args.get(1)))+"");
		};
		ext.export("getChar", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() < 2)
				return TUndefined.getInstance();
			game.playSound(TNumber.integer(args.get(0)), TNumber.integer(args.get(1)));
			return TUndefined.getInstance();
		};
		ext.export("playSound", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 1) {
				game.s.clear();
			}
			else if(args.size() >= 4) {
				game.s.clear(TNumber.integer(args.get(0)), TNumber.integer(args.get(1)),
						TNumber.integer(args.get(2)), TNumber.integer(args.get(3)));
			}
			return TUndefined.getInstance();
		};
		ext.export("clear", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() < 2)
				return TUndefined.getInstance();
			game.s.setColors(TNumber.integer(args.get(0)), TNumber.integer(args.get(1)));
			return TUndefined.getInstance();
		};
		ext.export("setColors", new InternalFunc(f));
	}
}
