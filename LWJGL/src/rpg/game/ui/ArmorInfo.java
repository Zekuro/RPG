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
		int dialogHeight = 20 + 12 * i;

		
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
		
		switch(armor.getTier()){
		case T1:
			Font.render(armor.getName(), dialogX + 10, dialogY + dialogHeight - 16);
			break;
		case T2:
			Font.renderColored(armor.getName(), dialogX + 10, dialogY + dialogHeight - 16, 1, 0f, 1f, 0f);
			break;
		case T3:
			Font.renderColored(armor.getName(), dialogX + 10, dialogY + dialogHeight - 16, 1, 0f, 0f, 0.7f);
			break;
		case T4:
			Font.renderColored(armor.getName(), dialogX + 10, dialogY + dialogHeight - 16, 1, 0.5f, 0f, 0.7f);
			break;
		case T5:
			Font.renderColored(armor.getName(), dialogX + 10, dialogY + dialogHeight - 16, 1, 1f, 0.5f, 0.3f);
			break;
		}
		
		
		if(armor.getRequiredLvl() > Game.PLAYER.getLvl()){
			Font.renderColored("LvL: " + armor.getRequiredLvl(), dialogX + 10, dialogY + dialogHeight - 26, 1, 0.5f, 0f, 0f);
		}else{
			Font.render("LvL: " + armor.getRequiredLvl(), dialogX + 10, dialogY + dialogHeight - 26);
		}
		
		int k = 0;
		for (int  stat : armor.getStats().toArray()) {
			if(stat > 0){
				Font.render(armor.getStats().getStatName(k), dialogX + 10, dialogY + dialogHeight - k*10);
				Font.render(Integer.toString(stat), dialogX + 50, dialogY + dialogHeight - k*10);
			}
			k++;
		}
		
	}

}
