package rpg.game.objects;


public class Chest extends GameObject{

	private boolean open = false;
	private boolean containsLoot = true;
	
	public Chest() {
		super(true,false, 32, 32, "res/objects/chest.png");
	}
	
	public void use(){
		if(open){
			open = false;
			setTexture("res/objects/chest.png");
			loot();
		}else{
			open = true;
			setTexture("res/objects/emptyChest.png");
		}
	}
	

	public void loot(){
		if(containsLoot){
			containsLoot = false;
		}
	}
	
	public boolean isOpen(){
		return open;
	}
	
}
