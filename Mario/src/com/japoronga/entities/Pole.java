package com.japoronga.entities;

import java.awt.image.BufferedImage;

import com.japoronga.main.Clear;
import com.japoronga.main.Game;
import com.japoronga.main.Sound;
import com.japoronga.world.World;

public class Pole extends Entity{

	public Pole(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		System.out.println(Game.player.x);
		System.out.println(World.WIDTH);
		this.setMask(3, 0, 10, 16);
		if(Entity.isColliding(this, Game.player))
				Game.gamestate = "CLEAR";
		if(Entity.isColliding(this, Game.player) && World.isFree(Game.player.getX(), Game.player.getY()+1)) {
			Sound.clear.play();
			Game.player.x = this.x+7;
			Game.player.curSpeed = 0;
		}else if(!World.isFree(Game.player.getX(), Game.player.getY()+1) && Game.gamestate == "CLEAR"){
			Clear.z = true;
			Game.player.right = true;
			Game.player.curSpeed = 0.5;
			if(Game.player.curSpeed > 0.5) {
				Game.player.curSpeed = 0.5;
			}
		}
	}

}
