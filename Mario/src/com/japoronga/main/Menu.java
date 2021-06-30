package com.japoronga.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.japoronga.entities.Entity;
import com.japoronga.world.World;

public class Menu {
	
	public String[] option = {"Play","Exit"};
	
	public int currentOption = 0;
	public int maxOption = option.length - 1;
	
	public boolean up,down,enter;
	
	public void tick() {
		
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0) {
				currentOption = maxOption;
			}
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption) {
				currentOption = 0;
			}
		}
		if(enter) {
			enter = false;
			if(option[currentOption] == "Play") {
				
			}else if(option[currentOption] == "Exit") {
				System.exit(1);
			}
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,200));
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		
		g.setColor(Color.yellow);
		g.setFont(new Font("arial",Font.BOLD,50));
		g.drawString("Flappy Nelson", Game.WIDTH/2+40, 200);
		g.setFont(new Font("arial",Font.BOLD,30));
		g.drawString("Play", Game.WIDTH/2+70, 280);
		g.drawString("Exit", Game.WIDTH/2+260, 280);
		if(option[currentOption] == "Play") {
			g.setColor(Color.yellow);
			g.fillOval(Game.WIDTH/2+40, 262, 15, 15);
		}else if(option[currentOption] == "Exit") {
			g.fillOval(Game.WIDTH/2+230, 262, 15, 15);
		}
		
	}

}
