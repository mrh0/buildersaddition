package com.mrh0.buildersaddition.arcade.games;

import com.mrh0.buildersaddition.arcade.ArcadeGame;
import com.mrh0.buildersaddition.arcade.ArcadeScreen;
import com.mrh0.buildersaddition.tileentity.ArcadeTileEntity;

public class ArcadeSnake extends ArcadeGame {

	public ArcadeSnake(ArcadeScreen s, ArcadeTileEntity te) {
		super(s, te);
		pw = s.width - 10;
		ph = s.height;
	}
	
	int px = 0;
	int py = 0;
	
	int lpx = 0;
	int lpy = 0;
	
	int dir = -1;
	int ldir = -1;
	
	int pw;
	int ph;
	
	long deadTimer = Long.MAX_VALUE;
	boolean isDead = false;
	
	int snakeColor = 0xa;
	
	int score;
	
	boolean initScreen = true;
	
	Tail last = null;
	Tail first = null;
	int tailExt = 0;
	
	@Override
	public void start() {
		super.start();
		s.clear();
		s.testScreen();
		s.setColors(0x0, 0xf);
		s.clear(0, 1, s.width, s.height-2);
		s.print(1, 1, "Arcade Snake v1.0");
		//s.print(1, 2, "More games to come.");
		String msg = " Press any key to continue. ";
		s.print(s.width/2 - msg.length()/2, s.height/2, msg);
		
		initScreen = false; // skip
		begin(); // skip
		
		dir = -1;
	}
	
	private void begin() {
		s.setColors(0x0, 0xf);
		s.clear();
		px = pw/2;
		py = ph/2;
		lpx = px;
		lpy = py;
		dir = -1;
		ldir = -1;
		score = 0;
		isDead = false;
		deadTimer = Long.MAX_VALUE;
		last = null;
		first = null;
		tailExt = 0;
		s.setColors(0x8, 0xa);
		s.clear(pw, 0, s.width-pw, s.height);
		s.print(pw+1, 1, "Score:");
		s.print(pw+1, 2, score+"");
		//s.print(pw+1, 3, "Top:");
		//s.print(pw+1, 4, "-");
		s.print(pw+1, s.height-5, "Arrows");
		s.print(pw+1, s.height-4, "or WASD");
		spawnFood();
		spawnFood();
	}
	
	public int randomRange(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
	
	private void spawnFood() {
		int x = 0;
		int y = 0;
		do {
			x = randomRange(0, pw);
			y = randomRange(0, ph);
		} while(s.getBackground(x, y) != 0x0 && s.getChar(x, y) != 'o');
		s.setColors(0x0, 0xe);//tickRate(score+1) < tickRate(score) ? 0x6 : 
		s.print(x, y, "o");
		s.setColors(0x8, 0xa);
		s.print(pw+1, s.height-2, repeat(">", 7-tickRate(score)));
	}
	
	private int tickRate(int t) {
		if(t > 20)
			return 2;
		else if(t > 10)
			return 3;
		else if(t > 7)
			return 4;
		else if(t > 3)
			return 5;
		return 6;
	}
	
	private String repeat(String t, int n) {
		String r = "";
		for(int i = 0; i < n; i++)
			r += t;
		return r;
	}
	
	private void updateMovement(long time) {
		lpx = px;
		lpy = py;
		if(dir >= 0) {
			switch(dir) {
				case 0:
					py--;
					if(py < 0)
						py = ph-1;
					break;
				case 1:
					px++;
					if(px > pw-1)
						px = 0;
					break;
				case 2:
					py++;
					if(py > ph-1)
						py = 0;
					break;
				case 3:
					px--;
					if(px < 0)
						px = pw-1;
					break;
			}
			if(s.getBackground(px, py) == snakeColor) {
				deadTimer = time + 30;
				isDead = true;
				s.setColors(0x4, 0xf);
				s.print(px, py,"x");
				playSound(0);
				return;
			}
			else if(s.getChar(px, py) == 'o') {
				score += 1;
				s.setColors(0x8, 0xa);
				s.print(pw+1, 1, "Score:");
				s.print(pw+1, 2, score+"");
				spawnFood();
				tailExt = 3;
				playSound(30);
			}
			if(last != null && tailExt <= 0) {
				s.setColors(0x0, 0xf);
				s.print(last.x,  last.y, " ");
				last = last.next;
			}
			s.setColors(snakeColor, 0xf);
			s.print(lpx, lpy, getToken(dir, ldir));
			ldir = dir;
			Tail t = new Tail(lpx, lpy, null);
			if(first != null)
				first.next = t;
			first = t;
			if(last == null)
				last = t;
			tailExt--;
			if(tailExt < 0)
				tailExt = 0;
		}
		s.setColors(snakeColor, 0xf);
		s.print(px, py, "@");
	}
	
	private String getToken(int d, int ld) {
		if(d == ld) {
			return dir == 0 || dir == 2 ? "-" : "|";
		}
		else if((d == 0 && ld == 1) || (d == 1 && ld == 0))
			return "\\";
		else if((d == 0 && ld == 3) || (d == 3 && ld == 0))
			return "/";
		else if((d == 2 && ld == 1) || (d == 1 && ld == 2))
			return "/";
		else if((d == 2 && ld == 3) || (d == 3 && ld == 2))
			return "\\";
		return "-";
	}

	@Override
	public void frame(long time) {
		super.frame(time);
		/*if(initScreen) {
			s.print(1, s.height-2, time+"");
			return;
		}*/
		if(time > deadTimer)
			begin();
		if(time%tickRate(score) == 0 && !isDead)
			updateMovement(time);
	}
	
	@Override
	public void onKeyPressed(int key) {
		super.onKeyPressed(key);
		if(initScreen && isAny(key)) {
			initScreen = false;
			begin();
		}
		if(isUp(key) && ldir != 2) {
			dir = 0;
		}
		else if(isRight(key) && ldir != 3) {
			dir = 1;
		}
		else if(isDown(key) && ldir != 0) {
			dir = 2;
		}
		else if(isLeft(key) && ldir != 1) {
			dir = 3;
		}
		else if(isReset(key)) {
			s.setColors(0x4, 0x0);
			String msg = " Restarting. ";
			s.print(pw/2 - msg.length()/2, s.height/2, msg);
		}
	}
	
	@Override
	public void onKeyReleased(int key) {
		super.onKeyReleased(key);
		if(isReset(key)) {
			begin();
		}
	}
	
	private class Tail {
		public int x;
		public int y;
		public Tail next;
		
		public Tail(int x, int y, Tail next) {
			this.x = x;
			this.y = y;
			this.next = next;
		}
	}
}
