package com.japoronga.entities;

import java.awt.image.BufferedImage;

import com.japoronga.main.Game;
import com.japoronga.world.World;

public class Coin_block extends Entity{
	
	private int spd = 3;
	private int t = 0;
	
	public Coin_block(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		if(!World.isFree((int)x,(int)(y))) {
			spd = 3;
			y-=spd;
		}else {
			t++;
			if(t > 20)
				Game.entities.remove(this);
		}
	}

}
