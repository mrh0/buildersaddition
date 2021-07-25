package com.mrh0.buildersaddition.blocks;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class VerticalRepeaterBlock extends VerticalRedstoneDiodeBlock {
	public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;
	public static final IntegerProperty DELAY = BlockStateProperties.DELAY; //1_4

	public VerticalRepeaterBlock() {
	      super("vertical_repeater", Block.Properties.copy(Blocks.REPEATER));
	      this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(VERTICAL_FACING, Direction.UP).setValue(DELAY, Integer.valueOf(1)).setValue(LOCKED, Boolean.valueOf(false)).setValue(POWERED, Boolean.valueOf(false)));
	   }

	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
	      if (!player.getAbilities().mayBuild) {
	         return InteractionResult.PASS;
	      } else {
	         world.setBlock(pos, state.cycle(DELAY), 3);
	         return InteractionResult.sidedSuccess(world.isClientSide);
	      }
	   }

	protected int getDelay(BlockState state) {
		return state.getValue(DELAY) * 2;
	}

	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = super.getStateForPlacement(context);
		return blockstate.setValue(LOCKED,
				Boolean.valueOf(this.isLocked(context.getLevel(), context.getClickedPos(), blockstate)));
	}

	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		return !worldIn.isClientSide() && facing.getAxis() != stateIn.getValue(VERTICAL_FACING).getAxis()
				? stateIn.setValue(LOCKED, Boolean.valueOf(this.isLocked(worldIn, currentPos, stateIn)))
				: super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	public boolean isLocked(LevelReader worldIn, BlockPos pos, BlockState state) {
		return false;//this.getPowerOnSides(worldIn, pos, state) > 0;
	}

	protected boolean isAlternateInput(BlockState state) {
		return isDiode(state);
	}
	
	@Override
	public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
		return new ItemStack(Items.REPEATER);
	}

	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
		if(!worldIn.isClientSide())
			return;
		if (stateIn.getValue(POWERED)) {
			Direction direction = stateIn.getValue(VERTICAL_FACING);
			double d0 = (double) pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
			double d1 = (double) pos.getY() + 0.4D + (rand.nextDouble() - 0.5D) * 0.2D;
			double d2 = (double) pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
			float f = -5.0F;
			if (rand.nextBoolean()) {
				f = (float) (stateIn.getValue(DELAY) * 2 - 1);
			}

			f = f / 16.0F;
			double d3 = (double) (f * (float) direction.getStepX());
			double d4 = (double) (f * (float) direction.getStepZ());
			worldIn.addParticle(DustParticleOptions.REDSTONE, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(VERTICAL_FACING, HORIZONTAL_FACING, DELAY, LOCKED, POWERED);
	}
}
