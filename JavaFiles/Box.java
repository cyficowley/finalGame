package javaFiles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.ArrayList;
public class Box
{
	Holder[] holders = new Holder[4]; // top right bottom left

	double centerPointY;
	double centerPointX;
	double angle = 0;
	double startX=10;
	double endX=-10;
	boolean active = false;
	double width;
	double height;
	public Box(double width, double height, double centerPointX, double centerPointY, double centerBoxX, double centerBoxY, double angle)
	{
		this.width = width;
		this.height = height;
		this.centerPointX = centerPointX;
		this.centerPointY = centerPointY;
		this.angle = angle;
		holders[0] = new Holder(new Line(centerBoxX, centerBoxY - height/2,0,width/2, width/2), 0);
		holders[2] = new Holder(new Line(centerBoxX, centerBoxY + height/2,0,width/2, width/2), Math.PI);
		holders[1] = new Holder(new Line(centerBoxX+ width/2, centerBoxY ,0,height/2, height/2), Math.PI/2);
		holders[3] = new Holder(new Line(centerBoxX- width/2, centerBoxY ,0,height/2, height/2), Math.PI*3/2);
	}


	public class Holder
	{
		Line line;
		private double startAngle;
		private double endAngle;
		private double length;
		public Holder(Line inputLine, double endAngle)
		{
			this.line = inputLine;
			this.endAngle = endAngle;
			length = Math.sqrt(Math.pow(line.centerPointX - centerPointX,2)+Math.pow(line.centerPointY - centerPointY,2));
			startAngle = Math.atan2(line.centerPointY - centerPointY,line.centerPointX - centerPointX);
		}
	}
	public void calculateMe()
	{
		for(Holder each : holders)
		{
			each.line.angle = each.endAngle + angle;
			each.line.centerPointX = Math.cos(each.startAngle + angle)* each.length + centerPointX;
			each.line.centerPointY = Math.sin(each.startAngle + angle)* each.length  + centerPointY;
			each.line.calculateMe();
		}
	}
	public void drawMe(Graphics g)
	{
		for(Holder each : holders)
		{
			if(active)
			{
				each.line.active = true;
			}
			each.line.drawMe(g);
		}
		active = false;
	}
}