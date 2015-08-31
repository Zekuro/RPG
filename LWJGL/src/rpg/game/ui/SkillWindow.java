package rpg.game.ui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.Sound;
import rpg.game.items.Item.Tier;
import rpg.game.player.ActionBar;
import rpg.game.skills.Skill;
import rpg.game.skills.Skill.SkillEffect;
import rpg.game.skills.Skill.SkillType;

public class SkillWindow {

	private static final int width = 400;
	private static final int height = 500;
	
	private static Skill selectedSkill = null;
	private static ArrayList<SkillEffect> effects = new ArrayList<>();
	
	private static Skill selectedOriginSkill = null;
	
	public static boolean renderOverview = true;
	public static boolean renderCreateSkill = false;
	public static boolean renderEditSkill = false;
	
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
		if(renderEditSkill || renderCreateSkill) renderEditSkill(windowX, windowY);
		
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
	
private static void renderEditSkill(int windowX, int windowY){
		
		renderElements(windowX, windowY);
		renderTypes(windowX, windowY);
		renderEffects(windowX, windowY);
		
		//Bottom Buttons
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
			if(i == 0) msg = "Übernehmen";
			if(i == 1) msg = "Abbrechen";
			Font.render(msg, windowX + (i+1)*16 + (i+1)*(width-48)/2 - (width-48)/4 - msg.length() * 4, windowY + 26);
		}
	}
	
	public static void processInput(int button, int mouseX, int mouseY){
		if(renderOverview){
			processOverviewInput(button, mouseX, mouseY);
			if(Mouse.isButtonDown(0)) return;
		}
		if(renderCreateSkill || renderEditSkill) processEditSkillInput(button, mouseX, mouseY);
	}
	
	private static void processOverviewInput(int button, int mouseX, int mouseY){
		int windowX = Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH/2-width/2;
		int windowY = Game.PLAYER.getCameraY() + Game.SCREEN_HEIGHT/2-height/2;
		
		// put skill into actionbar
		if(Mouse.getEventButtonState() && Mouse.getEventButton() == 0){
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
							renderCreateSkill = true;
							renderOverview = false;
							selectedSkill = null;
							break;
						case 1:
							if(selectedSkill != null){
								selectedOriginSkill = new Skill(selectedSkill.getName(), selectedSkill.getTier(), selectedSkill.getType());
								for (SkillEffect effect : selectedSkill.getSkillEffects()) {
									selectedOriginSkill.addEffect(effect);
								}
								
								renderOverview = false;
								renderEditSkill = true;
							}
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
	
	private static void processEditSkillInput(int button, int mouseX, int mouseY){
		int windowX = Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH/2-width/2;
		int windowY = Game.PLAYER.getCameraY() + Game.SCREEN_HEIGHT/2-height/2;
		// if selectedSkill != null then just get informations about it and save it when accepting changes
		
		if(selectedSkill == null){
			selectedSkill = new Skill("NEW SKILL", Tier.T1, SkillType.PROJECTILE);
		}
		
		
		if(Mouse.getEventButtonState() && Mouse.getEventButton() == 0){
			processTypes(mouseX, mouseY, windowX, windowY);
			processEffects(mouseX, mouseY, windowX, windowY);
			
			//BOTTOM (0 OK . 1 Cancel)Buttons
			for(int i = 0; i < 2; i++){
				if(	Game.PLAYER.getCameraX()+mouseX > windowX + (i+1)*16 + i*(width-48)/2
						&&Game.PLAYER.getCameraX()+mouseX < windowX + (i+1)*16 + (i+1)*(width-48)/2
						&&Game.PLAYER.getCameraY()+mouseY > windowY + 16
						&&Game.PLAYER.getCameraY()+mouseY < windowY + 48){
				
					switch (i) {
					case 0:
						// TODO check costs
						// TODO make name based of effects? or let user make a name
						if(renderCreateSkill)Game.PLAYER.getSkillList().add(selectedSkill);
						renderCreateSkill = false;
						renderEditSkill = false;
						renderOverview = true;
						break;
					case 1:
						
						if(renderEditSkill){
							int ind = Game.PLAYER.getSkillList().indexOf(selectedSkill);
							
							ArrayList<Skill> skillList = new ArrayList<>();
							
							for(int l = 0; l < Game.PLAYER.getSkillList().size(); l++){
								
								if(ind == l){
									skillList.add(selectedOriginSkill);
								}else{
									skillList.add(Game.PLAYER.getSkillList().get(l));
								}
								
							}
							
							Game.PLAYER.setSkillList(skillList);
						}
						
						selectedSkill = null;
						renderCreateSkill = false;
						renderEditSkill = false;
						renderOverview = true;
						
						break;
					}
				}
				
			}
		
		}
	}
	
	
	private static void renderElements(int windowX, int windowY){
		Font.render("Element", windowX + 16, windowY + height - 24);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		for(int i = 0; i < 3; i++){
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(windowX + 16 + i*32 + i*4, windowY + height - 26);
			GL11.glVertex2f(windowX + 48 + i*32 + i*4, windowY + height - 26);
			GL11.glVertex2f(windowX + 48 + i*32 + i*4, windowY + height - 58);
			GL11.glVertex2f(windowX + 16 + i*32 + i*4, windowY + height - 58);
			GL11.glEnd();
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	
	private static void renderTypes(int windowX, int windowY){
		Font.render("Typ", windowX + 16, windowY + height - 24 - 50);
		for(int i = 0; i < 4; i++){
			
			String texturePath = "/res/skills/";
			SkillType type = null;
			
			switch (i) {
			case 0:
				// PROJECTILE
				type = SkillType.PROJECTILE;
				texturePath += "S_Fire05.png";
				break;
			case 1:
				// LASER
				type = SkillType.LASER;
				texturePath += "S_Fire01.png";
				break;
			case 2:
				// IMPACT
				type = SkillType.IMPACT;
				texturePath += "S_Earth04.png";
				break;
			case 3:
				//AURA
				type = SkillType.AURA;
				texturePath += "S_Buff05.png";
				break;
			}
			
			Texture texture = null;
			try {
				texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(texturePath));
				} catch (IOException e) {
					e.printStackTrace();
			}  
			texture.bind();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			if(selectedSkill != null && selectedSkill.getType() == type){
				GL11.glColor3f(0.4f, 0.8f, 0.4f);
			}else{
				GL11.glColor3f(0.5f, 0.5f, 0.5f);
			}
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(windowX + 16 + i*32 + i*4, windowY + height - 76);
			GL11.glVertex2f(windowX + 48 + i*32 + i*4, windowY + height - 76);
			GL11.glVertex2f(windowX + 48 + i*32 + i*4, windowY + height - 108);
			GL11.glVertex2f(windowX + 16 + i*32 + i*4, windowY + height - 108);
			GL11.glEnd();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			GL11.glColor3f(1, 1, 1);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(windowX + 16 + i*32 + i*4, windowY + height - 76);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(windowX + 48 + i*32 + i*4, windowY + height - 76);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(windowX + 48 + i*32 + i*4, windowY + height - 108);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(windowX + 16 + i*32 + i*4, windowY + height - 108);
			GL11.glEnd();
		}

	}
	
	
	private static void renderEffects(int windowX, int windowY){
		Font.render("Effekt", windowX + 16, windowY + height - 24 - 100);
		for(int i = 0; i < 4; i++){
			GL11.glDisable(GL11.GL_TEXTURE_2D);
				// CHECKBOX OUTER RING
				GL11.glColor3f(0.5f, 0.5f, 0.5f);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(windowX + 16, windowY + height - 126 - i* 36);
				GL11.glVertex2f(windowX + 48, windowY + height - 126 - i* 36);
				GL11.glVertex2f(windowX + 48, windowY + height - 158 - i* 36);
				GL11.glVertex2f(windowX + 16, windowY + height - 158 - i* 36);
				GL11.glEnd();
				
				// CHECKBOX INSIDE DARK
				GL11.glColor3f(0.3f, 0.3f, 0.3f);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(windowX + 18, windowY + height - 128 - i* 36);
				GL11.glVertex2f(windowX + 46, windowY + height - 128 - i* 36);
				GL11.glVertex2f(windowX + 46, windowY + height - 156 - i* 36);
				GL11.glVertex2f(windowX + 18, windowY + height - 156 - i* 36);
				GL11.glEnd();
				
				SkillEffect effect= null;
				switch (i) {
				case 0:
					effect = SkillEffect.DOUBLE;
					break;
				case 1:
					effect = SkillEffect.SPREAD;
					break;
				case 2:
					effect = SkillEffect.RANGE;
					break;
				case 3:
					effect = SkillEffect.SIZE;
					break;
				}
				
				
				// CHECKBOX IF CLICKED
				if(selectedSkill != null && selectedSkill.hasEffect(effect)){
				GL11.glColor3f(0.4f, 0.8f, 0.4f);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(windowX + 22, windowY + height - 132 - i* 36);
				GL11.glVertex2f(windowX + 42, windowY + height - 132 - i* 36);
				GL11.glVertex2f(windowX + 42, windowY + height - 152 - i* 36);
				GL11.glVertex2f(windowX + 22, windowY + height - 152 - i* 36);
				GL11.glEnd();
				}
				// ICON
				GL11.glColor3f(0.5f, 0.5f, 0.5f);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(windowX + 16 + 36, windowY + height - 126 - i* 36);
				GL11.glVertex2f(windowX + 48 + 36, windowY + height - 126 - i* 36);
				GL11.glVertex2f(windowX + 48 + 36, windowY + height - 158 - i* 36);
				GL11.glVertex2f(windowX + 16 + 36, windowY + height - 158 - i* 36);
				GL11.glEnd();
				
				// NAME
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(windowX + 16 + 72, windowY + height - 126 - i* 36);
				GL11.glVertex2f(windowX + width - 16 - 36, windowY + height - 126 - i* 36);
				GL11.glVertex2f(windowX + width - 16 - 36, windowY + height - 158 - i* 36);
				GL11.glVertex2f(windowX + 16 + 72, windowY + height - 158 - i* 36);
				GL11.glEnd();
				
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				
				String msg = "";
				
				switch (i) {
				case 0:
					msg = "Dopplung";
					break;
				case 1:
					msg = "Streuung";
					break;
				case 2:
					msg = "Reichweite";
					break;
				case 3:
					msg = "Grösse";
					break;
				default:
					msg = "FEHLER 01";
					break;
				}
				
				Font.render(msg, windowX + 24 + 72, windowY + height - 148 - i* 36);
		}
	}
	
	/**
	 * Requires Pressed Button
	 * @param mouseX
	 * @param mouseY
	 * @param windowX
	 * @param windowY
	 */
	private static void processTypes(int mouseX, int mouseY, int windowX, int windowY){
		
		for(int i = 0; i < 4; i++){
			if(	Game.PLAYER.getCameraX()+mouseX > windowX + 16 + i*32 + i*4
					&&Game.PLAYER.getCameraX()+mouseX < windowX + 48 + i*32 + i*4
					&&Game.PLAYER.getCameraY()+mouseY > windowY + height - 108
					&&Game.PLAYER.getCameraY()+mouseY < windowY + height - 76){

				effects = selectedSkill.getSkillEffects();
				
				switch (i) {
				case 0:
					// PROJECTILE
					selectedSkill = new Skill("NEW SKILL", Tier.T1, SkillType.PROJECTILE);
					break;
				case 1:
					// LASER
					selectedSkill = new Skill("NEW SKILL", Tier.T1, SkillType.LASER);
					break;
				case 2:
					// IMPACT
					selectedSkill = new Skill("NEW SKILL", Tier.T1, SkillType.IMPACT);
					break;
				case 3:
					//AURA
					selectedSkill = new Skill("NEW SKILL", Tier.T1, SkillType.AURA);
					break;
				}
			
				for (SkillEffect effect : effects) {
					selectedSkill.addEffect(effect);
				}
			
			}
		}

	}

	
	/**
	 * Requires Pressed Button
	 * @param mouseX
	 * @param mouseY
	 * @param windowX
	 * @param windowY
	 */
	// TODO skillCosts
	private static void processEffects(int mouseX, int mouseY, int windowX, int windowY){
		
		for(int i = 0; i < 4; i++){
			if(	Game.PLAYER.getCameraX()+mouseX > windowX + 18
					&&Game.PLAYER.getCameraX()+mouseX < windowX + 46
					&&Game.PLAYER.getCameraY()+mouseY > windowY + height - 156 - i* 36
					&&Game.PLAYER.getCameraY()+mouseY < windowY + height - 128 - i* 36){

				SkillEffect effect = null;
				
				switch (i) {
				case 0:
					effect = SkillEffect.DOUBLE;
					break;
				case 1:
					effect = SkillEffect.SPREAD;
					break;
				case 2:
					effect = SkillEffect.RANGE;
					break;
				case 3:
					effect = SkillEffect.SIZE;
					break;
				}
			
				if(selectedSkill.hasEffect(effect)){
					selectedSkill.removeEffect(effect);
				}else{
					selectedSkill.addEffect(effect);
				}
				
			}
		}

	}
	
	
}
