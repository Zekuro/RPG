package rpg.game.ui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.Quest;
import rpg.game.items.Item;

public class QuestLog {

	private static final int maxSize = 20;
	private static int descriptionOffest = 0;
	private static ArrayList<Quest> questLog = new ArrayList();
	private static Quest selectedQuest = null;
	
	public static void render(int x, int y){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-6*40-2, y + Game.SCREEN_HEIGHT/2-5*36-2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*40+2, y + Game.SCREEN_HEIGHT/2-5*36-2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*40+2, y + Game.SCREEN_HEIGHT/2+5*36+2);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-6*40-2, y + Game.SCREEN_HEIGHT/2+5*36+2);
		GL11.glEnd();
		
		GL11.glColor3f(0.3f, 0.3f, 0.3f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-6*40, y + Game.SCREEN_HEIGHT/2-5*36);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*40, y + Game.SCREEN_HEIGHT/2-5*36);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*40, y + Game.SCREEN_HEIGHT/2+5*36);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-6*40, y + Game.SCREEN_HEIGHT/2+5*36);
		GL11.glEnd();
		
		// LEFT BAR
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-6*38, y + Game.SCREEN_HEIGHT/2-5*35);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-2*38, y + Game.SCREEN_HEIGHT/2-5*35);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-2*38, y + Game.SCREEN_HEIGHT/2+5*32);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-6*38, y + Game.SCREEN_HEIGHT/2+5*32);
		GL11.glEnd();
		
		// RIGHT BAR
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-2*32, y + Game.SCREEN_HEIGHT/2-5*35);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38, y + Game.SCREEN_HEIGHT/2-5*35);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38, y + Game.SCREEN_HEIGHT/2+5*32);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-2*32, y + Game.SCREEN_HEIGHT/2+5*32);
		GL11.glEnd();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		String msg = "- Q U E S T L O G -";
		Font.render(msg,x + Game.SCREEN_WIDTH/2 - msg.length()*4, y + Game.SCREEN_HEIGHT/2+5*36-16);
		
		// TODO make me look better
		for (int i = 0; i < questLog.size(); i++) {
		
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			if(selectedQuest != null && selectedQuest == questLog.get(i)){
				GL11.glColor3f(1f, 1f, 1f);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-6*38, y + Game.SCREEN_HEIGHT/2+5*32 - (i+1)*16-2);
				GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-2*38, y + Game.SCREEN_HEIGHT/2+5*32 - (i+1)*16-2);
				GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-2*38, y + Game.SCREEN_HEIGHT/2+5*32 - (i+1)*16 + 16);
				GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-6*38, y + Game.SCREEN_HEIGHT/2+5*32 - (i+1)*16 + 16);
				GL11.glEnd();
			}
			GL11.glColor3f(0.3f, 0.3f, 0.3f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-6*38 + 2, y + Game.SCREEN_HEIGHT/2+5*32 - (i+1)*16);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-2*38 - 2, y + Game.SCREEN_HEIGHT/2+5*32 - (i+1)*16);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-2*38 - 2, y + Game.SCREEN_HEIGHT/2+5*32 - (i+1)*16 + 14);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-6*38 + 2, y + Game.SCREEN_HEIGHT/2+5*32 - (i+1)*16 + 14);
			GL11.glEnd();

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			String title = questLog.get(i).getTitle();
			Font.render(title, x + Game.SCREEN_WIDTH/2-6*38 + 2, y + Game.SCREEN_HEIGHT/2+5*32 - (i+1)*16 + 2);
		}
		
		// max 20 lines = 35*20 letters
		if(selectedQuest != null){
			String description = selectedQuest.getDescription();

			int lines = 0;
			
			for(int i = 0; i <= description.length() / 35 - descriptionOffest*20; i++){
				String desc;
				int lineOffset = i + 20*descriptionOffest;
				
				if(lineOffset*35+35 <= description.length()){
					desc = description.substring(lineOffset*35, lineOffset*35+35);
				}else{
					desc = description.substring(lineOffset*35, description.length());
				}
				
				Font.renderColored(desc, x + Game.SCREEN_WIDTH/2-2*32 + 8, y + Game.SCREEN_HEIGHT/2+5*32 - 16 - i* 16, 1, 0.2f, 0.2f, 0.2f);

				// rendering 'previous Page'
				if(descriptionOffest > 0){
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glColor3f(0.3f, 0.3f, 0.3f);
					GL11.glBegin(GL11.GL_TRIANGLES);
					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-2*32 + 24, y + Game.SCREEN_HEIGHT/2-5*35+2);
					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-2*32 + 8, y + Game.SCREEN_HEIGHT/2-5*35+7);
					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2-2*32 + 24, y + Game.SCREEN_HEIGHT/2-5*35+12);
					GL11.glEnd();
					
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}

				// rendering 'next page'
				if(i + descriptionOffest > descriptionOffest+18){
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glColor3f(0.3f, 0.3f, 0.3f);
					GL11.glBegin(GL11.GL_TRIANGLES);
					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38-24, y + Game.SCREEN_HEIGHT/2-5*35+2);
					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38-8, y + Game.SCREEN_HEIGHT/2-5*35+7);
					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38-24, y + Game.SCREEN_HEIGHT/2-5*35+12);
					GL11.glEnd();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					break;
				}
			lines = i;
			}

			
			// render rewards
			if(lines <= 12 && selectedQuest.getReward() != null){
				// render on same page
				renderRewards(lines);
				
			}else if(lines > 12 && description.length() / 35 - (descriptionOffest+1)*20 <= 0){
				// render on next page
				
				// TODO: render rewards on next page!
				// rendering 'next page'
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glColor3f(0.3f, 0.3f, 0.3f);
					GL11.glBegin(GL11.GL_TRIANGLES);
					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38-24, y + Game.SCREEN_HEIGHT/2-5*35+2);
					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38-8, y + Game.SCREEN_HEIGHT/2-5*35+7);
					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38-24, y + Game.SCREEN_HEIGHT/2-5*35+12);
					GL11.glEnd();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			}
			
		}

		
	}
	
	public static void processInput(int button, int mouseX, int mouseY){
		int x = Game.PLAYER.getCameraX();
		int y = Game.PLAYER.getCameraY();
		
		if(button == 0){
		for (int i = 0; i < questLog.size(); i++) {

			// Left Box Select Quest
			if(	x + mouseX > x + Game.SCREEN_WIDTH/2-6*38 + 2
				&& x + mouseX < x + Game.SCREEN_WIDTH/2-2*38 - 2
				&& y + mouseY > y + Game.SCREEN_HEIGHT/2+5*32 - (i+1)*16
				&& y + mouseY < y + Game.SCREEN_HEIGHT/2+5*32 - (i+1)*16 + 14){
				selectedQuest = questLog.get(i);
				descriptionOffest = 0;
			}
			
			// Right Box Next Page
			if(	x + mouseX > x + Game.SCREEN_WIDTH/2+6*38-36
				&& x + mouseX < x + Game.SCREEN_WIDTH/2+6*38
				&& y + mouseY > y + Game.SCREEN_HEIGHT/2-5*35
				&& y + mouseY < y + Game.SCREEN_HEIGHT/2-5*35+18
				&& selectedQuest != null
				&& selectedQuest.getDescription().length() / 35 - descriptionOffest*20 > descriptionOffest*20){
				descriptionOffest++;
				break;
			}
			
			if(	x + mouseX > x + Game.SCREEN_WIDTH/2-2*32
					&& x + mouseX < x + Game.SCREEN_WIDTH/2-2*32+32
					&& y + mouseY > y + Game.SCREEN_HEIGHT/2-5*35
					&& y + mouseY < y + Game.SCREEN_HEIGHT/2-5*35+18
					&& selectedQuest != null
					&& descriptionOffest > 0){
				descriptionOffest--;
				break;
			}
			
		}
		}
	}
	
	private static void renderRewards(int lines){
		int x = Game.PLAYER.getCameraX();
		int y = Game.PLAYER.getCameraY();
		
		Item[] rewards = selectedQuest.getReward();
		
		lines++;
		Font.renderColored("Belohnungen:", x + Game.SCREEN_WIDTH/2-2*32 + 8, y + Game.SCREEN_HEIGHT/2+5*32 - 16 - lines* 16, 1, 0.2f, 0.2f, 0.2f);
		lines++;
		
		int itemX = x + Game.SCREEN_WIDTH/2-2*32 + 8;
		int itemY = y + Game.SCREEN_HEIGHT/2+5*32 - 48 - lines* 16;

		for(int k = 0; k < rewards.length; k++){

			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			
			GL11.glColor3f(0.7f, 0.7f, 0.7f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(itemX, itemY);
			GL11.glVertex2f(itemX + 32, itemY);
			GL11.glVertex2f(itemX + 32, itemY + 32);
			GL11.glVertex2f(itemX, itemY + 32);
			GL11.glEnd();

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			GL11.glColor3f(1, 1, 1);
			rewards[k].render(itemX, itemY);
			
			itemX += 40;
		}
	}
	
	public static void add(Quest q){
		if(!isFull()) questLog.add(q);
	}
	
	public static boolean isFull(){
		if(questLog.size() == maxSize) return true;
		return false;
	}
	
}
