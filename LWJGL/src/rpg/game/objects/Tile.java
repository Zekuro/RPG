package rpg.game.objects;
import java.awt.Image;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rpg.game.LevelColor;


public class Tile {

	private byte id;
	private int x;
	private int y;
	private int width;
	private int height;
	private LevelColor color;
	private Texture texture;
	
	public static final Tile[] tiles = new Tile[10];
	public static final Tile GRASS = new Tile(1,new LevelColor(0, 255, 0),"res/grass.png");
	public static final Tile STONE = new Tile(2,new LevelColor(200,200,200),"res/stone.png");
	public static final Tile WATER = new Tile(3,new LevelColor(0,0,255),"res/water.png");
	public static final Tile SAND = new Tile(4,new LevelColor(255,255,0),"res/sand.png");
	
	public Tile(int id, LevelColor color,String texturePath){
		this.id = (byte)id;
		this.color = color;
		if(tiles[id]!=null)throw new RuntimeException("Duplicate tile id on " + id);
		setTexture(texturePath); 
		tiles[id] = this;
		
	}
	
	public Tile(String texturePath){
		setTexture(texturePath); 
	}
	
	public void update(){
		
	}
	
	public void render(){
		Color.white.bind();
		texture.bind(); // or GL11.glBind(texture.getTextureID());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(x+width, y);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(x+width, y+height);
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(x, y+height);
		GL11.glEnd();
	}
	
	public void setTexture(String texturePath){
		try {
			 texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(texturePath));
			} catch (IOException e) {
				e.printStackTrace();
		}  
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public LevelColor getLevelColor(){
		return color;
	}
	
}
