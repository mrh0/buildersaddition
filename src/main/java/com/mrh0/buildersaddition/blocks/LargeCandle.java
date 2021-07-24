package com.mrh0.buildersaddition.blocks;

import java.util.Random;
import com.mrh0.buildersaddition.blocks.base.BaseBlock;
import com.mrh0.buildersaddition.config.Config;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class LargeCandle extends BaseBlock {

	public static final BooleanProperty LIT = BooleanProperty.create("lit");
	protected static final VoxelShape SHAPE = Block.box(6.5d, 0d, 6.5d, 9.5d, 6d, 9.5d);
	private ParticleOptions parts;
	
	public LargeCandle(String name, ParticleOptions parts) {
		super(name, Properties.copy(Blocks.TORCH).lightLevel((s) -> s.getValue(LIT).booleanValue() ? 15 : 0));
		registerDefaultState(defaultBlockState().setValue(LIT, true));
		this.parts = parts;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext c) {
		if(c.getPlayer().isCreative())
			return defaultBlockState().setValue(LIT, true);
		return defaultBlockState().setValue(LIT, false);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
			InteractionHand handIn, BlockHitResult c) {
		ItemStack item = player.getItemInHand(handIn);
		boolean lit = state.getValue(LIT);
		if(player.isCreative() || !Config.REQUIRE_FLINT_AND_STEEL.get()) {
			if(!lit) {
				worldIn.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1, 1, false);
				if(!worldIn.isClientSide())
					worldIn.setBlockAndUpdate(pos, defaultBlockState().setValue(LIT, true));
			}
			else {
				worldIn.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.REDSTONE_TORCH_BURNOUT, SoundSource.BLOCKS, 1, 1, false);
				if(!worldIn.isClientSide())
					worldIn.setBlockAndUpdate(pos, defaultBlockState().setValue(LIT, false));
			}
		}
		else if(item.isEmpty()) {
			if(lit) {
				worldIn.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.REDSTONE_TORCH_BURNOUT, SoundSource.BLOCKS, 1, 1, false);
				if(!worldIn.isClientSide())
					worldIn.setBlockAndUpdate(pos, defaultBlockState().setValue(LIT, false));
			}
		}
		else if(item.getItem() == Items.FLINT_AND_STEEL) {
			if(!lit) {
				worldIn.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1, 1, false);
				if(!worldIn.isClientSide()) {
					worldIn.setBlockAndUpdate(pos, defaultBlockState().setValue(LIT, true));
					item.hurtAndBreak(1, player, (e) -> {});
				}
			}
		}
		
		if(worldIn.isClientSide())
			return InteractionResult.SUCCESS;
		return InteractionResult.CONSUME;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
		if(!stateIn.getValue(LIT).booleanValue())
			return;
		double d0 = (double)pos.getX() + 0.5D;
		double d1 = (double)pos.getY() + 0.6D;
		double d2 = (double)pos.getZ() + 0.5D;
		worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(parts, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}
}
