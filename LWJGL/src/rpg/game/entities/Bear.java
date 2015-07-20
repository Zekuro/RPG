package rpg.game.entities;

import org.lwjgl.opengl.GL11;

public class Bear extends Entity{

	private int speed = 1;
	private int movingDir = 0;
	private int movingDirAnimation = 0;
	
	private int change = 1;
	private int delta = (int) (Math.random() * 180);
	
	public Bear(int lvl) {
		super(lvl, 45, 50, false);
		colOffsetX = 12;
		colOffsetY = 4;
		setTexture("res/entities/bear.png");
	}
	
	public void update(){
		delta++;
		
		int animationSpeed = 120;
		int update = delta%animationSpeed;
		
		if(update == 0){
			change = 1;
		}else
		if(update == animationSpeed/5|| update == 4*animationSpeed/5){
			change = 2;
		}else
		if(update == 2*animationSpeed/5 || update == 3*animationSpeed/5){
			change = 3;
		}
		
		if(delta % 40 == 0){
			movingDir = (int) (Math.random() * 8);
		}
		
		if(delta % 250 < 140 && delta% 2 == 0){
			switch(movingDir){
				case 0:
					move(0,1);
					movingDirAnimation = 8;
					break;
				case 1:
					move(0,-1);
					movingDirAnimation = 2;
					break;
				case 2:
					move(-1,0);
					movingDirAnimation = 4;
					break;
				case 3:
					move(1,0);
					movingDirAnimation = 6;
					break;
				case 4:
					move(1,1);
					// 8 od 6
					movingDirAnimation = 8;
					break;
				case 5:
					move(-1,-1);
					// 2 od 4
					movingDirAnimation = 2;
					break;
				case 6:
					move(-1,1);
					// 4 od 8
					movingDirAnimation = 8;
					break;
				case 7:
					move(1,-1);
					// 6 od 2
					movingDirAnimation = 6;
					break;
			}}
	}
	
	public void render(){
		texture.bind();
		
		int xChange = (change-1) % 3;
		int yChange = movingDirAnimation/2 - 1;

		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(xChange * 0.25f,yChange * 0.25f + 0.25f);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(xChange * 0.25f + 0.25f,yChange * 0.25f + 0.25f);
		GL11.glVertex2f(x+64, y);
		GL11.glTexCoord2f(xChange * 0.25f + 0.25f,yChange * 0.25f);
		GL11.glVertex2f(x+64, y+64);
		GL11.glTexCoord2f(xChange * 0.25f,yChange * 0.25f);
		GL11.glVertex2f(x, y+64);
	    GL11.glEnd();
		
	}
	
}
