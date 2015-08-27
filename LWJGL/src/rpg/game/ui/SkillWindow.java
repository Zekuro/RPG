package rpg.game.ui;

import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;

public class SkillWindow {

	private static final int width = 400;
	private static final int height = 500;
	
	private static boolean renderOverview = true;
	
	public static void render(){
		int x = Game.PLAYER.getCameraX();
		int y = Game.PLAYER.getCameraY();

		int windowX = x + Game.SCREEN_WIDTH/2-width/2;
		int windowY = y + Game.SCREEN_HEIGHT/2-height/2;
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		// border
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-width/2-2, y + Game.SCREEN_HEIGHT/2-height/2-2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+width/2+2, y + Game.SCREEN_HEIGHT/2-height/2-2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+width/2+2, y + Game.SCREEN_HEIGHT/2+height/2+2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-width/2-2, y + Game.SCREEN_HEIGHT/2+height/2+2);
		GL11.glEnd();
		
		// window bg
		GL11.glColor3f(0.3f, 0.3f, 0.3f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-width/2, y + Game.SCREEN_HEIGHT/2-height/2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+width/2, y + Game.SCREEN_HEIGHT/2-height/2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+width/2, y + Game.SCREEN_HEIGHT/2+height/2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-width/2, y + Game.SCREEN_HEIGHT/2+height/2);
		GL11.glEnd();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		
		if(renderOverview) renderSkillOverview(windowX, windowY);
		
	}
	
	private static void renderSkillOverview(int windowX, int windowY){
		
		//Buttons
		for(int i = 0; i < 3; i++){
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(windowX + (i+1)*16 + i*(width-64)/3, windowY + 16);
			GL11.glVertex2f(windowX + (i+1)*16 + (i+1)*(width-64)/3, windowY + 16);
			GL11.glVertex2f(windowX + (i+1)*16 + (i+1)*(width-64)/3, windowY + 48);
			GL11.glVertex2f(windowX + (i+1)*16 + i*(width-64)/3, windowY + 48);
			GL11.glEnd();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
	
			String msg = "";
			if(i == 0) msg = "Neu";
			if(i == 1) msg = "Bearbeiten";
			if(i == 2) msg = "Löschen";
			Font.render(msg, windowX + (i+1)*16 + (i+1)*(width-64)/3 - (width-64)/6 - msg.length() * 4, windowY + 26);
		}

		for(int i = 0; i < 12; i++){
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glBegin(GL11.GL_QUADS);
			
			// Skill Icon
			GL11.glVertex2f(windowX + 16 , windowY + height - 16 - i*32 - i*4);
			GL11.glVertex2f(windowX + 48, windowY + height - 16 - i*32 - i*4);
			GL11.glVertex2f(windowX + 48, windowY + height - 48 - i*32 - i*4);
			GL11.glVertex2f(windowX + 16, windowY + height - 48 - i*32 - i*4);
			GL11.glEnd();
			
			// Skill Name
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(windowX + 48 + 8, windowY + height - 16 - i*32 - i*4);
			GL11.glVertex2f(windowX + width-16, windowY + height - 16 - i*32 - i*4);
			GL11.glVertex2f(windowX + width-16, windowY + height - 48 - i*32 - i*4);
			GL11.glVertex2f(windowX + 48 + 8, windowY + height - 48 - i*32 - i*4);
			GL11.glEnd();
			
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			if(i+1 <= Game.PLAYER.getSkillList().size()){
				GL11.glColor3f(1, 1, 1);
				Game.PLAYER.getSkillList().get(i).render(windowX + 16 , windowY + height - 48 - i*32 - i*4);
				Font.render(Game.PLAYER.getSkillList().get(i).getName(), windowX + 48 + 16, windowY + height - 48 - i*32 - i*4 + 12);
			}
		}
		
	}
	
	public static void processInput(int button, int mouseX, int mouseY){
		
		if(renderOverview) processOverviewInput(button, mouseX, mouseY);
		
	}
	
	private static void processOverviewInput(int button, int mouseX, int mouseY){
		int windowX = Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH/2-width/2;
		int windowY = Game.PLAYER.getCameraY() + Game.SCREEN_HEIGHT/2-height/2;
		
		if(button == 0){
			for(int i = 0; i < 12; i++){
				
				int itemX = windowX + 16;
				int itemY = windowY + height - 48 - i*32 - i*4;
				
				if(	Game.PLAYER.getCameraX()+mouseX > itemX
					&&Game.PLAYER.getCameraX()+mouseX < itemX + 32
					&&Game.PLAYER.getCameraY()+mouseY > itemY
					&&Game.PLAYER.getCameraY()+mouseY < itemY + 32
					&& i+1 <= Game.PLAYER.getSkillList().size()){
					
//					Game.UI.setDragSkill(Game.PLAYER.getSkillList().get(i));
//					Item item = Game.PLAYER.getSkillList().get(i);
//					Game.UI.clickedInventorySlot = true;
//					Game.UI.setDragItem(item);
					
					break;
				}
				
			}
		}
		
	}
}
