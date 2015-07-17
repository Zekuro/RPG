package rpg.game.objects;

import rpg.game.Map;
import rpg.game.Player;

public class Portal extends GameObject{

	private int id;
	private boolean active = true;
	
	public Portal(int id) {
		super(false, 32, 32, "res/sand.png");
		this.id = id;
	}

	public void teleport(Player p){
		int teleportId = id;
		
		if(id%2 == 0){
			teleportId = id-1;
		}
		
		if(id%2 == 1){
			teleportId = id+1;
		}
		
		for (GameObject object : Map.objectList) {
			if(object.getClass() == this.getClass()){
				Portal portal = (Portal) object;
				if(portal.getId() == teleportId){
					portal.setActive(false);
					p.setPosition(portal.getX(), portal.getY());
					p.setOnSpecialTile(true);
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
