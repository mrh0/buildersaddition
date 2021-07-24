package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.state.MountState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import DirectionProperty;

public class SupportBracket extends BaseDerivativeBlock {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final DirectionProperty SUPPORTING = DirectionProperty.create("supporting", p -> p == Direction.UP || p == Direction.DOWN);
	public static final EnumProperty<MountState> MOUNT = EnumProperty.<MountState>create("mount", MountState.class);
	
	//Full
	protected static final VoxelShape SHAPE_UP_SOUTH = Block.makeCuboidShape(6d, 4d, 4d, 10d, 16d, 16d);
	protected static final VoxelShape SHAPE_UP_NORTH = Block.makeCuboidShape(6d, 4d, 0d, 10d, 16d, 12d);
	
	protected static final VoxelShape SHAPE_UP_EAST = Block.makeCuboidShape(4d, 4d, 6d, 16d, 16d, 10d);
	protected static final VoxelShape SHAPE_UP_WEST = Block.makeCuboidShape(0d, 4d, 6d, 12d, 16d, 10d);
	
	protected static final VoxelShape SHAPE_DOWN_SOUTH = Block.makeCuboidShape(6d, 0d, 4d, 10d, 12d, 16d);
	protected static final VoxelShape SHAPE_DOWN_NORTH = Block.makeCuboidShape(6d, 0d, 0d, 10d, 12d, 12d);
	
	protected static final VoxelShape SHAPE_DOWN_EAST = Block.makeCuboidShape(4d, 0d, 6d, 16d, 12d, 10d);
	protected static final VoxelShape SHAPE_DOWN_WEST = Block.makeCuboidShape(0d, 0d, 6d, 12d, 12d, 10d);
	
	//Pillar
	static int o1 = 2;
	protected static final VoxelShape SHAPE_UP_SOUTH_PILLAR = Block.makeCuboidShape(6d, 4d, 4d + o1, 10d, 16d, 16d + o1);
	protected static final VoxelShape SHAPE_UP_NORTH_PILLAR = Block.makeCuboidShape(6d, 4d, 0d - o1, 10d, 16d, 12d - o1);
	
	protected static final VoxelShape SHAPE_UP_EAST_PILLAR = Block.makeCuboidShape(4d + o1, 4d, 6d, 16d + o1, 16d, 10d);
	protected static final VoxelShape SHAPE_UP_WEST_PILLAR = Block.makeCuboidShape(0d - o1, 4d, 6d, 12d - o1, 16d, 10d);
	
	protected static final VoxelShape SHAPE_DOWN_SOUTH_PILLAR = Block.makeCuboidShape(6d, 0d, 4d + o1, 10d, 12d, 16d + o1);
	protected static final VoxelShape SHAPE_DOWN_NORTH_PILLAR = Block.makeCuboidShape(6d, 0d, 0d - o1, 10d, 12d, 12d - o1);
	
	protected static final VoxelShape SHAPE_DOWN_EAST_PILLAR = Block.makeCuboidShape(4d + o1, 0d, 6d, 16d + o1, 12d, 10d);
	protected static final VoxelShape SHAPE_DOWN_WEST_PILLAR = Block.makeCuboidShape(0d - o1, 0d, 6d, 12d - o1, 12d, 10d);
	
	//Wall
	static int o2 = 4;
	protected static final VoxelShape SHAPE_UP_SOUTH_WALL = Block.makeCuboidShape(6d, 4d, 4d + o2 -2, 10d, 16d, 16d + o2);
	protected static final VoxelShape SHAPE_UP_NORTH_WALL = Block.makeCuboidShape(6d, 4d, 0d - o2, 10d, 16d, 12d - o2 +2);
	
	protected static final VoxelShape SHAPE_UP_EAST_WALL = Block.makeCuboidShape(4d + o2 -2, 4d, 6d, 16d + o2, 16d, 10d);
	protected static final VoxelShape SHAPE_UP_WEST_WALL = Block.makeCuboidShape(0d - o2, 4d, 6d, 12d - o2 +2, 16d, 10d);
	
