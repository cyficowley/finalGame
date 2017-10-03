package finalGame.usefulClasses;
import finalGame.Screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
public class Line
{
	public double yInt= 0;	//y = mx +b
	public double centerPointY;
	public double centerPointX;
	public double angle = 0;
	public double slope =0;
	public double startX=10;
	public double endX=-10;
	public double lengthLeft=50;
	public double lengthRight=50;
	public boolean active = false;
	public Line(double centerPointX, double centerPointY, double angle)
	{
		this.centerPointX = centerPointX;
		this.centerPointY = centerPointY;
		this.angle = angle;
	}
	public Line(double centerPointX, double centerPointY, double angle, double lengthLeft, double lengthRight)
	{
		this.centerPointX = centerPointX;
		this.centerPointY = centerPointY;
		this.lengthRight = lengthRight;
		this.lengthLeft = lengthLeft;
		this.angle = angle;
	}
	public void calculateMe()
	{
		if(angle % Math.PI == Math.PI/2)
		{
			angle += .001;
		}
		slope = Math.tan(angle);
		startX = centerPointX - Math.cos(angle)*lengthLeft;
		endX = centerPointX + Math.cos(angle)*lengthRight;
		yInt = -slope * centerPointX + centerPointY;
		if(startX > endX)
		{
			double temp = startX;
			startX = endX;
			endX = temp;
		}
	}
	public void drawMe(Graphics g)
	{
		if(active)
		{
			g.setColor(Color.blue);
		}
		else
		{
			g.setColor(Color.black);
		}
		active = false;
		g.drawLine((int)(startX - Screen.screenX),(int)(Math.tan(angle) * startX + yInt - Screen.screenY), (int)(endX- Screen.screenX),(int)(Math.tan(angle) * endX + yInt - Screen.screenY));
	}
}