public class MovingObject extends MainObject
{
	double xVelocity=0;
	double yVelocity=0;
	double pastX;
	double pastY;
	public MovingObject(double x, double y, double width, double height)
	{
		super(x,y,width,height);
	}
	public void moveMe()
	{
		pastY = y;
		pastX = x;
		x += xVelocity;
		y += yVelocity;
		yVelocity += Screen.gravity;
	}
	public void collision()
	{
		
	}
}