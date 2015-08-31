package rpg.game;

import java.io.IOException;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {

	public static Audio menuBackground;
	public static Audio menuClick;
	public static Audio equip;
	public static Audio item;
	public static Audio click;
	public static Audio levelUp;
	
	public static Music theme01;
	
	public static void loadSounds(){
		 try {
				menuBackground = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/background/MenuBackground.wav"));
				menuClick = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/effects/MenuClick.wav"));
				equip = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/effects/cloth.wav"));
				item = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/effects/ring.wav"));
				click = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/effects/click.wav"));
				levelUp = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/effects/levelUp.wav"));
				theme01 = new Music("res/sound/background/RedCurtain.wav");
				
			} catch (IOException | SlickException e) {
				e.printStackTrace();
			}
	}
	
}
