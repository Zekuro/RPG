package rpg.game.skills;

import java.awt.Point;

import rpg.game.objects.GameObject;

public class Projectile extends GameObject{


	private int size;
	private int speed;
	private int damage;
	
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

//		x+=speed;
//		y= f(x);
		
		if(startPoint.getX() < endPoint.getX()) x+= speed;
		if(startPoint.getX() > endPoint.getX()) x-= speed;
		if(startPoint.getY() < endPoint.getY()) y+= speed;
		if(startPoint.getY() > endPoint.getY()) y-= speed;
	
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
