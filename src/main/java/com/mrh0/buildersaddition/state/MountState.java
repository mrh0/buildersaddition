package com.mrh0.buildersaddition.state;

import com.mrh0.buildersaddition.blocks.Pillar;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public enum MountState implements IStringSerializable {
	FullBlock("full"),
	Pillar("pillar"),
	Wall("wall"),
	Fence("fence");
	
	
	private String name;
	
	private MountState(String name) {
		this.name = name;
	}
	
	@Override
	public String getString() {
		return this.name;
	}
	
	public static MountState getFor(Direction face, BlockPos pos, World world) {
		Block b = world.getBlockState(pos.offset(face.getOpposite())).getBlock();
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
}
