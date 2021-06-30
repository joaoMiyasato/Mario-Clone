package com.japoronga.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Clear {
	
	private static double t = 0;
	public static boolean z = false;
	
	public static void tick() {
		
	}
	
	public static void render(Graphics g) {
		if(z) {
			t+=0.6;
			if(t >= 250)
				t = 250;
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(new Color(0,0,0,(int)t));
			g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			if(t == 250) {
				t = 0;
				Game.player.right = false;
				Game.gamestate = "PLAY";
			}
		}
	}

}
