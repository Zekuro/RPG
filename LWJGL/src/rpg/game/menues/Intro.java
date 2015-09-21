package rpg.game.menues;

import org.lwjgl.opengl.GL11;

import rpg.game.Game;
import rpg.game.Game.GameState;

public class Intro {

	
	private static int delta = 0;
	private static int y = Game.SCREEN_HEIGHT + 390;
	private static int radius = 100;
	private static float alpha = 1f;
	
	public static void update()
	{
		if(Game.logo.getY() > Game.SCREEN_HEIGHT/2 - 210){
			Game.logo.setPosition(Game.logo.getX(), Game.logo.getY() - 3);
			y -= 3;
			delta++;
		}
		
		if(Game.logo.getY() <= Game.SCREEN_HEIGHT/2 - 210){
			radius += 5;
		}

		if(radius >= 1000) Game.setState(GameState.MAINMENU);
		
	}
	public static void render(){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		drawCircle(Game.SCREEN_WIDTH/2, y, radius);
		 
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Game.logo.render();
	}
	
	private static void drawCircle(double x, double y, double radius){
			//filled circle
				double x1,y1,x2,y2;
				double angle;
				 
				GL11.glColor3f(0.7f, 0.7f, 0.7f);
				GL11.glBegin(GL11.GL_TRIANGLE_FAN);
				GL11.glVertex2d(x,y);
				 
				for (angle=1.0f;angle<361.0f;angle+=0.2)
				{
				    x2 = x+Math.sin(angle)*radius;
				    y2 = y+Math.cos(angle)*radius;
				    GL11.glVertex2d(x2,y2);
				}
				GL11.glEnd();
	}
	
}
