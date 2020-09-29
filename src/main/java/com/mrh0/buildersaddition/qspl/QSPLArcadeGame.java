package com.mrh0.buildersaddition.qspl;

import com.mrh0.buildersaddition.arcade.ArcadeGame;
import com.mrh0.buildersaddition.arcade.ArcadeScreen;
import com.mrh0.buildersaddition.tileentity.ArcadeTileEntity;
import com.mrh0.qspl.QSPL;
import com.mrh0.qspl.internal.StandardModules;
import com.mrh0.qspl.io.file.Read;
import com.mrh0.qspl.type.TContainer;
import com.mrh0.qspl.type.TString;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.func.Arguments;
import com.mrh0.qspl.type.func.TFunc;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.type.var.Var;
import com.mrh0.qspl.vm.module.Include;

public class QSPLArcadeGame extends ArcadeGame {
	
	private QSPL qspl;

	public QSPLArcadeGame(ArcadeScreen s, ArcadeTileEntity te) {
		super(s, te);
		init();
	}
	
	private void init() {
		this.qspl = new QSPL(Read.fromFile("C:\\MRHLang\\arcade.qs"), StandardModules::limited);
		TContainer included = Include.module(new ExtArcade(this));
		for(String akey : included.getKeys()) {
			qspl.getVM().setVariable(new Var(akey, included.get(akey)));
		}
		start();
	}
	
	private void func(String name, Arguments args) {
		Var v = qspl.getVM().getVariable(name);
		if(v == null)
			return;
		if(v.isUndefined())
			return;
		TFunc f = TFunc.from(v);
		f.execute(qspl.getVM(), TUndefined.getInstance(), args);
	}
	
	@Override
	public void frame(long time) {
		super.frame(time);
		func("frame", new Arguments().put(TNumber.create((int)time)));
	}
	
	@Override
	public void start() {
		super.start();
		func("start", new Arguments());
	}
	
	@Override
	public void onKeyPressed(int key) {
		super.onKeyPressed(key);
		if(isReset(key)) {
			s.setColors(0x4, 0x0);
			String msg = "[Reloading...]";
			s.print(s.width/2 - msg.length()/2, s.height/2, msg);
		}
		Arguments a = new Arguments();
		a.put(TNumber.create(key));
		a.put(new TString(getKeyName(key)));
		func("keyPressed", a);
	}
	
	@Override
	public void onKeyReleased(int key) {
		super.onKeyReleased(key);
		if(isReset(key)) {
			s.setColors(0x0, 0xf);
			s.clear();
			init();
			return;
		}
		Arguments a = new Arguments();
		a.put(TNumber.create(key));
		a.put(new TString(getKeyName(key)));
		func("keyReleased", a);
	}
	
	@Override
	public void onMousePressed(int key) {
		super.onMousePressed(key);
	}
	
	@Override
	public void onMouseReleased(int key) {
		super.onMouseReleased(key);
	}
	
	public ArcadeScreen getScreen() {
		return s;
	}
}
