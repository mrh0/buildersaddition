package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.blocks.base.IConnects;
import com.mrh0.buildersaddition.config.Config;
import com.mrh0.buildersaddition.state.PillarState;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Pillar extends BaseDerivativeBlock implements SimpleWaterloggedBlock {
	
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<PillarState> STATE = EnumProperty.<PillarState>create("state", PillarState.class);
	protected static final AABB PILLAR = new AABB(2D/16D, 0.0D, 2D/16D, 14D/16D, 1D, 14D/16D);
	protected static final AABB PILLAR_NOT_CONNECTED = new AABB(1D/16D, 0.0D, 1D/16D, 15D/16D, 1D, 15D/16D);
	protected static final AABB PILLAR_BOTTOM = new AABB(1D/16D, 0.0D, 1D/16D, 15D/16D, 2D/16D, 15D/16D);
	protected static final AABB PILLAR_TOP = new AABB(1D/16D, 14D/16D, 1D/16D, 15D/16D, 1D, 15D/16D);
	
	private final int light;
	private final IConnects iconnect;

	public Pillar(String name, Block source, int light, IConnects connects) {
		super("cut_" + name + "_pillar", source);
		this.light = light;
		this.registerDefaultState(
				this.defaultBlockState().setValue(STATE, PillarState.Both).setValue(WATERLOGGED, Boolean.valueOf(false)));
		this.iconnect = connects;
	}
	
	public Pillar(String name, Block source, IConnects connects) {
		this(name, source, 0, connects);
	}
	
	public Pillar(String name, Block source, int light) {
		this(name, source, (state, b) -> state.getBlock() == b);
	}
	
	public Pillar(String name, Block source) {
		this(name, source, 0);
	}
	
	@Override
	public int getLightBlock(BlockState state, BlockGetter wolrd, BlockPos pos) {
		return (state.getValue(STATE) == PillarState.Top) ? light : 0;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(STATE, WATERLOGGED);
	}
	
	public VoxelShape getShape(BlockState state) {
		switch(state.getValue(STATE)) {
			case None:
				return Shapes.create(PILLAR);
			case Top:
				return Shapes.or(Shapes.create(PILLAR), Shapes.create(PILLAR_TOP));
			case Bottom:
				return Shapes.or(Shapes.create(PILLAR), Shapes.create(PILLAR_BOTTOM));
			case Both:
				return Shapes.create(PILLAR_NOT_CONNECTED);
		}
			
        return Shapes.create(PILLAR);
    }
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return getShape(state);
	}
	
	public BlockState getState(BlockState state, BlockGetter worldIn, BlockPos pos) {
		BlockState bstop = worldIn.getBlockState(pos.above());
		BlockState bsbottom = worldIn.getBlockState(pos.below());
		boolean top = !connects(bstop, this);
		boolean bottom = !connects(bsbottom, this);
		
		if(top && bottom) {
			return state.setValue(STATE, PillarState.Both);
		}
		else if(top) {
			return state.setValue(STATE, PillarState.Top);
		}
		else if(bottom) {
			return state.setValue(STATE, PillarState.Bottom);
		}
		return state.setValue(STATE, PillarState.None);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		return getState(this.defaultBlockState(), c.getLevel(), c.getClickedPos()).setValue(WATERLOGGED, 
				Boolean.valueOf(c.getLevel().getFluidState(c.getClickedPos()).getType() == Fluids.WATER));
	}
	
	public boolean connects(BlockState state, Block block) {
		return iconnect.connect(state, block) || connectsAll(state, block);
	}
	
	private boolean connectsAll(BlockState state, Block block) {
		return (state.getBlock() instanceof Pillar) && Config.PILLARS_CONNECT_ALL.get();
	}
	
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidStateIn) {
		return SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluidStateIn);
	}

	@Override
	public boolean canPlaceLiquid(BlockGetter world, BlockPos pos, BlockState state, Fluid fluidIn) {
		return SimpleWaterloggedBlock.super.canPlaceLiquid(world, pos, state, fluidIn);
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
		switch (type) {
		case LAND:
			return false;
		case WATER:
			return world.getFluidState(pos).is(FluidTags.WATER);
		case AIR:
			return false;
		default:
			return false;
		}
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState otherState, LevelAccessor world,
			BlockPos currentPos, BlockPos otherPos) {
		if(state.getValue(WATERLOGGED)) {
			world.m_186469_(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return getState(state, world, currentPos);
	}
}
