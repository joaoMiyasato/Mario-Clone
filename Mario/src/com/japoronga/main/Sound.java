package com.japoronga.main;
//Método antigo
/*
import java.applet.Applet;
import java.applet.AudioClip;
*/

//Método novo
import java.io.*;

import javax.sound.sampled.*;

public class Sound {
	//Método antigo
	/*
	private AudioClip clip;
	
	public static final Sound musicBackground = new Sound("/music.wav");
	public static final Sound hit = new Sound("/hit.mp3");
	public static final Sound shoot = new Sound("/shotgun_pumpAction.wav");
	
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch(Throwable e) {}
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		}catch(Throwable e) {}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		}catch(Throwable e) {}
	}
*/
	
	//Método novo
	
	public static class Clips{
		public Clip[] clips;
		private int p;
		private int count;
		
		public Clips(byte[] buffer, int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
			if(buffer == null)
				return;
			
			clips = new Clip[count];
			this.count = count;
			
			for(int i = 0; i < count; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
			}
		}
		
		public void play() {
			if(clips == null) return;
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			p++;
			if(p>=count) p = 0;
		}
		
		public void loop(int qtd) {
			if(clips == null) return;
			clips[p].loop(qtd);
		}
		
	}
	
	private static Clips load(String name,int count) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));
			
			byte[] buffer = new byte[1024];
			int read = 0;
			while((read = dis.read(buffer)) >= 0) {
				baos.write(buffer,0,read);
			}
			dis.close();
			byte[] data = baos.toByteArray();
			return new Clips(data,count);
		}catch(Exception e) {
			try {
				return new Clips(null,0);
			}catch(Exception ee) {
				return null;
			}
		}
	}
	

	public static Clips jump = load("/mario_pulo.wav",1);
	public static Clips music = load("/mario_musica.wav",1);
	public static Clips coin = load("/Coin.wav",1);
	public static Clips enemyjump = load("/mario_enemyjump.wav",1);
	public static Clips powerup = load("/mario_powerup.wav",1);
	public static Clips powerup_appears = load("/mario_powerup_appears.wav",1);
	public static Clips morte = load("/mario_morte.wav",1);
	public static Clips oneup = load("/mario_1up.wav",1);
	public static Clips starTheme = load("/mario_startheme.wav",1);
	public static Clips kick = load("/mario_kick.wav",1);
	public static Clips clear = load("/mario_clear.wav",1);
}
