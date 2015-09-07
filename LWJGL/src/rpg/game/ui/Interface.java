package rpg.game.ui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.Game.GameState;
import rpg.game.Sound;
import rpg.game.World;
import rpg.game.entities.Entity;
import rpg.game.items.Item;
import rpg.game.menues.Options;
import rpg.game.objects.GameObject;
import rpg.game.player.ActionBar;
import rpg.game.player.Inventory;

public class Interface {

	// put these into their class?
	private boolean renderInfos = false;
	private boolean renderPaused = false;
	private boolean renderInventory = false;
	private boolean renderPlayerStats = false;
	private boolean renderQuestLog = false;
	private boolean renderMap = false;
	private boolean renderSKillWindow = false;

	public boolean renderLootDialog = false;
	public boolean clickedInventorySlot = false;
	public boolean clickedActioBarSlot = false;
	
	
	private Item dragItem = null;
	private Entity selectedEntity = null;
	
	private int x;
	private int y;
	
	private int playerX;
	private int playerY;
	
	public int update = 0;
	
	public void render(){
		
		x = Game.PLAYER.getCameraX();
		y = Game.PLAYER.getCameraY();
		
		if(update > 0){
			Font.renderColored("LVL UP", Game.PLAYER.getX() - 6*4, Game.PLAYER.getY() + 32, 2, 1f, 0.8f, 0);
			update--;
		}
		
		if(renderInventory)	Inventory.render(x, y);
		if(renderQuestLog) QuestLog.render(x, y);
		if(renderPlayerStats) PlayerStats.render();
		if(renderSKillWindow) SkillWindow.render();
		renderStandardUI();
		renderInfos();
		
		if(renderLootDialog)LootWindow.renderLootDialog();
		if(renderPaused)renderPaused();
		
		if(dragItem != null) dragItem.render(x + Mouse.getX(), y + Mouse.getY() - 32);
	}
	
	public void update(){
		// FIXME
		boolean mouseEvent = Mouse.next();
		boolean keyEvent = Keyboard.next();
		
		if(renderMap) Map.processInput();

		while(mouseEvent || keyEvent){
			if(mouseEvent) processMouseEvents();
		
			if(keyEvent && Keyboard.getEventKeyState()) processKeyEvents();
			if(keyEvent && (renderSKillWindow || renderInventory)) ActionBar.detectPressedKey();
		
			
			if(renderLootDialog && (playerX != Game.PLAYER.getX() || playerY != Game.PLAYER.getY())){
				renderLootDialog = false;
			}
			
			updateSelectedEntity();
			
			
			if(clickedActioBarSlot == false && clickedInventorySlot == false){
				dragItem = null;
				Inventory.dragIndex = -1;
			}
			
			mouseEvent = Mouse.next();
			keyEvent = Keyboard.next();
		}
		
		
	}
	
	private void processMouseEvents(){
		if(Mouse.isButtonDown(0) && Game.isPaused() == false && renderLootDialog == false){
			
			for (GameObject object: World.entityList) {
				
				Entity entity = (Entity) object;
				
				if(entity.isEntityAt(Game.PLAYER.getCameraX() + Mouse.getX(), Game.PLAYER.getCameraY() + Mouse.getY())){
					selectedEntity = entity;
					break;
				}
				
				selectedEntity = null;
				
			}
			
		}
		
		int button = -1;
		
		// 0 = left 1 = right
		if(Mouse.isButtonDown(0)) button = 0;
		if(Mouse.isButtonDown(1)) button = 1;
		
		if(button == 1 && dragItem != null){
			dragItem = null;
			Inventory.dragIndex = -1;
		}
		
		if(renderLootDialog == true){
			LootWindow.processInput(button, Mouse.getX(), Mouse.getY());
		}
		
		if(renderInventory){
			Inventory.processInput(button, Mouse.getX(), Mouse.getY());
		}
		
		if(renderPlayerStats){
			PlayerStats.processInput(button, Mouse.getX(), Mouse.getY());
		}
		
		if(renderQuestLog){
			QuestLog.processInput(button, Mouse.getX(), Mouse.getY());
		}
		
		if(renderSKillWindow){
			SkillWindow.processInput(button, Mouse.getX(), Mouse.getY());
		}
		
		ActionBar.update(button, Mouse.getX(), Mouse.getY());
	}
	
