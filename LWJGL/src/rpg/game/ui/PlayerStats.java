package rpg.game.ui;

import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.Options;
import rpg.game.Sound;
import rpg.game.Stats;
import rpg.game.armor.Armor;
import rpg.game.player.Inventory;

public class PlayerStats {
	
	private static int dialogWidth = 400;
	private static int dialogHeight = 300;
	private static int armorOffsetX = 150;
	private static int accessoirOffestX = armorOffsetX + 200;
	private static boolean renderArmorInfo = false;
	private static Armor hoveredArmor = null;
	
	public static void render(){
		int dialogX = Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH/2 - dialogWidth/2;
		int dialogY = Game.PLAYER.getCameraY() + Game.SCREEN_HEIGHT/2 - dialogHeight/2;
		
		Stats stats = Game.PLAYER.getStats();
		
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

		Armor armor = null;
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(dialogX + armorOffsetX, dialogY + dialogHeight - i*4 - i*32);
		GL11.glVertex2f(dialogX + armorOffsetX + 32, dialogY + dialogHeight - i*4 - i*32);
		GL11.glVertex2f(dialogX + armorOffsetX + 32, dialogY + dialogHeight - i*4 - i*32 - 32);
		GL11.glVertex2f(dialogX + armorOffsetX, dialogY + dialogHeight - i*4 - i*32 - 32);
		GL11.glEnd();
		
		switch(i){
		case 1: armor = Game.PLAYER.getEquip().getHead();
		break;
		case 2: armor = Game.PLAYER.getEquip().getShoulder();
		break;
		case 3: armor = Game.PLAYER.getEquip().getBreast();
		break;
		case 4: armor = Game.PLAYER.getEquip().getGloves();
		break;
		case 5: armor = Game.PLAYER.getEquip().getBelt();
		break;
		case 6: armor = Game.PLAYER.getEquip().getPants();
		break;
		case 7: armor = Game.PLAYER.getEquip().getShoes();
		break;
		}
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(1, 1, 1);
		if(armor != null){
			armor.render(dialogX + armorOffsetX, dialogY + dialogHeight - (i+1)*4 - (i+1)*32);
		}
		
		}
		
		// ACCESSOIR
		for(int i = 1; i <= 5; i++){
			GL11.glDisable(GL11.GL_TEXTURE_2D);
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
		
		if(hoveredArmor != null) ArmorInfo.render(hoveredArmor);
	
	}
	
	public static void processInput(int button, int mouseX, int mouseY){
		int size = 32;
		int index = -1;
		
		int dialogX = Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH/2 - dialogWidth/2;
		int dialogY = Game.PLAYER.getCameraY() + Game.SCREEN_HEIGHT/2 - dialogHeight/2;
		Armor armor = null;

		// ARMOR
		for(int i = 1; i <= 7; i++){

			
			int itemX = dialogX + armorOffsetX;
			int itemY = dialogY + dialogHeight - (i+1)*4 - (i+1)*32;
			
			if(	Game.PLAYER.getCameraX()+mouseX > itemX
					&&Game.PLAYER.getCameraX()+mouseX < itemX + size
					&&Game.PLAYER.getCameraY()+mouseY > itemY
					&&Game.PLAYER.getCameraY()+mouseY < itemY + size){

				switch(i){
				case 1: armor = Game.PLAYER.getEquip().getHead();
				break;
				case 2: armor = Game.PLAYER.getEquip().getShoulder();
				break;
				case 3: armor = Game.PLAYER.getEquip().getBreast();
				break;
				case 4: armor = Game.PLAYER.getEquip().getGloves();
				break;
				case 5: armor = Game.PLAYER.getEquip().getBelt();
				break;
				case 6: armor = Game.PLAYER.getEquip().getPants();
				break;
				case 7: armor = Game.PLAYER.getEquip().getShoes();
				break;
				}
				
				if(renderArmorInfo)hoveredArmor = armor;
				
				break;
				}
			hoveredArmor = null;
			
		}
				
		// ACCESSOIR
		for(int i = 1; i <= 5; i++){

			int itemX = dialogX + accessoirOffestX;
			int itemY = dialogY + dialogHeight - i*4 - i*32;
					
		
			if(	Game.PLAYER.getCameraX()+mouseX > itemX
					&&Game.PLAYER.getCameraX()+mouseX < itemX + size
					&&Game.PLAYER.getCameraY()+mouseY > itemY
					&&Game.PLAYER.getCameraY()+mouseY < itemY + size){

				switch(i){
				case 1: armor = Game.PLAYER.getEquip().getRingLeft();
				break;
				case 2: armor = Game.PLAYER.getEquip().getRingRight();
				break;
				case 3: armor = Game.PLAYER.getEquip().getNecklate();
				break;
				case 4: armor = Game.PLAYER.getEquip().getRingLeft();
				break;
				case 5: armor = Game.PLAYER.getEquip().getRingRight();
				break;
				}
				
				break;
				}
			
		}
		
		// MouseButton: -1 = nothing, 0 = left, 1 = right
		if(armor != null){
			if(button == 0){

			}else if(button == 1){
				if(Inventory.hasSpace()){
					Inventory.add(armor);
					Game.PLAYER.getEquip().remove(armor);
					Sound.equip.playAsSoundEffect(1, Options.FXVolume, false);
				}
			}else if(button == -1){
				renderArmorInfo = true;
//				itemName = item.getName();
			}
		}else{
			if(button == 0){
				// DRAG ONTO FREE SPACE
			}
			renderArmorInfo = false;
		}
		
	}

}
