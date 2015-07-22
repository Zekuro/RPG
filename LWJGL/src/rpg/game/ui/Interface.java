package rpg.game.ui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.World;
import rpg.game.entities.Entity;
import rpg.game.objects.GameObject;
import rpg.game.player.Inventory;

public class Interface {

	private final int actionSlots = 9;
	private Texture actionBar;
	
	private boolean renderInfos = false;
	private boolean renderPaused = false;
	private boolean renderInventory = false;
	
	private Entity selectedEntity = null;
	
	private int x;
	private int y;
	
	public Interface(){
		actionBar = loadTexture("res/actionbar.png");
	}
	

	public void render(){
		
		x = Game.PLAYER.getCameraX();
		y = Game.PLAYER.getCameraY();
		
		if(renderInventory){
			Inventory.render(x, y);
		}
		
		renderStandardUI();
		renderInfos();
		
		if(renderPaused){
			renderPaused();
		}
		
	}
	
	public void update(){
		while(Keyboard.next() || Mouse.next()){
		if(Keyboard.isKeyDown(Keyboard.KEY_F1)){
			renderInfos = !renderInfos;
		}
			if(Keyboard.isKeyDown(Keyboard.KEY_P)){
				Game.setPaused(!Game.isPaused());
				renderPaused = !renderPaused;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_I)){
				Game.setPaused(!Game.isPaused());
				renderInventory = !renderInventory;
			}
			if(Mouse.isButtonDown(0) && Game.isPaused() == false){
				
				for (GameObject object: World.entityList) {
					
					Entity entity = (Entity) object;
					
					if(entity.isEntityAt(Game.PLAYER.getCameraX() + Mouse.getX(), Game.PLAYER.getCameraY() + Mouse.getY())){
						selectedEntity = entity;
						break;
					}
					
					selectedEntity = null;
					
				}
				
			}
			
			if(renderInventory){
				if(Mouse.isButtonDown(0)){
					Inventory.processInput(0, Mouse.getX(), Mouse.getY());
				}else if(Mouse.isButtonDown(1)){
					Inventory.processInput(1, Mouse.getX(), Mouse.getY());
				}else{
					Inventory.processInput(-1, Mouse.getX(), Mouse.getY());
				}
				
			}
			
			updateSelectedEntity();
		}
		
	}
	
	private synchronized void listenToKeyInput(){
		
	}
	
	private void renderStandardUI(){
		
		actionBar.bind();
		
		for(int i = 0; i < actionSlots; i++){	
			GL11.glColor3f(0.8f, 0.8f, 0.8f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH / 2 - actionSlots*16 + i*32, y);
			GL11.glTexCoord2f(1f, 0);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH / 2 - actionSlots*16 + i*32 + 32, y);
			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH / 2 - actionSlots*16 + i*32 + 32, y + 32);
			GL11.glTexCoord2f(0, 1f);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH / 2 - actionSlots*16 + i*32, y + 32);
			GL11.glEnd();
		}
		
		if(selectedEntity != null){
			renderSelectedEntity();
		}
		
		renderHealthBar();
		renderManaBar();
		
	}
	
	private void renderInfos(){
		if(!renderInfos)return;
		
		Font.render("FPS: " + Game.FPS, x + Game.SCREEN_WIDTH - 80, y + Game.SCREEN_HEIGHT - 15);
		
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
	
	
	private void renderHealthBar(){
		
		int endX  =  Game.SCREEN_WIDTH / 2 - actionSlots*16;
		int healthBarWidth = endX * Game.PLAYER.getHealth() / Game.PLAYER.getMaxHealth();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(1, 0, 0);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x , y);
			GL11.glVertex2f(x + healthBarWidth, y);
			GL11.glVertex2f(x + healthBarWidth, y + 32);
			GL11.glVertex2f(x , y + 32);
			GL11.glEnd();
			GL11.glColor3f(0.3f, 0.3f, 0.3f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x + healthBarWidth, y);
			GL11.glVertex2f(x + endX, y);
			GL11.glVertex2f(x + endX, y + 32);
			GL11.glVertex2f(x + healthBarWidth, y + 32);
			GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		String health = Game.PLAYER.getHealth() + "/" + Game.PLAYER.getMaxHealth();
		Font.render(health, x + (endX)/2 - health.length()*4, y + 8);
		
	}
	
	private void renderManaBar(){
		
		int startX = Game.SCREEN_WIDTH / 2 - actionSlots*16 + actionSlots * 32;
		int endX = Game.SCREEN_WIDTH;
		int manaBarWidth = (endX-startX) * Game.PLAYER.getMana() / Game.PLAYER.getMaxMana();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(0, 0, 1);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x + startX, y);
			GL11.glVertex2f(x + startX + manaBarWidth, y);
			GL11.glVertex2f(x + startX + manaBarWidth, y + 32);
			GL11.glVertex2f(x + startX, y + 32);
			GL11.glEnd();
			GL11.glColor3f(0.3f, 0.3f, 0.3f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x + startX + manaBarWidth, y);
			GL11.glVertex2f(x + endX, y);
			GL11.glVertex2f(x + endX, y + 32);
			GL11.glVertex2f(x + startX + manaBarWidth, y + 32);
			GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		
		
		String mana = Game.PLAYER.getMana() + "/" + Game.PLAYER.getMaxMana();
		Font.render(mana, x + Game.SCREEN_WIDTH - ((Game.SCREEN_WIDTH / 2 - actionSlots*16)/2)  - mana.length()*4, y + 8);
	}
	
	private void renderPaused(){
		Font.render("PAUSED", x + Game.SCREEN_WIDTH/2 - 3*8*4, y + Game.SCREEN_HEIGHT/2-4*4,4);
	}

	private void renderSelectedEntity(){
		int startX = Game.SCREEN_WIDTH / 2 - 200;
		int endX = Game.SCREEN_WIDTH / 2 + 200;
		int healthBarWidth = (endX-startX) * selectedEntity.getHealth() / selectedEntity.getMaxHealth();
		
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
			|| selectedEntity.getY() + selectedEntity.getHeight() < y){
			selectedEntity = null;
		}}
	}

}
