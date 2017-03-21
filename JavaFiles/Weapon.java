import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.ArrayList;
public class Weapon
{

	double damage;
	TestCharacter mc;
	public Weapon(TestCharacter mc)
	{
		this.mc = mc;
	}

	public void moveMe(){}

	public void drawMe(Graphics g){}

	public void collision(Enemy enemy){}

	public void hit(Enemy enemy){}
}