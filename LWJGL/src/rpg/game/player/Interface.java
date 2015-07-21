package rpg.game.player;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.Map;
import rpg.game.entities.Entity;
import rpg.game.objects.GameObject;

public class Interface {

	private final int actionSlots = 9;
	private Texture actionBar;
	private int wait = 0;
	
	private boolean renderInfos = false;
	private boolean renderPaused = false;
	
	private int x;
	private int y;
	
	public Interface(){
		actionBar = loadTexture("res/actionbar.png");
	}
	

	public void render(){
		
		x = Game.PLAYER.getCameraX();
		y = Game.PLAYER.getCameraY();
		
		renderStandardUI();
		renderInfos();
		
		if(renderPaused){
			renderPaused();
		}
		
	}
	
	public void update(){
		
		if(wait == 0){
			if(Keyboard.isKeyDown(Keyboard.KEY_F1)){
				renderInfos = !renderInfos;
				wait = 30;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_P)){
				Game.setPaused(!Game.isPaused());
				renderPaused = !renderPaused;
				wait = 30;
			}
			if(Mouse.isButtonDown(0)){
			
				for (GameObject object: Map.entityList) {
					
					Entity entity = (Entity) object;
					
					if(entity.isEntityAt(Mouse.getX(), Mouse.getY())){
						System.out.println("LVL: " + entity.getLvl() + " HEALTH: " + entity.getHealth() + " MANA: " + entity.getMana());
					}
					
				}
				 wait = 30;
				
			}
		}
		
		
		
		
		if(wait > 0){
			wait--;
		}
		
	}
	
	// TODO length of blue and red bars are incorrect
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
	
}
