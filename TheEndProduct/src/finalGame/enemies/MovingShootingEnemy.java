package finalGame.enemies;
import finalGame.Screen;
import finalGame.usefulClasses.TouchData;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MovingShootingEnemy extends ShootingEnemy
{
	public MovingShootingEnemy(double x, double y, double width, double height, double damage)
	{
		super(x,y,width,height,damage);
		speed = 1.5;
		multiplier = 1.3;
		try
		{
			icon = new ImageIcon(new File("res/images/shootingEnemy.gif").toURI().toURL()).getImage();
		}catch(IOException e){System.out.println(e);}
	}
	@Override
	public void moveMe()
	{
		if(!moved)
		{
			if(Screen.mc.x + Screen.mc.width/2 -(x + width/2) > 300)
			{
				direction = true;
			}
			else if(Screen.mc.x + Screen.mc.width/2 -(x + width/2) < -300)
			{
				direction = false;
			}
			if(invulnerablilityCount < 0)
			{
				if(direction)
				{
					moveRight();
					for(TouchData each : touched)
					{
						if(each.touched.type.equals("block") && each.direction ==3)
						{
							jump();
						}
					}
				}
				else
				{
					moveLeft();
					for(TouchData each : touched)
					{
						if(each.touched.type.equals("block") && each.direction ==2)
						{
							jump();
						}
					}
				}
			}
		}
		super.moveMe();
	}
}