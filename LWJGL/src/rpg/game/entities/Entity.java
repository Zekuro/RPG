package rpg.game.entities;

import rpg.game.Game;
import rpg.game.Map;
import rpg.game.objects.GameObject;

public class Entity extends GameObject{

	protected int lvl;
	protected int health;
	protected int mana;
	protected int exp;
	protected int speed = 1;
	
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
				System.out.println(object.getClass().toString());
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
				System.out.println(object.getClass().toString());
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
	

}
