package com.mrh0.buildersaddition.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mrh0.buildersaddition.blocks.Shelf;
import com.mrh0.buildersaddition.inventory.ModInventory;
import com.mrh0.buildersaddition.tileentity.ShelfTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;

public class ShelfRenderer implements BlockEntityRenderer<ShelfTileEntity> {
	public ShelfRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
		super();
	}
	
	private static final float u1 = 1f/16f;

	@Override
	public void render(ShelfTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Direction dir = tileEntityIn.getBlockState().getValue(Shelf.FACING);
		ModInventory items = tileEntityIn.handler;
        
		float scale = .30f;
		float xoffset = 5.2f;
		float yoffset = 3.5f;
		float yoffset2 = 4.5f;
		float zoffset = .15f;
		
        //Stack 1
		if(items.getStackInSlot(0) != ItemStack.EMPTY) {
	        matrixStackIn.pushPose();
	        matrixStackIn.translate(.5f, .5f, .5f);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-dir.toYRot()+180f)); // rotate | getHorizontalAngle
			matrixStackIn.translate(u1*xoffset, u1*yoffset, zoffset);
			matrixStackIn.scale(scale, scale, scale);
	        Minecraft.getInstance().getItemRenderer().renderStatic(items.getStackInSlot(0), ItemTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 1); // 1 ? 
	        matrixStackIn.popPose();
		}
        
        //Stack 2
		if(items.getStackInSlot(1) != ItemStack.EMPTY) {
	        matrixStackIn.pushPose();
	        matrixStackIn.translate(.5f, .5f, .5f);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-dir.toYRot()+180f));
			matrixStackIn.translate(0, u1*yoffset, zoffset);
			matrixStackIn.scale(scale, scale, scale);
	        Minecraft.getInstance().getItemRenderer().renderStatic(items.getStackInSlot(1), ItemTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 1);
	        matrixStackIn.popPose();
		}
        
        //Stack 3
        if(items.getStackInSlot(2) != ItemStack.EMPTY) {
	        matrixStackIn.pushPose();
	        matrixStackIn.translate(.5f, .5f, .5f);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-dir.toYRot()+180f));
			matrixStackIn.translate(-u1*xoffset, u1*yoffset, zoffset);
			matrixStackIn.scale(scale, scale, scale);
	        Minecraft.getInstance().getItemRenderer().renderStatic(items.getStackInSlot(2), ItemTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 1);
	        matrixStackIn.popPose();
        }
        
      //Stack 4
        if(items.getStackInSlot(3) != ItemStack.EMPTY) {
	        matrixStackIn.pushPose();
	        matrixStackIn.translate(.5f, .5f, .5f);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-dir.toYRot()+180f));
			matrixStackIn.translate(u1*xoffset, -u1*yoffset2, zoffset);
			matrixStackIn.scale(scale, scale, scale);
	        Minecraft.getInstance().getItemRenderer().renderStatic(items.getStackInSlot(3), ItemTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 1);
	        matrixStackIn.popPose();
        }
        
        //Stack 5
        if(items.getStackInSlot(4) != ItemStack.EMPTY) {
	        matrixStackIn.pushPose();
	        matrixStackIn.translate(.5f, .5f, .5f);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-dir.toYRot()+180f));
			matrixStackIn.translate(0, -u1*yoffset2, zoffset);
			matrixStackIn.scale(scale, scale, scale);
	        Minecraft.getInstance().getItemRenderer().renderStatic(items.getStackInSlot(4), ItemTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 1);
	        matrixStackIn.popPose();
        }
        
        //Stack 6
        if(items.getStackInSlot(5) != ItemStack.EMPTY) {
	        matrixStackIn.pushPose();
	        matrixStackIn.translate(.5f, .5f, .5f);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-dir.toYRot()+180f));
			matrixStackIn.translate(-u1*xoffset, -u1*yoffset2, zoffset);
			matrixStackIn.scale(scale, scale, scale);
	        Minecraft.getInstance().getItemRenderer().renderStatic(items.getStackInSlot(5), ItemTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 1);
	        matrixStackIn.popPose();
        }
	}
}
