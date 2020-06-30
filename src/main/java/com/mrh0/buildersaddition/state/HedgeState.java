package com.mrh0.buildersaddition.state;

import net.minecraft.util.IStringSerializable;

public enum HedgeState implements IStringSerializable {
	None("none"),
	Straight_X("straight_x"),
	Straight_Z("straight_z"),
	Corner_NW("corner_nw"),
	Corner_NE("corner_ne"),
	Corner_SE("corner_se"),
	Corner_SW("corner_sw"),
	TCross_N("tcross_n"),
	TCross_E("tcross_e"),
	TCross_S("tcross_s"),
	TCross_W("tcross_w"),
	Cross("cross");

	private String name;
	
	private HedgeState(String name) {
		this.name = name;
	}
	
	/*@Override
	public String getName() {
		return this.name;
	}*/

	@Override
	public String func_176610_l() {
		return this.name;
	}
}