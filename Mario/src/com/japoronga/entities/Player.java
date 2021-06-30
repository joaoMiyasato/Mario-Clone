package com.japoronga.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.japoronga.main.Game;
import com.japoronga.main.Sound;
import com.japoronga.world.Camera;
import com.japoronga.world.World;

public class Player extends Entity {
	
	public boolean right = false, left = false;
	
	public double vspd = 0;
	private double gravity = 0.3;
	public int dir = 1;
	
	public static boolean gRight = false;
	public static boolean gLeft = false;
	public static boolean gRight2 = false;
	public static boolean gLeft2 = false;
	
	public boolean jump = false;
	public boolean jump2 = false;
	public boolean enemyJump = false;
	public boolean isJumping = false;
	public int jumpFrames = 0;
	public int jumpHeight = 60;
	
	private int framesAnimation = 0;
	private int maxFrames = 15;
	private int maxSprite = 2;
	private int curSprite = 0;
	private boolean moving = false;
	
	private boolean onAir = false;
	
	public boolean crouch = false;
	public boolean run = false;
	public double curSpeed = 0;
	public double maxSpeed = 2.5;
	
	public boolean imune = false;
	public boolean noDamage = false;
	public int imuneTime = 0;
	public int imuneFrames = 0;
	public int im = 1;
	public int es = 0;
	
	public boolean estrelinha = false;
	
	public Player(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
	}
	
