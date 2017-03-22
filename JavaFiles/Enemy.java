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

	public double damage;
	boolean direction; //true = right
	double invulnerablilityCount= 0;
	double defense = 1;
	double angle;
	double speed;
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
			System.out.println(this.yVelocity);
			System.out.println(mc.yVelocity);
			invulnerablilityCount = 20;
		}
	}

	@Override
	public void drawMe(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect((int)(x - Screen.screenX), (int)(y- Screen.screenY), (int)width, (int)height); // for testing
		drawn = true;
	}
}