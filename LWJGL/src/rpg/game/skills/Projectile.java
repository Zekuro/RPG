package rpg.game.skills;

import java.awt.Point;

import rpg.game.objects.GameObject;

public class Projectile extends GameObject{


	private int size;
	private int speed;
	private int damage;
	private int delta;
	
	
	private Point startPoint;
	private Point endPoint;
	
	public Projectile(int size, int speed, int damage, Point startPoint, Point endPoint, String texture) {
		super(false, false, size, size, texture);
			this.size = size;
			this.speed = speed;
			this.damage = damage;
			this.startPoint = startPoint;
			this.endPoint = endPoint;
			this.setPosition((int) startPoint.getX(), (int)startPoint.getY());
			degree = 0;
	}
	
	
	public void update(){
		double m = (endPoint.getY() - startPoint.getY())/(endPoint.getX() - startPoint.getX());
		
		if(endPoint.getX() > startPoint.getX()){
			x += speed*Math.cos(Math.atan(m));
			y += speed*Math.sin(Math.atan(m));
		}else if(endPoint.getX() < startPoint.getY()){
			x -= speed*Math.cos(Math.atan(m));
			y -= speed*Math.sin(Math.atan(m));
		}else if(endPoint.getX() == startPoint.getX()){
			
			if(endPoint.getY() > startPoint.getY()){
				y += speed;
			}else{
				y -= speed;
			}
			
		}
		
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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
