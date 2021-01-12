package com.mrh0.buildersaddition.blocks;

import java.util.Random;
import com.mrh0.buildersaddition.blocks.base.BaseBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LargeCandle extends BaseBlock {

	public static final BooleanProperty LIT = BooleanProperty.create("lit");
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(6.5d, 0d, 6.5d, 9.5d, 6d, 9.5d);
	private IParticleData parts;
	
	public LargeCandle(String name, IParticleData parts) {
		super(name, Properties.from(Blocks.TORCH).setLightLevel((s) -> s.get(LIT).booleanValue() ? 15 : 0));
		setDefaultState(getDefaultState().with(LIT, true));
		this.parts = parts;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		if(c.getPlayer().isCreative())
			return getDefaultState().with(LIT, true);
		return getDefaultState().with(LIT, false);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		ItemStack item = player.getHeldItem(handIn);
		boolean lit = state.get(LIT);
		if(player.isCreative()) {
			if(!lit) {
				worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1, 1, false);
				if(!worldIn.isRemote())
					worldIn.setBlockState(pos, getDefaultState().with(LIT, true));
			}
			else {
				worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 1, 1, false);
				if(!worldIn.isRemote())
					worldIn.setBlockState(pos, getDefaultState().with(LIT, false));
			}
		}
		else if(item.isEmpty()) {
			if(lit) {
				worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 1, 1, false);
				if(!worldIn.isRemote())
					worldIn.setBlockState(pos, getDefaultState().with(LIT, false));
			}
		}
		else if(item.getItem() == Items.FLINT_AND_STEEL) {
			if(!lit) {
				worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1, 1, false);
				if(!worldIn.isRemote()) {
					worldIn.setBlockState(pos, getDefaultState().with(LIT, true));
					item.damageItem(1, player, (e) -> {});
				}
			}
		}
		
		if(worldIn.isRemote())
			return ActionResultType.SUCCESS;
		return ActionResultType.CONSUME;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if(!stateIn.get(LIT).booleanValue())
			return;
		double d0 = (double)pos.getX() + 0.5D;
		double d1 = (double)pos.getY() + 0.6D;
		double d2 = (double)pos.getZ() + 0.5D;
		worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(parts, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}
}
