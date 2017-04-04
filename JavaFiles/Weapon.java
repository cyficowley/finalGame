package javaFiles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.Graphics2D;
public class Weapon
{

	double damage;
	TestCharacter mc;
	public Weapon(TestCharacter mc)
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