	private void processKeyEvents(){
		
		if(Game.isPaused() == false) ActionBar.respondToActionKeys();
		
		if(Keyboard.getEventKey() == Keyboard.KEY_F1){
			renderInfos = !renderInfos;
		}
		if(Keyboard.getEventKey() == Keyboard.KEY_P){
			if(!renderInventory && !renderPlayerStats){
			Game.setPaused(!Game.isPaused());
			renderPaused = Game.isPaused();
			}
		}
		
		if(!renderPaused){
			
			
			if(Keyboard.getEventKey() == Keyboard.KEY_C){
				Sound.click.playAsSoundEffect(1, Options.FXVolume, false);
				if(renderInventory || renderQuestLog || renderMap || renderSKillWindow){
					renderInventory = false;
					renderQuestLog = false;
					renderMap = false;
					renderSKillWindow = false;
				}else{
					Game.setPaused(!Game.isPaused());
				}
				renderPlayerStats = !renderPlayerStats;
			}
			
			
			if(Keyboard.getEventKey() == Keyboard.KEY_I){
				Sound.click.playAsSoundEffect(1, Options.FXVolume, false);
				if(renderPlayerStats || renderQuestLog || renderMap || renderSKillWindow){
					renderQuestLog = false;
					renderPlayerStats = false;
					renderMap = false;
					renderSKillWindow = false;
				}else{
					Game.setPaused(!Game.isPaused());
				}
				renderInventory = !renderInventory;
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_L){
				Sound.click.playAsSoundEffect(1, Options.FXVolume, false);
				if(renderPlayerStats || renderInventory || renderMap || renderSKillWindow){
					renderPlayerStats = false;
					renderInventory = false;
					renderMap = false;
					renderSKillWindow = false;
				}else{
					Game.setPaused(!Game.isPaused());
				}
				renderQuestLog = !renderQuestLog;
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_M){
				Sound.click.playAsSoundEffect(1, Options.FXVolume, false);
				if(renderPlayerStats || renderInventory || renderQuestLog || renderSKillWindow){
					renderPlayerStats = false;
					renderInventory = false;
					renderQuestLog = false;
					renderSKillWindow = false;
				}else{
					Game.setPaused(!Game.isPaused());
				}
				renderMap = !renderMap;
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_K){
				Sound.click.playAsSoundEffect(1, Options.FXVolume, false);
				if(renderPlayerStats || renderInventory || renderQuestLog || renderMap){
					renderPlayerStats = false;
					renderInventory = false;
					renderQuestLog = false;
					renderMap = false;
				}else{
					Game.setPaused(!Game.isPaused());
				}
				renderSKillWindow= !renderSKillWindow;
				if(renderSKillWindow == true){
					SkillWindow.renderOverview = true;
					SkillWindow.renderCreateSkill = false;
					SkillWindow.renderEditSkill = false;
				}
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glOrtho(0, Game.SCREEN_WIDTH, 0, Game.SCREEN_HEIGHT, 0, -1);

				Game.setState(GameState.MENU);
			}
			
			if(Keyboard.getEventKey() == Keyboard.KEY_X){
				int exp = (int) (0.01*Math.pow(Game.PLAYER.getLvl(), 2)+Game.PLAYER.getLvl() + 10);
				Game.PLAYER.addExp(exp);
			}
		}
	}
	
	private void renderStandardUI(){
		
		if(selectedEntity != null){
			renderSelectedEntity();
		}
		renderExpBar();
		renderHealthBar();
		renderManaBar();
		ActionBar.render();
		
	}
	
