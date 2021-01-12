package com.mrh0.buildersaddition.blocks;

import com.mrh0.buildersaddition.blocks.base.BaseDerivativeBlock;
import com.mrh0.buildersaddition.tileentity.SpeakerTileEntity;
import com.mrh0.buildersaddition.tileentity.base.BaseInstrument;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class Speaker extends BaseDerivativeBlock {
	
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(2d, 1d, 2d, 14d, 16d, 14d);
	public static final DirectionProperty FACING = DirectionProperty.create("facing", p -> p.getIndex() > 1 && p.getIndex() < Direction.values().length);

	public Speaker() {
		super("speaker", Blocks.OAK_PLANKS);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
    public BlockState getStateForPlacement(BlockItemUseContext c) {
    	return this.getDefaultState().with(FACING, c.getPlayer().getHorizontalFacing());
    }
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new SpeakerTileEntity();
	}
	
	@Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (player.isSpectator()) {
            return ActionResultType.PASS;
        }
    	if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }
    	
    	BaseInstrument mte = (BaseInstrument) worldIn.getTileEntity(pos);
		NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) mte, extraData -> {
            extraData.writeBlockPos(mte.getPos());
        });
    	return ActionResultType.SUCCESS;
    }
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
