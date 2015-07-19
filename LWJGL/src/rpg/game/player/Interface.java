package rpg.game.player;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rpg.game.Font;
import rpg.game.Game;

public class Interface {

	private boolean renderInfos = false;
	private final int actionSlots = 9;
	private Texture actionBar;
	private int wait = 0;
	
	public Interface(){
		actionBar = loadTexture("res/actionbar.png");
	}
	

	public void render(){
		
		renderActionBar();
		renderInfos();
		
	}
	
	public void update(){
		
		if(wait == 0){
			if(Keyboard.isKeyDown(Keyboard.KEY_F1)){
				renderInfos = !renderInfos;
				wait = 30;
			}
		}
		
		
		if(wait > 0){
			wait--;
		}
		
	}
	
	private void renderActionBar(){
		
		actionBar.bind();
		
		for(int i = 0; i < actionSlots; i++){	
			GL11.glColor3f(0.8f, 0.8f, 0.8f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - actionSlots*16 + i*32, Game.PLAYER.getCameraY());
			GL11.glTexCoord2f(1f, 0);
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - actionSlots*16 + i*32 + 32, Game.PLAYER.getCameraY());
			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - actionSlots*16 + i*32 + 32, Game.PLAYER.getCameraY() + 32);
			GL11.glTexCoord2f(0, 1f);
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - actionSlots*16 + i*32, Game.PLAYER.getCameraY() + 32);
			GL11.glEnd();
		}
		
	}
	
	private void renderInfos(){
		if(!renderInfos)return;
		
		Font.render("FPS: " + Game.FPS, Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH - 80, Game.PLAYER.getCameraY() + Game.SCREEN_HEIGHT - 15);
		
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
	
}
