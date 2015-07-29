package rpg.game.ui;

import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;

public class QuestLog {

	public static void render(int x, int y){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-5*40-2, y + Game.SCREEN_HEIGHT/2-5*36-2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+5*40+2, y + Game.SCREEN_HEIGHT/2-5*36-2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+5*40+2, y + Game.SCREEN_HEIGHT/2+5*36+2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-5*40-2, y + Game.SCREEN_HEIGHT/2+5*36+2);
		GL11.glEnd();
		
		GL11.glColor3f(0.3f, 0.3f, 0.3f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-5*40, y + Game.SCREEN_HEIGHT/2-5*36);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+5*40, y + Game.SCREEN_HEIGHT/2-5*36);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+5*40, y + Game.SCREEN_HEIGHT/2+5*36);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-5*40, y + Game.SCREEN_HEIGHT/2+5*36);
		GL11.glEnd();
		
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		String msg = "- Q U E S T L O G -";
		Font.render(msg,x + Game.SCREEN_WIDTH/2 - msg.length()*4, y + Game.SCREEN_HEIGHT/2+5*36-16);
		
	}
	
}
