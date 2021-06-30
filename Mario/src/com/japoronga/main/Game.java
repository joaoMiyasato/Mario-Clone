package com.japoronga.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import com.japoronga.entities.Entity;
import com.japoronga.entities.Player;
import com.japoronga.graphics.Spritesheet;
import com.japoronga.graphics.UI;
import com.japoronga.world.Tile;
import com.japoronga.world.World;

public class Game extends Canvas implements Runnable,KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private boolean isRunning;
	private Thread thread;
	public static final int WIDTH = 384;
	public static final int HEIGHT = 272;
	public static final int SCALE = 2;
	
	public static int lifeqtd = 3;
	public static int life = 1;
	public static int coin = 0;
	
	private BufferedImage image;

	public static List<Entity> entities;
	public static List<Tile> tiles;
	public static Spritesheet spritesheet;
	public static Player player;
	public static World world;
	
	public static String gamestate = "PLAY";
	public Menu menu;
	public UI ui;
	
	public Game() {
		//Sound.music.loop();
		
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		
		//Inicializando objetos
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		spritesheet = new Spritesheet("/spritesheet.png");
		entities = new ArrayList<Entity>();
		tiles = new ArrayList<Tile>();
		player = new Player(0,0,16,16,1.6,Entity.PLAYER_RIGHT_STOP_EN);
		world = new World("/level1.png");
		menu = new Menu();
		ui = new UI();
		
		entities.add(player);
	}
	
	public void initFrame() {
		frame = new JFrame("Mario");
		frame.add(this);
		frame.setUndecorated(false);
		frame.setResizable(false);
		frame.pack();
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		if(gamestate == "PLAY") {
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
		}else if(gamestate == "DEAD") {
			lifeqtd--;
			World.restartGame();
		}else if(gamestate == "CLEAR") {
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			Clear.tick();
		}
		
	}
		
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		
		Collections.sort(entities,Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		if(gamestate == "CLEAR") {
			Clear.render(g);
		}
		
		ui.render(g);
		bs.show();
	}
	
	public void run() {
		
		//Padrão
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer += 1000;
			}
		}
		
		stop();
		
	}
	//Abaixo teclas e seus respectivos comandos
	@Override
	public void keyPressed(KeyEvent e) {
		if(gamestate != "CLEAR") {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump = true;
			player.jump2 = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.crouch = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			player.run = true;
		}
	}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(gamestate != "CLEAR") {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump = false;
			player.jump2 = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.crouch = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_R) {
			World.restartGame();
		}
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			player.run = false;
		}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