	public void tick() {
		depth = 2;
		updateCamera();
		gRight = World.isFree((int)(x+maxSpeed), (int)(y));
		gLeft = World.isFree((int)(x-maxSpeed), (int)(y));
		gRight2 = World.isFree((int)(x+maxSpeed), (int)(y-8));
		gLeft2 = World.isFree((int)(x-maxSpeed), (int)(y-8));
		//Gravidade
		if(vspd > 3) {
			vspd = 3;
		}
		vspd += gravity;
		if(!World.isFree((int)x, (int)(y+1)) && jump || enemyJump) {
			if(!enemyJump) {
				Sound.jump.play();
			}else {
				Sound.enemyjump.play();
			}
			vspd = -2.8; 
			onAir = true;
			jump = false;
			enemyJump = false;
			isJumping = true;
		}
		if(jump2 && isJumping || enemyJump) {
			jumpFrames += 5;
			vspd -= 0.4;
			if(jumpFrames >= jumpHeight) {
				jump2 = false;
				isJumping = false;
				jumpFrames = 0;
			}
		}else {
			jumpFrames = 0;
			isJumping = false;
		}
		
		if(!World.isFree((int)x, (int)(y+vspd))) {
			int signvspd = 0;
			if(vspd >= 0) 
			{
				signvspd = 1;
			}else {
				signvspd = -1;
			}
			while(World.isFree((int)(x), (int)(y+signvspd))) {
				y = y + signvspd;
			}
			vspd = 0;
		}
		if(!World.isFree((int)x, (int)(y+vspd-8)) && Game.life == 2 && !crouch) {
			vspd = 0;
			isJumping=false;
		}
		y = y + vspd;
		
		if(World.isFree((int)x, (int)(y+1)) && vspd > 0 && !estrelinha) {
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Enemy) {
					if(isColliding(this, e)) {
						enemyJump = true;
						Game.entities.remove(i);
						break;
					}
				}
			}
		}else if(!World.isFree((int)x, (int)(y+1))) {
			onAir = false;
		}
		
		if(crouch && !onAir){
			curSpeed -= 0.15;
			if(curSpeed <= 0)
				curSpeed = 0;
		}
		
		if(curSpeed > 0) {
			if(moving) {
			curSpeed -= 0.03;
			}else if(!moving) {
				curSpeed -= 1.5;
			}
			if(curSpeed <= 0)
				curSpeed = 0;
		}
		
		if(right && gRight && Game.life == 1 || right && gRight && gRight2 && Game.life == 2 && !crouch || right && gRight && Game.life == 2 && crouch) {
			if(!crouch || onAir)
				curSpeed += 0.13;
			if(!run) {
				if(curSpeed > speed) {
					curSpeed = speed;
				}
			}else if(run) {
				if(curSpeed >= maxSpeed)
					curSpeed = maxSpeed;
			}
			dir = 1;
			moving = true;
		}else if(left && gLeft && Game.life == 1 || left && gLeft && gLeft2 && Game.life == 2 && !crouch || left && gLeft && Game.life == 2 && crouch) {
			if(!crouch || onAir)
				curSpeed += 0.13;
			if(!run) {
				if(curSpeed > speed) {
					curSpeed = speed;
				}
			}else if(run) {
				if(curSpeed >= maxSpeed)
					curSpeed = maxSpeed;
			}
			dir = 2;
			moving = true;
		}else {
			moving = false;
		}
		
		if(!World.isFree((int)(x+curSpeed), (int)(y))) {
			int signCurSpd = 0;
			if(curSpeed >= 0) 
			{
				signCurSpd = 1;
			}else {
				signCurSpd = -1;
			}
			while(World.isFree((int)(x+signCurSpd), (int)(y))) {
				x = x + signCurSpd;
			}
			curSpeed = 0;
		}
		
		if(dir == 1 && gRight) {
			x+=curSpeed;
		}else if(dir == 2 && gLeft) {
			x-=curSpeed;
		}
		if(Mushroom.not == false) {
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Mushroom) {
					if(isColliding(this, e)) {
						Sound.powerup.play();
						Game.life++;
						Game.entities.remove(e);
						if(Game.life > 2)
							Game.life = 2;
						break;
					}
				}
			}
		}
		if(Oneup.not == false) {
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Oneup) {
					if(isColliding(this, e)) {
						Sound.oneup.play();
						Game.lifeqtd++;
						Game.entities.remove(e);
						break;
					}
				}
			}
		}
		if(Star.not == false) {
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Star) {
					if(isColliding(this, e)) {
						estrelinha = true;
						Game.entities.remove(e);
						break;
					}
				}
			}
		}
		
		if(estrelinha) {
			Sound.starTheme.loop(0);
			es++;
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Enemy) {
					if(isColliding(this, e)) {
						Sound.kick.play();
						Game.entities.remove(e);
						break;
					}
				}
			}
			if(es > 60*10) {
				es = 0;
				estrelinha = false;
			}
		}
		if(y > 458) {
			Game.gamestate = "DEAD";
		}
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				if(isColliding(this, e)) {
					if(!noDamage && !estrelinha) {
						Game.life--;
						noDamage = true;
						im = 1;
					}
					if(Game.life <= 0) {
						Game.gamestate = "DEAD";
					}
				}
			}
		}
		if(noDamage) {
			imuneTime++;
			imuneFrames++;
			if(imuneTime>130) {
				noDamage = false;
				imuneTime = 0;
			}
			if(imuneFrames > 8) {
				if(!imune) {
					if(im >= 1)
						imune = true;
					im++;
				}else if(imune) {
					imune = false;
					im = 0;
				}
				imuneFrames = 0;
			}
		}else {
			imune = false;
			im = 0;
		}
		if(Game.life == 2 && !crouch) {
			this.setMask(0, -8, 16, 24);
		}else {
			this.setMask(0, 0, 16, 16);
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
		if(Game.life <= 1) {
			if(dir == 1 && moving && !onAir && !crouch && !imune) {
				sprite = Entity.PLAYER_RIGHT_EN[curSprite];
			}else if(dir == 1 && !moving && !onAir && !crouch && !imune) {
				sprite = Entity.PLAYER_RIGHT_STOP_EN;
			}else if(dir == 1 && onAir && !crouch && !imune) {
				sprite = Entity.PLAYER_RIGHT_JUMPING_EN;
			}else if(dir == 1 && crouch && !imune) {
				sprite = Entity.PLAYER_RIGHT_CROUCHING_EN;
			}else if(dir == 2 && moving && !onAir && !crouch && !imune) {
				sprite = Entity.PLAYER_LEFT_EN[curSprite];
			}else if(dir == 2 && !moving && !onAir && !crouch && !imune) {
				sprite = Entity.PLAYER_LEFT_STOP_EN;
			}else if(dir == 2 && onAir && !crouch && !imune) {
				sprite = Entity.PLAYER_LEFT_JUMPING_EN;
			}else if(dir == 2 && crouch && !imune) {
				sprite = Entity.PLAYER_LEFT_CROUCHING_EN;
			}else if(!crouch && imune && !onAir || onAir && imune && !crouch) {
				sprite = Entity.PLAYER_DAMAGE_STAND_EN;
			}else if(crouch && imune && !onAir || onAir && imune && crouch) {
				sprite = Entity.PLAYER_DAMAGE_CROUCHING_EN;
			}
		}else if(Game.life == 2) {
			sprite = null;
			if(dir == 1 && moving && !onAir && !crouch && !imune) {
				g.drawImage(Entity.PLAYER_RIGHT2_EN[curSprite], this.getX() - Camera.x, this.getY() - 16 - Camera.y, null);
			}else if(dir == 1 && !moving && !onAir && !crouch && !imune) {
				g.drawImage(Entity.PLAYER_RIGHT_STOP2_EN, this.getX() - Camera.x, this.getY() - 16 - Camera.y, null);
			}else if(dir == 1 && onAir && !crouch && !imune) {
				g.drawImage(Entity.PLAYER_RIGHT_JUMPING2_EN, this.getX() - Camera.x, this.getY() - 16 - Camera.y, null);
			}else if(dir == 1 && crouch && !imune) {
				g.drawImage(Entity.PLAYER_RIGHT_CROUCHING2_EN, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == 2 && moving && !onAir && !crouch && !imune) {
				g.drawImage(Entity.PLAYER_LEFT2_EN[curSprite], this.getX() - Camera.x, this.getY() - 16 - Camera.y, null);
			}else if(dir == 2 && !moving && !onAir && !crouch && !imune) {
				g.drawImage(Entity.PLAYER_LEFT_STOP2_EN, this.getX() - Camera.x, this.getY() - 16 - Camera.y, null);
			}else if(dir == 2 && onAir && !crouch && !imune) {
				g.drawImage(Entity.PLAYER_LEFT_JUMPING2_EN, this.getX() - Camera.x, this.getY() - 16 - Camera.y, null);
			}else if(dir == 2 && crouch && !imune) {
				g.drawImage(Entity.PLAYER_LEFT_CROUCHING2_EN, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(!crouch && imune && !onAir || onAir && imune && !crouch) {
				g.drawImage(Entity.PLAYER_DAMAGE_STAND2_EN, this.getX() - Camera.x, this.getY() - 16 - Camera.y, null);
			}else if(crouch && imune && !onAir || onAir && imune && crouch) {
				g.drawImage(Entity.PLAYER_DAMAGE_CROUCHING2_EN, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
		super.render(g);
	}
	
}
