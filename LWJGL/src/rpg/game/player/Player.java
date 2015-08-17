package rpg.game.player;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rpg.game.Game;
import rpg.game.Quest;
import rpg.game.Stats;
import rpg.game.World;
import rpg.game.armor.body.SimpleClothArmor;
import rpg.game.armor.boots.SimpleBoots;
import rpg.game.items.Item;
import rpg.game.items.Item.ItemObject;
import rpg.game.items.Item.Tier;
import rpg.game.objects.Chest;
import rpg.game.objects.GameObject;
import rpg.game.objects.Portal;
import rpg.game.skills.Skill;
import rpg.game.skills.Skill.SkillType;
import rpg.game.ui.QuestLog;


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
	private int exp = 50;
	
	private int healthReg = 5;
	private int manaReg = 5;

	private Stats baseStats = new Stats();
	private Stats stats = new Stats();
	private Equip equip = new Equip();
	
	public Player(int x, int y){
		this.x = x;
		this.y = y;
		centerCamera();
		
		baseStats.setIntelligence(15);
		baseStats.setStrength(10);
		baseStats.setWisdom(10);
		baseStats.setVitality(12);
		
		baseStats.setpDef(10);
		baseStats.setmDef(8);
		
		recalculateStats();
		// TODO remove these testcases

		// START TESTCASES
		for(int i = 0; i<99; i++){
			Inventory.add(Item.get(ItemObject.smallHealthPotion));
			Inventory.add(Item.get(ItemObject.smallManaPotion));
		}
		
		Inventory.add(new SimpleBoots(1, 5, Tier.T1));
		Inventory.add(new SimpleClothArmor(1, 5, Tier.T1));
		
		Item[] actionBar = ActionBar.getActionBar();
		actionBar[0] = new Skill("TEST", Tier.T1, SkillType.PROJECTILE);
		
		QuestLog.add(new Quest(1, new Item[]{new SimpleBoots(1, 1, Tier.T1)}, "Tutorial1", "Beschreibung 1"));
		
		String longDescription = ""
				+ "Diese Beschreibung dient als Bei-  "
				+ "spiel für eine sehr lange Beschrei-"
				+ "bung.                              "
				+ "Ob Textumbrüche automatisch oder   "
				+ "manuell eingefügt werden sollen,   "
				+ "muss noch getestet werden. Falls   "
				+ "diese automatisch eingefügt werden "
				+ "sollen, dann muss dafür noch ein   "
				+ "Parser gebastelt werden.           "
				+ "Man soll auch weiterblättern können"
				+ "wenn der Text unten zu viel Platz  "
				+ "einnimmt.                          "
				+ "Am Ende einer Quest sollen auch die"
				+ "Belohnungen zu sehen sein. Das aber"
				+ "auch erst wenn man alle Seiten     "
				+ "durchgeblättert hat. Da muss noch  "
				+ "geklärt werden wie das genau sein  "
				+ "soll. Wenn noch genügend Platz ist "
				+ "Dann kann es noch auf die Seite mit"
				+ "drauf, ansonsten muss eine neue    "
				+ "Seite her.                         "
				+ "Testbeispiel1 Testbeispiel2 Testbeispiel3 Testbeispiel4 Testbeispiel5 Testbeispiel6"
				+ "Testbeispiel7 Testbeispiel8 Testbeispiel9 Testbeispiel10 Testbeispiel11 Testbeispiel12"
				+ "Testbeispiel13 Testbeispiel14 Testbeispiel15 Testbeispiel16 Testbeispiel17 Testbeispiel18"
				+ "Testbeispiel Testbeispiel Testbeispiel Testbeispiel Testbeispiel Testbeispiel"
				+ "Testbeispiel Testbeispiel Testbeispiel Testbeispiel Testbeispiel Testbeispiel"
				+ "Testbeispiel Testbeispiel Testbeispiel Testbeispiel Testbeispiel Testbeispiel";
		QuestLog.add(new Quest(1, new Item[]{new SimpleBoots(1, 1, Tier.T1)}, "Tutorial2", longDescription));

		int i = 3;
		while(!QuestLog.isFull()){
			QuestLog.add(new Quest(1, new Item[]{new SimpleBoots(1,1, Tier.T1)},"Tutorial" + i, "Beschreibung " + i));
			i++;
		}
		
		// END TESTCASE
		
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
	
	public void mapRender(int scale, int offX, int offY){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		texture.bind(); // or GL11.glBind(texture.getTextureID());
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f((textPos%4-1)*0.25f, (textPos/4+1)*0.25f);
		GL11.glVertex2f(x/scale + cameraX + offX, y/scale + cameraY + offY);
		GL11.glTexCoord2f(textPos%4*0.25f,(textPos/4+1)*0.25f);
		GL11.glVertex2f(x/scale+width/scale + cameraX + offX, y/scale + cameraY + offY);
		GL11.glTexCoord2f(textPos%4*0.25f,textPos/4*0.25f);
		GL11.glVertex2f(x/scale+width/scale + cameraX + offX, y/scale+height/scale + cameraY + offY);
		GL11.glTexCoord2f((textPos%4-1)*0.25f,textPos/4*0.25f);
		GL11.glVertex2f(x/scale + cameraX + offX, y/scale+height/scale + cameraY + offY);
	    GL11.glEnd();
	}
	
	private void levelUp(){
		Game.UI.update = 100;
		exp = exp - maxExp;
		lvl++;
		maxExp = (int) (16*Math.pow(lvl, 2)+lvl+100);		
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
				&& y - height/2 < object.getYColOffset() + object.getY() + object.getHeight()
				&& x + width/2  > object.getXColOffset() + object.getX()
				&& x + width/2 < object.getXColOffset() + object.getX()+ object.getWidth()){
				return true;
			}
			break;
		case 4:
			if( x - width/2 > object.getXColOffset() + object.getX()
					&& x - width/2 < object.getXColOffset() + object.getX() + object.getWidth()
					&& y + height/2  > object.getYColOffset() + object.getY()
					&& y + height/2 < object.getYColOffset() + object.getY()+ object.getHeight()){
					return true;
				}
			break;
		case 6:
			if( x + width + width/2 < object.getXColOffset() + object.getX() + object.getWidth()
					&& x + width + width/2 > object.getXColOffset() + object.getX()
					&& y + height/2  > object.getYColOffset() + object.getY()
					&& y + height/2 < object.getYColOffset() + object.getY()+ object.getHeight()){
					return true;
				}
			break;
		case 8:
			if(	y + height + height/2 > object.getYColOffset() + object.getY()
				&& y + height + height/2 < object.getYColOffset() + object.getY() + object.getHeight()
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
		this.health = value;
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
	
	public int getExp(){
		return exp;
	}
	
	public int getMaxExp(){
		return maxExp;
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
	
	public Equip getEquip(){
		return equip;
	}
	
	public void recalculateStats(){
		stats.setIntelligence(baseStats.getIntelligence() + equip.getIntelligence());
		stats.setStrength(baseStats.getStrength() + equip.getStrength());
		stats.setVitality(baseStats.getVitality() + equip.getVitality());
		stats.setWisdom(baseStats.getWisdom() + equip.getWisdom());
		
		stats.setpDef(baseStats.getpDef() + equip.getPDef());
		stats.setmDef(baseStats.getmDef() + equip.getMDef());
		
		stats.setFireResistance(baseStats.getFireResistance() + equip.getFireResistance());
		stats.setIceResistance(baseStats.getIceResistance() + equip.getIceResistance());
		stats.setElectricResistance(baseStats.getElectricResistance() + equip.getElectricResistance());

	}
}
