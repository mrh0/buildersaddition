package com.mrh0.buildersaddition.blocks;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class VerticalComparatorBlock extends VerticalRedstoneDiodeBlock {
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
	protected int getActiveSignal(IBlockReader worldIn, BlockPos pos, BlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity instanceof ComparatorTileEntity ? ((ComparatorTileEntity) tileentity).getOutputSignal() : 0;
	}

	
	private int calculateOutput(World worldIn, BlockPos pos, BlockState state) {
		return state.getValue(MODE) == ComparatorMode.SUBTRACT
				? Math.max(this.calculateInputStrength(worldIn, pos, state) - this.getPowerOnSides(worldIn, pos, state),
						0)
				: this.calculateInputStrength(worldIn, pos, state);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = super.getStateForPlacement(context);
		return blockstate.with(MODE, ComparatorMode.COMPARE);
	}

	@Override
	protected boolean shouldBePowered(World worldIn, BlockPos pos, BlockState state) {
		int i = this.calculateInputStrength(worldIn, pos, state);
		if (i == 0) {
			return false;
		} else {
			int j = this.getPowerOnSides(worldIn, pos, state);
			if (i > j) {
				return true;
			} else {
				return i == j && state.get(MODE) == ComparatorMode.COMPARE;
			}
		}
	}

	protected int calculateInputStrength(World worldIn, BlockPos pos, BlockState state) {
		int i = super.calculateInputStrength(worldIn, pos, state);
		Direction direction = state.get(VERTICAL_FACING);
		BlockPos blockpos = pos.offset(direction);
		BlockState blockstate = worldIn.getBlockState(blockpos);
		if (blockstate.hasComparatorInputOverride()) {
			i = blockstate.getComparatorInputOverride(worldIn, blockpos);
		} else if (i < 15 && blockstate.isNormalCube(worldIn, blockpos)) {
			blockpos = blockpos.offset(direction);
			blockstate = worldIn.getBlockState(blockpos);
			ItemFrameEntity itemframeentity = this.findItemFrame(worldIn, direction, blockpos);
			int j = Math.max(itemframeentity == null ? Integer.MIN_VALUE : itemframeentity.getAnalogOutput(),
					blockstate.hasComparatorInputOverride() ? blockstate.getComparatorInputOverride(worldIn, blockpos)
							: Integer.MIN_VALUE);
			if (j != Integer.MIN_VALUE) {
				i = j;
			}
		}

		return i;
	}

	@Nullable
	private ItemFrameEntity findItemFrame(World worldIn, Direction facing, BlockPos pos) {
		List<ItemFrameEntity> list = worldIn.getEntitiesWithinAABB(ItemFrameEntity.class,
				new AxisAlignedBB((double) pos.getX(), (double) pos.getY(), (double) pos.getZ(),
						(double) (pos.getX() + 1), (double) (pos.getY() + 1), (double) (pos.getZ() + 1)),
				(itemFrame) -> {
					// Issue here if there are more than one at block! (Why you so hardcoded)
					return itemFrame != null; // itemFrame.getHorizontalFacing() == facing
				});
		return list.size() == 1 ? list.get(0) : null;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (!player.abilities.allowEdit) {
			return ActionResultType.PASS;
		} else {
			state = state.func_235896_a_(MODE);
			float f = state.get(MODE) == ComparatorMode.SUBTRACT ? 0.55F : 0.5F;
			worldIn.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3F, f);
			worldIn.setBlockState(pos, state, 2);
			this.onStateChange(worldIn, pos, state);
			return ActionResultType.func_233537_a_(worldIn.isRemote);
		}
	}

	@Override
	protected void updateState(World worldIn, BlockPos pos, BlockState state) {
		if (!worldIn.getPendingBlockTicks().isTickPending(pos, this)) {
			int i = this.calculateOutput(worldIn, pos, state);
			TileEntity tileentity = worldIn.getTileEntity(pos);
			int j = tileentity instanceof ComparatorTileEntity ? ((ComparatorTileEntity) tileentity).getOutputSignal()
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
		TileEntity tileentity = worldIn.getTileEntity(pos);
		int j = 0;
		if (tileentity instanceof ComparatorTileEntity) {
			ComparatorTileEntity comparatortileentity = (ComparatorTileEntity) tileentity;
			j = comparatortileentity.getOutputSignal();
			comparatortileentity.setOutputSignal(i);
		}

		if (j != i || state.get(MODE) == ComparatorMode.COMPARE) {
			boolean flag1 = this.shouldBePowered(worldIn, pos, state);
			boolean flag = state.get(POWERED);
			if (flag && !flag1) {
				worldIn.setBlockState(pos, state.with(POWERED, Boolean.valueOf(false)), 2);
			} else if (!flag && flag1) {
				worldIn.setBlockState(pos, state.with(POWERED, Boolean.valueOf(true)), 2);
			}

			this.notifyNeighbors(worldIn, pos, state);
		}
	}
	
	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos,
			PlayerEntity player) {
		return new ItemStack(Items.COMPARATOR);
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		this.onStateChange(worldIn, pos, state);
	}

	@Override
	public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
		super.eventReceived(state, worldIn, pos, id, param);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity != null && tileentity.receiveClientEvent(id, param);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ComparatorTileEntity();
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, VERTICAL_FACING, MODE, POWERED);
	}

	@Override
	public boolean getWeakChanges(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos) {
		return state.isIn(Blocks.COMPARATOR);
	}

	@Override
	public void onNeighborChange(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos,
			BlockPos neighbor) {
		if (pos.getY() == neighbor.getY() && world instanceof World && !((World) world).isRemote()) {
			state.neighborChanged((World) world, pos, world.getBlockState(neighbor).getBlock(), neighbor, false);
		}
	}
}
