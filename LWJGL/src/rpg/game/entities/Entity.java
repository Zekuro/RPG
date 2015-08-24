package rpg.game.entities;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import rpg.game.Game;
import rpg.game.World;
import rpg.game.items.Item;
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
	
	protected int respawnTime;
	private int respawnTimer;
	
	private int respawnWidth;
	private int respawnHeight;
	private int respawnX;
	private int respawnY;
	private int respawnColX;
	private int respawnColY;
	private Texture respawnTexture;
	
	private boolean aggressive;
	private boolean alive = true;
	
	private ArrayList<Item> loot = new ArrayList<>();
	
	
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
		case 2: mob = new AniBear(lvl);
		break;
		}
		
		return mob;
	}
	
	public void update(){
		if(isDead() && Game.SECONDS >= respawnTimer){
				if(canRespawn()) respawn();
		}
	}
	
	// TODO update for large Worlds
	public boolean hasCollision(int x, int y){
		if(	x + colOffsetX + width > Game.PLAYER.getX()
			&& x + colOffsetX < Game.PLAYER.getX() + Game.PLAYER.getWidth()
			&& y + colOffsetY + height > Game.PLAYER.getY()
			&& y + colOffsetY < Game.PLAYER.getY() + Game.PLAYER.getHeight()){
				
				return true;
		}
		
		for (GameObject object : World.objectList) {
			
			if(	object.isSolid()
				&& !object.equals(this)
				&& x + colOffsetX + width > object.getX() + object.getXColOffset()
				&& x + colOffsetX < object.getX() + object.getXColOffset() + object.getWidth()
				&& y + colOffsetY + height > object.getY() + object.getYColOffset()
				&& y + colOffsetY < object.getY() + object.getYColOffset() + object.getHeight()){
				return true;
			}
			
		}
		
		for (GameObject object : World.entityList) {
			
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
	
	// when entity is dead
	public void renderItemBag(){
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(x+texture.getImageWidth(), y);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(x+texture.getImageWidth(), y+texture.getImageHeight());
		GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(x, y+texture.getImageHeight());
	    GL11.glEnd();
	}
	
	public boolean hasCollisionAt(int x, int y){
		if(	x > Game.PLAYER.getX()
			&& x < Game.PLAYER.getX() + Game.PLAYER.getWidth()
			&& y > Game.PLAYER.getY()
			&& y < Game.PLAYER.getY() + Game.PLAYER.getHeight()){
				
				return true;
		}
		
		for (GameObject object : World.objectList) {
			
			if(	object.isSolid()
				&& !object.equals(this)
				&& x > object.getX() + object.getXColOffset()
				&& x < object.getX() + object.getXColOffset() + object.getWidth()
				&& y > object.getY() + object.getYColOffset()
				&& y < object.getY() + object.getYColOffset() + object.getHeight()){
				return true;
			}
			
		}
		
		for (GameObject object : World.entityList) {
			
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
	
	
	public boolean canRespawn(){
		if(	respawnX + respawnColX + respawnWidth > Game.PLAYER.getX()
			&& respawnX + respawnColX < Game.PLAYER.getX() + Game.PLAYER.getWidth()
			&& respawnY + respawnColY + respawnHeight > Game.PLAYER.getY()
			&& respawnY + respawnColY < Game.PLAYER.getY() + Game.PLAYER.getHeight()){
				
				return false;
		}
		
		for (GameObject object : World.objectList) {
			
			if(	object.isSolid()
				&& !object.equals(this)
				&& respawnX + respawnColX + respawnWidth > object.getX() + object.getXColOffset()
				&& respawnX + respawnColX < object.getX() + object.getXColOffset() + object.getWidth()
				&& respawnY + respawnColY + respawnHeight > object.getY() + object.getYColOffset()
				&& respawnY + respawnColY < object.getY() + object.getYColOffset() + object.getHeight()){
				return false;
			}
			
		}
		
		for (GameObject object : World.entityList) {
			
			if(	object.isSolid()
				&& !object.equals(this)
				&& respawnX + respawnColX + respawnWidth > object.getX() + object.getXColOffset()
				&& respawnX + respawnColX < object.getX() + object.getXColOffset() + object.getWidth()
				&& respawnY + respawnColY + respawnHeight > object.getY() + object.getYColOffset()
				&& respawnY + respawnColY < object.getY() + object.getYColOffset() + object.getHeight()){
				return false;
			}
			
		}
		
		return true;
	}
	
	public void move(int x, int y){
		
		if(alive && !hasCollision(this.x+x*speed, this.y+y*speed)){
			this.x += x*speed;
			this.y += y*speed;
		}
		
	}
	
	protected void setImageBounds(int xOffset, int yOffset, int width, int height){
		imageOffsetX = xOffset;
		imageOffsetY = yOffset;
		imageWidth = width;
		imageHeight = height;
	}
	
	private void respawn(){
		setBounds(respawnX, respawnY, respawnWidth, respawnHeight);
		colOffsetX = respawnColX;
		colOffsetY = respawnColY;
		texture = respawnTexture;
		health = maxHealth;
		alive = true;
	}
	
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
		if(alive){
			if(health < maxHealth){
				health+=healthReg;
				if(health > maxHealth) health = maxHealth;
			}
			
			if(mana < maxMana){
				mana+=manaReg;
				if(mana > maxMana) mana= maxMana;
			}
		}
	}
	
	public void addLoot(Item item){
		loot.add(item);
	}
	
	public ArrayList<Item> getLoot(){
		return loot;
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
	
	public void setHealthReg(int i){
		healthReg = i;
	}
	
	public void setManaRed(int i){
		manaReg = i;
	}
	
	public void loseHealth(int i){
		if(alive){
			health -= i;
			if(health <= 0){
				health = 0;
				die();
			}
		}
	}
	
	public boolean isDead(){
		return !alive;
	}
	
	public void die(){
		alive = false;
		respawnX = x;
		respawnY = y;
		respawnColX = colOffsetX;
		respawnColY = colOffsetY;
		respawnWidth = width;
		respawnHeight = height;
		respawnTexture = texture;
		
		setBounds(x + colOffsetX + width/2 - 16, y + colOffsetY + height/2 - 16,32,32);
		setCollisionBox(6, 0, 20, 32);
		setTexture("res/items/bag.png");
		respawnTimer = Game.SECONDS + respawnTime;
		
	}
	
	public void setRespawnTime(int seconds){
		respawnTime = seconds;
	}
	
	
	}
