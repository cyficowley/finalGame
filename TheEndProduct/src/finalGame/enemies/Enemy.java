package finalGame.enemies;
import finalGame.MainCharacter;
import finalGame.MovingObject;
import finalGame.Screen;
import finalGame.usefulClasses.TouchData;

import java.awt.Graphics2D;
import java.awt.Color;

import static finalGame.Screen.currentYChunks;
import static finalGame.Screen.screenX;
import static finalGame.Screen.screenY;
import static java.awt.Color.red;

public class Enemy extends MovingObject
{

	public double damage;
	public double health = 100;
	public double maxHealth = 100;
	public boolean direction; //true = right
	public double invulnerablilityCount= 0; //when above 0 doens't allow more damage
	public double defense = 1;
	public double angle;
	public double speed = 1;
	public double multiplier = 1;
	double startX;
	double startY;
	int resetting= 0;
	public boolean grounded = false; //if it is touching the ground
	public Enemy(double x, double y, double width, double height, double damage)
	{
		super(x,y,width,height);
		startX = x;
		startY = y;
		this.type = "enemy";
		this.damage = damage;
	}
	@Override
	public void moveMe()
	{
		super.moveMe();
		grounded = false;
		for(TouchData each : touched)
		{
			if(each.touched.fixed == true && each.direction == 1)
			{
				grounded = true;
			}
		}
		invulnerablilityCount --;
		if(health <= 0)
		{
			Screen.enemyScore += 100 * multiplier * (1 + 3*Math.pow(y / (currentYChunks * 20 * Screen.blockWidth),3));
			removeMe();
		}
		if(resetting == 10 && !type.equals("enemyBullet"))
		{
			x = startX;
			y = startY;
			xVelocity = 0;
			yVelocity = 0;
		}
		resetting ++;
	}

	public void collision()
	{
		for(TouchData each : touched)
		{
			if(each.touched.type.equals("mc"))
			{
				mcHit(each, (MainCharacter)each.touched);
			}
		}
	}

	public void mcHit(TouchData touched, MainCharacter mc)
	{
		if(invulnerablilityCount < 0)
		{
			if(touched.direction == 2)
			{
				mc.xVelocity -= damage / mc.defense/4;
				mc.yVelocity = this.pastYVelocity - damage / mc.defense/4;
			}
			else if(touched.direction == 3)
			{
				mc.xVelocity += damage / mc.defense/4;
				mc.yVelocity = this.pastYVelocity - damage / mc.defense/4;
			}
			else
			{
				int mult =1;
				if(touched.direction == 1)
				{
					mult =-1;
				}
				if(mc.x + mc.width /2 > x + width/2)
				{
					mc.xVelocity += damage / mc.defense/4;
					mc.yVelocity = this.pastYVelocity - damage / mc.defense/4 * mult * 200/mc.mass;
				}
				else
				{
					mc.xVelocity -= damage / mc.defense/4;
					mc.yVelocity = this.pastYVelocity - damage / mc.defense/4 *mult * 200/mc.mass;
				}
			}
			mc.health -= damage / mc.defense;
			invulnerablilityCount = 20;
			if(mc.health <= 0)
			{
				Screen.movingObjects.add(0, Screen.movingObjects.remove(Screen.movingObjects.indexOf(this)));
			}
		}
	}
	public void moveRight()
	{
		if(xVelocity < speed )
		{
			if((type.equals("mc") && grounded)|| !type.equals("mc"))
			{
				x+=speed/60;
				xVelocity += speed/7;
				if(xVelocity > speed)
				{
					xVelocity = speed;
				}
			}

		}
	}
	public void moveLeft()
	{
		if(xVelocity > -speed)
		{
			if((type.equals("mc") && grounded)|| !type.equals("mc"))
			{
				x -= speed/60;
				xVelocity -= speed/7;
				if(xVelocity < -speed)
				{
					xVelocity = -speed;
				}
			}
		}
	}
	public void jump()
	{
		if(grounded)
		{
			yVelocity -= Math.sqrt(speed*16); //you jump only if touching a fixed block from bottom
		}
	}

	@Override
	public void drawMe(Graphics2D g) {
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