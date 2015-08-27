package rpg.game.ui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.Sound;

public class MainMenu {

	private int size = 3;
	
	private String[] options = { "Start Game","Resume","Options", "Exit"};
	private int selectedIndex = 0;
	
	private Game game;
	
	public MainMenu(Game game){
		this.game = game;
		
		 Sound.menuBackground.playAsMusic(1.0f, 1.0f, true);
	}
	
	public void update(){
		
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
					
					/*	0 -> Start Game
					 *  1 -> Resume (or load)
					 *  2 -> Options
					 *  3 -> Exit
					 */
					switch (selectedIndex) {
					case 0:
						Sound.menuClick.playAsSoundEffect(1.0f, 1.0f, false);
						Sound.menuBackground.stop();
						game.start();
						break;
					case 1:
						break;
					case 2:
						break;
					case 3:
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
	
	public void render(){
		
		// BLUE BACKGROUND
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.5f, 0.7f, 1f);
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
