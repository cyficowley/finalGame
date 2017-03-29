package javaFiles;
public class CollisionContainer
{
	MovingObject one;
	MovingObject two;
	Block three;
	boolean first;
	public CollisionContainer(MovingObject one, MovingObject two)
	{
		this.one = one;
		this.two = two;
		first = true;
	}
	public CollisionContainer(MovingObject one, Block three)
	{
		this.one = one;
		this.three = three;
	}
	public void run()
	{	
		if(first)
		{
			if(one.type.equals("mc") && two.type.equals("bullet") ||two.type.equals("mc") && one.type.equals("bullet"))
			{
			}
			else if(one.collision(two))
			{
				boolean oneLesser = false;
				boolean xCollision = false;
				TouchData touchedOne;
				TouchData touchedTwo;

				boolean oneAbove;
				boolean oneLeft;
				if(one.pastY + one.height/2 > two.pastY + two.height/2)
				{
					oneAbove = false;
				}
				else
				{
					oneAbove = true;
				}
				if(one.pastX + one.width/2 > two.pastX + two.width/2)
				{
					oneLeft = false;
				}
				else
				{
					oneLeft = true;
				}
				double differenceY;
				double differenceX;
				if(oneAbove)
				{
					differenceY = Math.abs((one.pastY + one.height- two.pastY));
				}
				else
				{
					differenceY = Math.abs((one.pastY - two.height- two.pastY));
				}
				if(oneLeft)
				{
					differenceX = Math.abs((one.pastX + one.width- two.pastX));
				}
				else
				{
					differenceX = Math.abs((one.pastX - two.width- two.pastX));
				}
				
				if(differenceX < differenceY)
				{
					xCollision = true;
					oneLesser = oneLeft;
				}
				else
				{
					xCollision = false;
					oneLesser = oneAbove;
				}
				if(xCollision)
		        {
		    		one.pastX = one.x;
		    		two.pastX = two.x;
					if(oneLesser)
					{
						double deltaX = one.x + one.width - two.x;
						one.x -= deltaX * (Math.abs(one.xVelocity) / (Math.abs(one.xVelocity) + Math.abs(two.xVelocity)));
						two.x += deltaX * (Math.abs(two.xVelocity) / (Math.abs(one.xVelocity) + Math.abs(two.xVelocity)));
						touchedOne = new TouchData(3);
						touchedTwo = new TouchData(2);
					}
					else
					{
						double deltaX = two.x + two.width - one.x;
						one.x += deltaX * (Math.abs(one.xVelocity) / (Math.abs(one.xVelocity) + Math.abs(two.xVelocity)));
						two.x -= deltaX * (Math.abs(two.xVelocity) / (Math.abs(one.xVelocity) + Math.abs(two.xVelocity)));
						touchedOne = new TouchData(2);
						touchedTwo = new TouchData(3);
					}
					double velocityConstant = (one.mass * one.xVelocity + two.mass * two.xVelocity)/(one.mass + two.mass);
					one.xVelocity = velocityConstant;
					two.xVelocity = velocityConstant;
				}
		        else
		        {
		    		one.pastY = one.y;
		    		two.pastY = two.y;
		        	if(oneLesser)
		        	{
		        		double deltaY =  one.y + one.height - two.y;
		        		one.y -= deltaY * (Math.abs(one.yVelocity) / (Math.abs(one.yVelocity) + Math.abs(two.yVelocity)));
		        		two.y += deltaY * (Math.abs(two.yVelocity) / (Math.abs(one.yVelocity) + Math.abs(two.yVelocity)));
		        		touchedOne = new TouchData(1);
		        		touchedTwo = new TouchData(0);
		        	}
		        	else
		        	{
		        		double deltaY =  two.y + two.height - one.y;
		        		one.y += deltaY * (Math.abs(one.yVelocity) / (Math.abs(one.yVelocity) + Math.abs(two.yVelocity)));
		        		two.y -= deltaY * (Math.abs(two.yVelocity) / (Math.abs(one.yVelocity) + Math.abs(two.yVelocity)));
		        		touchedOne = new TouchData(0);
		        		touchedTwo = new TouchData(1);
		        	}        	
		        	double velocityConstant = (one.mass * one.yVelocity + two.mass * two.yVelocity)/(one.mass + two.mass);
		        	one.yVelocity = velocityConstant;
		        	two.yVelocity = velocityConstant;
		        }

		        touchedTwo.add(one);
		        touchedOne.add(two);
		        one.touched.add(touchedOne);
		        two.touched.add(touchedTwo);
		    }
		}
		else
		{
			if(one.collision(three))
			{
				boolean oneLesser = false;
				boolean xCollision = false;
				TouchData touchedOne;
				TouchData touchedTwo;

				boolean oneAbove;
				boolean oneLeft;
				if(one.pastY + one.height/2 > three.y + three.height/2)
				{
					oneAbove = false;
				}
				else
				{
					oneAbove = true;
				}
				if(one.pastX + one.width/2 > three.x + three.width/2)
				{
					oneLeft = false;
				}
				else
				{
					oneLeft = true;
				}
				double differenceY;
				double differenceX;
				if(oneAbove)
				{
					differenceY = Math.abs((one.pastY + one.height- three.y));
				}
				else
				{
					differenceY = Math.abs((one.pastY - three.height- three.y));
				}
				if(oneLeft)
				{
					differenceX = Math.abs((one.pastX + one.width- three.x));
				}
				else
				{
					differenceX = Math.abs((one.pastX - three.width- three.x));
				}
				
				if(differenceX < differenceY)
				{
					xCollision = true;
					oneLesser = oneLeft;
				}
				else
				{
					xCollision = false;
					oneLesser = oneAbove;
				}


				if(xCollision)
		        {
		    		one.pastX = one.x;
					if(oneLesser)
					{
						double deltaX = one.x + one.width - three.x;
						one.x -= deltaX;
						one.xVelocity = 0;
						touchedOne = new TouchData(3);
						touchedTwo = new TouchData(2);
					}
					else
					{
						double deltaX = three.x + three.width - one.x;
						one.x += deltaX;
						one.xVelocity = 0;
						touchedOne = new TouchData(2);
						touchedTwo = new TouchData(3);
					}
				}
		        else
		        {
		    		one.pastY = one.y;
		        	if(oneLesser)
		        	{
		        		double deltaY = one.y + one.height - three.y;
						one.y -= deltaY;
						one.yVelocity = 0;
		        		touchedOne = new TouchData(1);
		        		touchedTwo = new TouchData(0);
		        	}
		        	else
		        	{
		        		double deltaY = three.y + three.height - one.y;
						one.y += deltaY;
						one.yVelocity = 0;
		        		touchedOne = new TouchData(0);
		        		touchedTwo = new TouchData(1);
		        	}
		        }
		        touchedTwo.add(one);
		        touchedOne.add(three);
		        one.touched.add(touchedOne);
		        three.touched.add(touchedTwo);
			}
		}
    }
}