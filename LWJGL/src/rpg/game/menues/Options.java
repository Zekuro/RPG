package rpg.game.menues;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.Game.GameState;

public class Options {

	public static float BGVolume = 0.3f;
	public static float FXVolume = 0.3f;
	
	public static GameState previousState = GameState.MAINMENU;
	
	public static void render(){
		
		// BG
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.4f, 0.4f, 0.4f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(0, 0);
		GL11.glVertex2d(Game.SCREEN_WIDTH, 0);
		GL11.glVertex2d(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		GL11.glVertex2d(0, Game.SCREEN_HEIGHT);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		// BGM
		Font.render("BGM", Game.SCREEN_WIDTH/8, Game.SCREEN_HEIGHT - 116,2);

		// BGM PANEL
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.2f, 0.2f, 0.2f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4, Game.SCREEN_HEIGHT - 108);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4*3, Game.SCREEN_HEIGHT - 108);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4*3, Game.SCREEN_HEIGHT - 104);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4, Game.SCREEN_HEIGHT - 104);
		GL11.glEnd();
		
		// BGM MOVEABLE PANEL
		GL11.glColor3f(0.7f, 0.7f, 0.7f);
		int bgWidth = (int) (Game.SCREEN_WIDTH/4*2*Options.BGVolume);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4 + bgWidth, Game.SCREEN_HEIGHT - 116);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4 + bgWidth + 8, Game.SCREEN_HEIGHT - 116);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4 + bgWidth + 8, Game.SCREEN_HEIGHT - 96);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4 + bgWidth, Game.SCREEN_HEIGHT - 96);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		
		// FXM
		Font.render("FXM", Game.SCREEN_WIDTH/8, Game.SCREEN_HEIGHT - 166,2);

		// FXM PANEL
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.2f, 0.2f, 0.2f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4, Game.SCREEN_HEIGHT - 158);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4*3, Game.SCREEN_HEIGHT - 158);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4*3, Game.SCREEN_HEIGHT - 154);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4, Game.SCREEN_HEIGHT - 154);
		GL11.glEnd();
		
		// FXM MOVEABLE PANEL
		GL11.glColor3f(0.7f, 0.7f, 0.7f);
		int fxWidth = (int) (Game.SCREEN_WIDTH/4*2*Options.FXVolume);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4 + fxWidth, Game.SCREEN_HEIGHT - 166);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4 + fxWidth + 8, Game.SCREEN_HEIGHT - 166);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4 + fxWidth + 8, Game.SCREEN_HEIGHT - 146);
		GL11.glVertex2d(Game.SCREEN_WIDTH/4 + fxWidth, Game.SCREEN_HEIGHT - 146);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}		
	
	public static void update(){
		
		while(Keyboard.next()){
			processKeyEvents();
		}
		
		while(Mouse.next()){
			processMouseEvents();
		}
	}

	private static void processMouseEvents(){

			if( Mouse.isButtonDown(0)){
				// FX VOLUME
				if(	Mouse.getX() > Game.SCREEN_WIDTH/4
						&& Mouse.getX() < Game.SCREEN_WIDTH/4*3
						&& Mouse.getY() > Game.SCREEN_HEIGHT - 166
						&& Mouse.getY() < Game.SCREEN_HEIGHT - 146){
					
						Options.FXVolume = (float) (Mouse.getX() - Game.SCREEN_WIDTH/4) / (Game.SCREEN_WIDTH/4*2);
				}
				
				// BG VOLUME
				if(	Mouse.getX() > Game.SCREEN_WIDTH/4
						&& Mouse.getX() < Game.SCREEN_WIDTH/4*3
						&& Mouse.getY() > Game.SCREEN_HEIGHT - 116
						&& Mouse.getY() < Game.SCREEN_HEIGHT - 96){
					
						Options.BGVolume = (float) (Mouse.getX() - Game.SCREEN_WIDTH/4) / (Game.SCREEN_WIDTH/4*2);
				}
				
			}
		
	}
	
	private static void processKeyEvents(){
		if(Keyboard.getEventKeyState()){
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
				Game.setState(previousState);
			}
		}
	}
}
