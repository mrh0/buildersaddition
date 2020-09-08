package com.mrh0.buildersaddition.state;

import com.mrh0.buildersaddition.blocks.Pillar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.Direction.Axis;

public enum ShopSignState implements IStringSerializable {
	Up_X("up_x"),
	Up_Z("up_z"),
	Down_X("down_x"),
	Down_Z("down_z"),
	North("north"),
	East("east"),
	South("south"),
	West("west"),
	
	North_Pillar("north_pillar"),
	East_Pillar("east_pillar"),
	South_Pillar("south_pillar"),
	West_Pillar("west_pillar"),
	
	North_Wall("north_wall"),
	East_Wall("east_wall"),
	South_Wall("south_wall"),
	West_Wall("west_wall"),
	
	North_Fence("north_fence"),
	East_Fence("east_fence"),
	South_Fence("south_fence"),
	West_Fence("west_fence");

	private String name;
	
	private ShopSignState(String name) {
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
	
	public static ShopSignState getFor(Direction face, Direction facing, BlockPos pos, World world) {
		Block b = world.getBlockState(pos.offset(face.getOpposite())).getBlock();
		switch(face) {
			case NORTH:
				return getForBlock(face, b);
			case EAST:
				return getForBlock(face, b);
			case SOUTH:
				return getForBlock(face, b);
			case WEST:
				return getForBlock(face, b);
			case UP:
				return facing.getAxis() == Axis.X ? Up_X : Up_Z;
			case DOWN:
				return facing.getAxis() == Axis.X ? Down_X : Down_Z;
		}
		return North;
	}
	
	private static ShopSignState getForBlock(Direction d, Block b) {
		if(b instanceof Pillar) {
			switch(d) {
				case NORTH:
					return North_Pillar;
				case EAST:
					return East_Pillar;
				case SOUTH:
					return South_Pillar;
				case WEST:
					return West_Pillar;
			}
		}
		else if(b instanceof WallBlock) {
			switch(d) {
				case NORTH:
					return North_Wall;
				case EAST:
					return East_Wall;
				case SOUTH:
					return South_Wall;
				case WEST:
					return West_Wall;
			}
		}
		else if(b instanceof FenceBlock) {
			switch(d) {
				case NORTH:
					return North_Fence;
				case EAST:
					return East_Fence;
				case SOUTH:
					return South_Fence;
				case WEST:
					return West_Fence;
			}
		}
		switch(d) {
			case NORTH:
				return North;
			case EAST:
				return East;
			case SOUTH:
				return South;
			case WEST:
				return West;
		}
		return North;
	}
	
	public Axis getAxis() {
		if(this == East || this == West || this == Up_X || this == Down_X || this == East_Pillar || this == West_Pillar || this == East_Wall || this == West_Wall || this == East_Fence || this == West_Fence)
			return Axis.X;
		return Axis.Z;
	}
	
	public boolean isPillar() {
		return this == North_Pillar || this == East_Pillar || this == South_Pillar || this == West_Pillar;
	}
	
	public boolean isWall() {
		return this == North_Wall || this == East_Wall || this == South_Wall || this == West_Wall;
	}
	
	public boolean isFence() {
		return this == North_Fence || this == East_Fence || this == South_Fence || this == West_Fence;
	}
	
	public boolean isNormal() {
		return !isPillar() && !isWall() && !isFence();
	}
	
	public float getOffset() {
		if(isPillar())
			return 2f;
		if(isWall())
			return 4f;
		if(isFence())
			return 6f;
		return 0f;
	}
	
	public Direction getDirection() {
		switch(this) {
			case Up_X:
				return Direction.UP;
			case Up_Z:
				return Direction.UP;
			case Down_X:
				return Direction.DOWN;
			case Down_Z:
				return Direction.DOWN;
			case North:
				return Direction.NORTH;
			case East:
				return Direction.EAST;
			case South:
				return Direction.SOUTH;
			case West:
				return Direction.WEST;
				
			case North_Pillar:
				return Direction.NORTH;
			case East_Pillar:
				return Direction.EAST;
			case South_Pillar:
				return Direction.SOUTH;
			case West_Pillar:
				return Direction.WEST;
				
			case North_Wall:
				return Direction.NORTH;
			case East_Wall:
				return Direction.EAST;
			case South_Wall:
				return Direction.SOUTH;
			case West_Wall:
				return Direction.WEST;
				
			case North_Fence:
				return Direction.NORTH;
			case East_Fence:
				return Direction.EAST;
			case South_Fence:
				return Direction.SOUTH;
			case West_Fence:
				return Direction.WEST;
		default:
			break;
		}
		return Direction.NORTH;
	}
	
	public boolean isHorizontal() {
		return this != Up_X && this != Up_Z && this != Down_X && this != Down_Z;
	}
}
