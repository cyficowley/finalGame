import java.util.ArrayList;
public class MovingObject extends MainObject
{
	double xVelocity=0;
	double yVelocity=0;
	double pastX;
	double pastY;
	ArrayList<TouchData> touched= new ArrayList<TouchData>();
	double mass;
	public MovingObject(double x, double y, double width, double height)
	{
		super(x,y,width,height);
		mass = width * height;
	}
	public void moveMe()
	{
		pastY = y;
		pastX = x;
		x += xVelocity;
		y += yVelocity;
		yVelocity += Screen.gravity;
	}
	public void collision(Chunk chunk)
	{
		for(int i = 0; i < chunk.chunkSize; i ++)
		{
			for(int j = 0; j < chunk.chunkSize; j ++)
			{
				if(chunk.blocks[i][j].collisionActive)
				{
					if(collision(chunk.blocks[i][j]))
					{
						Screen.collisions.add(new CollisionContainer(this, chunk.blocks[i][j]));
					}
				}
			}
		}
	}
}