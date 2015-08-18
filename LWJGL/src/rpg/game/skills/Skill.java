package rpg.game.skills;

import java.awt.Point;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import rpg.game.Game;
import rpg.game.World;
import rpg.game.items.Item;

public class Skill extends Item{

	public static enum SkillType{
		PROJECTILE, LASER, IMPACT, AURA
	}
	
	public static enum SkillEffect{
		DOUBLE, SPREAD, RANGE, SIZE
	}
	
	public static enum Element{
		FIRE, ICE, ELECTICITY, SHIELD
	}
	
	private SkillType type;
	
	// FIXME CHANGE ME!!!
	private int requiredMana = 0;

	private int damage;
	private int fireDamage;
	private int iceDamage;
	private int electricDamage;
	
	private ArrayList<SkillEffect> skillEffects = new ArrayList();
	private ArrayList<Element> elements = new ArrayList();
	
	
	public Skill(String name, Tier tier, SkillType type) {
		super(name, tier, 0, 0, 0, "");
		this.type = type;
		
		String texturePath = "/res/skills/";
		
		switch (type) {
		case PROJECTILE:
			texturePath += "S_Fire05.png";
			break;
		case LASER:
			texturePath += "S_Fire01.png";
			break;
		case IMPACT:
			texturePath += "S_Earth04.png";
			break;
		case AURA:
			texturePath += "S_Buff05.png";
			break;
		}
		
		setTexture(texturePath);
		isSkill = true;
		stackable = false;
	}

	// change icon to same element
	public void addElement(Element e){
		for (Element element: elements) {
			if(element== e) return;
		}
		elements.add(e);
	}
	
	public void addEffect(SkillEffect e){
		for (SkillEffect effect : skillEffects) {
			if(effect == e) return;
		}
			skillEffects.add(e);
			
			switch(skillEffects.size()){
			case 1: tier = Tier.T1;
				break;
			case 2: tier = Tier.T2;
				break;
			case 3: tier = Tier.T3;
				break;
			case 4: tier = Tier.T4;
				break;
			case 5: tier = Tier.T5;
				break;
			}
	}
	
	public ArrayList<Element> getElements(){
		return elements;
	}
	
	public ArrayList<SkillEffect> getSkillEffects(){
		return skillEffects;
	}
	
	public void use(){
		// use skill by type
		// add effects & element
	
		if(Game.PLAYER.reduceMana(requiredMana)){
		
		switch(type){
		case PROJECTILE: projectileAttack();
			break;
		case LASER: laserAttack();
			break;
		case IMPACT: impactAttack();
			break;
		case AURA: auraAttack();
			break;
		}
		
		}
		
	}
	
	private void projectileAttack(){
		
		Point startPoint = new Point(Game.PLAYER.getX(), Game.PLAYER.getY());
		Point endPoint = new Point(Mouse.getX() + Game.PLAYER.getCameraX(), Mouse.getY()+ Game.PLAYER.getCameraY());
		
		
		if(skillEffects.isEmpty()){
			World.effects.add(new Projectile(32,300, 8, 5,startPoint, endPoint, "/res/skills/S_Fire05.png"));
		}
		
		
	}
	
	private void laserAttack(){
		
	}
	
	private void impactAttack(){
		
	}
	
	private void auraAttack(){
		
	}
}
