package com.mrh0.buildersaddition.state;

import net.minecraft.util.StringRepresentable;

public enum PlanterState implements StringRepresentable {
	Dirt("dirt"),
	Farmland("farmland");

	private String name;
	
	private PlanterState(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}

