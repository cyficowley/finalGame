import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Enemy extends MovingObject
{

	double damage;
	double health = 100;
	double maxHealth = 100;
	boolean direction; //true = right
	double invulnerablilityCount= 0; //when above 0 doens't allow more damage
	double defense = 1;
	double angle;
	double speed = 1;
	boolean grounded = false; //if it is touching the ground
	public Enemy(double x, double y, double width, double height, double damage)
	{
		super(x,y,width,height);
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
			if(each.touched.fixed == true &&each.direction == 1)
			{
				grounded = true;
			}
		}
		invulnerablilityCount --;
	}

	public void collision()
	{
		for(TouchData each : touched)
		{
			if(each.touched.type.equals("mc"))
			{
				mcHit(each, (TestCharacter)each.touched);
			}
		}
	}

	public void mcHit(TouchData touched, TestCharacter mc)
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
					mc.yVelocity = this.pastYVelocity - damage / mc.defense/4 * mult;
				}
				else
				{
					mc.xVelocity -= damage / mc.defense/4;
					mc.yVelocity = this.pastYVelocity - damage / mc.defense/4 *mult;
				}
			}
			mc.health -= damage / mc.defense;
			invulnerablilityCount = 20;
		}
	}
	public void moveRight()
	{
		if(xVelocity < speed)
		{
			x+=speed/60;
			xVelocity += speed/7;
			if(xVelocity > speed)
			{
				xVelocity = speed;
			}
		}
	}
	public void moveLeft()
	{
		if(xVelocity > -speed)
		{
			xVelocity -= speed/60;
			xVelocity -= speed/7;
			if(xVelocity < -speed)
			{
				xVelocity = -speed;
			}
		}
	}
	public void jump()
	{
		if(grounded)
		{
			yVelocity -= speed/4*7; //you jump only if touching a fixed block from bottom
		}
	}

	@Override
	public void drawMe(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)(x - width/20 - Screen.screenX), (int)(y- height/5 - Screen.screenY), (int)(width * 11/10), (int)(height/20 * 3));
		g.setColor(Color.green);
		g.fillRect((int)(x - width/20 - Screen.screenX), (int)(y- height/5 - Screen.screenY), (int)(width * 11/10 * health/maxHealth), (int)(height/20 * 3));

		drawn = true;
	}
}