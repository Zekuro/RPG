package rpg.game;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import rpg.game.entities.Entity;
import rpg.game.objects.Chest;
import rpg.game.objects.GameObject;
import rpg.game.objects.Portal;
import rpg.game.objects.Tile;
import rpg.game.objects.Tree;


public class World {

	public static final int TILESIZE = 32;
	
	private int width;
	private int height;
	
	private Tile previousTile;
	
	private boolean loading = true;
	
	public static final ArrayList<GameObject> objectList = new ArrayList<GameObject>();
	public static final ArrayList<GameObject> entityList = new ArrayList<GameObject>();
	public static final ArrayList<GameObject> backgroundTiles = new ArrayList<GameObject>();
	
	public World(){
		loadFromImage();
	}
	
	// DEFINE GAMEOBJECTS HERE
	public GameObject createObject(LevelColor color){
			
		GameObject object = null;
		
		/*
		 *	reserves colors:
		 *						255.255.1-255 portals
		 */

		// DevObjects
		if(color.hasColors(120, 60, 0)) object = new GameObject(true,false,4*32, 2*32, new Tile("res/objects/house.png"));
		
		// Objects with soft Color
		if(color.hasRed(255) && color.hasGreen(255) && !color.hasBlue(0)) object = new Portal(color.getBlue());
		
		// Objects with hard Color
		if(color.hasColors(160, 60, 0)) object = new Chest();
		if(color.hasColors(0, 100, 0)) object = new Tree();
		return object;
	}
		
	public GameObject createEntity(LevelColor color){
		
		GameObject object = null;
		
		/*
		 *	reserves colors:
		 *						255.1-200.255 npc's	 	
		 *						
		 *						1-255.1-255.255 mobs
		 *						id.lvl.255
		 */

		// Objects with soft Color
		if(!color.hasRed(0) && !color.hasGreen(0) && color.hasBlue(255)) object = Entity.create(color.getRed(), color.getGreen());
		
		return object;
	}
	
	public GameObject createBackground(LevelColor color){
			
			GameObject object = new GameObject(false,true,TILESIZE, TILESIZE, previousTile);;

			// turn me into real GameObjects and delete Tile
			for(Tile t: Tile.tiles){
				tileCheck: 
					if(t != null && t.getLevelColor().matchesColor(color)){
						 object = new GameObject(false, true,TILESIZE, TILESIZE, t);
						 previousTile = t;
						break tileCheck;
					}
		    }

			return object;
		}
	
	public void update(){
		
		for (GameObject gameObject : objectList) {
			gameObject.update();
		}
		
		for (GameObject gameObject : entityList) {
			gameObject.update();
		}
		
	}
	
	public void renderBackground(){
		for (GameObject gameObject : backgroundTiles) {
			gameObject.render();
		}
	}
	
	public void renderEntities(){
		for (GameObject gameObject : entityList) {
			gameObject.render();
		}
	}
	
	public void renderObjects(){
			for (GameObject gameObject : objectList) {
				gameObject.render();
			}
	}

	public void loadFromImage(){
		try {
			BufferedImage img = ImageIO.read(new File("res/Map.png"));
			createWorld(img);
			loading = false;
			System.out.println("ready");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	  private void createWorld(BufferedImage image) {
	    width = image.getWidth();
	    height = image.getHeight();

	    Game.WORLD_WIDTH = width * TILESIZE;
	    Game.WORLD_HEIGHT = height * TILESIZE;
	    
	    Tile previousTile = null;
	    
	    for(int e = 1; e <= 3; e++){
		    for (int i = 0; i < height; i++) {
		      for (int j = 0; j < width; j++) {
		        int pixel = image.getRGB(j, i);
	
		        int alpha = (pixel >> 24) & 0xff;
			    int red = (pixel >> 16) & 0xff;
			    int green = (pixel >> 8) & 0xff;
			    int blue = (pixel) & 0xff;
			   
			    LevelColor levelColor = new LevelColor(red, green, blue);
			    GameObject object = null;
			    
			    if(e == 1){    
				    object = createBackground(levelColor);
			    }if(e == 2){
			    	object = createObject(levelColor);
			    }if(e == 3){
			    	object = createEntity(levelColor);
			    }
			    
			    if(object != null){
			    	object.setPosition(j*TILESIZE, height*TILESIZE-TILESIZE - i*TILESIZE);
			    	if(object.isBackground() && e != 3) backgroundTiles.add(object);
			    	if(!object.isBackground() && e != 3) objectList.add(object);
			    	if(e == 3) entityList.add(object);
			    }
			    
		      }
	    	}
	    }
	  }
	  
	  public boolean isLoading(){
		  return loading;
	  }
	  
}
