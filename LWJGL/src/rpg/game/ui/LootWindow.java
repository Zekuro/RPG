package rpg.game.ui;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.items.Item;
import rpg.game.player.Inventory;

public class LootWindow {

	private static String itemName = null;
	private static boolean renderItemName = false;
	public static ArrayList<Item> loot = null;
	
	public static void renderLootDialog(){
		// in actual state it is centered
		
		int dialogWidth = 96;
		int dialogHeight = 32 + 16*loot.size();
		int dialogX;
		
		int x = Game.PLAYER.getCameraX();
		int y = Game.PLAYER.getCameraY();
		
		if(Game.UI.isRenderingInventory()){
			dialogX = x + Game.SCREEN_WIDTH/2+5*40+2 + 50;
		}else{
			dialogX = x + Game.SCREEN_WIDTH/2 - dialogWidth/2;
		}
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(dialogX - 2, y + Game.SCREEN_HEIGHT/2 - dialogHeight - 2);
		GL11.glVertex2f(dialogX + dialogWidth + 2, y + Game.SCREEN_HEIGHT/2 - dialogHeight - 2);
		GL11.glVertex2f(dialogX + dialogWidth +2, y + Game.SCREEN_HEIGHT/2 + dialogHeight + 2);
		GL11.glVertex2f(dialogX - 2, y + Game.SCREEN_HEIGHT/2 + dialogHeight + 2);
		GL11.glEnd();
		
		GL11.glColor3f(0.3f, 0.3f, 0.3f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(dialogX, y + Game.SCREEN_HEIGHT/2 - dialogHeight);
		GL11.glVertex2f(dialogX + dialogWidth, y + Game.SCREEN_HEIGHT/2 - dialogHeight);
		GL11.glVertex2f(dialogX + dialogWidth, y + Game.SCREEN_HEIGHT/2 + dialogHeight);
		GL11.glVertex2f(dialogX, y + Game.SCREEN_HEIGHT/2 + dialogHeight);
		GL11.glEnd();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		for (Item item : loot) {
			int i = loot.indexOf(item) + 1;
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(dialogX + dialogWidth/2 - 16, y + Game.SCREEN_HEIGHT/2 + dialogHeight - i*4 - i*32 - 32);
			GL11.glVertex2f(dialogX + dialogWidth/2 + 16, y + Game.SCREEN_HEIGHT/2 + dialogHeight - i*4 - i*32 - 32);
			GL11.glVertex2f(dialogX + dialogWidth/2 + 16, y + Game.SCREEN_HEIGHT/2 + dialogHeight - i*4 - i*32);
			GL11.glVertex2f(dialogX + dialogWidth/2 - 16, y + Game.SCREEN_HEIGHT/2 + dialogHeight - i*4 - i*32);
			GL11.glEnd();
			
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			GL11.glColor3f(1, 1, 1);
			item.render(dialogX + dialogWidth/2 - 16, y + Game.SCREEN_HEIGHT/2 + dialogHeight - i*4 - i*32 - 32);
			String msg = Integer.toString(item.getStacks());
			Font.render(msg, dialogX + dialogWidth/2 + 16 - msg.length()* 8, y + Game.SCREEN_HEIGHT/2 + dialogHeight - i*4 - i*32 - 32);
		}
		
		if(renderItemName){
			Font.render(itemName, Mouse.getX() + Game.PLAYER.getCameraX(), Mouse.getY() + Game.PLAYER.getCameraY() + 10);
		}
		
	}
	
	public static void processInput(int button, int mouseX, int mouseY){
		int index = -1;
		
		int dialogWidth = 96;
		int dialogHeight = 32 + 16*loot.size();
		int dialogX;
		
		int x = Game.PLAYER.getCameraX();
		int y = Game.PLAYER.getCameraY();
		
		if(Game.UI.isRenderingInventory()){
			dialogX = x + Game.SCREEN_WIDTH/2+5*40+2 + 50;
		}else{
			dialogX = x + Game.SCREEN_WIDTH/2 - dialogWidth/2;
		}
		
		for (Item item : loot) {
			int i = loot.indexOf(item) + 1;
			
			if(	mouseX + x > dialogX + dialogWidth/2 - 16
				&& mouseX + x < dialogX + dialogWidth/2 + 16
				&& mouseY + y > y + Game.SCREEN_HEIGHT/2 + dialogHeight - i*4 - i*32 - 32
				&& mouseY + y < y + Game.SCREEN_HEIGHT/2 + dialogHeight - i*4 - i*32){
				index = i-1;
				break;
			}
		}
		
		
		// MouseButton: -1 = nothing, 0 = left, 1 = right
		if(index >= 0){
			Item item = loot.get(index);
			if(button == 1){
				for(int i = 0; i < item.getStacks(); i++) {
					Inventory.add(item);
				}
				loot.remove(index);
				if(loot.isEmpty()){
					Game.UI.renderLootDialog = false;
				}
			}else if(button == -1){
				renderItemName = true;
				itemName = item.getName();
			}
		}else{
			renderItemName = false;
		}
	}
	
}
