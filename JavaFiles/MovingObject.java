import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
public class MovingObject extends MainObject
{
	double xVelocity=0; // velocity
	double yVelocity=0;
	double pastX; // the previous x value
	double pastY;
	ArrayList<TouchData> touched= new ArrayList<TouchData>(); //  an array of all the mainobjects this one has touched
	double mass;
	boolean moved = false;
	boolean drawn = false;
	boolean active = false;
	public MovingObject(double x, double y, double width, double height)
	{
		super(x,y,width,height);
		mass = width * height;
	}
	public void moveMe()
	{
		if(!moved) // this makes it so that it will only move once if it is in multipl chunks at once
		{
			pastY = y; 
			pastX = x;
			x += xVelocity;
			y += yVelocity;
			if(touched.size() > 0)
			{
				if(touched.get(0).touched.fixed == true &&touched.get(0).direction == 1)
				{
					xVelocity *= .9; // slows it down if it is touching a fixed block from the bottom
				}
			}
			yVelocity += Screen.gravity;
			moved = true;
		}
	}
	public void collision(Chunk chunk)
	{
		touched.clear(); // removes all mainobjects from the arraylist
		for(int i = 0; i < chunk.chunkSize; i ++)
		{
			for(int j = 0; j < chunk.chunkSize; j ++)
			{
				if(chunk.blocks[i][j].collisionActive)
				{
					if(collision(chunk.blocks[i][j]))
					{
						Screen.collisions.add(new CollisionContainer(this, chunk.blocks[i][j])); // collides with all the active blocks (right now only red ones)
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
				Screen.collisions.add(new CollisionContainer(this, each)); //this does collisions with other movingobjects 
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