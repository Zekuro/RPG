package rpg.game.ui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.Sound;
import rpg.game.player.ActionBar;
import rpg.game.skills.Skill;

public class SkillWindow {

	private static final int width = 400;
	private static final int height = 500;
	
	private static Skill selectedSkill = null;
	
	public static boolean renderOverview = true;
	private static boolean renderNewSkill = false;
	private static boolean renderEditSkill = false;
	
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
		if(renderNewSkill) renderCreateSkill(windowX, windowY);
		
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
			if(i+1 <= Game.PLAYER.getSkillList().size() && selectedSkill != null && selectedSkill.equals(Game.PLAYER.getSkillList().get(i))){
				GL11.glColor3f(0.7f, 0.7f, 0.7f);
			}else{
				GL11.glColor3f(0.5f, 0.5f, 0.5f);
			}
			
			
			// Skill Icon
			GL11.glBegin(GL11.GL_QUADS);
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
	
	private static void renderCreateSkill(int windowX, int windowY){
		
		Font.render("Element", windowX + 16, windowY + height - 24);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		for(int i = 0; i < 3; i++){
		// Elements
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(windowX + 16 + i*32 + i*4, windowY + height - 26);
			GL11.glVertex2f(windowX + 48 + i*32 + i*4, windowY + height - 26);
			GL11.glVertex2f(windowX + 48 + i*32 + i*4, windowY + height - 58);
			GL11.glVertex2f(windowX + 16 + i*32 + i*4, windowY + height - 58);
			GL11.glEnd();
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		
		Font.render("Typ", windowX + 16, windowY + height - 74);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		for(int i = 0; i < 4; i++){
		// Types
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(windowX + 16 + i*32 + i*4, windowY + height - 76);
			GL11.glVertex2f(windowX + 48 + i*32 + i*4, windowY + height - 76);
			GL11.glVertex2f(windowX + 48 + i*32 + i*4, windowY + height - 108);
			GL11.glVertex2f(windowX + 16 + i*32 + i*4, windowY + height - 108);
			GL11.glEnd();
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		//Buttons
		for(int i = 0; i < 2; i++){
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(windowX + (i+1)*16 + i*(width-48)/2, windowY + 16);
			GL11.glVertex2f(windowX + (i+1)*16 + (i+1)*(width-48)/2, windowY + 16);
			GL11.glVertex2f(windowX + (i+1)*16 + (i+1)*(width-48)/2, windowY + 48);
			GL11.glVertex2f(windowX + (i+1)*16 + i*(width-48)/2, windowY + 48);
			GL11.glEnd();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
	
			String msg = "";
			if(i == 0) msg = "Erstellen";
			if(i == 1) msg = "Abbrechen";
			Font.render(msg, windowX + (i+1)*16 + (i+1)*(width-48)/2 - (width-48)/4 - msg.length() * 4, windowY + 26);
		}
	}
	
	public static void processInput(int button, int mouseX, int mouseY){
		
		if(renderOverview) processOverviewInput(button, mouseX, mouseY);
//		if(renderNewSkill) processCreateSkillInput(button, mouseX, mouseY);
	}
	
	private static void processOverviewInput(int button, int mouseX, int mouseY){
		int windowX = Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH/2-width/2;
		int windowY = Game.PLAYER.getCameraY() + Game.SCREEN_HEIGHT/2-height/2;
		
		// put skill into actionbar
		if(button == 0){
			for(int i = 0; i < 12; i++){
				
				int itemY = windowY + height - 48 - i*32 - i*4;
				
				if(	Game.PLAYER.getCameraX()+mouseX > windowX + 16
					&&Game.PLAYER.getCameraX()+mouseX < windowX + width - 16
					&&Game.PLAYER.getCameraY()+mouseY > itemY
					&&Game.PLAYER.getCameraY()+mouseY < itemY + 32
					&& i+1 <= Game.PLAYER.getSkillList().size()){
					
					if(Keyboard.getEventKeyState()){
					
						int[] keys = {Keyboard.KEY_1,Keyboard.KEY_2,Keyboard.KEY_3,Keyboard.KEY_4,Keyboard.KEY_5,Keyboard.KEY_6,Keyboard.KEY_7,Keyboard.KEY_8,Keyboard.KEY_9};
						
						for(int j=0; j < keys.length; j++){
							
							if(Keyboard.getEventKey() == keys[j]){
								ActionBar.actionBar[j] = Game.PLAYER.getSkillList().get(i);
								Sound.item.playAsSoundEffect(1, 0.1f, false);
								return;
							}
							
						}
					}
					selectedSkill = Game.PLAYER.getSkillList().get(i);
					
					break;
				}
				
			}
		
		
		//Buttons NEW / EDIT / DELETE
				for(int i = 0; i < 3; i++){
					
					if(	Game.PLAYER.getCameraX()+mouseX > windowX + (i+1)*16 + i*(width-64)/3
							&&Game.PLAYER.getCameraX()+mouseX < windowX + (i+1)*16 + (i+1)*(width-64)/3
							&&Game.PLAYER.getCameraY()+mouseY > windowY + 16
							&&Game.PLAYER.getCameraY()+mouseY < windowY + 48){
						
						switch (i) {
						case 0:
							renderOverview = false;
							renderNewSkill = true;
							selectedSkill = null;
							break;
						case 1:
							renderOverview = false;
							renderEditSkill = true;
							break;
						case 2:
							
							if(selectedSkill != null){
								
								for(int j = 0; j < ActionBar.actionBar.length; j++){
									if(ActionBar.actionBar[j] != null && selectedSkill == ActionBar.actionBar[j]) ActionBar.actionBar[j] = null;
								
								}
								Game.PLAYER.getSkillList().remove(selectedSkill);
								selectedSkill = null;
							}
							
							break;
							
						}
						
						break;
					}
			
				}
		}
	}
	
	private static void processCreateSkillInput(int button, int mouseX, int mouseY){
		int windowX = Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH/2-width/2;
		int windowY = Game.PLAYER.getCameraY() + Game.SCREEN_HEIGHT/2-height/2;
		
		if(button == 0){
			//Buttons
			for(int i = 0; i < 2; i++){
				if(	Game.PLAYER.getCameraX()+mouseX > windowX + (i+1)*16 + i*(width-48)/2
						&&Game.PLAYER.getCameraX()+mouseX < windowX + (i+1)*16 + (i+1)*(width-48)/2
						&&Game.PLAYER.getCameraY()+mouseY > windowY + 16
						&&Game.PLAYER.getCameraY()+mouseY < windowY + 48){
				}
		
				switch (i) {
				case 0:
					
					break;
				case 1:
					renderNewSkill = false;
					renderOverview = false;
					break;
				}
				
			}
		
		}
	}
}
