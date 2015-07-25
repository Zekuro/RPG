package rpg.game.player;

import org.lwjgl.opengl.GL11;

import rpg.game.Game;
import rpg.game.items.Item;

public class ActionBar {

	public static final int ACTIONSLOTS = 9;
	private static Item[] actionBar = new Item[9];
	
	public static void render(){
		
		for(int i = 0; i < ACTIONSLOTS; i++){	
			GL11.glColor3f(0.8f, 0.8f, 0.8f);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - ACTIONSLOTS*16 + i*32, Game.PLAYER.getCameraY());
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - ACTIONSLOTS*16 + i*32 + 32, Game.PLAYER.getCameraY());
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - ACTIONSLOTS*16 + i*32 + 32, Game.PLAYER.getCameraY() + 32);
			GL11.glVertex2f(Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH / 2 - ACTIONSLOTS*16 + i*32, Game.PLAYER.getCameraY() + 32);
			GL11.glEnd();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
		
	}
	
	public static void processInput(){
		
	}
	
}
