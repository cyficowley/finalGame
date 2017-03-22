import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BasicAiEnemy extends Enemy
{
	public BasicAiEnemy(double x, double y, double width, double height, double damage)
	{
		super(x,y,width,height, 25);
		speed = 3;
		fixed = true;
	}
	@Override
	public void moveMe()
	{
		super.moveMe();
		if(Screen.mc.x + Screen.mc.width/2 -(x + width/2) > 300)
		{
			direction = true;
		}
		else if(Screen.mc.x + Screen.mc.width/2 -(x + width/2) < -300)
		{
			direction = false;
		}
		if(invulnerablilityCount < 0)
		{
			if(direction)
			{
				moveRight();
				for(TouchData each : touched)
				{
					if(each.touched.type.equals("block") && each.direction ==3)
					{
						jump();
					}
				}
			}
			else
			{
				moveLeft();
				for(TouchData each : touched)
				{
					if(each.touched.type.equals("block") && each.direction ==2)
					{
						jump();
					}
				}
			}
		}
	}
	@Override
	public void drawMe(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect((int)(x - Screen.screenX), (int)(y- Screen.screenY), (int)width, (int)height); // for testing
		super.drawMe(g);
	}
}