package rpg.game.objects;

import rpg.game.World;
import rpg.game.player.Player;

public class Portal extends GameObject{

	private int id;
	private int targetId;
	private boolean active = true;
	
	public Portal(int id) {
		super(false,true, 32, 32, "res/objects/portal.png");
		this.id = id;
		
		if(id%2 == 0){
			targetId = id-1;
		}
		
		if(id%2 == 1){
			targetId = id+1;
		}
		
	}

	public void teleport(Player p){
		
		for (GameObject object : World.backgroundTiles) {
			if(object.getClass() == this.getClass()){
				Portal portal = (Portal) object;
				if(portal.getId() == targetId){
					p.setPosition(portal.getX(), portal.getY());
				}
			}
		}
	}
	
	public void setActive(boolean b){
		this.active = b;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public int getId(){
		return id;
	}
	
}