	private void renderInfos(){
		if(!renderInfos)return;
		
		Font.render("FPS: " + Game.FPS, x + Game.SCREEN_WIDTH - 80, y + Game.SCREEN_HEIGHT - 15);
		Font.render("BGM: " + Options.BGVolume, x + Game.SCREEN_WIDTH - 80, y + Game.SCREEN_HEIGHT - 45);
		Font.render("FXM: " + Options.FXVolume, x + Game.SCREEN_WIDTH - 80, y + Game.SCREEN_HEIGHT - 60);

	}
	
	
	public Texture loadTexture(String path){
		Texture tex = null;
		try {
			 tex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
			} catch (IOException e) {
				e.printStackTrace();
		}  
		return tex;
	}
	
private void renderExpBar(){
		
		int expBarWidth = Game.SCREEN_WIDTH * Game.PLAYER.getExp() / Game.PLAYER.getMaxExp();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(0.4f, 0, 0.6f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x , y + 36);
			GL11.glVertex2f(x + expBarWidth, y + 36);
			GL11.glVertex2f(x + expBarWidth, y + 44);
			GL11.glVertex2f(x , y + 44);
			GL11.glEnd();
			GL11.glColor3f(0.7f, 0.5f, 0.9f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x + expBarWidth, y + 36);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH, y + 36);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH, y + 44);
			GL11.glVertex2f(x + expBarWidth, y + 44);
			GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		String exp = Game.PLAYER.getExp() + "/" + Game.PLAYER.getMaxExp();
		Font.render(exp, x + (Game.SCREEN_WIDTH)/2 - exp.length()*4, y + 36);
		
	}
	
	private void renderHealthBar(){
		
		int healthBarEndX  =  Game.SCREEN_WIDTH / 2 - ActionBar.ACTIONSLOTS*17 - 2;
		int healthBarWidth = healthBarEndX * Game.PLAYER.getHealth() / Game.PLAYER.getMaxHealth();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(1, 0, 0);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x , y);
			GL11.glVertex2f(x + healthBarWidth, y);
			GL11.glVertex2f(x + healthBarWidth, y + 32 + 4);
			GL11.glVertex2f(x , y + 32 + 4);
			GL11.glEnd();
			GL11.glColor3f(0.3f, 0, 0);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x + healthBarWidth, y);
			GL11.glVertex2f(x + healthBarEndX, y);
			GL11.glVertex2f(x + healthBarEndX, y + 32 + 4);
			GL11.glVertex2f(x + healthBarWidth, y + 32 + 4);
			GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		String health = Game.PLAYER.getHealth() + "/" + Game.PLAYER.getMaxHealth();
		Font.render(health, x + (healthBarEndX)/2 - health.length()*4, y + 8);
		
	}
	
	private void renderManaBar(){
		
		int startX = Game.SCREEN_WIDTH / 2 - ActionBar.ACTIONSLOTS*17 + ActionBar.ACTIONSLOTS * 34;
		int endX = Game.SCREEN_WIDTH;
		int manaBarWidth = (endX-startX) * Game.PLAYER.getMana() / Game.PLAYER.getMaxMana();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(0, 0, 1);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x + startX, y);
			GL11.glVertex2f(x + startX + manaBarWidth, y);
			GL11.glVertex2f(x + startX + manaBarWidth, y + 32 + 4);
			GL11.glVertex2f(x + startX, y + 32 + 4);
			GL11.glEnd();
			GL11.glColor3f(0, 0, 0.3f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x + startX + manaBarWidth, y);
			GL11.glVertex2f(x + endX, y);
			GL11.glVertex2f(x + endX, y + 32 + 4);
			GL11.glVertex2f(x + startX + manaBarWidth, y + 32 + 4);
			GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		
		
		String mana = Game.PLAYER.getMana() + "/" + Game.PLAYER.getMaxMana();
		Font.render(mana, x + Game.SCREEN_WIDTH - ((Game.SCREEN_WIDTH / 2 - ActionBar.ACTIONSLOTS*16)/2)  - mana.length()*4, y + 8);
	}
	
	private void renderPaused(){
		Font.render("PAUSED", x + Game.SCREEN_WIDTH/2 - 3*8*4, y + Game.SCREEN_HEIGHT/2-4*4,4);
	}

	private void renderSelectedEntity(){
		int startX = Game.SCREEN_WIDTH / 2 - 200;
		int endX = Game.SCREEN_WIDTH / 2 + 200;
		int healthBarWidth = (endX-startX) * selectedEntity.getHealth() / selectedEntity.getMaxHealth();
		
		Font.render(Integer.toString(selectedEntity.getLvl()), x + startX - (32*Integer.toString(selectedEntity.getLvl()).length()), y + Game.SCREEN_HEIGHT - 32,3);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(1, 0, 0);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x + startX, y + Game.SCREEN_HEIGHT - 32);
			GL11.glVertex2f(x + startX + healthBarWidth, y + Game.SCREEN_HEIGHT - 32);
			GL11.glVertex2f(x + startX + healthBarWidth, y + Game.SCREEN_HEIGHT);
			GL11.glVertex2f(x + startX, y + Game.SCREEN_HEIGHT);
			GL11.glEnd();
			GL11.glColor3f(0.3f, 0.3f, 0.3f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x + startX + healthBarWidth, y + Game.SCREEN_HEIGHT - 32);
			GL11.glVertex2f(x + endX, y + Game.SCREEN_HEIGHT - 32);
			GL11.glVertex2f(x + endX, y + Game.SCREEN_HEIGHT);
			GL11.glVertex2f(x + startX + healthBarWidth, y + Game.SCREEN_HEIGHT);
			GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		
		
		String health = selectedEntity.getHealth() + "/" + selectedEntity.getMaxHealth();
		Font.render(health, x + Game.SCREEN_WIDTH/2 - health.length()*4, y + Game.SCREEN_HEIGHT - 24);
	}
	
	private void updateSelectedEntity(){
		if(selectedEntity != null){
		if(	selectedEntity.getX() > x + Game.SCREEN_WIDTH 
			|| selectedEntity.getX() + selectedEntity.getWidth() < x
			|| selectedEntity.getY() > y + Game.SCREEN_HEIGHT
			|| selectedEntity.getY() + selectedEntity.getHeight() < y
			|| selectedEntity.isDead()){
			selectedEntity = null;
		}}
	}
	
	public void showLootDialog(ArrayList<Item> loot){
		renderLootDialog = true;
		LootWindow.loot = loot;
		playerX = Game.PLAYER.getX();
		playerY = Game.PLAYER.getY();
	}
	
	public boolean isRenderingInventory(){
		return renderInventory;
	}
	
	public boolean isRenderingPlayerStats(){
		return renderPlayerStats;
	}
	
	public boolean isRenderingMap(){
		return renderMap;
	}

	public Item getDragItem(){
		return dragItem;
	}
	
	public void setDragItem(Item item){
		dragItem = item;
	}
	
	public Entity getSelectedEntity(){
		return selectedEntity;
	}
}