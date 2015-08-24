package rpg.game.entities;

import org.lwjgl.opengl.GL11;

public class Slime extends Entity{

	private int speed = 1;
	private int movingDir = 0;
	
	private int change = 1;
	private int delta = (int) (Math.random() * 180);
	
	public Slime(int lvl) {
		super(lvl, 15, 16, false);
		colOffsetX = 10;
		setTexture("res/entities/slime.png");
		setRespawnTime(10);
	}
	
	public void update(){
		delta++;
		
		if(!isDead()) move();
	}
	
	public void move(){
		int animationSpeed = 120;
		int update = delta%animationSpeed;
		
		if(update == 0){
			change = 1;
		}else
		if(update == animationSpeed/6|| update == 5*animationSpeed/6){
			change = 2;
		}else
		if(update == 2*animationSpeed/6 || update == 4*animationSpeed/6){
			change = 3;
		}else
		if(update == 3*animationSpeed/6){
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
		if(!isDead()){
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
		}else{
			renderItemBag();
		}
	}
	
}
