package rpg.game.objects;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.tiled.TileSet;

public class Chest extends GameObject{

	private boolean containsLoot = true;
	
	public Chest() {
		super(true, 32, 32, "res/chest.png");
	}
	
	public void open(){
		if(containsLoot){
			containsLoot = false;
			setTexture("res/emptyChest.png");
		}
	}

}
