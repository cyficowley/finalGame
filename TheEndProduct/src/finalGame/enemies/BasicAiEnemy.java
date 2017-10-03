package finalGame.enemies;
import finalGame.Screen;
import finalGame.usefulClasses.TouchData;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static finalGame.Screen.screenX;
import static finalGame.Screen.screenY;
import static java.awt.Color.red;

public class BasicAiEnemy extends Enemy
{
	Image icon;

	public BasicAiEnemy(double x, double y, double width, double height, double damage)
	{
		super(x,y,width,height, damage);
		speed = 3;

		fixed = true;
		try
		{
			icon = new ImageIcon(new File("res/images/basicAiEnemy.gif").toURI().toURL()).getImage();
		}catch(IOException e){System.out.println(e);}

	}
	@Override
	public void moveMe()
	{
		super.moveMe();
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
		if(Screen.mc.x + Screen.mc.width/2 -(x + width/2) > 300)
		{
			direction = true;
		}
		else if(Screen.mc.x + Screen.mc.width/2 -(x + width/2) < -300)
		{
			direction = false;
		}
	}
	@Override
	public void drawMe(Graphics2D g) {
		g.drawImage(icon,(int)(x - Screen.screenX), (int)(y- Screen.screenY), (int)width, (int)height,null);
		if(health < maxHealth)
		{
			g.setColor(red);
			g.fillRect((int) (x - width / 20 - screenX), (int) (y - height / 5 - screenY), (int) (width * 11 / 10), (int) (height / 20 * 3));
			g.setColor(new Color(19, 128, 30));
			g.fillRect((int) (x - width / 20 - screenX), (int) (y - height / 5 - screenY), (int) (width * 11 / 10 * health / maxHealth), (int) (height / 20 * 3));
		}

		drawn = true;
	}
}