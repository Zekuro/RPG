package rpg.game.objects;


public class Chest extends GameObject{

	private boolean containsLoot = true;
	
	public Chest() {
		super(true,false, 32, 32, "res/objects/chest.png");
	}
	
	public void open(){
		if(containsLoot){
			containsLoot = false;
			setTexture("res/objects/emptyChest.png");
		}
	}

}
