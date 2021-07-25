package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.state.PlanterState;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class Planter extends BaseDerivativeBlock {

	public static final EnumProperty<PlanterState> STATE = EnumProperty.<PlanterState>create("type", PlanterState.class);
	
	public Planter() {
		super("planter", Blocks.BARREL);
		registerDefaultState(defaultBlockState().setValue(STATE, PlanterState.Dirt));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(STATE);
	}
	
	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing,
			IPlantable plantable) {
		if(state.getValue(STATE) == PlanterState.Farmland)
			return plantable.getPlantType(world, pos) == PlantType.CROP || super.canSustainPlant(state, world, pos, facing, plantable);
		else 
			return plantable.getPlantType(world, pos) != PlantType.CROP && !super.canSustainPlant(state, world, pos, facing, plantable);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		if(state.getValue(STATE) == PlanterState.Farmland)
			return InteractionResult.PASS;
		ItemStack i = player.getItemInHand(hand);
		if (i.getItem() instanceof HoeItem) {
			i.hurtAndBreak(1, player, (Player e) -> {});
			world.setBlockAndUpdate(pos, defaultBlockState().setValue(STATE, PlanterState.Farmland));
			world.playSound(player, pos, SoundEvents.GRAVEL_PLACE, SoundSource.BLOCKS, 1f, 1f);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
