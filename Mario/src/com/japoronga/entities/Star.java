package com.japoronga.entities;

import java.awt.image.BufferedImage;

import com.japoronga.main.Game;
import com.japoronga.world.World;

public class Star extends Entity{

	public boolean right = false,left = true;
	private double gravity = 0.2;
	public static boolean not = true;
	private int t = 0;
	private boolean fora = false;
	
	private boolean jump = false;
	private double vspd = 0;
	public int jumpFrames = 0;
	public int jumpHeight = 3;

	public Star(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		depth = 0;
		this.setSpd(0.8);
		if(vspd > 3) {
			vspd = 3;
		}
		vspd += gravity;
		if(!World.isFree((int)x,(int)(y))) {
			y-= 0.4;
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
		t++;
		if(t > 40) {
			not = false;
			t = 0;
		}
		if(!World.isFree((int)x,(int)(y+1))) {
			jump = true;
		}
		if(jump) {
			jumpFrames++;
			vspd -= 2;
			if(vspd > 2.5) {
				vspd = 2.5;
			}
			if(jumpFrames >= jumpHeight) {
				jump = false;
				jumpFrames = 0;
				fora = true;
			}
		}
		if(fora) {
			if(!World.isFree((int)x, (int)(y+vspd))) {
				int signvspd = 0;
				if(vspd >= 0) 
				{
					signvspd = 1;
				}else {
					signvspd = -1;
				}
				while(World.isFree((int)(x), (int)(y+signvspd))) {
					y = y + signvspd;
				}
				vspd = 0;
			}
		}
		y = y + vspd;
		if(y > 458) {
			Game.entities.remove(this);
		}
		System.out.println(vspd);
	}
}
