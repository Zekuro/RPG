package rpg.game.objects;

import rpg.game.Map;
import rpg.game.Player;

public class Portal extends GameObject{

	private int id;
	
	public Portal(int id) {
		super(true, 32, 32, "res/sand.png");
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

		System.out.println("ID: " +teleportId);
		
		for (GameObject object : Map.objectList) {
			if(object.getClass() == this.getClass()){
				Portal portal = (Portal) object;
				if(portal.getId() == teleportId){
					p.setPosition(portal.getX(), portal.getY());
				}
			}
		}
	}
	
	public int getId(){
		return id;
	}
	
}
