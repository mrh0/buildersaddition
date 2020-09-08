package com.mrh0.buildersaddition.state;

import net.minecraft.util.IStringSerializable;

public enum PlanterState implements IStringSerializable {
	Dirt("dirt"),
	Farmland("farmland");

	private String name;
	
	private PlanterState(String name) {
		this.name = name;
	}
	
	/*@Override
	public String getName() {
		return this.name;
	}*/

	@Override
	public String getString() {
		return this.name;
	}
}

