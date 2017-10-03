package finalGame.equipable;
import finalGame.MainCharacter;
import finalGame.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Gun extends Weapon
{
	public double cooldown;
	public double maxCooldown;
	public double knockback;
	public double angle;
	public double speed = 4;
	public boolean fire = false;
	public boolean drawRight = false;
	int sinceFired = 0;
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public Gun(MainCharacter mc)
	{
		super(mc);
		knockback = .75;
		maxCooldown = 10;
		cooldown = maxCooldown;
		damage = mc.damage;
	}
	public Gun(MainCharacter mc, boolean dontImportPictures)
	{
		super(mc);
		knockback = 2;
		maxCooldown = 20;
		cooldown = maxCooldown;
		damage = mc.damage;
	}
	@Override
	public void moveMe()
	{
		fire = true;
		angle -= (angle - Math.PI/2)/6;
		if(Screen.d)
		{
			angle += .05;
		}
		if(Screen.a)
		{
			angle -= .05;
		}
		cooldown--;
		if(Screen.space && cooldown <0)
		{
			cooldown = maxCooldown;
			fire();
			sinceFired = 0;
		}
		sinceFired ++;
	}
	public void fire()
	{
		if(fire)
		{
			bullets.add(new Bullet(speed,true,20,mc.x + mc.width/2+ Math.cos(angle) * 30-10, mc.y + mc.height/2 + Math.sin(angle) * 30-6,angle,40,this));
			if(!mc.grounded)
			{
				mc.xVelocity += knockback * Math.cos(angle + Math.PI) *4;
				mc.yVelocity += knockback * Math.sin(angle + Math.PI);
			}
		}
	}
	@Override
	public void drawMe(Graphics2D g)
	{
		if(sinceFired <= 10)
		{
			g.setColor(new Color(255,255,0,(int)(50 - sinceFired*5)));
			g.fillOval((int)(Screen.mc.x - Screen.screenX), (int)(Screen.mc.y - 1.5 - Screen.screenY + Screen.mc.height/2), 64, 64);
			g.setColor(new Color(255,255,0,(int)(50 - sinceFired*5)/2));
			g.fillOval((int)(Screen.mc.x +11 - Screen.screenX), (int)(Screen.mc.y - 4.5 + 15 - Screen.screenY + Screen.mc.height/2), 42, 42);
		}
	}
	@Override
	public void collision()
	{
		for(int i = 0; i < bullets.size(); i ++)
		{
			if(bullets.get(i).touched.size() != 0)
			{
				bullets.get(i).collision();
			}
		}
	}
	public void unSelected()
	{
		for(int i = 0; i < Screen.movingObjects.size(); i ++)
		{
			if(Screen.movingObjects.get(i).type.equals("bullet"))
			{
				Screen.movingObjects.remove(i);
				i --;
			}
		}
	}
}