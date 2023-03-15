package com.mrh0.buildersaddition.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrh0.buildersaddition.blocks.ShopSign;
import com.mrh0.buildersaddition.state.ShopSignState;
import com.mrh0.buildersaddition.tileentity.ShopSignTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;

public class ShopSignRenderer implements BlockEntityRenderer<ShopSignTileEntity> {

	private final Minecraft mc = Minecraft.getInstance();

	public ShopSignRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
		super();
	}
	
	private static final float u1 = 1f/16f;

	@Override
	public void render(ShopSignTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		ShopSignState state = tileEntityIn.getBlockState().getValue(ShopSign.STATE);
		
		ItemStack item = tileEntityIn.getDisplayItem();
		
		if(item == ItemStack.EMPTY)
			return;
		
		//First side
		matrixStackIn.pushPose();
		matrixStackIn.translate(.5f, .5f, .5f);
		matrixStackIn.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180f));
		if(state.getAxis() == Axis.Z) {
			matrixStackIn.mulPose(com.mojang.math.Axis.YP.rotationDegrees(90f));
		}
		matrixStackIn.translate(-offsetSide(state), offsetY(state), u1);
		matrixStackIn.scale(0.8f, 0.8f, .8f);
		matrixStackIn.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180f));
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 1);
        matrixStackIn.popPose();
        
        //Other side
        matrixStackIn.pushPose();
		matrixStackIn.translate(.5f, .5f, .5f);
		if(state.getAxis() == Axis.Z) {
			matrixStackIn.mulPose(com.mojang.math.Axis.YP.rotationDegrees(90f));
		}
		matrixStackIn.translate(offsetSide(state), offsetY(state), u1);
		matrixStackIn.scale(0.8f, 0.8f, .8f);
		matrixStackIn.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180f));
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 1);
        matrixStackIn.popPose();
	}
	
	private float offsetY(ShopSignState state) {
		switch(state.getDirection()) {
			case DOWN:
				return -u1;
			case UP:
				return u1;
		}
		return 0f;
	}
	
	private float offsetSide(ShopSignState state) {
		switch(state.getDirection()) {
			case NORTH:
				return u1 - state.getOffset()/16f;
			case SOUTH:
				return -u1 + state.getOffset()/16f;
			case EAST:
				return u1 - state.getOffset()/16f;
			case WEST:
				return -u1 + state.getOffset()/16f;
		}
		return 0f;
	}
}
