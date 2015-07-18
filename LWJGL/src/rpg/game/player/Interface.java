package rpg.game.player;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rpg.game.Game;

public class Interface {

	private final int actionSlots = 9;
	private Texture actionBar;
	
	public Interface(){
		actionBar = loadTexture("res/actionbar.png");
	}
	

	public void render(){

		
		
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
	
	public void update(){
		
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
