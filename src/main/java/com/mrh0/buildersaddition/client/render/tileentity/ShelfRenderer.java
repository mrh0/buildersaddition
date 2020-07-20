package com.mrh0.buildersaddition.client.render.tileentity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrh0.buildersaddition.blocks.Shelf;
import com.mrh0.buildersaddition.inventory.ModInventory;
import com.mrh0.buildersaddition.tileentity.ShelfTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

public class ShelfRenderer extends TileEntityRenderer<ShelfTileEntity> {

	private Object object;
	private final Minecraft mc = Minecraft.getInstance();

	public ShelfRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}
	
	private static final float u1 = 1f/16f;

	@Override
	public void render(ShelfTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Direction dir = tileEntityIn.getBlockState().get(Shelf.FACING);
		
		ModInventory items = tileEntityIn.handler;
        
		float scale = .30f;
		float xoffset = 5.2f;
		float yoffset = 3.5f;
		float yoffset2 = 4.5f;
		float zoffset = .15f;
		
        //Stack 1
		if(items.getStackInSlot(0) != ItemStack.EMPTY) {
	        matrixStackIn.push();
	        matrixStackIn.translate(.5f, .5f, .5f);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-dir.getHorizontalAngle()+180f));
			matrixStackIn.translate(u1*xoffset, u1*yoffset, zoffset);
			matrixStackIn.scale(scale, scale, scale);
	        Minecraft.getInstance().getItemRenderer().renderItem(items.getStackInSlot(0), ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
	        matrixStackIn.pop();
		}
        
        //Stack 2
		if(items.getStackInSlot(1) != ItemStack.EMPTY) {
	        matrixStackIn.push();
	        matrixStackIn.translate(.5f, .5f, .5f);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-dir.getHorizontalAngle()+180f));
			matrixStackIn.translate(0, u1*yoffset, zoffset);
			matrixStackIn.scale(scale, scale, scale);
	        Minecraft.getInstance().getItemRenderer().renderItem(items.getStackInSlot(1), ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
	        matrixStackIn.pop();
		}
        
        //Stack 3
        if(items.getStackInSlot(2) != ItemStack.EMPTY) {
	        matrixStackIn.push();
	        matrixStackIn.translate(.5f, .5f, .5f);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-dir.getHorizontalAngle()+180f));
			matrixStackIn.translate(-u1*xoffset, u1*yoffset, zoffset);
			matrixStackIn.scale(scale, scale, scale);
	        Minecraft.getInstance().getItemRenderer().renderItem(items.getStackInSlot(2), ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
	        matrixStackIn.pop();
        }
        
      //Stack 4
        if(items.getStackInSlot(3) != ItemStack.EMPTY) {
	        matrixStackIn.push();
	        matrixStackIn.translate(.5f, .5f, .5f);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-dir.getHorizontalAngle()+180f));
			matrixStackIn.translate(u1*xoffset, -u1*yoffset2, zoffset);
			matrixStackIn.scale(scale, scale, scale);
	        Minecraft.getInstance().getItemRenderer().renderItem(items.getStackInSlot(3), ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
	        matrixStackIn.pop();
        }
        
        //Stack 5
        if(items.getStackInSlot(4) != ItemStack.EMPTY) {
	        matrixStackIn.push();
	        matrixStackIn.translate(.5f, .5f, .5f);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-dir.getHorizontalAngle()+180f));
			matrixStackIn.translate(0, -u1*yoffset2, zoffset);
			matrixStackIn.scale(scale, scale, scale);
	        Minecraft.getInstance().getItemRenderer().renderItem(items.getStackInSlot(4), ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
	        matrixStackIn.pop();
        }
        
        //Stack 6
        if(items.getStackInSlot(5) != ItemStack.EMPTY) {
	        matrixStackIn.push();
	        matrixStackIn.translate(.5f, .5f, .5f);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-dir.getHorizontalAngle()+180f));
			matrixStackIn.translate(-u1*xoffset, -u1*yoffset2, zoffset);
			matrixStackIn.scale(scale, scale, scale);
	        Minecraft.getInstance().getItemRenderer().renderItem(items.getStackInSlot(5), ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
	        matrixStackIn.pop();
        }
	}
}
