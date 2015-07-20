package rpg.game;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import rpg.game.player.Interface;
import rpg.game.player.Player;


public class Game {
	
	
	// on windows
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	public static int WORLD_WIDTH = 0;
	public static int WORLD_HEIGHT = 0;
	
	public static Player PLAYER;
	public static Interface UI;
	private Map map;
	public static int FPS;

	public static void main(String[] args) {
		Game launcher = new Game();
	}
	
	public Game(){
		init();
		run();
	}
	
	public void init(){
		try {
			
			// uncomment on windows
			
//			DisplayMode mode = new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT);
//			
//			 DisplayMode modes[] = Display.getAvailableDisplayModes();
//	            for (int i=0; i< modes.length; i++) {
//	               DisplayMode m = modes[i];
//	               int bpp = Display.getDisplayMode().getBitsPerPixel();
//	               if (m.getBitsPerPixel() == bpp
//	                ) {
//	                   if ( m.getWidth() <= 640 && m.getHeight() <= 480&&
//	                        m.getFrequency() <= 85)
//	                       mode = m;
//	                   if ( m.getWidth() == 640 && m.getHeight() == 480 &&
//	                        m.getFrequency() == 60)
//	                       break;
//	                   }
//	            }
//	                       
//	        Display.setDisplayMode(mode);
			
			Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
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

		map = new Map();
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
				frames = 0;
				updates = 0;
			}

		}
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
		
		map.update();
		PLAYER.update();
		UI.update();
	}
	
	public void render(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		map.renderBackground();
		PLAYER.render();
		map.renderEntities();
		map.renderObjects();
		UI.render();
		
		Display.update();
	}
	
}
