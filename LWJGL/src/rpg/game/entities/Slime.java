package rpg.game.entities;

import org.lwjgl.opengl.GL11;

public class Slime extends Entity{

	private int speed = 1;
	private int movingDir = 0;
	
	private int change = 1;
	private int delta = (int) (Math.random() * 180);
	
	public Slime(int lvl) {
		super(lvl, 24, 16, false);
		colOffsetX = 5;
		setTexture("res/entities/slime.png");
	}
	
	public void update(){
		delta++;
		
		int update = delta%180;
		
		if(update == 0){
			change = 1;
		}else
		if(update == 30|| update == 150){
			change = 2;
		}else
		if(update == 60 || update == 120){
			change = 3;
		}else
		if(update == 90){
			change = 4;
		}
		
		if(delta % 40 == 0){
			movingDir = (int) (Math.random() * 8);
		}
		
		if(delta % 250 < 140 && delta% 2 == 0){
			switch(movingDir){
				case 0:
					move(0,1);
					break;
				case 1:
					move(0,-1);
					break;
				case 2:
					move(-1,0);
					break;
				case 3:
					move(1,0);
					break;
				case 4:
					move(1,1);
					break;
				case 5:
					move(-1,-1);
					break;
				case 6:
					move(-1,1);
					break;
				case 7:
					move(1,-1);
					break;
			}}
	}
	
	public void render(){
		texture.bind();
		
		int xChange = (change-1) % 2;
		int yChange = (change-1) / 2;
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(xChange * 0.5f,yChange * 0.5f + 0.5f);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(xChange * 0.5f + 0.5f,yChange * 0.5f + 0.5f);
		GL11.glVertex2f(x+32, y);
		GL11.glTexCoord2f(xChange * 0.5f + 0.5f,yChange * 0.5f);
		GL11.glVertex2f(x+32, y+32);
		GL11.glTexCoord2f(xChange * 0.5f,yChange * 0.5f);
		GL11.glVertex2f(x, y+32);
	    GL11.glEnd();
		
	}
	
}
