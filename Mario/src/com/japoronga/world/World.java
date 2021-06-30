package com.japoronga.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.japoronga.entities.Brick;
import com.japoronga.entities.Coin;
import com.japoronga.entities.Enemy;
import com.japoronga.entities.Entity;
import com.japoronga.entities.HPole;
import com.japoronga.entities.Player;
import com.japoronga.entities.Pole;
import com.japoronga.entities.Question;
import com.japoronga.entities.Question2;
import com.japoronga.entities.Question3;
import com.japoronga.entities.Question4;
import com.japoronga.graphics.Spritesheet;
import com.japoronga.graphics.UI;
import com.japoronga.main.Game;
import com.japoronga.main.Menu;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			//Definindo onde os Tiles irão e o que são
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.BACKGROUND);
					
					if(pixelAtual == 0x0FF000000) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.BACKGROUND);
					}else if(pixelAtual == 0x0FFffffff) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_FLOOR);
						if(yy-1 >= 0 && pixels[xx + ((yy-1) * map.getWidth())] == 0x0FFffffff ) {
							tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_FLOOR2);
						}
					}else if(pixelAtual == 0x0FF0026FF) {
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}else if(pixelAtual == 0x0FFff0000) {
						Enemy goompa = new Enemy(xx*16,yy*16,16,16,1,Game.spritesheet.getSprite(0, 0, 16, 16));
						Game.entities.add(goompa);
					}else if(pixelAtual == 0x0FFFF6A00) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.BACKGROUND);
						Brick brick = new Brick(xx*16,yy*16,16,16,0,Entity.BRICK_EN);
						Game.entities.add(brick);
					}else if(pixelAtual == 0x0FFFFD800) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.BACKGROUND);
						Question question = new Question(xx*16,yy*16,16,16,0,Entity.QUESTION_EN[0]);
						Game.entities.add(question);
					}else if(pixelAtual == 0x0FF2F9E00) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_PIPE);
						if(yy-1 >= 0 && pixels[xx + ((yy-1) * map.getWidth())] == 0x0FF2F9E00 ) {
							tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_PIPE3);
						}
					}else if(pixelAtual == 0x0FF2FD900) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_PIPE2);
						if(yy-1 >= 0 && pixels[xx + ((yy-1) * map.getWidth())] == 0x0FF2FD900 ) {
							tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_PIPE4);
						}
					}else if(pixelAtual == 0x0FFFFFF00) {
						Coin coin = new Coin(xx*16,yy*16,16,16,0,Entity.COIN_EN);
						Game.entities.add(coin);
					}else if(pixelAtual == 0x0FFFFAA00) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.BACKGROUND);
						Question2 question2 = new Question2(xx*16,yy*16,16,16,0,Entity.QUESTION_EN[0]);
						Game.entities.add(question2);
					}else if(pixelAtual == 0x0FFFF5500) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.BACKGROUND);
						Question3 question3 = new Question3(xx*16,yy*16,16,16,0,Entity.QUESTION_EN[0]);
						Game.entities.add(question3);
					}else if(pixelAtual == 0x0FFFF7800) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.BACKGROUND);
						Question4 question4 = new Question4(xx*16,yy*16,16,16,0,Entity.QUESTION_EN[0]);
						Game.entities.add(question4);
					}else if(pixelAtual == 0x0FFA0A0A0) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_BLOCK);
					}else if(pixelAtual == 0x0FFB6FF00) {
						HPole hpole = new HPole(xx*16, yy*16, 16, 16, 0, Entity.POLE2_EN);
						Game.entities.add(hpole);
					}else if(pixelAtual == 0x0FF88FF00) {
						Pole pole = new Pole(xx*16, yy*16, 16, 16, 0, Entity.POLE_EN);
						Game.entities.add(pole);
					}
					
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext,int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		if			(!((tiles[x1  +  (y1*World.WIDTH)] instanceof WallTile) ||
					(tiles[x2  +  (y2*World.WIDTH)] instanceof WallTile) ||
					(tiles[x3  +  (y3*World.WIDTH)] instanceof WallTile) ||
					(tiles[x4  +  (y4*World.WIDTH)] instanceof WallTile))) {
			return true;
		}
		return false;
	}
	
	//Restart
	public static void restartGame() {
		Game.life = 1;
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.entities = new ArrayList<Entity>();
		Game.player = new Player(0,0,16,16,1.6,Entity.PLAYER_RIGHT_STOP_EN);
		Game.world = new World("/level1.png");
		
		Game.entities.add(Game.player);
		Game.gamestate = "PLAY";
		return;
	}
	
	public void render(Graphics g) {
		
		//Renderizando tiles
		int xstart = Camera.x / TILE_SIZE;
		int ystart = Camera.y / TILE_SIZE;
		
		int xfinal = xstart + (Game.WIDTH / TILE_SIZE);
		int yfinal = ystart + (Game.HEIGHT / TILE_SIZE);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
}
