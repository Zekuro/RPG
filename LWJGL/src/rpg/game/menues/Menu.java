package rpg.game.menues;

import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.Game.GameState;
import rpg.game.Sound;

public class Menu {

	private static int size = 3;
	
	private static String[] options = { "Resume","Save Game", "Load Game" ,"Options", "Exit"};
	private static int selectedIndex = 0;
	
	public static void update(){
		
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_W || Keyboard.getEventKey() == Keyboard.KEY_UP) {
					Sound.menuClick.playAsSoundEffect(1.0f, 1.0f, false);
					if(selectedIndex - 1 >= 0) selectedIndex--;
				}
				
				if(Keyboard.getEventKey() == Keyboard.KEY_S || Keyboard.getEventKey() == Keyboard.KEY_DOWN){
					Sound.menuClick.playAsSoundEffect(1.0f, 1.0f, false);
					if(selectedIndex + 1 <= options.length-1) selectedIndex++;
				}
				
				if(Keyboard.getEventKey() == Keyboard.KEY_RETURN){
					
					/*	0 -> Resume
					 *  1 -> Save
					 *  2 -> Load
					 *  3 -> Options
					 *  4 -> Exit
					 */
					switch (selectedIndex) {
					case 0:
						Game.setState(GameState.GAME);
						Sound.menuClick.playAsSoundEffect(1.0f, 1.0f, false);
						break;
					case 1:
						break;
					case 2:
						break;
					case 3:
						Options.previousState = GameState.MENU;
						Game.setState(GameState.OPTIONS);
						break;
					case 4:
						AL.destroy();
						System.exit(0);
						break;
						
					default:
						break;
					}
					
					
				}
			}
		}
		SoundStore.get().poll(0);
	}
	
	public static void render(){
		
		// BLUE BACKGROUND
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.4f, 0.4f, 0.4f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(0 , 0);
		GL11.glVertex2f(Game.SCREEN_WIDTH, 0);
		GL11.glVertex2f(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		GL11.glVertex2f(0, Game.SCREEN_HEIGHT);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	
		for(int i = 0; i <= options.length-1; i++){
		
			if(i == selectedIndex){
				Font.renderColored(options[i], Game.SCREEN_WIDTH/2 - size*8*options[i].length()/2, Game.SCREEN_HEIGHT/2 - options.length * 8 * (size+1) + (options.length-i) * 8 * (size+1), size,0,0,0);
			}else{
				Font.render(options[i], Game.SCREEN_WIDTH/2 - size*8*options[i].length()/2, Game.SCREEN_HEIGHT/2 - options.length * 8 * (size+1) + (options.length-i) * 8 * (size+1), size);
			}
		}
		
	}
	
}
