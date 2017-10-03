package finalGame.enemies;

import finalGame.Screen;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by cyrus on 5/23/17.
 */
public class LedgeShootingEnemy extends LedgeEnemy
{
	double sinceShot = 0;
	double bulletSize = 15;
	double bulletVelocity = 12;
	public LedgeShootingEnemy(double x, double y, double width, double height, double damage)
	{
		super(x,y,width,height,damage);
		multiplier = 1.8;
		try
		{
			icon = new ImageIcon(new File("res/images/ledgeShootingEnemy.gif").toURI().toURL()).getImage();
		}catch(IOException e){System.out.println(e);}
	}
	@Override
	public void moveMe()
	{
		if(!moved)
		{
			if(Math.random() < Math.pow((double)sinceShot/360.0, 3))
			{
				double firstPart = Math.sqrt(Math.abs(Math.pow(bulletVelocity,4)-(Screen.gravity* -1)*((Screen.gravity* -1) * Math.pow((Screen.mc.x +Screen.mc.width/2 - bulletSize/2) - (x + width/2),2)+2*((Screen.mc.y + Screen.mc.height/2 - bulletSize /2) - (y + height/2)) * Math.pow(bulletVelocity,2))));
				if(Math.random() > .5)
				{
					firstPart *= -1;
				}
				double angle = Math.atan((Math.pow(bulletVelocity,2) + firstPart)/((Screen.gravity* -1) * ((Screen.mc.x +Screen.mc.width/2 - bulletSize/2) - (x + width/2))));
				if((Screen.mc.x +Screen.mc.width/2) - (this.x + this.width/2) < 0)
				{
					angle += Math.PI;
				}
				EnemyBullet temp = new EnemyBullet(x + width/2,y + height/2,bulletSize, angle, bulletVelocity, damage);
				sinceShot = 0;
			}
			sinceShot ++;
		}
		super.moveMe();
	}
}
