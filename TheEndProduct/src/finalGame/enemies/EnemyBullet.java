package finalGame.enemies;
import finalGame.Screen;
import finalGame.usefulClasses.TouchData;

import java.awt.Graphics2D;
import java.awt.Color;

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