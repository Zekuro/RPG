package rpg.game;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import rpg.game.objects.Chest;
import rpg.game.objects.GameObject;
import rpg.game.objects.Portal;
import rpg.game.objects.Tile;


public class Map {

	public static final int TILESIZE = 32;
	private int width;
	private int height;
	
	private Tile previousTile;
	
	public static final ArrayList<GameObject> objectList = new ArrayList<GameObject>();
	
	public Map(){
		loadFromImage();
	}
	
	// DEFINE GAMEOBJECTS HERE
	public GameObject createObject(LevelColor color){
			
		GameObject object = null;
		
		if(color.hasColors(120, 60, 0)) object = new GameObject(true,4*32, 3*32, new Tile("res/house.png"));
		if(color.hasColors(160, 60, 0)) object = new Chest();
		if(color.hasRed(255) && color.hasGreen(255) && !color.hasBlue(0)) object = new Portal(color.getBlue());
		return object;
	}
		
	public GameObject createBackground(LevelColor color){
			
			GameObject object = null;

			if(color.hasColors(0, 0, 0)){
				object = new GameObject(false,TILESIZE, TILESIZE, previousTile);
			}
			
			// turn me into real GameObjects and delete Tile
			for(Tile t: Tile.tiles){
				tileCheck: 
					if(t != null && t.getLevelColor().matchesColor(color)){
						 object = new GameObject(false,TILESIZE, TILESIZE, t);
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
		
	}
	
	public void render(){
			for (GameObject gameObject : objectList) {
				gameObject.render();
			}
	}

	public void loadFromImage(){
		try {
			BufferedImage img = ImageIO.read(new File("res/Map.png"));
			createMap(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	  private void createMap(BufferedImage image) {
	    width = image.getWidth();
	    height = image.getHeight();

	    Game.WORLD_WIDTH = width * TILESIZE;
	    Game.WORLD_HEIGHT = height * TILESIZE;
	    
	    Tile previousTile = null;
	    
	    for(int e = 1; e <= 2; e++){
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
			    }
			    
			    if(object != null){
			    	object.setPosition(j*TILESIZE, height*TILESIZE-TILESIZE - i*TILESIZE);
			    	objectList.add(object);
			    }
			    
		      }
	    	}
	    }
	  }
	  
}
