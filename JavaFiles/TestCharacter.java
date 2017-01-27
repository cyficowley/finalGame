import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
public class TestCharacter extends MovingObject
{
	public TestCharacter(double x, double y, double width, double height)
	{
		super(x,y,width,height);
	}
	@Override
	public void moveMe()
	{
		super.moveMe();
		if(Screen.space)
		{
			if(touched.size() > 0)
			{
				if(touched.get(0).touched.fixed == true &&touched.get(0).direction == 1)
				{
					yVelocity -= 3;
				}
			}
		}
		if(Screen.d)
		{
			xVelocity = 3;
		}
		if(Screen.a)
		{
			xVelocity = -3;
		}
		Screen.screenX = x + width/2- Screen.screenWidth/2;
		Screen.screenY = y + height/2 - Screen.screenHeight/2;
	}
}