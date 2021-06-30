package com.japoronga.entities;

import java.awt.image.BufferedImage;

import com.japoronga.main.Game;
import com.japoronga.main.Sound;
import com.japoronga.world.World;

public class HPole extends Entity{

	public int c = 0;
	
	public HPole(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		this.setMask(3, 0, 10, 16);
		if(Entity.isColliding(this, Game.player) && World.isFree(Game.player.getX(), Game.player.getY()+1)) {
			if(c == 0) {
				Sound.oneup.play();
				Game.lifeqtd++;
				c++;
			}
			Game.gamestate = "CLEAR";
			Game.player.x = this.x+7;
			Game.player.curSpeed = 0;
		}else {
			c = 0;
		}
	}

}
