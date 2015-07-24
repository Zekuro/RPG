package rpg.game.ui;

import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.Stats;

public class PlayerStats {
	
	private static int dialogWidth = 400;
	private static int dialogHeight = 300;
	private static int armorOffsetX = 150;
	private static int accessoirOffestX = armorOffsetX + 200;
	
	public static void render(){
		int x = Game.PLAYER.getCameraX();
		int y = Game.PLAYER.getCameraY();
		
		int dialogX;
		int dialogY = y + Game.SCREEN_HEIGHT/2 - dialogHeight/2;
		
		Stats stats = Game.PLAYER.getStats();
		
		dialogX = x + Game.SCREEN_WIDTH/2 - dialogWidth/2;
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(dialogX - 2, dialogY - 2);
		GL11.glVertex2f(dialogX + dialogWidth + 2, dialogY - 2);
		GL11.glVertex2f(dialogX + dialogWidth + 2, dialogY + dialogHeight + 2);
		GL11.glVertex2f(dialogX - 2, dialogY + dialogHeight + 2);
		GL11.glEnd();
		
		GL11.glColor3f(0.3f, 0.3f, 0.3f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(dialogX, dialogY);
		GL11.glVertex2f(dialogX + dialogWidth, dialogY);
		GL11.glVertex2f(dialogX + dialogWidth, dialogY + dialogHeight);
		GL11.glVertex2f(dialogX, dialogY + dialogHeight);
		GL11.glEnd();
		
		
		// ARMOR
		for(int i = 1; i <= 7; i++){
		
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(dialogX + armorOffsetX, dialogY + dialogHeight - i*4 - i*32);
		GL11.glVertex2f(dialogX + armorOffsetX + 32, dialogY + dialogHeight - i*4 - i*32);
		GL11.glVertex2f(dialogX + armorOffsetX + 32, dialogY + dialogHeight - i*4 - i*32 - 32);
		GL11.glVertex2f(dialogX + armorOffsetX, dialogY + dialogHeight - i*4 - i*32 - 32);
		GL11.glEnd();
		
		}
		
		// ACCESSOIR
		for(int i = 1; i <= 5; i++){
			
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(dialogX + accessoirOffestX, dialogY + dialogHeight - i*4 - i*32);
			GL11.glVertex2f(dialogX + accessoirOffestX + 32, dialogY + dialogHeight - i*4 - i*32);
			GL11.glVertex2f(dialogX + accessoirOffestX + 32, dialogY + dialogHeight - i*4 - i*32 - 32);
			GL11.glVertex2f(dialogX + accessoirOffestX, dialogY + dialogHeight - i*4 - i*32 - 32);
			GL11.glEnd();
			
			}
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		String msg = "- S P I E L E R I N F O -";
		Font.render(msg, dialogX+dialogWidth/2 - msg.length()*4, dialogY + dialogHeight - 16);
	
		Font.render("LvL: " + Game.PLAYER.getLvl(), dialogX + 15, dialogY + dialogHeight - 48);
		Game.PLAYER.getStats().render(dialogX + 15, dialogY + dialogHeight - 80);
	
	}

}
