package com.japoronga.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.japoronga.main.Game;
import com.japoronga.world.Camera;
import com.japoronga.world.Node;
import com.japoronga.world.Vector2i;
import com.japoronga.world.World;

public class Entity {
	
	public static BufferedImage PLAYER_RIGHT_STOP_EN = Game.spritesheet.getSprite(0, 0, 16, 16);
	public static BufferedImage PLAYER_LEFT_STOP_EN = Game.spritesheet.getSprite(16*7, 0, 16, 16);
	public static BufferedImage PLAYER_RIGHT_JUMPING_EN = Game.spritesheet.getSprite(32, 16, 16, 16);
	public static BufferedImage PLAYER_LEFT_JUMPING_EN = Game.spritesheet.getSprite(48, 16, 16, 16);
	public static BufferedImage PLAYER_RIGHT_CROUCHING_EN = Game.spritesheet.getSprite(16*4, 16, 16, 16);
	public static BufferedImage PLAYER_LEFT_CROUCHING_EN = Game.spritesheet.getSprite(16*5, 16, 16, 16);
	public static BufferedImage PLAYER_DAMAGE_STAND_EN = Game.spritesheet.getSprite(16*8, 0, 16, 16);
	public static BufferedImage PLAYER_DAMAGE_CROUCHING_EN = Game.spritesheet.getSprite(16*9, 0, 16, 16);
	public static BufferedImage[] PLAYER_RIGHT_EN = {Game.spritesheet.getSprite(16, 0, 16, 16),Game.spritesheet.getSprite(16*2, 0, 16, 16),Game.spritesheet.getSprite(16*3, 0, 16, 16)};
	public static BufferedImage[] PLAYER_LEFT_EN = {Game.spritesheet.getSprite(16*4, 0, 16, 16),Game.spritesheet.getSprite(16*5, 0, 16, 16),Game.spritesheet.getSprite(16*6, 0, 16, 16)};
	public static BufferedImage[] GOOMPA_LEFT_EN = {Game.spritesheet.getSprite(0, 16*4, 16, 16),Game.spritesheet.getSprite(0, 16*5, 16, 16)};
	public static BufferedImage[] GOOMPA_RIGHT_EN = {Game.spritesheet.getSprite(16, 16*4, 16, 16),Game.spritesheet.getSprite(16, 16*5, 16, 16)};
	public static BufferedImage COIN_EN = Game.spritesheet.getSprite(48, 32, 16, 16);
	public static BufferedImage COIN2_EN = Game.spritesheet.getSprite(16*6, 32, 16, 16);
	public static BufferedImage BRICK_EN = Game.spritesheet.getSprite(32, 32, 16, 16);
	public static BufferedImage[] QUESTION_EN = {Game.spritesheet.getSprite(16, 32, 16, 16), Game.spritesheet.getSprite(16*4, 32, 16, 16)};
	public static BufferedImage MUSHROOM_EN = Game.spritesheet.getSprite(16*5, 32, 16, 16);
	public static BufferedImage PLAYER_RIGHT_STOP2_EN = Game.spritesheet.getSprite(0, 96, 16, 32);
	public static BufferedImage PLAYER_LEFT_STOP2_EN = Game.spritesheet.getSprite(16*7, 96, 16, 32);
	public static BufferedImage[] PLAYER_RIGHT2_EN = {Game.spritesheet.getSprite(16, 96, 16, 32),Game.spritesheet.getSprite(16*2, 96, 16, 32),Game.spritesheet.getSprite(16*3, 96, 16, 32)};
	public static BufferedImage[] PLAYER_LEFT2_EN = {Game.spritesheet.getSprite(16*4, 96, 16, 32),Game.spritesheet.getSprite(16*5, 96, 16, 32),Game.spritesheet.getSprite(16*6, 96, 16, 32)};
	public static BufferedImage PLAYER_DAMAGE_STAND2_EN = Game.spritesheet.getSprite(16*8, 96, 16, 32);
	public static BufferedImage PLAYER_DAMAGE_CROUCHING2_EN = Game.spritesheet.getSprite(16*9, 96, 16, 16);
	public static BufferedImage PLAYER_RIGHT_JUMPING2_EN = Game.spritesheet.getSprite(0, 128, 16, 32);
	public static BufferedImage PLAYER_LEFT_JUMPING2_EN = Game.spritesheet.getSprite(16, 128, 16, 32);
	public static BufferedImage PLAYER_RIGHT_CROUCHING2_EN = Game.spritesheet.getSprite(16*2, 128, 16, 16);
	public static BufferedImage PLAYER_LEFT_CROUCHING2_EN = Game.spritesheet.getSprite(16*3, 128, 16, 16);
	public static BufferedImage STAR_EN = Game.spritesheet.getSprite(96, 16, 16, 16);
	public static BufferedImage ONEUP_EN = Game.spritesheet.getSprite(112, 16, 16, 16);
	public static BufferedImage POLE_EN = Game.spritesheet.getSprite(112, 32, 16, 16);
	public static BufferedImage POLE2_EN = Game.spritesheet.getSprite(128, 32, 16, 16);
	
	public double x;
	public double y;
	protected int z;
	protected int width;
	protected int height;
	protected double speed;

	private int maskx, masky, mwidth, mheight;
	
	public int depth = 0;
	
	protected List<Node> path;
	
	protected BufferedImage sprite;
	
	public static Random rand = new Random();
	
	public Entity(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0, Entity n1) {
			if(n1.depth < n0.depth)
				return +1;
			if(n1.depth > n0.depth)
				return -1;
			return 0;
		}
	};
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void updateCamera() {
		Camera.x =  Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH - 16);
		Camera.y =  Camera.clamp(this.getY() , 0, World.HEIGHT*16 - Game.HEIGHT - 16);
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setSpd(double newSpd) {
		this.speed = newSpd;
	}
	
	public void setTamanho(int newWidth, int newHeight) {
		this.width = newWidth;
		this.height = newHeight;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target  = path.get(path.size() - 1).tile;
				//xprev = x;
				//vprev = y;
				if(x < target.x * 16) {
					x++;
				}else if(x > target.x *16) {
					x--;
				}
				
				if(y < target.y * 16) {
					y++;
				}else if(y > target.y * 16) {
					y--;
				}
				
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
			}
		}
	}
	
	public boolean isColliding(int xnext, int ynext, int trgtx, int trgty) {
		Rectangle entityCurrent = new Rectangle(xnext, ynext, width, height);
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e == this)
				continue;
			Rectangle targetEntity = new Rectangle(trgtx, trgty, width, height);
			if(entityCurrent.intersects(targetEntity)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY()+e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY()+e2.masky, e2.mwidth, e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public Rectangle topEntity() {
		return new Rectangle(
				(int)(x+maskx), 
				(int)(y+masky), 
				(int)(mwidth), 
				(int)(mheight/2)
				);
	}
	
	public Rectangle bottonEntity() {
		return new Rectangle(
				(int)(x+maskx), 
				(int)(y+(mheight/2)+masky), 
				(int)(mwidth), 
				(int)(mheight/2)
				);
	}
	
	public Rectangle leftEntity() {
		return new Rectangle(
				(int)(x+maskx),
				(int)(y+masky),
				(int)(mwidth/2),
				(int)(mheight)
				);
	}
	
	public Rectangle rightEntity() {
		return new Rectangle(
				(int)(x+mwidth/2+maskx),
				(int)(y+masky),
				(int)(mwidth/2),
				(int)(mheight)
				);
	}
	
	public void render(Graphics g) {
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y,mwidth,mheight);
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
	
}
