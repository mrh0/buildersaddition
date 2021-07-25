package com.mrh0.buildersaddition.blocks;

import java.util.Random;
import com.mrh0.buildersaddition.blocks.base.BaseBlock;
import com.mrh0.buildersaddition.event.opts.BlockOptions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.TickPriority;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class VerticalRedstoneDiodeBlock extends BaseBlock {
	public static final DirectionProperty VERTICAL_FACING = DirectionProperty.create("vertical_facing", d -> d.getAxis() == Axis.Y);
	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
	protected static final VoxelShape WEST_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_SHAPE = Block.box(14D, 0.0D, 0.0D, 16D, 16.0D, 16.0D);
	protected static final VoxelShape NORTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16D, 16D, 2D);
	protected static final VoxelShape SOUTH_SHAPE = Block.box(0.0D, 0.0D, 14D, 16D, 16D, 16D);
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	protected VerticalRedstoneDiodeBlock(String name, BlockBehaviour.Properties builder) {
		super(name, builder, new BlockOptions().hasItem(false));
	}

	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		switch(state.getValue(HORIZONTAL_FACING)) {
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


	public boolean isValidPosition(BlockState state, LevelReader world, BlockPos pos) {
		return canSupportRigidBlock(world, pos.relative(state.getValue(HORIZONTAL_FACING)));
	}
	 

	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
		if (!this.isLocked(worldIn, pos, state)) {
			boolean flag = state.getValue(POWERED);
			boolean flag1 = this.shouldTurnOn(worldIn, pos, state);
			if (flag && !flag1) {
				worldIn.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(false)), 2);
			} 
			else if (!flag) {
				worldIn.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(true)), 2);
				if (!flag1)
					worldIn.getBlockTicks().scheduleTick(pos, this, this.getDelay(state), TickPriority.VERY_HIGH);
			}

		}
	}
	
	@Override
	public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
		return state.getDirectSignal(world, pos, side);
	}
	
	@Override
	public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
		if (!state.getValue(POWERED)) {
			return 0;
		} else {
			return state.getValue(VERTICAL_FACING) == side ? this.getOutputSignal(world, pos, state) : 0;
		}
	}

	/*public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		if (state.isValidPosition(worldIn, pos)) {
			this.updateState(worldIn, pos, state);
		} else {
			BlockEntity tileentity = state.hasBlockEntity() ? worldIn.getBlockEntity(pos) : null;
			spawnDrops(state, worldIn, pos, tileentity);
			worldIn.removeBlock(pos, false);

			for (Direction direction : Direction.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
			}

		}
	}

	protected void updateState(World worldIn, BlockPos pos, BlockState state) {
		if (!this.isLocked(worldIn, pos, state)) {
			boolean flag = state.getValue(POWERED);
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
	}*/
	
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos otherPos, boolean flag) {
	      if (state.canSurvive(world, pos)) {
	         this.checkTickOnNeighbor(world, pos, state);
	      } else {
	         BlockEntity blockentity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
	         dropResources(state, world, pos, blockentity);
	         world.removeBlock(pos, false);

	         for(Direction direction : Direction.values()) {
	            world.updateNeighborsAt(pos.relative(direction), this);
	         }

	      }
	   }

	   protected void checkTickOnNeighbor(Level world, BlockPos pos, BlockState state) {
	      if (!this.isLocked(world, pos, state)) {
	         boolean flag = state.getValue(POWERED);
	         boolean flag1 = this.shouldTurnOn(world, pos, state);
	         if (flag != flag1 && !world.getBlockTicks().willTickThisTick(pos, this)) {
	            TickPriority tickpriority = TickPriority.HIGH;
	            if (this.shouldPrioritize(world, pos, state)) {
	               tickpriority = TickPriority.EXTREMELY_HIGH;
	            } else if (flag) {
	               tickpriority = TickPriority.VERY_HIGH;
	            }

	            world.getBlockTicks().scheduleTick(pos, this, this.getDelay(state), tickpriority);
	         }

	      }
	   }

	public boolean isLocked(LevelReader worldIn, BlockPos pos, BlockState state) {
		return false;
	}

	protected boolean shouldTurnOn(Level worldIn, BlockPos pos, BlockState state) {
		return this.getInputSignal(worldIn, pos, state) > 0;
	}

	protected int getInputSignal(Level worldIn, BlockPos pos, BlockState state) {
		Direction direction = state.getValue(VERTICAL_FACING);
		BlockPos blockpos = pos.relative(direction);
		int i = worldIn.getSignal(blockpos, direction);
		if (i >= 15) {
			return i;
		} else {
			BlockState blockstate = worldIn.getBlockState(blockpos);
			return Math.max(i, blockstate.is(Blocks.REDSTONE_WIRE) ? blockstate.getValue(RedStoneWireBlock.POWER) : 0);
		}
	}

	protected int getAlternateSignal(LevelReader worldIn, BlockPos pos, BlockState state) {
		Direction direction = state.getValue(HORIZONTAL_FACING);
		Direction direction1 = direction.getClockWise();
		Direction direction2 = direction.getCounterClockWise();
		return Math.max(this.getAlternateSignalAt(worldIn, pos.relative(direction1), direction1),
				this.getAlternateSignalAt(worldIn, pos.relative(direction2), direction2));
	}

	protected int getAlternateSignalAt(LevelReader worldIn, BlockPos pos, Direction side) {
		BlockState blockstate = worldIn.getBlockState(pos);
		if (this.isAlternateInput(blockstate)) {
			if (blockstate.is(Blocks.REDSTONE_BLOCK))
				return 15;
			else
				return blockstate.is(Blocks.REDSTONE_WIRE) ? blockstate.getValue(RedStoneWireBlock.POWER) : worldIn.getDirectSignal(pos, side);
		} else
			return 0;
	}
	
	public boolean isSignalSource(BlockState state) {
		return true;
	}

	public BlockState getStateForPlacement(BlockPlaceContext c) {
		boolean flag = c.getClickLocation().y - (double) c.getClickedPos().getY() - .5d < 0;
		if(c.getClickedFace().getAxis() == Axis.Y)
			return this.defaultBlockState().setValue(HORIZONTAL_FACING, c.getHorizontalDirection()).setValue(VERTICAL_FACING, c.getClickedFace());
		return this.defaultBlockState().setValue(HORIZONTAL_FACING, c.getClickedFace().getOpposite()).setValue(VERTICAL_FACING, flag ? Direction.UP : Direction.DOWN);
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity ent,
			ItemStack stack) {
		if (this.shouldTurnOn(world, pos, state)) {
			world.getBlockTicks().scheduleTick(pos, this, 1);
		}
	}
	
	@Override
	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState newState, boolean flag) {
		this.notifyNeighbors(world, pos, state);
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!isMoving && !state.is(newState.getBlock())) {
			super.onRemove(state, world, pos, newState, isMoving);
			this.notifyNeighbors(world, pos, state);
		}
	}

	protected void notifyNeighbors(Level world, BlockPos pos, BlockState state) {
		Direction direction = state.getValue(VERTICAL_FACING);
		BlockPos blockpos = pos.relative(direction.getOpposite());
		if (net.minecraftforge.event.ForgeEventFactory.onNeighborNotify(world, pos, world.getBlockState(pos),
				java.util.EnumSet.of(direction.getOpposite()), false).isCanceled())
			return;
		world.neighborChanged(blockpos, this, pos);
		world.updateNeighborsAtExceptFromFacing(blockpos, this, direction);
	}

	protected boolean isAlternateInput(BlockState state) {
		return state.isSignalSource();
	}

	protected int getOutputSignal(BlockGetter worldIn, BlockPos pos, BlockState state) {
		return 15;
	}

	public static boolean isDiode(BlockState state) {
		return state.getBlock() instanceof DiodeBlock;
	}

	public boolean shouldPrioritize(BlockGetter worldIn, BlockPos pos, BlockState state) {
		Direction direction = state.getValue(VERTICAL_FACING).getOpposite();
		BlockState blockstate = worldIn.getBlockState(pos.relative(direction));
		return isDiode(blockstate) && blockstate.getValue(VERTICAL_FACING) != direction;
	}

	protected abstract int getDelay(BlockState state);
}
