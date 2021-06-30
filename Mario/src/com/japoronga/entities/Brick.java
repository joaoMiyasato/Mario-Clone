package com.japoronga.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.japoronga.main.Game;
import com.japoronga.world.FloorTile;
import com.japoronga.world.Tile;
import com.japoronga.world.World;

public class Brick extends Entity{

	public Brick(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		depth = 1;
		int xx = (int)(x/16);
		int yy = (int)(y/16);
		if(Game.player.isJumping && Game.life == 2) {
			if(this.isColliding((int)(Game.player.getX()), (int)(Game.player.y - 2), (int)(this.getX()), (int)(this.getY())) && Game.life == 1 || this.isColliding((int)(Game.player.getX()), (int)(Game.player.y - 2), (int)(this.getX()), (int)(this.getY())) && Game.life == 2 && Game.player.crouch || this.isColliding((int)(Game.player.getX()), (int)(Game.player.y - 10), (int)(this.getX()), (int)(this.getY())) && Game.life == 2 && !Game.player.crouch) {
				Game.player.isJumping = false;
				Game.player.jump2 = false;
				Game.player.enemyJump = false;
				Game.player.vspd = 0;
				World.tiles[xx+((yy)*World.WIDTH)] = new FloorTile(xx*16,yy*16,Tile.BACKGROUND);
				sprite = Tile.BACKGROUND;
				Game.entities.remove(this);
			}
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
	}

}
