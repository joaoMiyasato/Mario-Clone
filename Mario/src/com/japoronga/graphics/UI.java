package com.japoronga.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.japoronga.main.Game;

public class UI {
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("times new roman",Font.BOLD,20));
		g.drawString("Life "+Game.lifeqtd, 20, 30);
		g.drawString("Coins "+Game.coin, 20, 55);
	}

}
