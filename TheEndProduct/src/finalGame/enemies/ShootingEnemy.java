package finalGame.enemies;
import finalGame.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static finalGame.Screen.screenX;
import static finalGame.Screen.screenY;
import static java.awt.Color.red;

public class ShootingEnemy extends Enemy
{
	public double sinceShot = 0;
	public double bulletSize = 15;
	public double bulletVelocity = 12;
	public double differentiation;
	public double reloadTime = 360.0;

	public Image icon;
	public ShootingEnemy(double x, double y, double width, double height, double damage)
	{
		super(x,y,width,height,damage);
		differentiation = Math.sqrt(Math.pow(width/2 + bulletSize /2,2) + Math.pow(height/2 +bulletSize /2,2));
		try
		{
			icon = new ImageIcon(new File("res/images/nomovyshooty.png").toURI().toURL()).getImage();
		}catch(IOException e){System.out.println(e);}

	}
	@Override
	public void moveMe()
	{
		if(!moved)
		{
			if(Math.random() < Math.pow((double)sinceShot/reloadTime, 3))
			{
				double firstPart = Math.sqrt(Math.abs(Math.pow(bulletVelocity,4)-(Screen.gravity* -1)*((Screen.gravity* -1) * Math.pow((Screen.mc.x +Screen.mc.width/2 - bulletSize/2) - (x + width/2),2)+2*((Screen.mc.y + Screen.mc.height/2 - bulletSize /2) - (y + height/2)) * Math.pow(bulletVelocity,2))));
				if(Math.random() > .5 || bulletVelocity > 15)
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