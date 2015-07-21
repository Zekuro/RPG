package rpg.game.entities;

import org.lwjgl.opengl.GL11;

public class Bear extends Entity{

	private int speed = 1;
	private int movingDir = 0;
	private int movingDirAnimation = 0;
	private int yChange = 0;
	private boolean stopAnimation = false;
	
	private int change = 1;
	private int delta = (int) (Math.random() * 180);
	
	// TODO UPDATE MY WHOLE CLASS AND MAKE AN ANIMATED MOB CLASS OR STH
	
	public Bear(int lvl) {
		super(lvl, 45, 50, false);
		colOffsetX = 12;
		colOffsetY = 4;
		setTexture("res/entities/bear.png");
		setImageBounds(15, 0, width+10, height+10);
		
		healthReg = 5;
		maxHealth = 100;
		health = 50;
		
	}
	
	public void update(){
		delta++;
		
		int animationSpeed = 60;
		int update = delta%animationSpeed;
		
		if(stopAnimation == false){
		if(update == 0){
			change = 1;
		}else
		if(update == animationSpeed/5|| update == 4*animationSpeed/5){
			change = 2;
		}else
		if(update == 2*animationSpeed/5 || update == 3*animationSpeed/5){
			change = 3;
		}}
		
		if(delta%120 == 0){
			regenerate();
		}
		
		if(delta % 250 == 0){
			movingDir = (int) (Math.random() * 8);
		}
		
		if(delta % 250 < 100){
			stopAnimation = false;
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
			}}else{
				stopAnimation = true;
				change = 2;
			}
		
		if(movingDirAnimation == 2 || movingDirAnimation == 8){
			if(	!hasCollisionAt(x+20, y) 
					&& !hasCollisionAt(x+20, y+45)
					&& !hasCollisionAt(x+45, y)
					&& !hasCollisionAt(x+45, y+45)){
						yChange = movingDirAnimation/2 - 1;
						colOffsetX = 20;
						width = 25;
						height = 45;
						setImageBounds(15, 0, width+10, height+10);
			}
		}
		
		if(movingDirAnimation == 4 || movingDirAnimation == 6){
			if(	!hasCollisionAt(x+10, y) 
					&& !hasCollisionAt(x+10, y+32)
					&& !hasCollisionAt(x+60, y)
					&& !hasCollisionAt(x+60, y+32)){
					yChange = movingDirAnimation/2 - 1;
					colOffsetX = 10;
					width = 50;
					height = 32;
					setImageBounds(0, 0, width+5, height+10);
				}
		}
		
		
	}
	
	public void render(){
		texture.bind();
		
		int xChange = (change-1) % 3;
		
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
