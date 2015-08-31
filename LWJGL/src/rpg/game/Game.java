package rpg.game;

import java.util.Properties;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import rpg.game.player.Player;
import rpg.game.ui.Interface;
import rpg.game.ui.MainMenu;
import rpg.game.ui.Map;


public class Game {
	
	// on windows 640 x 480 on linux 800 x 600
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	public static int WORLD_WIDTH = 0;
	public static int WORLD_HEIGHT = 0;
	
	public static Player PLAYER;
	public static Interface UI;
	public static MainMenu MAINMENU;
	private World world;
	public static int FPS;
	public static int UPS;
	public static int SECONDS = 0;
	
	public static GameState state;
	
	private static boolean paused = false;
	
	
	public static enum GameState{
		MENU, GAME, OPTIONS
	}
	
	public static void main(String[] args) {
		Game launcher = new Game();
	}
	
	public Game(){
		init();
		run();
	}
	
	public void init(){
		try {
			
		        
		    Properties prop = System.getProperties( );
		        
		     if(prop.getProperty("os.name").toLowerCase().contains("windows")){
		        
		     DisplayMode mode = new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT);
			
			 DisplayMode modes[] = Display.getAvailableDisplayModes();
	            for (int i=0; i< modes.length; i++) {
	               DisplayMode m = modes[i];
	               int bpp = Display.getDisplayMode().getBitsPerPixel();
	               if (m.getBitsPerPixel() == bpp
	                ) {
	                   if ( m.getWidth() <= SCREEN_WIDTH && m.getHeight() <= SCREEN_HEIGHT&&
	                        m.getFrequency() <= 85)
	                       mode = m;
	                   if ( m.getWidth() == SCREEN_WIDTH && m.getHeight() == SCREEN_HEIGHT&&
	                        m.getFrequency() == 60)
	                       break;
	                   }
	            }
	                       
	        Display.setDisplayMode(mode);
		     }else{
		    	 Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
		     }
		     
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		//GL11.glEnable(GL11.GL_TEXTURE_2D);               
		  
    	// enable alpha blending
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	Font.loadFonts();
    	
    	Sound.loadSounds();
    	
    	state = GameState.MENU;
    	MAINMENU = new MainMenu(this);
    	
    	render();
	}
	
	public void start(){
		state = GameState.GAME;
		world = new World();
		render();
    	world.loadFromImage();
		PLAYER = new Player(100,100);
		UI = new Interface();
	}

	public void run(){
		
		long lastTime = System.nanoTime();
		double nsPerUpdate = 1000000000D / 60D;

		int updates = 0;
		int frames = 0;

		long lastTimer = System.currentTimeMillis();
		double unprocessed = 0;

		while (!Display.isCloseRequested()) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerUpdate;
			lastTime = now;

			boolean shouldRender = true;

			while (unprocessed >= 1) {
				updates++;
				update();
				unprocessed -= 1;
				shouldRender = false;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer > 100) {
				lastTimer += 1000;
				FPS = frames;
				UPS = updates;
				SECONDS++;
				frames = 0;
				updates = 0;
			}

		}
		AL.destroy();
		Display.destroy();
	}
	
	public void update(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F11)){
			try {
				Display.setVSyncEnabled(!Display.isFullscreen());
				Display.setFullscreen(!Display.isFullscreen());
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
		}

		switch (state) {
		case OPTIONS:
			Options.update();
			break;
		case MENU:
			MAINMENU.update();
			break;

		case GAME:
			
			if(!world.isLoading() && !Sound.theme01.playing()){
				Sound.theme01.loop(1, Options.BGVolume);
			}
			
			if(!paused){
				world.update();
				PLAYER.update();
			}
			
			UI.update();
			break;
			
		default:
			break;
		}
		
	}
	
	// FIXME not showing correct on different resolution
	public void render(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		switch (state) {
		case OPTIONS:
			 Options.render();
			break;
		case MENU:
			MAINMENU.render();
			break;
		case GAME:
			if(world.isLoading()){
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glColor3f(0, 0, 0);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2i(0, 0);
				GL11.glVertex2i(SCREEN_WIDTH, 0);
				GL11.glVertex2i(SCREEN_WIDTH, SCREEN_HEIGHT);
				GL11.glVertex2i(0, SCREEN_HEIGHT);
				GL11.glEnd();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				
				String msg = "L O A D I N G . . .";
				Font.render(msg, SCREEN_WIDTH/2-msg.length()*16/2, SCREEN_HEIGHT/2,2);
			}else{
			
			if(!UI.isRenderingMap()){
				world.render(World.backgroundTiles);
//				world.renderBackground();
				world.renderEffects();
				PLAYER.render();
				world.renderEntities();
				world.renderObjects();
				UI.render();
			}else{
				Map.render(world);
			}
			
			}
			break;
		default:
			break;
		}
		
		
		Display.update();
	}
	
	public static void setPaused(boolean b){
		paused = b;
	}
	
	public static boolean isPaused(){
		return paused;
	}
	
	public static void setState(GameState t){
		state = t;
	}
	
}
