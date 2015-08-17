package rpg.game.objects;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rpg.game.Game;

public class GameObject {

	private byte id;
	protected int x;
	protected int y;
	protected int colOffsetX;
	protected int colOffsetY;
	protected int width;
	protected int height;
	private Tile tile;
	protected Texture texture;
	
	protected String name;
	
	private boolean isBackground;
	private boolean solid;
	
	public GameObject(boolean solid, boolean background,int width, int height, Tile tile){
		this.width = width;
		this.height = height;
		this.tile = tile;
		this.solid = solid;
		this.isBackground = background;
	}
	
	public GameObject(boolean solid, boolean background,int width, int height, String texture){
		this.width = width;
		this.height = height;
		this.tile = tile;
		this.solid = solid;
		this.isBackground = background;
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
	
	public void mapRender(int scale, int xOff, int yOff){
		if(texture == null) texture = tile.getTexture();
		texture.bind(); // or GL11.glBind(texture.getTextureID());
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x/scale + Game.PLAYER.getCameraX() + xOff, y/scale + Game.PLAYER.getCameraY() + yOff);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(x/scale+texture.getImageWidth()/scale + Game.PLAYER.getCameraX() + xOff, y/scale + Game.PLAYER.getCameraY() + yOff);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(x/scale+texture.getImageWidth()/scale + Game.PLAYER.getCameraX() + xOff, y/scale+texture.getImageHeight()/scale + Game.PLAYER.getCameraY() + yOff);
		GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(x/scale + Game.PLAYER.getCameraX() + xOff, y/scale+texture.getImageHeight()/scale + Game.PLAYER.getCameraY() + yOff);
		GL11.glEnd();
	}
	
	public void use(){
		
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
	
	protected void setCollisionOffset(int x, int y){
		this.colOffsetX = x;
		this.colOffsetY = y;
	}
	
	public int getXColOffset(){
		return colOffsetX;
	}
	
	public int getYColOffset(){
		return colOffsetY;
	}
	
	public boolean isBackground(){
		return isBackground;
	}
	
	public void setCollisionBox(int x, int y, int width, int height){
		this.colOffsetX = x;
		this.colOffsetY = y;
		this.width = width;
		this.height = height;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
