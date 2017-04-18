package javaFiles;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ShootingEnemy extends Enemy
{
	double sinceShot = 0;
	double bulletSize = 15;
	double bulletVelocity = 12;
	double damage = 15;
	double differentiation;
	public ShootingEnemy(double x, double y, double width, double height, double damage)
	{
		super(x,y,width,height,damage);
		differentiation = Math.sqrt(Math.pow(width/2 + bulletSize /2,2) + Math.pow(height/2 +bulletSize /2,2));
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
	@Override
	public void drawMe(Graphics2D g)
	{
		g.setColor(Color.blue);
		g.fillRect((int)(x - Screen.screenX), (int)(y - Screen.screenY), (int)(width), (int)(height));
		super.drawMe(g);
	}
}