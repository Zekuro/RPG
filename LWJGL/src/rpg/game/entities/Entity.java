package rpg.game.entities;

import rpg.game.Game;
import rpg.game.Map;
import rpg.game.objects.GameObject;

public class Entity extends GameObject{

	protected int lvl;
	protected int maxHealth;
	protected int maxMana;
	
	protected int health;
	protected int mana;
	protected int exp;
	protected int speed = 1;
	
	protected int healthReg;
	protected int manaReg;

	protected int imageOffsetX;
	protected int imageOffsetY;
	protected int imageWidth;
	protected int imageHeight;
	
	private boolean aggressive;
	
	// private ArrayList<Item> loot;
	
	
	public Entity(int lvl, int width, int height,boolean aggressive) {
		super(true, false, width, height, "res/background/water.png");
		this.lvl = lvl;
		this.aggressive = aggressive;
	}
	
	public static Entity create(int id, int lvl){
		
		Entity mob = null;
		
		switch(id){
		case 1: mob = new Slime(lvl);
		break;
		case 2: mob = new Bear(lvl);
		break;
		}
		
		return mob;
	}
	public boolean hasCollision(int x, int y){
		if(	x + colOffsetX + width > Game.PLAYER.getX()
			&& x + colOffsetX < Game.PLAYER.getX() + Game.PLAYER.getWidth()
			&& y + colOffsetY + height > Game.PLAYER.getY()
			&& y + colOffsetY < Game.PLAYER.getY() + Game.PLAYER.getHeight()){
				
				return true;
		}
		
		for (GameObject object : Map.objectList) {
			
			if(	object.isSolid()
				&& !object.equals(this)
				&& x + colOffsetX + width > object.getX() + object.getXColOffset()
				&& x + colOffsetX < object.getX() + object.getXColOffset() + object.getWidth()
				&& y + colOffsetY + height > object.getY() + object.getYColOffset()
				&& y + colOffsetY < object.getY() + object.getYColOffset() + object.getHeight()){
				return true;
			}
			
		}
		
		for (GameObject object : Map.entityList) {
			
			if(	object.isSolid()
				&& !object.equals(this)
				&& x + colOffsetX + width > object.getX() + object.getXColOffset()
				&& x + colOffsetX < object.getX() + object.getXColOffset() + object.getWidth()
				&& y + colOffsetY + height > object.getY() + object.getYColOffset()
				&& y + colOffsetY < object.getY() + object.getYColOffset() + object.getHeight()){
				return true;
			}
			
		}
		
		return false;
	}
	
	public boolean hasCollisionAt(int x, int y){
		if(	x > Game.PLAYER.getX()
			&& x < Game.PLAYER.getX() + Game.PLAYER.getWidth()
			&& y > Game.PLAYER.getY()
			&& y < Game.PLAYER.getY() + Game.PLAYER.getHeight()){
				
				return true;
		}
		
		for (GameObject object : Map.objectList) {
			
			if(	object.isSolid()
				&& !object.equals(this)
				&& x > object.getX() + object.getXColOffset()
				&& x < object.getX() + object.getXColOffset() + object.getWidth()
				&& y > object.getY() + object.getYColOffset()
				&& y < object.getY() + object.getYColOffset() + object.getHeight()){
				return true;
			}
			
		}
		
		for (GameObject object : Map.entityList) {
			
			if(	object.isSolid()
				&& !object.equals(this)
				&& x > object.getX() + object.getXColOffset()
				&& x < object.getX() + object.getXColOffset() + object.getWidth()
				&& y > object.getY() + object.getYColOffset()
				&& y < object.getY() + object.getYColOffset() + object.getHeight()){
				return true;
			}
			
		}
		
		return false;
	}
	
	public void move(int x, int y){
		
		if(!hasCollision(this.x+x*speed, this.y+y*speed)){
			this.x += x*speed;
			this.y += y*speed;
		}
		
	}
	
	// FIXME
	protected void setImageBounds(int xOffset, int yOffset, int width, int height){
		imageOffsetX = xOffset;
		imageOffsetY = yOffset;
		imageWidth = width;
		imageHeight = height;
	}
	
	// FIXME ?
	public boolean isEntityAt(int x, int y){
		
		if( x > this.x + imageOffsetX
			&& x < this.x + imageOffsetX + imageWidth
			&& y > this.y + imageOffsetY
			&& y < this.y + imageOffsetY + imageHeight){
			return true;
		}
		return false;
	}
	
	public void regenerate(){
		if(health < maxHealth){
			health+=healthReg;
			if(health > maxHealth) health = maxHealth;
		}
		
		if(mana < maxMana){
			mana+=manaReg;
			if(mana > maxMana) mana= maxMana;
		}
	}
	
	public int getHealth(){
		return health;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getMana(){
		return mana;
	}
	
	public int getMaxMana(){
		return maxMana;
	}
	
	public int getLvl(){
		return lvl;
	}

}
