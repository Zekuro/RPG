package rpg.game;
import java.awt.Color;
import java.awt.RenderingHints.Key;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import rpg.game.objects.Chest;
import rpg.game.objects.GameObject;
import rpg.game.objects.Portal;


public class Player {

	private int x;
	private int y;
	private int cameraX = 0;
	private int cameraY = 0;
	private final int width = 32;
	private final int height = 32;
	private final int speed = 4;
	
	public Player(int x, int y){
		this.x = x;
		this.y = y;
		cameraX = x + width/2 - Game.SCREEN_WIDTH/2;
		cameraY = y + height/2 - Game.SCREEN_HEIGHT/2;
		
		if(cameraX < 0){
			cameraX = 0;
		}
		
		if(cameraX > Game.WORLD_WIDTH - Game.SCREEN_WIDTH){
			cameraX = Game.WORLD_WIDTH - Game.SCREEN_WIDTH;
		}
		
		if(cameraY < 0){
			cameraY = 0;
		}
		
		if(cameraY > Game.WORLD_HEIGHT - Game.SCREEN_HEIGHT){
			cameraY = Game.WORLD_HEIGHT - Game.SCREEN_HEIGHT;
		}
	}
	
	public void update(){
		move();
	}

	private void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)){
			if(x-speed > 0 && !hasCollision(x-speed, y)){
				x-=speed;
			}
			
			if(cameraX-speed > 0 && x <= cameraX + Game.SCREEN_WIDTH/2 - width/2){
				cameraX-=speed;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)){
			if(x+speed < Game.WORLD_WIDTH-width && !hasCollision(x+speed,y)){
				x+=speed;
			}
			
			if(cameraX+speed < Game.WORLD_WIDTH-Game.SCREEN_WIDTH && x >= cameraX + Game.SCREEN_WIDTH/2 - width/2){
				cameraX+=speed;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)){
			if(y+speed < Game.WORLD_HEIGHT-height && !hasCollision(x, y+speed)){
				y+=speed;
			}
			
			if(cameraY+speed < Game.WORLD_HEIGHT-Game.SCREEN_HEIGHT && y >= cameraY + Game.SCREEN_HEIGHT/2 - height/2){
				cameraY+=speed;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)){
			if(y-speed > 0 && !hasCollision(x, y-speed)){
				y-=speed;
			}
			
			if(cameraY-speed > 0 && y <= cameraY + Game.SCREEN_HEIGHT/2 - height/2){
				cameraY-=speed;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_B)){
			for (GameObject object : Map.objectList) {
				if(object.getClass() == Chest.class){
					Chest chest = (Chest) object;
					chest.open();
				}
			}
		}
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(cameraX, cameraX + Game.SCREEN_WIDTH, cameraY, cameraY + Game.SCREEN_HEIGHT, 1, -1);
	}
	
	public void render(){
		// ???????
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		// draw quad
		GL11.glColor3f(1f,1f,1f);
		GL11.glBegin(GL11.GL_QUADS);
	    GL11.glVertex2f(x,y);
		GL11.glVertex2f(x+width,y);
		GL11.glVertex2f(x+width,y+height);
		GL11.glVertex2f(x,y+height);
	    GL11.glEnd();
	}
	
	public boolean hasCollision(int x, int y){
		
		
		// TODO check my edges pls
		for (GameObject object : Map.objectList) {
			if(	object.isSolid()
				&& x + width > object.getX()
				&& x < object.getX()+object.getWidth()
				&& y + height > object.getY()
				&& y < object.getY()+object.getHeight()){
				
				if(object.getClass() == Portal.class){
					Portal portal = (Portal) object;
					portal.teleport(this);
				}
				
				return true;
			}
			
		}
		
		return false;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
}
