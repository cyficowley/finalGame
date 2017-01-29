import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
public class TestCharacter extends MovingObject
{
	public TestCharacter(double x, double y, double width, double height)
	{
		super(x,y,width,height); // this entire class is just temporary before we get a real main character class going
	}
	@Override
	public void moveMe()
	{
		if(!moved){ // makes it so you can only jump once even if in multiple chunks
			if(Screen.space || Screen.w)
			{
				if(touched.size() > 0)
				{
					if(touched.get(0).touched.fixed == true &&touched.get(0).direction == 1)
					{
						yVelocity -= 3; //you jump only if touching a fixed block from bottom
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
		}
		super.moveMe();
	}
}