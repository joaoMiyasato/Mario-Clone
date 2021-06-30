package com.japoronga.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.japoronga.world.World;

public class Enemy extends Entity{
	
	public boolean right = false,left = true;
	
	private int framesAnimation = 0;
	private int maxFrames = 28;
	private int maxSprite = 1;
	private int curSprite = 0;
	private int gravity = 2;

	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		depth = 1;
		this.setSpd(0.5);
		if(World.isFree((int)x, (int)(y+gravity))) {
			y+=gravity;
		}
		if(right && World.isFree((int)(x+speed), (int)(y))) {
			x+=speed;
			if(World.isFree((int)(x+16), (int)(y+1)) && World.isFree((int)(x+16), (int)(y+17))) {
				right = false;
				left = true;
			}
		}else if(left && World.isFree((int)(x-speed), (int)(y))) {
			x-=speed;
			if(World.isFree((int)(x-16), (int)(y+1)) && World.isFree((int)(x-16), (int)(y+17))) {
				left = false;
				right = true;
			}
		}else if(!World.isFree((int)(x-speed), (int)(y))) {
			left = false;
			right = true;
		}else if(!World.isFree((int)(x+speed), (int)(y))) {
			right = false;
			left = true;
		}
		this.setMask(0, 5, 16, 11);
	}
	
	public void render(Graphics g) {
		framesAnimation++;
		if(framesAnimation == maxFrames) {
			curSprite++;
			framesAnimation = 0;
			if(curSprite > maxSprite) {
				curSprite = 0;
			}
		}
		
		if(right) {
			sprite = Entity.GOOMPA_RIGHT_EN[curSprite];
		}else if (left) {
			sprite = Entity.GOOMPA_LEFT_EN[curSprite];
		}
		
		super.render(g);
	}

}
