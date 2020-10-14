package com.mrh0.buildersaddition.arcade.games;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrh0.buildersaddition.arcade.ArcadeGame;
import com.mrh0.buildersaddition.arcade.ArcadeScreen;
import com.mrh0.buildersaddition.tileentity.ArcadeTileEntity;

import net.minecraft.client.gui.screen.Screen;

public class ArcadeBreakout extends ArcadeGame {

	public ArcadeBreakout(ArcadeScreen s, ArcadeTileEntity te) {
		super(s, te);
		balls = new ArrayList<Ball>();
	}
	
	final int padWidth = 6;
	final float padSpeed = 1;
	final float ballSpeed = 1f;
	
	final ArrayList<Ball> balls;
	
	@Override
	public void start() {
		super.start();
		s.clear();
		balls.add(new Ball(s.width/2, s.height/2));
		
		s.setFgRenderer(this::fgRender);
	}
	
	private void fgRender(MatrixStack stack, int x, int y, int width, int height) {
		for(Ball b : balls) {
			b.render(stack, x, y, width, height);
		}
	}

	private class Ball {
		public float x;
		public float y;
		
		public float vx = 0f;
		public float vy = 0f;
		
		public Ball(int x, int y) {
			this.x = x;
			this.y = y;
			
			this.setVelocityAngle((float)Math.random()*360f, ballSpeed);
		}
		
		public void update() {
			//s.setColors(0x0, 0x0);
			//s.print(getX(), getY(), " ");
			x += vx;
			y += vy;
			
			if(x + vx <= 0) {
				x = 0;
				bounceVertical();
			}
			if(y + vy <= 0) {
				y = 0;
				bounceHorizontal();
			}
			if(x + vx >= s.width-1) {
				x = s.width-1;
				bounceVertical();
			}
			if(y + vy >= s.height-1) {
				y = s.height-1;
				bounceHorizontal();
			}
			
			//s.setColors(0xa, 0xa);
			//s.print(getX(), getY(), " ");
		}
		
		public void render(MatrixStack stack, int sx, int sy, int swidth, int sheight) {
			Screen.fill(stack, sx + (int)(x*s.cellWidth), sy + (int)(y*s.cellHeight), sx + s.cellWidth + (int)(x*s.cellWidth), sy + s.cellHeight + (int)(y*s.cellHeight), ArcadeScreen.getRenderColor(0xa));
		}
		
		public int getX() {
			return (int)x;
		}
		
		public int getY() {
			return (int)y;
		}
		
		public void bounceVertical() {
			vx = -vx;
			playSound(NOTE_BIT, 0);
		}
		
		public void bounceHorizontal() {
			vy = -vy;
			playSound(NOTE_BIT, 0);
		}
		
		public void setVelocity(float x, float y) {
			this.vx = x;
			this.vy = y;
		}
		
		public void setVelocityAngle(float angle, float v) {
			this.setVelocity((float)Math.cos(Math.toRadians(angle))*v, (float)Math.sin(Math.toRadians(angle))*v);
		}
	}
	
	@Override
	public void frame(long time) {
		super.frame(time);
		for(Ball b : balls) {
			b.update();
		}
	}
}
