package rpg.game;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {

	public static Audio menuBackground;
	public static Audio menuClick;
	public static Audio equip;
	public static Audio item;
	public static Audio click;
	
	public static void loadSounds(){
		 try {
				menuBackground = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/MenuBackground.wav"));
				menuClick = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/MenuClick.wav"));
				equip = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/cloth.wav"));
				item = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/ring.wav"));
				click = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/click.wav"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
