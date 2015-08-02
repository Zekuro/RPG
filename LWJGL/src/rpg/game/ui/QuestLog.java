package rpg.game.ui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.Quest;

public class QuestLog {

	private static final int maxSize = 20;
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
		
		
		// TODO adapt length of description (SCROLLABLE), rewards should be displayed
		
		// max 20 Zeilen = 35*20 zeichen
		if(selectedQuest != null){
			String description = selectedQuest.getDescription();
			
			for(int i = 0; i <= description.length() / 35; i++){
				String desc;
				
				if(i*35+35 <= description.length()){
					desc = description.substring(i*35, i*35+35);
				}else{
					desc = description.substring(i*35, description.length());
				}
				
				Font.render(desc, x + Game.SCREEN_WIDTH/2-2*32 + 8, y + Game.SCREEN_HEIGHT/2+5*32 - 16 - i* 16);

				// rendering 'next page'
				if(i > 18){
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					
					// CLICKBOX
					// TODO: add this click-box to process Input
//					GL11.glColor3f(1, 1, 1);
//					GL11.glBegin(GL11.GL_QUADS);
//					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38-36, y + Game.SCREEN_HEIGHT/2-5*35);
//					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38, y + Game.SCREEN_HEIGHT/2-5*35);
//					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38, y + Game.SCREEN_HEIGHT/2-5*35+18);
//					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38-36, y + Game.SCREEN_HEIGHT/2-5*35+18);
//					GL11.glEnd();

					GL11.glColor3f(0.3f, 0.3f, 0.3f);
					GL11.glBegin(GL11.GL_TRIANGLES);
					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38-24, y + Game.SCREEN_HEIGHT/2-5*35+2);
					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38-8, y + Game.SCREEN_HEIGHT/2-5*35+7);
					GL11.glVertex2f(x + Game.SCREEN_WIDTH/2+6*38-24, y + Game.SCREEN_HEIGHT/2-5*35+12);
					GL11.glEnd();
					GL11.glEnable(GL11.GL_TEXTURE_2D);

					
					break;
				}
			}
		
		}

		
	}
	
	public static void processInput(int button, int mouseX, int mouseY){
		int x = Game.PLAYER.getCameraX();
		int y = Game.PLAYER.getCameraY();
		
		if(button == 0){
		for (int i = 0; i < questLog.size(); i++) {

			if(	x + mouseX > x + Game.SCREEN_WIDTH/2-6*38 + 2
				&& x + mouseX < x + Game.SCREEN_WIDTH/2-2*38 - 2
				&& y + mouseY > y + Game.SCREEN_HEIGHT/2+5*32 - (i+1)*16
				&& y + mouseY < y + Game.SCREEN_HEIGHT/2+5*32 - (i+1)*16 + 14){
				selectedQuest = questLog.get(i);
			}
			
		}
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
