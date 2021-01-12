package com.mrh0.buildersaddition.state;

import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

public enum VerticalSlabState implements IStringSerializable {
    NORTH(0, "north", Direction.NORTH),
    WEST(1, "west", Direction.WEST),
    EAST(2, "east", Direction.EAST),
    SOUTH(3, "south", Direction.SOUTH),
	DOUBLEX(4, "double_x", Direction.EAST),
	DOUBLEZ(5, "double_z", Direction.NORTH);
	
    private static final VerticalSlabState[] META_LOOKUP = new VerticalSlabState[values().length];
    private final int meta;
    private final String name;
    private final Direction facing;

    private VerticalSlabState(int meta, String name, Direction facing) {
        this.meta = meta;
        this.name = name;
        this.facing = facing;
    }

    public int getMetadata() {
        return this.meta;
    }

    public Direction getFacing() {
        return this.facing;
    }

    public String toString() {
        return this.name;
    }

    public static VerticalSlabState byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length)
        {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    public static VerticalSlabState forFacings(Direction clickedSide, Direction entityFacing) {
        if(clickedSide == Direction.UP || clickedSide == Direction.DOWN) {
        	if(entityFacing == Direction.NORTH)
        		return VerticalSlabState.NORTH;
        	if(entityFacing == Direction.EAST)
        		return VerticalSlabState.EAST;
        	if(entityFacing == Direction.SOUTH)
        		return VerticalSlabState.SOUTH;
        	if(entityFacing == Direction.WEST)
        		return VerticalSlabState.WEST;
        }
        if(clickedSide == Direction.NORTH) {
        	return VerticalSlabState.SOUTH;
        }
        if(clickedSide == Direction.EAST) {
        	return VerticalSlabState.WEST;
        }
        if(clickedSide == Direction.SOUTH) {
        	return VerticalSlabState.NORTH;
        }
        if(clickedSide == Direction.WEST) {
        	return VerticalSlabState.EAST;
        }
        return VerticalSlabState.NORTH;
    }

    /*public String getName() {
        return this.name;
    }*/

    static {
        for (VerticalSlabState d : values()) {
            META_LOOKUP[d.getMetadata()] = d;
        }
    }

	@Override
	public String getString() {
		return this.name;
	}
}