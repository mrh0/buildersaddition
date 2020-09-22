package com.mrh0.buildersaddition.qspl;

import com.mrh0.qspl.vm.module.Module;
import com.mrh0.qspl.vm.module.ModuleScope;

public class ExtArcade implements Module {

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
		
	}
}
