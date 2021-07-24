package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.blocks.base.ISeat;
import com.mrh0.buildersaddition.entity.SeatEntity;
import com.mrh0.buildersaddition.state.BenchState;
import com.mrh0.buildersaddition.state.SofaState;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


public class Bench extends BaseDerivativeBlock implements ISeat {

	public static final EnumProperty<BenchState> STATE = EnumProperty.<BenchState>create("state", BenchState.class);

	protected static final VoxelShape SHAPE_X = Block.box(0d, 7d, 2d, 16d, 9d, 14d);
	protected static final VoxelShape SHAPE_Z = Block.box(2d, 7d, 0d, 14d, 9d, 16d);
	
	protected static final VoxelShape SHAPE_NORTH = Block.box(3d, 0d, 1d, 13d, 7d, 3d);
	protected static final VoxelShape SHAPE_SOUTH = Block.box(3d, 0d, 13d, 13d, 7d, 15d);
	
	protected static final VoxelShape SHAPE_WEST = Block.box(1d, 0d, 3d, 3d, 7d, 13d);
	protected static final VoxelShape SHAPE_EAST = Block.box(13d, 0d, 3d, 15d, 7d, 13d);

	public Bench(String name) {
		super("bench_" + name, Blocks.OAK_PLANKS);
		this.registerDefaultState(this.defaultBlockState().setValue(STATE, BenchState.Both_X));
	}
	
	public Bench(String name, Block source) {
		super("bench_" + name, source);
		this.registerDefaultState(this.defaultBlockState().setValue(STATE, BenchState.Both_X));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(STATE);
	}

	public VoxelShape getShape(BlockState state) {
		VoxelShape shape = SHAPE_X;
		BenchState bs = state.getValue(STATE);
		if(bs.getAxis() == Axis.Z)
			shape = SHAPE_Z;
		if(bs.hasLegNorth())
			shape = Shapes.or(shape, SHAPE_NORTH);
		if(bs.hasLegWest())
			shape = Shapes.or(shape, SHAPE_WEST);
		if(bs.hasLegSouth())
			shape = Shapes.or(shape, SHAPE_SOUTH);
		if(bs.hasLegEast())
			shape = Shapes.or(shape, SHAPE_EAST);
		return shape;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return getShape(state);
	}
	
	public boolean connects(Axis current, BlockGetter worldIn, BlockPos pos, Direction dir) {
		BlockState state = worldIn.getBlockState(pos.relative(dir));
		if(state.getBlock() instanceof Bench) {
			return state.getValue(STATE).getAxis() == current;
		}
		return false;
	}
	
	public BlockState getState(Axis current, BlockGetter worldIn, BlockPos pos) {
		if(current == Axis.Z) {
			boolean n = connects(current, worldIn, pos, Direction.NORTH);
			boolean s = connects(current, worldIn, pos, Direction.SOUTH);
			if(n && !s)
				return this.defaultBlockState().setValue(STATE, BenchState.North);
			if(!n && s)
				return this.defaultBlockState().setValue(STATE, BenchState.South);
			if(n && s)
				return this.defaultBlockState().setValue(STATE, BenchState.None_Z);
			return this.defaultBlockState().setValue(STATE, BenchState.Both_Z);
		}
		boolean w = connects(current, worldIn, pos, Direction.WEST);
		boolean e = connects(current, worldIn, pos, Direction.EAST);
		if(e && !w)
			return this.defaultBlockState().setValue(STATE, BenchState.East);
		if(!e && w)
			return this.defaultBlockState().setValue(STATE, BenchState.West);
		if(e && w)
			return this.defaultBlockState().setValue(STATE, BenchState.None_X);
		return this.defaultBlockState().setValue(STATE, BenchState.Both_X);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		if(c.isSecondaryUseActive()) {
			return defaultBlockState().setValue(STATE, c.getHorizontalDirection().getClockWise().getAxis() == Axis.X ? BenchState.Both_X : BenchState.Both_Z);
		}
		return getState(c.getHorizontalDirection().getClockWise().getAxis(), c.getLevel(), c.getClickedPos());
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction p_60542_, BlockState p_60543_, LevelAccessor worldIn, BlockPos pos, BlockPos p_60546_) {
		return getState(stateIn.getValue(STATE).getAxis(), worldIn, pos);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
			InteractionHand p_60507_, BlockHitResult p_60508_) {
		return SeatEntity.createSeat(worldIn, pos, player, SoundEvents.WOOD_HIT);
	}
}
