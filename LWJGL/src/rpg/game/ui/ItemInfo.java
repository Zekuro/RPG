package rpg.game.ui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.items.Item;

public class ItemInfo {
	
	public static void render(Item item){
		
		int dialogWidth;
		
		if(item.getName().length() > item.getDescription().length()){
			dialogWidth = item.getName().length() * 8 + 20;
		}else{
			dialogWidth = item.getDescription().length() * 8 + 20;
		}
		// 20 + 12 * i (i = desc lines)
		int dialogHeight = 20 + 12 * (item.getDescription().length()/35 +2);

		
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
		
		switch(item.getTier()){
		case T1:
			Font.render(item.getName(), dialogX + 10, dialogY + dialogHeight - 16);
			break;
		case T2:
			Font.renderColored(item.getName(), dialogX + 10, dialogY + dialogHeight - 16, 1, 0f, 1f, 0f);
			break;
		case T3:
			Font.renderColored(item.getName(), dialogX + 10, dialogY + dialogHeight - 16, 1, 0f, 0f, 0.7f);
			break;
		case T4:
			Font.renderColored(item.getName(), dialogX + 10, dialogY + dialogHeight - 16, 1, 0.5f, 0f, 0.7f);
			break;
		case T5:
			Font.renderColored(item.getName(), dialogX + 10, dialogY + dialogHeight - 16, 1, 1f, 0.5f, 0.3f);
			break;
		}
		
		
		if(item.getRequiredLvl() > Game.PLAYER.getLvl()){
			Font.renderColored("LvL: " + item.getRequiredLvl(), dialogX + 10, dialogY + dialogHeight - 26, 1, 0.5f, 0f, 0f);
		}else{
			Font.render("LvL: " + item.getRequiredLvl(), dialogX + 10, dialogY + dialogHeight - 26);
		}
		
		for (int  i = 0; i < (item.getDescription().length()/35) + 1; i++) {
				if((i+1)*35 < item.getDescription().length()){
					Font.render(item.getDescription().substring(i*35, (i+1)*35), dialogX + 10, dialogY + dialogHeight - 36 - i*10);
				}else{
					Font.render(item.getDescription().substring(i*35, item.getDescription().length()), dialogX + 10, dialogY + dialogHeight - 36 - i*10);
				}
		}
		
	}

}
