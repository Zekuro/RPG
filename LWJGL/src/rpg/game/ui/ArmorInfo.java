package rpg.game.ui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.armor.Armor;

public class ArmorInfo {
	
	public static void render(Armor armor){
		
		int i = 1;
		for (int stat : armor.getStats().toArray()) {
			if(stat > 0) i++;
		}

		int dialogWidth = armor.getName().length() * 8 + 20;
		int dialogHeight = 20 + 10 * i;

		
		int dialogX = Game.PLAYER.getCameraX() + Mouse.getX() + 15;
		int dialogY = Game.PLAYER.getCameraY() + Mouse.getY() - dialogHeight + 15;
		
		
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
		
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		Font.render(armor.getName(), dialogX + 10, dialogY + dialogHeight - 16);
		
		int k = 0;
		for (int  stat : armor.getStats().toArray()) {
			if(stat > 0){
				Font.render(armor.getStats().getStatName(k), dialogX + 10, dialogY + 10 + dialogHeight - k*10);
				Font.render(Integer.toString(stat), dialogX + 50, dialogY + 10 + dialogHeight - k*10);
			}
			k++;
		}
		
	}

}
