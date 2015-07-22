package rpg.game.player;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rpg.game.Game;
import rpg.game.Stats;
import rpg.game.World;
import rpg.game.items.HealthPotion;
import rpg.game.items.ManaPotion;
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
	
	private int faceDir = 2;
	private Texture texture;
	
	private int lvl = 1;
	private int maxHealth = 100;
	private int maxMana = 100;
	private int maxExp = 100;
	private int health = 50;
	private int mana = 50;
	private int exp = 0;
	
	private int healthReg = 5;
	private int manaReg = 5;

	private Stats stats = new Stats();
	
	public Player(int x, int y){
		this.x = x;
		this.y = y;
		centerCamera();
		
		// TODO remove these testcases

		for(int i = 0; i<50; i++){
			Inventory.add(new ManaPotion(20));
			Inventory.add(new HealthPotion(20));
		}
		
		
		try {
			 texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/player.png"));
			} catch (IOException e) {
				e.printStackTrace();
		}  
		
	}
	
	public void update(){
		delta++;
		move();
		
		if(delta%120 == 0){
			if(mana < maxMana){
				mana += manaReg;
				if(mana > maxMana) mana = maxMana;
			}
			
			if(health < maxHealth){
				health += healthReg;
				if(health > maxHealth) health= maxHealth;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_E) && Keyboard.next()){
		action();
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
			textPos = 5 + change;
			faceDir = 4;
			if(x-speed > 0 && !hasCollision(x-speed, y)){
				x-=speed;
			}
			
			if(cameraX-speed > 0 && x <= cameraX + Game.SCREEN_WIDTH/2 - width/2){
				cameraX-=speed;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)){
			textPos = 9 + change;
			faceDir = 6;
			if(x+speed < Game.WORLD_WIDTH-width && !hasCollision(x+speed,y)){
				x+=speed;
			}
			
			if(cameraX+speed < Game.WORLD_WIDTH-Game.SCREEN_WIDTH && x >= cameraX + Game.SCREEN_WIDTH/2 - width/2){
				cameraX+=speed;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)){
			textPos = 13 + change;
			faceDir = 8;
			if(y+speed < Game.WORLD_HEIGHT-height && !hasCollision(x, y+speed)){
				y+=speed;
			}
			
			if(cameraY+speed < Game.WORLD_HEIGHT-Game.SCREEN_HEIGHT && y >= cameraY + Game.SCREEN_HEIGHT/2 - height/2){
				cameraY+=speed;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)){
			textPos = 1 + change;
			faceDir = 2;
			if(y-speed > 0 && !hasCollision(x, y-speed)){
				y-=speed;
			}
			
			if(cameraY-speed > 0 && y <= cameraY + Game.SCREEN_HEIGHT/2 - height/2){
				cameraY-=speed;
			}
		}
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(cameraX, cameraX + Game.SCREEN_WIDTH, cameraY, cameraY + Game.SCREEN_HEIGHT, 0, -1);
	}
	
	
	private void action(){
			for (GameObject object : World.backgroundTiles) {
				
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
			
			for (GameObject object : World.objectList) {
				if(	object.getClass() == Chest.class){
					if(inFrontOf(object)){
						object.use();
					}
					break;
				}
			}
			
			// TODO loot entity if it is dead and hasLoot
			
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
	
	public void levelUp(){
		exp = exp - maxExp;
		lvl++;
		
		// calc max exp and other stats
	}
	
	public boolean hasCollision(int x, int y){
		
		for (GameObject object : World.objectList) {
			
			if(	object.isSolid()
				&& x + width > object.getX() + object.getXColOffset()
				&& x < object.getX() + object.getXColOffset() + object.getWidth()
				&& y + height > object.getY() + object.getYColOffset()
				&& y < object.getY() + object.getYColOffset() + object.getHeight()){
				
				return true;
			}
			
		}
		
		for (GameObject object : World.entityList) {
			
			if(	object.isSolid()
				&& x + width > object.getX() + object.getXColOffset()
				&& x < object.getX() + object.getXColOffset() + object.getWidth()
				&& y + height > object.getY() + object.getYColOffset()
				&& y < object.getY() + object.getYColOffset() + object.getHeight()){
				
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
	
	public boolean inFrontOf(GameObject object){
		
		switch(faceDir){
		case 2:
			if( y - height/2 > object.getYColOffset() + object.getY()
				&& x + width/2  > object.getXColOffset() + object.getX()
				&& x + width/2 < object.getXColOffset() + object.getX()+ object.getWidth()){
				return true;
			}
			break;
		case 4:
			if( x - width/2 > object.getXColOffset() + object.getX()
					&& y + height/2  > object.getYColOffset() + object.getY()
					&& y + height/2 < object.getYColOffset() + object.getY()+ object.getHeight()){
					return true;
				}
			break;
		case 6:
			if( x + width + width/2 < object.getXColOffset() + object.getX() + object.getWidth()
					&& y + height/2  > object.getYColOffset() + object.getY()
					&& y + height/2 < object.getYColOffset() + object.getY()+ object.getHeight()){
					return true;
				}
			break;
		case 8:
			if(	y + height + height/2 < object.getYColOffset() + object.getY() +  object.getHeight()
				&& x + width/2 > object.getXColOffset() + object.getX()
				&& x + width/2 < object.getXColOffset() + object.getX() + object.getWidth()){
				return true;
			}
			break;
		}
		
		return false;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setHealth(int value){
		this.health = health;
	}
	
	public int getHealth(){
		return health;
	}
	
	public void setMana(int value){
		this.mana = value;
	}
	
	public int getMana(){
		return mana;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getMaxMana(){
		return maxMana;
	}
	
	public void addExp(int xp){
		exp += xp;
		if(exp > maxExp){
			levelUp();
		}
	}
	
	public void restoreHealth(int value){
		health += value;
		if(health > maxHealth) health = maxHealth;
	}
	
	public void restoreMana(int value){
		mana += value;
		if(mana> maxMana) mana= maxMana;
	}

	public int getLvl(){
		return lvl;
	}
	
	public Stats getStats(){
		return stats;
	}
}
