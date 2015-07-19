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
	
	public static void renderColored(String msg, int x, int y, float r, float g, float b){
		for(int i = 0; i < msg.length(); i++){
			int charIndex = chars.indexOf(msg.charAt(i));
			
			if(charIndex >= 0){
				
				int charX = charIndex % 16;
				int charY = charIndex / 16;
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, resource.getTextureID());
				GL11.glColor3f(r, g, b);
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(charX * 0.0625f, (charY + 1) * 0.0625f);
				GL11.glVertex2f(x+i*8, y);
				GL11.glTexCoord2f((charX+1) * 0.0625f,(charY + 1) * 0.0625f);
				GL11.glVertex2f(x+8+i*8, y);
				GL11.glTexCoord2f((charX+1) * 0.0625f, charY * 0.0625f);
				GL11.glVertex2f(x+8+i*8, y+8);
				GL11.glTexCoord2f(charX * 0.0625f, charY * 0.0625f);
				GL11.glVertex2f(x+i*8, y+8);
			    GL11.glEnd();
			}
		}
	}
	
	public static void render(String msg, int x, int y){
		renderColored(msg, x, y, 0, 0, 0);
	}
	
	
}
