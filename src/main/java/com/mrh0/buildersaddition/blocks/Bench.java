package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.blocks.base.ISeat;
import com.mrh0.buildersaddition.entity.SeatEntity;
import com.mrh0.buildersaddition.state.BenchState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class Bench extends BaseDerivativeBlock implements ISeat {

	public static final EnumProperty<BenchState> STATE = EnumProperty.<BenchState>create("state", BenchState.class);

	protected static final VoxelShape SHAPE_X = Block.makeCuboidShape(0d, 7d, 2d, 16d, 9d, 14d);
	protected static final VoxelShape SHAPE_Z = Block.makeCuboidShape(2d, 7d, 0d, 14d, 9d, 16d);
	
	protected static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(3d, 0d, 1d, 13d, 7d, 3d);
	protected static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(3d, 0d, 13d, 13d, 7d, 15d);
	
	protected static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(1d, 0d, 3d, 3d, 7d, 13d);
	protected static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(13d, 0d, 3d, 15d, 7d, 13d);

	public Bench(String name) {
		super("bench_" + name, Blocks.OAK_PLANKS);
		this.setDefaultState(this.getDefaultState().with(STATE, BenchState.Both_X));
	}
	
	public Bench(String name, Block source) {
		super("bench_" + name, source);
		this.setDefaultState(this.getDefaultState().with(STATE, BenchState.Both_X));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(STATE);
	}

	public VoxelShape getShape(BlockState state) {
		VoxelShape shape = SHAPE_X;
		BenchState bs = state.get(STATE);
		if(bs.getAxis() == Axis.Z)
			shape = SHAPE_Z;
		if(bs.hasLegNorth())
			shape = VoxelShapes.or(shape, SHAPE_NORTH);
		if(bs.hasLegWest())
			shape = VoxelShapes.or(shape, SHAPE_WEST);
		if(bs.hasLegSouth())
			shape = VoxelShapes.or(shape, SHAPE_SOUTH);
		if(bs.hasLegEast())
			shape = VoxelShapes.or(shape, SHAPE_EAST);
		return shape;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return getShape(state);
	}
	
	public boolean connects(Axis current, IWorld worldIn, BlockPos pos, Direction dir) {
		BlockState state = worldIn.getBlockState(pos.offset(dir));
		if(state.getBlock() instanceof Bench) {
			return state.get(STATE).getAxis() == current;
		}
		return false;
	}
	
	public BlockState getState(Axis current, IWorld worldIn, BlockPos pos) {
		if(current == Axis.Z) {
			boolean n = connects(current, worldIn, pos, Direction.NORTH);
			boolean s = connects(current, worldIn, pos, Direction.SOUTH);
			if(n && !s)
				return this.getDefaultState().with(STATE, BenchState.North);
			if(!n && s)
				return this.getDefaultState().with(STATE, BenchState.South);
			if(n && s)
				return this.getDefaultState().with(STATE, BenchState.None_Z);
			return this.getDefaultState().with(STATE, BenchState.Both_Z);
		}
		boolean w = connects(current, worldIn, pos, Direction.WEST);
		boolean e = connects(current, worldIn, pos, Direction.EAST);
		if(e && !w)
			return this.getDefaultState().with(STATE, BenchState.East);
		if(!e && w)
			return this.getDefaultState().with(STATE, BenchState.West);
		if(e && w)
			return this.getDefaultState().with(STATE, BenchState.None_X);
		return this.getDefaultState().with(STATE, BenchState.Both_X);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		return getState(c.getPlacementHorizontalFacing().rotateY().getAxis(), c.getWorld(), c.getPos());
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos pos, BlockPos otherPos) {
		return getState(stateIn.get(STATE).getAxis(), worldIn, pos);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		return SeatEntity.createSeat(worldIn, pos, player);
	}
}
