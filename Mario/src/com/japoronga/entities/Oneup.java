package com.japoronga.entities;

import java.awt.image.BufferedImage;

import com.japoronga.main.Game;
import com.japoronga.world.World;

public class Oneup extends Entity{
	
	public boolean right = false,left = true;
	public static boolean not = true;
	private int t = 0;
	private int gravity = 2;

	public Oneup(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		depth = 0;
		this.setSpd(0.7);
		if(World.isFree((int)x, (int)(y+gravity))) {
			y+=gravity;
		}
		if(right && World.isFree((int)(x+speed), (int)(y))) {
			x+=speed;
		}else if(left && World.isFree((int)(x-speed), (int)(y))) {
			x-=speed;
		}else if(!World.isFree((int)(x-speed), (int)(y))) {
			left = false;
			right = true;
		}else if(!World.isFree((int)(x+speed), (int)(y))) {
			right = false;
			left = true;
		}
		if(!World.isFree((int)x,(int)(y))) {
			y-= 0.4;
		}
		t++;
		if(t > 40) {
			not = false;
			t = 0;
		}
		if(y > 458) {
			Game.entities.remove(this);
		}
	}

}