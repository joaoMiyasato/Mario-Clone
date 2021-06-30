package com.japoronga.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.japoronga.main.Game;

public class Tile {
	
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 16, 16, 16);
	public static BufferedImage TILE_FLOOR2 = Game.spritesheet.getSprite(0, 16*2, 16, 16);
	public static BufferedImage BACKGROUND = Game.spritesheet.getSprite(16, 16, 16, 16);
	public static BufferedImage TILE_PIPE = Game.spritesheet.getSprite(0, 48, 16, 16);
	public static BufferedImage TILE_PIPE2 = Game.spritesheet.getSprite(16, 48, 16, 16);
	public static BufferedImage TILE_PIPE3 = Game.spritesheet.getSprite(32, 48, 16, 16);
	public static BufferedImage TILE_PIPE4 = Game.spritesheet.getSprite(48, 48, 16, 16);
	public static BufferedImage TILE_BLOCK = Game.spritesheet.getSprite(128, 16, 16, 16);
	
	public boolean show = false;
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
	
}
