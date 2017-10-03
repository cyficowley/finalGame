package finalGame.equipable;

import finalGame.Screen;
import finalGame.usefulClasses.TouchData;
import finalGame.enemies.Enemy;

public class BounceBullet extends Bullet
{
	int maxBounces = 0;
	int bounces = 0;
	public BounceBullet(double speed, boolean gravity, double diameter, double x, double y, double angle, Gun gun, int maxBounces)
	{
		super(speed, gravity, diameter, x, y, angle, 16, gun);
		this.maxBounces = maxBounces;
	}
	@Override
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
			if(bounces < maxBounces)
			{
				bounces ++;
				for(TouchData each : touched)
				{
					if(each.direction == 2 || each.direction ==3)
					{
						xVelocity = -pastXVelocity;
					}
					else
					{
						yVelocity = -pastYVelocity;
					}
				}
			}
			else
			{
				Screen.movingObjects.remove(this);
				gun.bullets.remove(this);
			}
		}
	}
}