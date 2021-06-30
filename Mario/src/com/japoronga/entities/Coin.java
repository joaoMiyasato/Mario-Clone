package com.japoronga.entities;

import java.awt.image.BufferedImage;

import com.japoronga.main.Game;
import com.japoronga.main.Sound;

public class Coin extends Entity{

	public Coin(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Coin) {
				if(Entity.isColliding(Game.player, atual)) {
					Sound.coin.play();
					Game.coin++;
					Game.entities.remove(atual);
				}
			}
			this.setMask(2, 0, 12, 16);
		}
		if(Game.coin == 100) {
			Game.lifeqtd++;
			Sound.oneup.play();
			Game.coin = 0;
		}
		
	}

}
