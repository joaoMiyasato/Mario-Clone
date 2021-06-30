package com.japoronga.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.japoronga.main.Game;
import com.japoronga.main.Sound;


public class Question2 extends Entity{
	
	private int framesAnimation = 0;
	private int maxFrames = 10;
	private int curSprite = 0;
	private int maxSprite = 3;
	private boolean click = false;
	private int v = 0;
	
	private boolean pulando = false;
	private int pulandoFrames = 0;
	private int pulandoFrames2 = 0;

	public Question2(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		depth = 1;
		if(Game.player.isJumping) {
			if(this.isColliding((int)(Game.player.getX()), (int)(Game.player.y - 2), (int)(this.getX()), (int)(this.getY())) && Game.life == 1 || this.isColliding((int)(Game.player.getX()), (int)(Game.player.y - 2), (int)(this.getX()), (int)(this.getY())) && Game.life == 2 && Game.player.crouch || this.isColliding((int)(Game.player.getX()), (int)(Game.player.y - 20), (int)(this.getX()), (int)(this.getY())) && Game.life == 2 && !Game.player.crouch) {
				v++;
				if(v < 2) {
					if(!click) {
						pulando = true;
						Sound.coin.play();
						Game.coin++;
						Coin_block coin2 = new Coin_block(this.getX(), this.getY(), 16, 16, 0, Entity.COIN2_EN);
						Game.entities.add(coin2);
						if(this.rand.nextInt(100) < 35)
							click = true;
					}
				}
			}else {
				v = 0;
			}
		}
		if(pulando) {
			if(pulandoFrames < 3) {
				y -= 2;
				pulandoFrames += 1;
			}
			if(pulandoFrames2 < 6) {
				y += 1;
				pulandoFrames2 += 1;
			}else {
				pulando = false;
				pulandoFrames = 0;
				pulandoFrames2 = 0;
			}
		}
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
		if(!click) {
			sprite = Entity.QUESTION_EN[0];
		}else {
			sprite = Entity.QUESTION_EN[1];
		}
		super.render(g);
	}
	
}