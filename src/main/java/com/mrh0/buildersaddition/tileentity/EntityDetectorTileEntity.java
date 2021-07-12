package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.EntityDetector;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class EntityDetectorTileEntity extends TileEntity implements ITickableTileEntity{

	private static int dis = 4;
	public int cycle = 0;
	
	public EntityDetectorTileEntity() {
		super(Index.ENTITY_DETECTOR_TILE_ENTITY_TYPE);
	}

	@Override
	public void tick() {
		cycle++;
		if(cycle >= 20)
			cycle = 0;
		if(cycle % 4 != 0)
			return;
		BlockState state = this.getBlockState();
		
		int power = 0;
		for(int i = dis; i > 0; i--) {
			BlockPos p1 = pos.offset(state.get(EntityDetector.FACING), i);
			int n = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(p1.getX(), p1.getY(), p1.getZ(), p1.getX() + 1, p1.getY() + 1, p1.getZ() + 1)).size();
			power = n > 0 ? (dis-i+1) * 15/dis : power;
		}
		if(power != state.get(EntityDetector.POWER))
			world.setBlockState(pos, Index.ENTITY_DETECTOR.getDefaultState().with(EntityDetector.FACING, state.get(EntityDetector.FACING)).with(EntityDetector.POWER, Math.max(Math.min(power, 15), 0)));
	}
}
