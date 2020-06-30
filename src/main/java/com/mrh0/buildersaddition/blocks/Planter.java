package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.state.PlanterState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class Planter extends BaseDerivativeBlock {

	public static final EnumProperty<PlanterState> STATE = EnumProperty.<PlanterState>create("type", PlanterState.class);
	
	public Planter() {
		super("planter", Blocks.BARREL);
		setDefaultState(getDefaultState().with(STATE, PlanterState.Dirt));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(STATE);
	}
	
	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
		if(state.get(STATE) == PlanterState.Farmland)
			return plantable.getPlantType(world, pos) == PlantType.CROP || super.canSustainPlant(state, world, pos, facing, plantable);
		else 
			return plantable.getPlantType(world, pos) != PlantType.CROP && !super.canSustainPlant(state, world, pos, facing, plantable);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(state.get(STATE) == PlanterState.Farmland)
			return ActionResultType.PASS;
		ItemStack i = player.getHeldItem(handIn);
		if (i.getItem() instanceof HoeItem) {
			i.damageItem(1, player, (PlayerEntity e) -> {});
			worldIn.setBlockState(pos, getDefaultState().with(STATE, PlanterState.Farmland));
			worldIn.playSound(player, pos, SoundEvents.BLOCK_GRAVEL_PLACE, SoundCategory.BLOCKS, 1f, 1f);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}
}
