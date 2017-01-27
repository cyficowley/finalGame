import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
public class MovingObject extends MainObject
{
	double xVelocity=0;
	double yVelocity=0;
	double pastX;
	double pastY;
	ArrayList<TouchData> touched= new ArrayList<TouchData>();
	double mass;
	boolean moved = false;
	boolean drawn = false;
	public MovingObject(double x, double y, double width, double height)
	{
		super(x,y,width,height);
		mass = width * height;
	}
	public void moveMe()
	{
		if(!moved)
		{
			pastY = y;
			pastX = x;
			x += xVelocity;
			y += yVelocity;
			if(touched.size() > 0)
			{
				if(touched.get(0).touched.fixed == true &&touched.get(0).direction == 1)
				{
					xVelocity *= .9;
				}
			}
			yVelocity += Screen.gravity;
			moved = true;
		}
	}
	public void collision(Chunk chunk)
	{
		touched.clear();
		for(int i = 0; i < chunk.chunkSize; i ++)
		{
			for(int j = 0; j < chunk.chunkSize; j ++)
			{
				if(chunk.blocks[i][j].collisionActive)
				{
					//System.out.println(chunk.xIndex +"    " + chunk.yIndex);
					if(collision(chunk.blocks[i][j]))
					{
						Screen.collisions.add(new CollisionContainer(this, chunk.blocks[i][j]));
					}
				}
			}
		}
	}
	public void collision2(Chunk chunk)
	{
		for(MovingObject each : chunk.containedObjects)
		{
			if(!each.equals(this) && collision(each))
			{
				Screen.collisions.add(new CollisionContainer(this, each));
			}
		}
	}
	public void drawMe(Graphics g)
	{
		g.setColor(Color.blue);
		g.fillRect((int)(x - Screen.screenX), (int)(y- Screen.screenY), (int)width, (int)height); // for testing
		drawn = true;
	}
}