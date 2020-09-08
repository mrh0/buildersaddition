package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.blocks.base.ISeat;
import com.mrh0.buildersaddition.entity.SeatEntity;
import com.mrh0.buildersaddition.state.SofaState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class Sofa extends BaseDerivativeBlock implements ISeat {

	public static final EnumProperty<SofaState> STATE = EnumProperty.<SofaState>create("state", SofaState.class);
	public static final DirectionProperty FACING = DirectionProperty.create("facing",
			p -> p.getIndex() > 1 && p.getIndex() < Direction.values().length);

	protected static final VoxelShape SHAPE_BASE = Block.makeCuboidShape(0d, 2d, 0d, 16d, 9d, 16d);
	
	protected static final VoxelShape SHAPE_BACK_NORTH = Block.makeCuboidShape(0d, 9d, 12d, 16d, 16d, 16d);
	protected static final VoxelShape SHAPE_BACK_EAST = Block.makeCuboidShape(0d, 9d, 0d, 4d, 16d, 16d);
	protected static final VoxelShape SHAPE_BACK_SOUTH = Block.makeCuboidShape(0d, 9d, 0d, 16d, 16d, 4d);
	protected static final VoxelShape SHAPE_BACK_WEST = Block.makeCuboidShape(12d, 9d, 0d, 16d, 16d, 16d);
	
	protected static final VoxelShape SHAPE_ARM_NORTH = Block.makeCuboidShape(0d, 9d, 0d, 16d, 14d, 2d);
	protected static final VoxelShape SHAPE_ARM_EAST = Block.makeCuboidShape(14d, 9d, 0d, 16d, 14d, 16d);
	protected static final VoxelShape SHAPE_ARM_SOUTH = Block.makeCuboidShape(0d, 9d, 14d, 16d, 14d, 16d);
	protected static final VoxelShape SHAPE_ARM_WEST = Block.makeCuboidShape(0d, 9d, 0d, 2d, 14d, 16d);
	
	public Sofa(String name) {
		super("sofa_" + name, Blocks.WHITE_WOOL);
		this.setDefaultState(this.getDefaultState().with(STATE, SofaState.Both).with(FACING, Direction.NORTH));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
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
		}
		return VoxelShapes.empty();
	}

	public VoxelShape getShape(BlockState state) {
		VoxelShape shape = VoxelShapes.empty();
		SofaState s = state.get(STATE);
		Direction d = state.get(FACING);
		if(s == SofaState.Left)
			shape = getArm(d.rotateY().rotateY().rotateY());
		if(s == SofaState.Right)
			shape = getArm(d.rotateY());
		if(s == SofaState.Both)
			shape = VoxelShapes.or(getArm(d.rotateY()), getArm(d.rotateY().rotateY().rotateY()));
		switch(d) {
			case EAST:
				return VoxelShapes.or(SHAPE_BASE, SHAPE_BACK_EAST, shape);
			case NORTH:
				return VoxelShapes.or(SHAPE_BASE, SHAPE_BACK_NORTH, shape);
			case SOUTH:
				return VoxelShapes.or(SHAPE_BASE, SHAPE_BACK_SOUTH, shape);
			case WEST:
				return VoxelShapes.or(SHAPE_BASE, SHAPE_BACK_WEST, shape);
			default:
				return SHAPE_BASE;
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return getShape(state);
	}
	
	public boolean connects(Direction current, IWorld worldIn, BlockPos pos, Direction dir, boolean disCheck) {
		BlockState adj = worldIn.getBlockState(pos.offset(dir));
		if(adj.getBlock() instanceof Sofa) {
			SofaState s = adj.get(STATE);
			Direction sd = adj.get(FACING);
			if((s == SofaState.Both || 
				(current == sd && dir == current.rotateY() && s == SofaState.Left) ||
				(current == sd && dir == current.rotateYCCW() && s == SofaState.Right)) && !disCheck)
				return false;
			return current == adj.get(FACING);
		}
		return false;
	}
	
	public BlockState getState(Direction dir, IWorld worldIn, BlockPos pos, boolean disCheck) {
		boolean l = connects(dir, worldIn, pos, dir.rotateY(), disCheck);
		boolean r = connects(dir, worldIn, pos, dir.rotateYCCW(), disCheck);
		if(l && !r)
			return getDefaultState().with(STATE, SofaState.Left).with(FACING, dir);
		if(!l && r)
			return getDefaultState().with(STATE, SofaState.Right).with(FACING, dir);
		if(l && r)
			return getDefaultState().with(STATE, SofaState.None).with(FACING, dir);
		return getDefaultState().with(STATE, SofaState.Both).with(FACING, dir);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		if(c.func_225518_g_()) {
			return getDefaultState().with(FACING, c.getPlacementHorizontalFacing().getOpposite()).with(STATE, SofaState.Both);
		}
		return getState(c.getPlacementHorizontalFacing().getOpposite(), c.getWorld(), c.getPos(), true);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos pos, BlockPos otherPos) {
		return getState(stateIn.get(FACING), worldIn, pos, false);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		return SeatEntity.createSeat(worldIn, pos, player);
	}
	
	
	//Bed Bounce
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		super.onFallenUpon(worldIn, pos, entityIn, fallDistance * 0.5F);
	}
	
	public void onLanded(IBlockReader worldIn, Entity entityIn) {
		if (entityIn.isSuppressingBounce())
			super.onLanded(worldIn, entityIn);
		else
			this.func_226860_a_(entityIn);
	}

	private void func_226860_a_(Entity entity) {
		Vector3d vector3d = entity.getMotion();
		if (vector3d.y < 0.0D) {
			double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
			entity.setMotion(vector3d.x, -vector3d.y * (double)0.66F * d0, vector3d.z);
		}
	}
}
