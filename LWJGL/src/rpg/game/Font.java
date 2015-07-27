package rpg.game;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Font {

	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,:;'\"!?$%()-=+/ ";
	
	private static Texture resource;
	private static Texture texture;
	
	public static void loadFonts(){
		try {
			 resource = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/fonts.png"));
			} catch (IOException e) {
				e.printStackTrace();
		} 
		
		try {
			 texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/background/sand.png"));
			} catch (IOException e) {
				e.printStackTrace();
		} 
	}
	
	public static void renderColored(String msg, int x, int y, int scale, float r, float g, float b){
		int size = 8;
		
		if(size > 0) size = size*scale;
		
		for(int i = 0; i < msg.length(); i++){
			int charIndex = chars.indexOf(msg.charAt(i));
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			if(charIndex >= 0){
				
				int charX = charIndex % 16;
				int charY = charIndex / 16;
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, resource.getTextureID());
				GL11.glColor3f(r, g, b);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(charX * 0.0625f, (charY + 1) * 0.0625f);
				GL11.glVertex2f(x+i*size, y);
				GL11.glTexCoord2f((charX+1) * 0.0625f,(charY + 1) * 0.0625f);
				GL11.glVertex2f(x+size+i*size, y);
				GL11.glTexCoord2f((charX+1) * 0.0625f, charY * 0.0625f);
				GL11.glVertex2f(x+size+i*size, y+size);
				GL11.glTexCoord2f(charX * 0.0625f, charY * 0.0625f);
				GL11.glVertex2f(x+i*size, y+size);
			    GL11.glEnd();
			}
		}
	}
	
	public static void render(String msg, int x, int y){
		render(msg, x, y, 1);
	}
	
	public static void render(String msg, int x, int y, int size){
		renderColored(msg, x, y, size, 1, 1, 1);
	}
	
	
}