	protected static final VoxelShape SHAPE_DOWN_SOUTH_WALL = Block.makeCuboidShape(6d, 0d, 4d + o2 -2, 10d, 12d, 16d + o2);
	protected static final VoxelShape SHAPE_DOWN_NORTH_WALL = Block.makeCuboidShape(6d, 0d, 0d - o2, 10d, 12d, 12d - o2 +2);
	
	protected static final VoxelShape SHAPE_DOWN_EAST_WALL = Block.makeCuboidShape(4d + o2 -2, 0d, 6d, 16d + o2, 12d, 10d);
	protected static final VoxelShape SHAPE_DOWN_WEST_WALL = Block.makeCuboidShape(0d - o2, 0d, 6d, 12d - o2 +2, 12d, 10d);
	
	//Fence
	static int o3 = 6;
	protected static final VoxelShape SHAPE_UP_SOUTH_FENCE = Block.makeCuboidShape(6d, 4d, 4d + o3 -4, 10d, 16d, 16d + o3);
	protected static final VoxelShape SHAPE_UP_NORTH_FENCE = Block.makeCuboidShape(6d, 4d, 0d - o3, 10d, 16d, 12d - o3 +4);
	
	protected static final VoxelShape SHAPE_UP_EAST_FENCE = Block.makeCuboidShape(4d + o3 -4, 4d, 6d, 16d + o3, 16d, 10d);
	protected static final VoxelShape SHAPE_UP_WEST_FENCE = Block.makeCuboidShape(0d - o3, 4d, 6d, 12d - o3 +4, 16d, 10d);
	
	protected static final VoxelShape SHAPE_DOWN_SOUTH_FENCE = Block.makeCuboidShape(6d, 0d, 4d + o3 -4, 10d, 12d, 16d + o3);
	protected static final VoxelShape SHAPE_DOWN_NORTH_FENCE = Block.makeCuboidShape(6d, 0d, 0d - o3, 10d, 12d, 12d - o3 +4);
	
	protected static final VoxelShape SHAPE_DOWN_EAST_FENCE = Block.makeCuboidShape(4d + o3 -4, 0d, 6d, 16d + o3, 12d, 10d);
	protected static final VoxelShape SHAPE_DOWN_WEST_FENCE = Block.makeCuboidShape(0d - o3, 0d, 6d, 12d - o3 +4, 12d, 10d);
	
	public SupportBracket(String name) {
		super("support_bracket_" + name, Blocks.OAK_PLANKS);
		setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(SUPPORTING, Direction.UP).with(MOUNT, MountState.FullBlock));
	}
	
	public SupportBracket(String name, Block b) {
		super("support_bracket_" + name, b);
		setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(SUPPORTING, Direction.UP).with(MOUNT, MountState.FullBlock));
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
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch(state.get(FACING)) {
			case NORTH:
				return getShapeNorth(state.get(MOUNT), state.get(SUPPORTING) == Direction.UP);
			case EAST:
				return getShapeEast(state.get(MOUNT), state.get(SUPPORTING) == Direction.UP);
			case SOUTH:
				return getShapeSouth(state.get(MOUNT), state.get(SUPPORTING) == Direction.UP);
			case WEST:
				return getShapeWest(state.get(MOUNT), state.get(SUPPORTING) == Direction.UP);
		}
		return SHAPE_UP_NORTH;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING, SUPPORTING, MOUNT);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		Direction f = c.getFace();
		
		if(f == Direction.UP)
			return getDefaultState().with(FACING, c.getPlacementHorizontalFacing()).with(SUPPORTING, Direction.DOWN);
		if(f == Direction.DOWN)
			return getDefaultState().with(FACING, c.getPlacementHorizontalFacing()).with(SUPPORTING, Direction.UP);
		
		double flag = c.getHitVec().y - (double) c.getPos().getY() - .5d;
		return getDefaultState().with(FACING, f.getOpposite()).with(SUPPORTING, flag > 0 ? Direction.UP : Direction .DOWN).with(MOUNT, MountState.getFor(c.getFace(), c.getPos(), c.getWorld()));
	}
}
