package rpg.game.ui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.World;
import rpg.game.objects.GameObject;

public class Map {
	
	private static int scale = 4;
	
	private static int mapOffsetX = 0;
	private static int mapOffsetY = 0;
	
	private static int scrollSpeed = scale;
	
	// FIXME starting at 0:0 with mapOffsetX & Y = 0 fix out of map check here too when it starts at player pos
	// FIXME only render specific sectors, else it is lagging!
	public static void render(World world){
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glColor3f(0.2f, 0.2f, 0.2f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(0 + Game.PLAYER.getCameraX(), 0 + Game.PLAYER.getCameraY());
		GL11.glVertex2f(Game.SCREEN_WIDTH + Game.PLAYER.getCameraX(), 0 + Game.PLAYER.getCameraY());
		GL11.glVertex2f(Game.SCREEN_WIDTH + Game.PLAYER.getCameraX(), Game.SCREEN_HEIGHT + Game.PLAYER.getCameraY());
		GL11.glVertex2f(0 + Game.PLAYER.getCameraX(), Game.SCREEN_HEIGHT + Game.PLAYER.getCameraY());
		GL11.glEnd();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		// stretch to screen
//		while(Game.WORLD_WIDTH/scale < Game.SCREEN_WIDTH && Game.WORLD_HEIGHT/scale < Game.SCREEN_HEIGHT){
//			scale -= 2;
//		}
		
		if(Game.WORLD_WIDTH/scale < Game.SCREEN_WIDTH){
			mapOffsetX = (Game.SCREEN_WIDTH - Game.WORLD_WIDTH/scale)/2;
		}
		
		if(Game.WORLD_HEIGHT/scale < Game.SCREEN_HEIGHT){
			mapOffsetY = (Game.SCREEN_HEIGHT - Game.WORLD_HEIGHT/scale)/2;
		}
		
		for (GameObject object : world.backgroundTiles) {
			object.mapRender(scale, mapOffsetX, mapOffsetY);
		}
		
		Game.PLAYER.mapRender(scale, mapOffsetX, mapOffsetY);
		
		// implement me when render method is defined in entity
//		for (GameObject object : world.entityList) {
//			object.mapRender(scale);
//		}
		
		for (GameObject object : world.objectList) {
			object.mapRender(scale, mapOffsetX, mapOffsetY);
		}
		
		Font.render("Karte", Game.PLAYER.getCameraX() + 32, Game.PLAYER.getCameraY() + Game.SCREEN_HEIGHT - 32, 2);
		
	}
	
	// FIXME fix me when starting at player pos (fix offset out of bounds check)
	public static void processInput(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W) && mapOffsetY  - scrollSpeed > -Game.WORLD_HEIGHT/scale){
			mapOffsetY -= scrollSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A) && mapOffsetX + scrollSpeed < 0){
			mapOffsetX += scrollSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S) && mapOffsetY + scrollSpeed < 0){
			mapOffsetY += scrollSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D) && mapOffsetX - scrollSpeed > -Game.WORLD_WIDTH/scale){
			mapOffsetX -= scrollSpeed;
		}
		
		
	}

}
