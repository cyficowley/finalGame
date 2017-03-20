import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TestCharacter extends Enemy
{
	private BufferedImage characterDefault;

	

	public TestCharacter(double x, double y, double width, double height, double damage)
	{
		super(x,y,width,height, damage); // this entire class is just temporary before we get a real main character class going

		try {
		    characterDefault = ImageIO.read(new File("images/characterDefault.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		this.speed = 4;
		this.type = "mc";
	}
	@Override
	public void moveMe()
	{
		if(!moved){ // makes it so you can only jump once even if in multiple chunks
			if(Screen.space || Screen.w)
			{
				for(TouchData each : touched)
				{
					if(each.touched.fixed == true &&each.direction == 1)
					{
						yVelocity -= 7; //you jump only if touching a fixed block from bottom
					}
				}
			}
			if(Screen.d)
			{
				if(xVelocity < speed)
				{
					xVelocity += speed/7;
					if(xVelocity > speed)
					{
						xVelocity = speed;
					}
				}
			}
			if(Screen.a)
			{
				if(xVelocity > -speed)
				{
					xVelocity -= speed/7;
					if(xVelocity < -speed)
					{
						xVelocity = -speed;
					}
				}
			}
		}
		super.moveMe();
	}

	@Override
	public void drawMe(Graphics g) {
		g.drawImage(characterDefault, (int) (x - Screen.screenX), (int) (y - Screen.screenY), (int) width, (int) height, null);
	}
}