import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.util.ArrayList;
public class Bullet extends MovingObject
{
	boolean gravity;
	Gun gun;
	public Bullet(double speed, boolean gravity, double diameter, double x, double y, double angle, Gun gun)
	{
		super(x,y,diameter, diameter);
		this.gun = gun;
		this.gravity = gravity;
		xVelocity = speed * Math.cos(angle);
		yVelocity = speed * Math.sin(angle);
		this.type = "bullet";
		Screen.movingObjects.add(this);
	}
	@Override
	public void drawMe(Graphics2D g)
	{
		g.setColor(Color.black);
		g.fillRect((int)(x - Screen.screenX), (int)(y- Screen.screenY), (int)width, (int)height); // for testing
		drawn = true;
	}
	@Override
	public void moveMe()
	{
		if(!gravity)
		{
			yVelocity += Screen.gravity;
		}
		super.moveMe();
	}
	public void collision()
	{
		if(touched.get(0).touched.type.equals("enemy"))
		{
			hit((Enemy)touched.get(0).touched);
			Screen.movingObjects.remove(this);
			gun.bullets.remove(this);
		}
		else
		{
			Screen.movingObjects.remove(this);
			gun.bullets.remove(this);
		}
	}
	public void hit(Enemy enemy)
	{
		if(enemy.invulnerablilityCount < 0)
		{
			int xMult = 1;
			if(x + width /2 < enemy.x + enemy.width/2)
			{
				xMult = -1;
			}
			enemy.xVelocity -= gun.damage / enemy.defense/4 * xMult * Math.pow(1000/enemy.mass, .2);
			enemy.yVelocity -= gun.damage / enemy.defense/4 * Math.pow(1000/enemy.mass, .2);
			enemy.invulnerablilityCount = 20;
			enemy.health -= gun.damage / enemy.defense;
		}
	}
}