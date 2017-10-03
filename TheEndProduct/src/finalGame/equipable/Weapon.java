package finalGame.equipable;
import finalGame.MainCharacter;
import finalGame.enemies.Enemy;

import java.awt.Graphics2D;
public class Weapon
{

	double damage;
	MainCharacter mc;
	public Weapon(MainCharacter mc)
	{
		this.mc = mc;
	}

	public void moveMe(){}

	public void drawMe(Graphics2D g){}

	public void collision(Enemy enemy){}
	
	public void collision(){}

	public void hit(Enemy enemy){}

	public void unSelected(){}
}