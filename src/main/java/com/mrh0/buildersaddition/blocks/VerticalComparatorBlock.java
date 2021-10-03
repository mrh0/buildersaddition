package com.mrh0.buildersaddition.blocks;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.TickPriority;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class VerticalComparatorBlock extends VerticalRedstoneDiodeBlock implements EntityBlock {
	public static final EnumProperty<ComparatorMode> MODE = BlockStateProperties.MODE_COMPARATOR;

	public VerticalComparatorBlock() {
		super("vertical_comparator", Block.Properties.copy(Blocks.REPEATER));
		this.registerDefaultState(this.stateDefinition.any().setValue(VERTICAL_FACING, Direction.UP)
				.setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(POWERED, Boolean.valueOf(false))
				.setValue(MODE, ComparatorMode.COMPARE));
	}

	protected int getDelay(BlockState state) {
		return 2;
	}

	@Override
	protected int getOutputSignal(BlockGetter worldIn, BlockPos pos, BlockState state) {
		BlockEntity tileentity = worldIn.getBlockEntity(pos);
		return tileentity instanceof ComparatorBlockEntity ? ((ComparatorBlockEntity) tileentity).getOutputSignal() : 0;
	}

	/*
	private int calculateOutputSignal(Level worldIn, BlockPos pos, BlockState state) {
		return state.getValue(MODE) == ComparatorMode.SUBTRACT
				? Math.max(this.calculateInputStrength(worldIn, pos, state) - this.getPowerOnSides(worldIn, pos, state),
						0)
				: this.calculateInputStrength(worldIn, pos, state);
	}
	*/
	
	private int calculateOutputSignal(Level p_51904_, BlockPos p_51905_, BlockState p_51906_) {
		int i = this.getInputSignal(p_51904_, p_51905_, p_51906_);
		if (i == 0) {
			return 0;
		} else {
			int j = this.getAlternateSignal(p_51904_, p_51905_, p_51906_);
			if (j > i) {
				return 0;
			} else {
				return p_51906_.getValue(MODE) == ComparatorMode.SUBTRACT ? i - j : i;
			}
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = super.getStateForPlacement(context);
		return blockstate.setValue(MODE, ComparatorMode.COMPARE);
	}

	@Override
	protected boolean shouldTurnOn(Level worldIn, BlockPos pos, BlockState state) {
		int i = this.getInputSignal(worldIn, pos, state);
		if (i == 0) {
			return false;
		} else {
			int j = this.getAlternateSignal(worldIn, pos, state);
			if (i > j) {
				return true;
			} else {
				return i == j && state.getValue(MODE) == ComparatorMode.COMPARE;
			}
		}
	}

	@Override
	protected int getInputSignal(Level worldIn, BlockPos pos, BlockState state) {
		int i = super.getInputSignal(worldIn, pos, state);
		Direction direction = state.getValue(VERTICAL_FACING);
		BlockPos blockpos = pos.relative(direction);
		BlockState blockstate = worldIn.getBlockState(blockpos);
		if (blockstate.hasAnalogOutputSignal()) {
			i = blockstate.getAnalogOutputSignal(worldIn, blockpos);
		} else if (i < 15 && blockstate.isRedstoneConductor(worldIn, blockpos)) {
			blockpos = blockpos.relative(direction);
			blockstate = worldIn.getBlockState(blockpos);
			ItemFrame itemframeentity = this.getItemFrame(worldIn, direction, blockpos);
			int j = Math.max(itemframeentity == null ? Integer.MIN_VALUE : itemframeentity.getAnalogOutput(),
					blockstate.hasAnalogOutputSignal() ? blockstate.getAnalogOutputSignal(worldIn, blockpos)
							: Integer.MIN_VALUE);
			if (j != Integer.MIN_VALUE) {
				i = j;
			}
		}

		return i;
	}

	@Nullable
	private ItemFrame getItemFrame(Level worldIn, Direction facing, BlockPos pos) {
		List<ItemFrame> list = worldIn.getEntitiesOfClass(ItemFrame.class,
				new AABB((double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), (double) (pos.getX() + 1),
						(double) (pos.getY() + 1), (double) (pos.getZ() + 1)),
				(itemFrame) -> {
					// Issue here if there are more than one at block! (Why you so hardcoded)
					return itemFrame != null; // itemFrame.getHorizontalFacing() == facing
				});
		return list.size() == 1 ? list.get(0) : null;
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		if (!player.getAbilities().mayBuild) {
			return InteractionResult.PASS;
		} else {
			state = state.cycle(MODE);
			float f = state.getValue(MODE) == ComparatorMode.SUBTRACT ? 0.55F : 0.5F;
			world.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, f);
			world.setBlock(pos, state, 2);
			this.refreshOutputState(world, pos, state);
			return InteractionResult.sidedSuccess(world.isClientSide);
		}
	}

	/*@Override
	protected void updateState(Level worldIn, BlockPos pos, BlockState state) {
		if (!worldIn.getPendingBlockTicks().isTickPending(pos, this)) {
			int i = this.calculateOutput(worldIn, pos, state);
			BlockEntity tileentity = worldIn.getTileEntity(pos);
			int j = tileentity instanceof ComparatorBlockEntity ? ((ComparatorBlockEntity) tileentity).getOutputSignal()
					: 0;
			if (i != j || state.get(POWERED) != this.shouldBePowered(worldIn, pos, state)) {
				TickPriority tickpriority = this.isFacingTowardsRepeater(worldIn, pos, state) ? TickPriority.HIGH
						: TickPriority.NORMAL;
				worldIn.getPendingBlockTicks().scheduleTick(pos, this, 2, tickpriority);
			}

		}
	}

	private void onStateChange(World worldIn, BlockPos pos, BlockState state) {
		int i = this.calculateOutput(worldIn, pos, state);
		BlockEntity tileentity = worldIn.getTileEntity(pos);
		int j = 0;
		if (tileentity instanceof ComparatorBlockEntity) {
			ComparatorBlockEntity comparatortileentity = (ComparatorBlockEntity) tileentity;
			j = comparatortileentity.getOutputSignal();
			comparatortileentity.setOutputSignal(i);
		}

		if (j != i || state.get(MODE) == ComparatorMode.COMPARE) {
			boolean flag1 = this.shouldBePowered(worldIn, pos, state);
			boolean flag = state.get(POWERED);
			if (flag && !flag1) {
				worldIn.setBlockState(pos, state.setValue(POWERED, Boolean.valueOf(false)), 2);
			} else if (!flag && flag1) {
				worldIn.setBlockState(pos, state.setValue(POWERED, Boolean.valueOf(true)), 2);
			}

			this.notifyNeighbors(worldIn, pos, state);
		}
	}*/
	@Override
	protected void checkTickOnNeighbor(Level world, BlockPos pos, BlockState state) {
		if (!world.getBlockTicks().willTickThisTick(pos, this)) {
			int i = this.calculateOutputSignal(world, pos, state);
			BlockEntity blockentity = world.getBlockEntity(pos);
			int j = blockentity instanceof ComparatorBlockEntity
					? ((ComparatorBlockEntity) blockentity).getOutputSignal()
					: 0;
			if (i != j || state.getValue(POWERED) != this.shouldTurnOn(world, pos, state)) {
				TickPriority tickpriority = this.shouldPrioritize(world, pos, state) ? TickPriority.HIGH
						: TickPriority.NORMAL;
				world.getBlockTicks().scheduleTick(pos, this, 2, tickpriority);
			}

		}
	}

	private void refreshOutputState(Level world, BlockPos pos, BlockState state) {
		int i = this.calculateOutputSignal(world, pos, state);
		BlockEntity blockentity = world.getBlockEntity(pos);
		int j = 0;
		if (blockentity instanceof ComparatorBlockEntity) {
			ComparatorBlockEntity comparatorblockentity = (ComparatorBlockEntity) blockentity;
			j = comparatorblockentity.getOutputSignal();
			comparatorblockentity.setOutputSignal(i);
		}

		if (j != i || state.getValue(MODE) == ComparatorMode.COMPARE) {
			boolean flag1 = this.shouldTurnOn(world, pos, state);
			boolean flag = state.getValue(POWERED);
			if (flag && !flag1) {
				world.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(false)), 2);
			} else if (!flag && flag1) {
				world.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(true)), 2);
			}
			this.updateNeighborsInFront(world, pos, state);
		}
	}

	@Override
	public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
		return new ItemStack(Items.COMPARATOR);
	}
	
	@Override
	public void tick(BlockState p_51869_, ServerLevel p_51870_, BlockPos p_51871_, Random p_51872_) {
		this.refreshOutputState(p_51870_, p_51871_, p_51869_);
	}

	@Override
	public boolean triggerEvent(BlockState p_51874_, Level p_51875_, BlockPos p_51876_, int p_51877_, int p_51878_) {
		super.triggerEvent(p_51874_, p_51875_, p_51876_, p_51877_, p_51878_);
		BlockEntity blockentity = p_51875_.getBlockEntity(p_51876_);
		return blockentity != null && blockentity.triggerEvent(p_51877_, p_51878_);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos p_153086_, BlockState p_153087_) {
		return new ComparatorBlockEntity(p_153086_, p_153087_);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, VERTICAL_FACING, MODE, POWERED);
	}

	/*
	 * @Override public boolean getWeakChanges(BlockState state,
	 * net.minecraft.world.IWorldReader world, BlockPos pos) { return
	 * state.is(Blocks.COMPARATOR); }
	 * 
	 * @Override public void onNeighborChange(BlockState state,
	 * net.minecraft.world.IWorldReader world, BlockPos pos, BlockPos neighbor) { if
	 * (pos.getY() == neighbor.getY() && world instanceof World && !((World)
	 * world).isRemote()) { state.neighborChanged((World) world, pos,
	 * world.getBlockState(neighbor).getBlock(), neighbor, false); } }
	 */
	@Override
	public boolean getWeakChanges(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos) {
		return state.is(Blocks.COMPARATOR);
	}

	@Override
	public void onNeighborChange(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos,
			BlockPos neighbor) {
		if (pos.getY() == neighbor.getY() && world instanceof Level && !((Level) world).isClientSide()) {
			state.neighborChanged((Level) world, pos, world.getBlockState(neighbor).getBlock(), neighbor, false);
		}
	}
}
