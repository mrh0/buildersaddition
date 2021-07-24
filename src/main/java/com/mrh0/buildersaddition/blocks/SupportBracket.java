package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.state.MountState;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SupportBracket extends BaseDerivativeBlock {

	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final DirectionProperty SUPPORTING = DirectionProperty.create("supporting", p -> p == Direction.UP || p == Direction.DOWN);
	public static final EnumProperty<MountState> MOUNT = EnumProperty.<MountState>create("mount", MountState.class);
	
	//Full
	protected static final VoxelShape SHAPE_UP_SOUTH = Block.box(6d, 4d, 4d, 10d, 16d, 16d);
	protected static final VoxelShape SHAPE_UP_NORTH = Block.box(6d, 4d, 0d, 10d, 16d, 12d);
	
	protected static final VoxelShape SHAPE_UP_EAST = Block.box(4d, 4d, 6d, 16d, 16d, 10d);
	protected static final VoxelShape SHAPE_UP_WEST = Block.box(0d, 4d, 6d, 12d, 16d, 10d);
	
	protected static final VoxelShape SHAPE_DOWN_SOUTH = Block.box(6d, 0d, 4d, 10d, 12d, 16d);
	protected static final VoxelShape SHAPE_DOWN_NORTH = Block.box(6d, 0d, 0d, 10d, 12d, 12d);
	
	protected static final VoxelShape SHAPE_DOWN_EAST = Block.box(4d, 0d, 6d, 16d, 12d, 10d);
	protected static final VoxelShape SHAPE_DOWN_WEST = Block.box(0d, 0d, 6d, 12d, 12d, 10d);
	
	//Pillar
	static int o1 = 2;
	protected static final VoxelShape SHAPE_UP_SOUTH_PILLAR = Block.box(6d, 4d, 4d + o1, 10d, 16d, 16d + o1);
	protected static final VoxelShape SHAPE_UP_NORTH_PILLAR = Block.box(6d, 4d, 0d - o1, 10d, 16d, 12d - o1);
	
	protected static final VoxelShape SHAPE_UP_EAST_PILLAR = Block.box(4d + o1, 4d, 6d, 16d + o1, 16d, 10d);
	protected static final VoxelShape SHAPE_UP_WEST_PILLAR = Block.box(0d - o1, 4d, 6d, 12d - o1, 16d, 10d);
	
	protected static final VoxelShape SHAPE_DOWN_SOUTH_PILLAR = Block.box(6d, 0d, 4d + o1, 10d, 12d, 16d + o1);
	protected static final VoxelShape SHAPE_DOWN_NORTH_PILLAR = Block.box(6d, 0d, 0d - o1, 10d, 12d, 12d - o1);
	
	protected static final VoxelShape SHAPE_DOWN_EAST_PILLAR = Block.box(4d + o1, 0d, 6d, 16d + o1, 12d, 10d);
	protected static final VoxelShape SHAPE_DOWN_WEST_PILLAR = Block.box(0d - o1, 0d, 6d, 12d - o1, 12d, 10d);
	
	//Wall
	static int o2 = 4;
	protected static final VoxelShape SHAPE_UP_SOUTH_WALL = Block.box(6d, 4d, 4d + o2 -2, 10d, 16d, 16d + o2);
	protected static final VoxelShape SHAPE_UP_NORTH_WALL = Block.box(6d, 4d, 0d - o2, 10d, 16d, 12d - o2 +2);
	
	protected static final VoxelShape SHAPE_UP_EAST_WALL = Block.box(4d + o2 -2, 4d, 6d, 16d + o2, 16d, 10d);
	protected static final VoxelShape SHAPE_UP_WEST_WALL = Block.box(0d - o2, 4d, 6d, 12d - o2 +2, 16d, 10d);
	
	protected static final VoxelShape SHAPE_DOWN_SOUTH_WALL = Block.box(6d, 0d, 4d + o2 -2, 10d, 12d, 16d + o2);
	protected static final VoxelShape SHAPE_DOWN_NORTH_WALL = Block.box(6d, 0d, 0d - o2, 10d, 12d, 12d - o2 +2);
	
	protected static final VoxelShape SHAPE_DOWN_EAST_WALL = Block.box(4d + o2 -2, 0d, 6d, 16d + o2, 12d, 10d);
	protected static final VoxelShape SHAPE_DOWN_WEST_WALL = Block.box(0d - o2, 0d, 6d, 12d - o2 +2, 12d, 10d);
	
	//Fence
	static int o3 = 6;
	protected static final VoxelShape SHAPE_UP_SOUTH_FENCE = Block.box(6d, 4d, 4d + o3 -4, 10d, 16d, 16d + o3);
	protected static final VoxelShape SHAPE_UP_NORTH_FENCE = Block.box(6d, 4d, 0d - o3, 10d, 16d, 12d - o3 +4);
	
	protected static final VoxelShape SHAPE_UP_EAST_FENCE = Block.box(4d + o3 -4, 4d, 6d, 16d + o3, 16d, 10d);
	protected static final VoxelShape SHAPE_UP_WEST_FENCE = Block.box(0d - o3, 4d, 6d, 12d - o3 +4, 16d, 10d);
	
	protected static final VoxelShape SHAPE_DOWN_SOUTH_FENCE = Block.box(6d, 0d, 4d + o3 -4, 10d, 12d, 16d + o3);
	protected static final VoxelShape SHAPE_DOWN_NORTH_FENCE = Block.box(6d, 0d, 0d - o3, 10d, 12d, 12d - o3 +4);
	
	protected static final VoxelShape SHAPE_DOWN_EAST_FENCE = Block.box(4d + o3 -4, 0d, 6d, 16d + o3, 12d, 10d);
	protected static final VoxelShape SHAPE_DOWN_WEST_FENCE = Block.box(0d - o3, 0d, 6d, 12d - o3 +4, 12d, 10d);
	
	public SupportBracket(String name) {
		super("support_bracket_" + name, Blocks.OAK_PLANKS);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(SUPPORTING, Direction.UP).setValue(MOUNT, MountState.FullBlock));
	}
	
	public SupportBracket(String name, Block b) {
		super("support_bracket_" + name, b);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(SUPPORTING, Direction.UP).setValue(MOUNT, MountState.FullBlock));
	}
	
	private VoxelShape getShapeNorth(MountState s, boolean up) {
		switch(s) {
			case FullBlock: return up ? SHAPE_UP_NORTH : SHAPE_DOWN_NORTH;
			case Pillar: return up ? SHAPE_UP_NORTH_PILLAR : SHAPE_DOWN_NORTH_PILLAR;
			case Wall: return up ? SHAPE_UP_NORTH_WALL : SHAPE_DOWN_NORTH_WALL;
			case Fence: return up ? SHAPE_UP_NORTH_FENCE : SHAPE_DOWN_NORTH_FENCE;
		}
		return SHAPE_UP_NORTH;
	}
	
	private VoxelShape getShapeSouth(MountState s, boolean up) {
		switch(s) {
			case FullBlock: return up ? SHAPE_UP_SOUTH : SHAPE_DOWN_SOUTH;
			case Pillar: return up ? SHAPE_UP_SOUTH_PILLAR : SHAPE_DOWN_SOUTH_PILLAR;
			case Wall: return up ? SHAPE_UP_SOUTH_WALL : SHAPE_DOWN_SOUTH_WALL;
			case Fence: return up ? SHAPE_UP_SOUTH_FENCE : SHAPE_DOWN_SOUTH_FENCE;
		}
		return SHAPE_UP_NORTH;
	}
	
	private VoxelShape getShapeEast(MountState s, boolean up) {
		switch(s) {
			case FullBlock: return up ? SHAPE_UP_EAST : SHAPE_DOWN_EAST;
			case Pillar: return up ? SHAPE_UP_EAST_PILLAR : SHAPE_DOWN_EAST_PILLAR;
			case Wall: return up ? SHAPE_UP_EAST_WALL : SHAPE_DOWN_EAST_WALL;
			case Fence: return up ? SHAPE_UP_EAST_FENCE : SHAPE_DOWN_EAST_FENCE;
		}
		return SHAPE_UP_NORTH;
	}
	
	private VoxelShape getShapeWest(MountState s, boolean up) {
		switch(s) {
			case FullBlock: return up ? SHAPE_UP_WEST : SHAPE_DOWN_WEST;
			case Pillar: return up ? SHAPE_UP_WEST_PILLAR : SHAPE_DOWN_WEST_PILLAR;
			case Wall: return up ? SHAPE_UP_WEST_WALL : SHAPE_DOWN_WEST_WALL;
			case Fence: return up ? SHAPE_UP_WEST_FENCE : SHAPE_DOWN_WEST_FENCE;
		}
		return SHAPE_UP_NORTH;
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		switch(state.getValue(FACING)) {
			case NORTH:
				return getShapeNorth(state.getValue(MOUNT), state.getValue(SUPPORTING) == Direction.UP);
			case EAST:
				return getShapeEast(state.getValue(MOUNT), state.getValue(SUPPORTING) == Direction.UP);
			case SOUTH:
				return getShapeSouth(state.getValue(MOUNT), state.getValue(SUPPORTING) == Direction.UP);
			case WEST:
				return getShapeWest(state.getValue(MOUNT), state.getValue(SUPPORTING) == Direction.UP);
		}
		return SHAPE_UP_NORTH;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, SUPPORTING, MOUNT);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		Direction f = c.getClickedFace();
		
		if(f == Direction.UP)
			return defaultBlockState().setValue(FACING, c.getHorizontalDirection()).setValue(SUPPORTING, Direction.DOWN);
		if(f == Direction.DOWN)
			return defaultBlockState().setValue(FACING, c.getHorizontalDirection()).setValue(SUPPORTING, Direction.UP);
		
		double flag = c.getClickLocation().y - (double) c.getClickedPos().getY() - .5d;
		return defaultBlockState().setValue(FACING, f.getOpposite()).setValue(SUPPORTING, flag > 0 ? Direction.UP : Direction .DOWN)
				.setValue(MOUNT, MountState.getFor(c.getClickedFace(), c.getClickedPos(), c.getLevel()));
	}
}
