package rpg.game.player;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rpg.game.Game;
import rpg.game.Map;
import rpg.game.objects.Chest;
import rpg.game.objects.GameObject;
import rpg.game.objects.Portal;


public class Player {

	private int x;
	private int y;
	private int cameraX = 0;
	private int cameraY = 0;
	private int textPos = 2;
	private final int width = 32;
	private final int height = 32;
	private final int speed = 4;
	private int delta = 0;
	private int change = 0;
	
	private int wait = 0;
	private Texture texture;
	
	public Player(int x, int y){
		this.x = x;
		this.y = y;
		centerCamera();
		try {
			 texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/player.png"));
			} catch (IOException e) {
				e.printStackTrace();
		}  
		
	}
	
	public void update(){
		delta++;
		move();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_E) && wait == 0){
		action();
		wait = 30;
		}

		if(wait > 0){
			wait--;
		}
		
		if(delta%60 == 0){
			change = 0;
		}else
		if(delta%60 == 15|| delta%60== 45){
			change = 1;
		}else
		if(delta%60 == 30){
			change = 2;
		}
		
	}

	private void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)){
			if(x-speed > 0 && !hasCollision(x-speed, y)){
				textPos = 5 + change;
				x-=speed;
			}
			
			if(cameraX-speed > 0 && x <= cameraX + Game.SCREEN_WIDTH/2 - width/2){
				cameraX-=speed;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)){
			if(x+speed < Game.WORLD_WIDTH-width && !hasCollision(x+speed,y)){
				textPos = 9 + change;
				x+=speed;
			}
			
			if(cameraX+speed < Game.WORLD_WIDTH-Game.SCREEN_WIDTH && x >= cameraX + Game.SCREEN_WIDTH/2 - width/2){
				cameraX+=speed;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)){
			if(y+speed < Game.WORLD_HEIGHT-height && !hasCollision(x, y+speed)){
				textPos = 13 + change;
				y+=speed;
			}
			
			if(cameraY+speed < Game.WORLD_HEIGHT-Game.SCREEN_HEIGHT && y >= cameraY + Game.SCREEN_HEIGHT/2 - height/2){
				cameraY+=speed;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)){
			if(y-speed > 0 && !hasCollision(x, y-speed)){
				textPos = 1 + change;
				y-=speed;
			}
			
			if(cameraY-speed > 0 && y <= cameraY + Game.SCREEN_HEIGHT/2 - height/2){
				cameraY-=speed;
			}
		}
		
		// TEST KEY
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
	
	
	private void action(){
			for (GameObject object : Map.backgroundTiles) {
				
				if(	object.getClass() == Portal.class
						&& x + width > object.getX()
						&& x < object.getX()+object.getWidth()
						&& y + height > object.getY()
						&& y < object.getY()+object.getHeight()){
					
					Portal portal = (Portal) object;
					portal.teleport(this);
					break;
				}
				
			}
	}
	
	public void render(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		texture.bind(); // or GL11.glBind(texture.getTextureID());
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f((textPos%4-1)*0.25f, (textPos/4+1)*0.25f);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(textPos%4*0.25f,(textPos/4+1)*0.25f);
		GL11.glVertex2f(x+width, y);
		GL11.glTexCoord2f(textPos%4*0.25f,textPos/4*0.25f);
		GL11.glVertex2f(x+width, y+height);
		GL11.glTexCoord2f((textPos%4-1)*0.25f,textPos/4*0.25f);
		GL11.glVertex2f(x, y+height);
	    GL11.glEnd();
	}
	
	public boolean hasCollision(int x, int y){
		
		for (GameObject object : Map.objectList) {
			
			if(	object.isSolid()
				&& x + width > object.getX() + object.getColX()
				&& x < object.getX() + object.getColX() + object.getWidth()
				&& y + height > object.getY() + object.getColY()
				&& y < object.getY() + object.getColY() + object.getHeight()){
				
				return true;
			}
			
		}
		
		
		return false;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
		centerCamera();
	}
	
	public int getCameraX(){
		return cameraX;
	}
	
	public int getCameraY(){
		return cameraY;
	}
	
	public void centerCamera(){
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
	
}
