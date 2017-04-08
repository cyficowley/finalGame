package javaFiles;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EnemyBullet extends Enemy
{
	public EnemyBullet(double x, double y, double radius, double angle,double speed, double damage)
	{
		super(x,y,radius,radius,damage);
		xVelocity = Math.cos(angle) * speed;
		yVelocity = Math.sin(angle) * speed;
		Screen.movingObjects.add(this);
		Screen.enemies.add(this);
		type = "enemyBullet";
	}
	@Override
	public void drawMe(Graphics2D g)
	{
		g.setColor(new Color(60,60,60));
		g.fillRect((int)(x - Screen.screenX), (int)(y - Screen.screenY), (int)(width), (int)(height));
	}
	public void moveMe()
	{
		for(TouchData each : touched){
			if(each.touched.type != "enemy")
			{
				Screen.enemies.remove(this);
				Screen.movingObjects.remove(this);
			}
		}
		super.moveMe();
	}
}