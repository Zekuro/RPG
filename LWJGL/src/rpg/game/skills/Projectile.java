package rpg.game.skills;

import java.awt.Point;

import rpg.game.World;
import rpg.game.entities.Entity;
import rpg.game.objects.GameObject;

public class Projectile extends GameObject{


	private int range;
	private int shootRange;
	private int speed;
	private int damage;
	private double m;
	
	private Point startPoint;
	private Point endPoint;
	
	public Projectile(int size, int range, int speed, int damage, Point startPoint, Point endPoint, String texture, int rotateDegree) {
		super(false, false, size, size, texture);
			this.shootRange = range;
			this.speed = speed;
			this.damage = damage;
			this.startPoint = startPoint;
			this.endPoint = endPoint;
			this.setPosition((int) startPoint.getX(), (int)startPoint.getY());
			m = (endPoint.getY() - startPoint.getY())/(endPoint.getX() - startPoint.getX());
			degree = rotateDegree;

			if(endPoint.getX() > startPoint.getX()){
				degree += Math.toDegrees(Math.atan(m));
			}else{
				degree += Math.toDegrees(Math.atan(m)) + 180;
			}

			// bugfix
			if((rotateDegree - degree)% 90 == 0 && startPoint.getX() == endPoint.getX()) degree += 180;
			
	}
	
	
	public void update(){
		
		if(endPoint.getX() > startPoint.getX()){
			x += speed*Math.cos(Math.atan(m));
			y += speed*Math.sin(Math.atan(m));
		}else if(endPoint.getX() < startPoint.getX()){
			x -= speed*Math.cos(Math.atan(m));
			y -= speed*Math.sin(Math.atan(m));
		}else if(endPoint.getX() == startPoint.getX()){
			
			if(endPoint.getY() > startPoint.getY()){
				y += speed;
			}else{
				y -= speed;
			}
			
		}
		
		range = (int) Math.sqrt(Math.pow((x-startPoint.getX()),2)+Math.pow((y-startPoint.getY()),2));
		if(range > shootRange || hasCollision()) destroy();
	}
	
	// TODO update me for large Worlds (only check displayed sectors)
	public boolean hasCollision(){

		for (GameObject object : World.objectList) {
			
			if(	object.isSolid()
				&& !object.equals(this)
				&& x + colOffsetX + width > object.getX() + object.getXColOffset()
				&& x + colOffsetX < object.getX() + object.getXColOffset() + object.getWidth()
				&& y + colOffsetY + height > object.getY() + object.getYColOffset()
				&& y + colOffsetY < object.getY() + object.getYColOffset() + object.getHeight()){
				return true;
			}
			
		}
		
		for (GameObject object : World.entityList) {
			
			if(	object.isSolid()
				&& !object.equals(this)
				&& x + colOffsetX + width > object.getX() + object.getXColOffset()
				&& x + colOffsetX < object.getX() + object.getXColOffset() + object.getWidth()
				&& y + colOffsetY + height > object.getY() + object.getYColOffset()
				&& y + colOffsetY < object.getY() + object.getYColOffset() + object.getHeight()){
				
					Entity entity = (Entity) object;
					entity.loseHealth(damage);
				
				return true;
			}
			
		}
		
		return false;
	}

	public int getRange(){
		return shootRange;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
	
}
