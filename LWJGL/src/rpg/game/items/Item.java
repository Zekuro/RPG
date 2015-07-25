package rpg.game.items;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rpg.game.Font;
import rpg.game.player.Inventory;

public abstract class Item {

	private String name;
	private Texture texture;
	private Tier tier;
	private int neededLvl;
	private int sellPrice;
	private int buyPrice;
	private final int size = 32;

	private int stack = 1;
	private int maxStacks = 99;
	private boolean stackable;
	
	
	/*
	 * 	T1 = gewoehnlich 	(grau)
	 * 	T2 = selten			(gelb)
	 * 	T3 = fantastisch	(blau)
	 *  T4 = episch			(lila)
	 *  T5 = orange			(orange)
	 */
	public static enum Tier{
		T1,T2,T3,T4,T5
	}
	
	
	
	public Item(String name, Tier tier, int neededLvl, int sellPrice, int buyPrice,String texture){
		this.name = name;
		this.tier = tier;
		this.neededLvl = neededLvl;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
		setTexture(texture);
	}
	
	public void render(int x,int y){
		texture.bind(); // or GL11.glBind(texture.getTextureID());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(x+size, y);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(x+size, y+size);
		GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(x, y+size);
		GL11.glEnd();
		
		String stacks = Integer.toString(stack);
		Font.render(stacks, x + 32 - 8*stacks.length(), y);
		
	}
	
	public abstract void use();
	
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
	
	public boolean canAdd(){
		if(maxStacks-stack > 0) return true;
		return false;
	}
	
	public void add(){
		stack++;
	}
	
	public void remove(){
		stack--;
	}
	
	public void destroy(){
		Inventory.remove(this);
	}
	
	public int getStacks(){
		return stack;
	}
	
	public String getName(){
		return name;
	}
	
	public void setStacks(int i){
		stack = i;
	}
	
}
