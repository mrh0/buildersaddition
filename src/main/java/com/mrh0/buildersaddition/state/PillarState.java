package com.mrh0.buildersaddition.state;

import net.minecraft.util.IStringSerializable;

public enum PillarState  implements IStringSerializable {
	None("none"),
	Bottom("bottom"),
	Top("top"),
	Both("both");

	private String name;
	
	private PillarState(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
}
