package rpg.game.objects;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GameObject {

	private byte id;
	private int x;
	private int y;
	private int width;
	private int height;
	private Tile tile;
	private Texture texture;
	
	private boolean solid;
	
	public GameObject(boolean solid,int width, int height, Tile tile){
		this.width = width;
		this.height = height;
		this.tile = tile;
		this.solid = solid;
	}
	
	public GameObject(boolean solid,int width, int height, String texture){
		this.width = width;
		this.height = height;
		this.tile = tile;
		this.solid = solid;
		setTexture(texture);
	}
	
	public void update(){
		
	}
	
	public void render(){
		Color.white.bind();
		if(texture == null) texture = tile.getTexture();
		texture.bind(); // or GL11.glBind(texture.getTextureID());
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(x+texture.getImageWidth(), y);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(x+texture.getImageWidth(), y+texture.getImageHeight());
		GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(x, y+texture.getImageHeight());
		GL11.glEnd();
	}
	
	
	public void setBounds(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setTile(Tile t){
		this.tile = t;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public void setTexture(String texturePath){
		try {
			 texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(texturePath));
			} catch (IOException e) {
				e.printStackTrace();
		}  
	}
	
}
