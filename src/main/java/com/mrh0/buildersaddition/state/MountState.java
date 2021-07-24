package com.mrh0.buildersaddition.state;

import com.mrh0.buildersaddition.blocks.Pillar;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.WallBlock;

public enum MountState implements StringRepresentable {
	FullBlock("full"),
	Pillar("pillar"),
	Wall("wall"),
	Fence("fence");
	
	
	private String name;
	
	private MountState(String name) {
		this.name = name;
	}
	
	public static MountState getFor(Direction face, BlockPos pos, Level world) {
		Block b = world.getBlockState(pos.relative(face.getOpposite())).getBlock();
		if(b instanceof Pillar) {
			return Pillar;
		}
		else if(b instanceof WallBlock) {
			return Wall;
		}
		else if(b instanceof FenceBlock) {
			return Fence;
		}
		return FullBlock;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
