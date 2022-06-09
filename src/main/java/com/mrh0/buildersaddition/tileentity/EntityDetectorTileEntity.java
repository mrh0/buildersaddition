package com.mrh0.buildersaddition.tileentity;

import com.mrh0.buildersaddition.Index;
import com.mrh0.buildersaddition.blocks.EntityDetector;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class EntityDetectorTileEntity extends BlockEntity {

	private static int dis = 4;
	public int cycle = 0;
	
	public EntityDetectorTileEntity(BlockPos pos, BlockState state) {
		super(Index.ENTITY_DETECTOR_TILE_ENTITY_TYPE.get(), pos, state);
	}

	public static void tick(Level world, BlockPos pos, BlockState state, EntityDetectorTileEntity te) {
		te.cycle++;
		if(te.cycle >= 20)
			te.cycle = 0;
		if(te.cycle % 4 != 0)
			return;
		
		int power = 0;
		for(int i = dis; i > 0; i--) {
			BlockPos p1 = pos.relative(state.getValue(EntityDetector.FACING), i);
			int n = world.getEntitiesOfClass(Entity.class, new AABB(p1.getX(), p1.getY(), p1.getZ(), p1.getX() + 1, p1.getY() + 1, p1.getZ() + 1)).size();
			power = n > 0 ? (dis-i+1) * 15/dis : power;
		}
		if(power != state.getValue(EntityDetector.POWER))
			world.setBlockAndUpdate(pos, Index.ENTITY_DETECTOR.get().defaultBlockState().setValue(EntityDetector.FACING, state.getValue(EntityDetector.FACING)).setValue(EntityDetector.POWER, Math.max(Math.min(power, 15), 0)));
	}
}
