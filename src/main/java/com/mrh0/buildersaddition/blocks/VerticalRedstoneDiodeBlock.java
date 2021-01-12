package com.mrh0.buildersaddition.blocks;

import java.util.Random;
import com.mrh0.buildersaddition.blocks.base.BaseBlock;
import com.mrh0.buildersaddition.event.opts.BlockOptions;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class VerticalRedstoneDiodeBlock extends BaseBlock {
	public static final DirectionProperty VERTICAL_FACING = DirectionProperty.create("vertical_facing", d -> d.getAxis() == Axis.Y);
	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
	protected static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(14D, 0.0D, 0.0D, 16D, 16.0D, 16.0D);
	protected static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16D, 16D, 2D);
	protected static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 14D, 16D, 16D, 16D);
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	protected VerticalRedstoneDiodeBlock(String name, AbstractBlock.Properties builder) {
		super(name, builder, new BlockOptions().hasItem(false));
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch(state.get(HORIZONTAL_FACING)) {
			case EAST:
				return EAST_SHAPE;
			case NORTH:
				return NORTH_SHAPE;
			case SOUTH:
				return SOUTH_SHAPE;
			case WEST:
				return WEST_SHAPE;
			default:
				return NORTH_SHAPE;
		}
	}


	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return hasSolidSideOnTop(worldIn, pos.offset(state.get(HORIZONTAL_FACING)));
	}
	 

	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (!this.isLocked(worldIn, pos, state)) {
			boolean flag = state.get(POWERED);
			boolean flag1 = this.shouldBePowered(worldIn, pos, state);
			if (flag && !flag1) {
				worldIn.setBlockState(pos, state.with(POWERED, Boolean.valueOf(false)), 2);
			} 
			else if (!flag) {
				worldIn.setBlockState(pos, state.with(POWERED, Boolean.valueOf(true)), 2);
				if (!flag1)
					worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.getDelay(state), TickPriority.VERY_HIGH);
			}

		}
	}

	public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return blockState.getWeakPower(blockAccess, pos, side);
	}

	public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		if (!blockState.get(POWERED)) {
			return 0;
		} else {
			return blockState.get(VERTICAL_FACING) == side ? this.getActiveSignal(blockAccess, pos, blockState) : 0;
		}
	}

	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		if (state.isValidPosition(worldIn, pos)) {
			this.updateState(worldIn, pos, state);
		} else {
			TileEntity tileentity = state.hasTileEntity() ? worldIn.getTileEntity(pos) : null;
			spawnDrops(state, worldIn, pos, tileentity);
			worldIn.removeBlock(pos, false);

			for (Direction direction : Direction.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
			}

		}
	}

	protected void updateState(World worldIn, BlockPos pos, BlockState state) {
		if (!this.isLocked(worldIn, pos, state)) {
			boolean flag = state.get(POWERED);
			boolean flag1 = this.shouldBePowered(worldIn, pos, state);
			if (flag != flag1 && !worldIn.getPendingBlockTicks().isTickPending(pos, this)) {
				TickPriority tickpriority = TickPriority.HIGH;
				if (this.isFacingTowardsRepeater(worldIn, pos, state)) {
					tickpriority = TickPriority.EXTREMELY_HIGH;
				} else if (flag) {
					tickpriority = TickPriority.VERY_HIGH;
				}

				worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.getDelay(state), tickpriority);
			}

		}
	}

	public boolean isLocked(IWorldReader worldIn, BlockPos pos, BlockState state) {
		return false;
	}

	protected boolean shouldBePowered(World worldIn, BlockPos pos, BlockState state) {
		return this.calculateInputStrength(worldIn, pos, state) > 0;
	}

	protected int calculateInputStrength(World worldIn, BlockPos pos, BlockState state) {
		Direction direction = state.get(VERTICAL_FACING);
		BlockPos blockpos = pos.offset(direction);
		int i = worldIn.getRedstonePower(blockpos, direction);
		if (i >= 15) {
			return i;
		} else {
			BlockState blockstate = worldIn.getBlockState(blockpos);
			return Math.max(i, blockstate.isIn(Blocks.REDSTONE_WIRE) ? blockstate.get(RedstoneWireBlock.POWER) : 0);
		}
	}

	protected int getPowerOnSides(IWorldReader worldIn, BlockPos pos, BlockState state) {
		Direction direction = state.get(HORIZONTAL_FACING);
		Direction direction1 = direction.rotateY();
		Direction direction2 = direction.rotateYCCW();
		return Math.max(this.getPowerOnSide(worldIn, pos.offset(direction1), direction1),
				this.getPowerOnSide(worldIn, pos.offset(direction2), direction2));
	}

	protected int getPowerOnSide(IWorldReader worldIn, BlockPos pos, Direction side) {
		BlockState blockstate = worldIn.getBlockState(pos);
		if (this.isAlternateInput(blockstate)) {
			if (blockstate.isIn(Blocks.REDSTONE_BLOCK))
				return 15;
			else
				return blockstate.isIn(Blocks.REDSTONE_WIRE) ? blockstate.get(RedstoneWireBlock.POWER) : worldIn.getStrongPower(pos, side);
		} else
			return 0;
	}
	
	public boolean canProvidePower(BlockState state) {
		return true;
	}

	public BlockState getStateForPlacement(BlockItemUseContext c) {
		boolean flag = c.getHitVec().y - (double) c.getPos().getY() - .5d < 0;
		if(c.getFace().getAxis() == Axis.Y)
			return this.getDefaultState().with(HORIZONTAL_FACING, c.getPlacementHorizontalFacing()).with(VERTICAL_FACING, c.getFace());
		return this.getDefaultState().with(HORIZONTAL_FACING, c.getFace().getOpposite()).with(VERTICAL_FACING, flag ? Direction.UP : Direction.DOWN);
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (this.shouldBePowered(worldIn, pos, state)) {
			worldIn.getPendingBlockTicks().scheduleTick(pos, this, 1);
		}

	}

	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		this.notifyNeighbors(worldIn, pos, state);
	}

	@SuppressWarnings("deprecation")
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!isMoving && !state.isIn(newState.getBlock())) {
			super.onReplaced(state, worldIn, pos, newState, isMoving);
			this.notifyNeighbors(worldIn, pos, state);
		}
	}

	protected void notifyNeighbors(World worldIn, BlockPos pos, BlockState state) {
		Direction direction = state.get(VERTICAL_FACING);
		BlockPos blockpos = pos.offset(direction.getOpposite());
		if (net.minecraftforge.event.ForgeEventFactory.onNeighborNotify(worldIn, pos, worldIn.getBlockState(pos),
				java.util.EnumSet.of(direction.getOpposite()), false).isCanceled())
			return;
		worldIn.neighborChanged(blockpos, this, pos);
		worldIn.notifyNeighborsOfStateExcept(blockpos, this, direction);
	}

	protected boolean isAlternateInput(BlockState state) {
		return state.canProvidePower();
	}

	protected int getActiveSignal(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return 15;
	}

	public static boolean isDiode(BlockState state) {
		return state.getBlock() instanceof RedstoneDiodeBlock;
	}

	public boolean isFacingTowardsRepeater(IBlockReader worldIn, BlockPos pos, BlockState state) {
		Direction direction = state.get(VERTICAL_FACING).getOpposite();
		BlockState blockstate = worldIn.getBlockState(pos.offset(direction));
		return isDiode(blockstate) && blockstate.get(VERTICAL_FACING) != direction;
	}

	protected abstract int getDelay(BlockState state);
}
