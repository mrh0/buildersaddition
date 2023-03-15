package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.blocks.base.ISeat;
import com.mrh0.buildersaddition.entity.SeatEntity;
import com.mrh0.buildersaddition.state.SofaState;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Sofa extends BaseDerivativeBlock implements ISeat {

	public static final EnumProperty<SofaState> STATE = EnumProperty.<SofaState>create("state", SofaState.class);
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	protected static final VoxelShape SHAPE_BASE = Block.box(0d, 2d, 0d, 16d, 9d, 16d);
	
	protected static final VoxelShape SHAPE_BACK_NORTH = Block.box(0d, 9d, 12d, 16d, 16d, 16d);
	protected static final VoxelShape SHAPE_BACK_EAST = Block.box(0d, 9d, 0d, 4d, 16d, 16d);
	protected static final VoxelShape SHAPE_BACK_SOUTH = Block.box(0d, 9d, 0d, 16d, 16d, 4d);
	protected static final VoxelShape SHAPE_BACK_WEST = Block.box(12d, 9d, 0d, 16d, 16d, 16d);
	
	protected static final VoxelShape SHAPE_ARM_NORTH = Block.box(0d, 9d, 0d, 16d, 14d, 2d);
	protected static final VoxelShape SHAPE_ARM_EAST = Block.box(14d, 9d, 0d, 16d, 14d, 16d);
	protected static final VoxelShape SHAPE_ARM_SOUTH = Block.box(0d, 9d, 14d, 16d, 14d, 16d);
	protected static final VoxelShape SHAPE_ARM_WEST = Block.box(0d, 9d, 0d, 2d, 14d, 16d);
	
	public Sofa(String name) {
		super("sofa_" + name, Blocks.WHITE_WOOL);
		this.registerDefaultState(this.defaultBlockState().setValue(STATE, SofaState.Both).setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(STATE, FACING);
	}
	
	private VoxelShape getArm(Direction dir) {
		switch(dir) {
			case NORTH:
				return SHAPE_ARM_NORTH;
			case EAST:
				return SHAPE_ARM_EAST;
			case SOUTH:
				return SHAPE_ARM_SOUTH;
			case WEST:
				return SHAPE_ARM_WEST;
		default:
			break;
		}
		return Shapes.empty();
	}

	public VoxelShape getShape(BlockState state) {
		VoxelShape shape = Shapes.empty();
		SofaState s = state.getValue(STATE);
		Direction d = state.getValue(FACING);
		if(s == SofaState.Left)
			shape = getArm(d.getCounterClockWise());
		if(s == SofaState.Right)
			shape = getArm(d.getClockWise());
		if(s == SofaState.Both)
			shape = Shapes.or(getArm(d.getClockWise()), getArm(d.getCounterClockWise()));
		switch(d) {
			case EAST:
				return Shapes.or(SHAPE_BASE, SHAPE_BACK_EAST, shape);
			case NORTH:
				return Shapes.or(SHAPE_BASE, SHAPE_BACK_NORTH, shape);
			case SOUTH:
				return Shapes.or(SHAPE_BASE, SHAPE_BACK_SOUTH, shape);
			case WEST:
				return Shapes.or(SHAPE_BASE, SHAPE_BACK_WEST, shape);
			default:
				return SHAPE_BASE;
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return getShape(state);
	}
	
	public boolean connects(Direction current, LevelAccessor worldIn, BlockPos pos, Direction dir, boolean disCheck) {
		BlockState adj = worldIn.getBlockState(pos.relative(dir));
		if(adj.getBlock() instanceof Sofa) {
			SofaState s = adj.getValue(STATE);
			Direction sd = adj.getValue(FACING);
			if((s == SofaState.Both || 
				(current == sd && dir == current.getClockWise() && s == SofaState.Left) ||
				(current == sd && dir == current.getCounterClockWise() && s == SofaState.Right)) && !disCheck)
				return false;
			return current == adj.getValue(FACING);
		}
		return false;
	}
	
	public BlockState getState(Direction dir, LevelAccessor worldIn, BlockPos pos, boolean disCheck) {
		boolean l = connects(dir, worldIn, pos, dir.getClockWise(), disCheck);
		boolean r = connects(dir, worldIn, pos, dir.getCounterClockWise(), disCheck);
		if(l && !r)
			return defaultBlockState().setValue(STATE, SofaState.Left).setValue(FACING, dir);
		if(!l && r)
			return defaultBlockState().setValue(STATE, SofaState.Right).setValue(FACING, dir);
		if(l && r)
			return defaultBlockState().setValue(STATE, SofaState.None).setValue(FACING, dir);
		return defaultBlockState().setValue(STATE, SofaState.Both).setValue(FACING, dir);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		if(c.isSecondaryUseActive()) {
			return defaultBlockState().setValue(FACING, c.getHorizontalDirection().getOpposite()).setValue(STATE, SofaState.Both);
		}
		return getState(c.getHorizontalDirection().getOpposite(), c.getLevel(), c.getClickedPos(), true);
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction dir, BlockState otherState, LevelAccessor world,
			BlockPos pos, BlockPos otherPos) {
		return getState(state.getValue(FACING), world, pos, false);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return SeatEntity.createSeat(world, pos, player, SoundEvents.WOOL_HIT);
	}
	
	@Override
	public void fallOn(Level world, BlockState state, BlockPos pos, Entity ent, float dis) {
		super.fallOn(world, state, pos, ent, dis * 0.5F);
	}
	
	public void updateEntityAfterFallOn(BlockGetter world, Entity entityIn) {
		if (entityIn.isSuppressingBounce())
			super.updateEntityAfterFallOn(world, entityIn);
		else
			this.bounceUp(entityIn);
	}

	private void bounceUp(Entity entity) {
		Vec3 vector3d = entity.getDeltaMovement();
		if (vector3d.y < 0.0D) {
			double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
			entity.setDeltaMovement(vector3d.x, -vector3d.y * (double)0.66F * d0, vector3d.z);
		}
	}
}
