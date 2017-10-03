package finalGame.usefulClasses;

import java.awt.Graphics;

public class Box
{
	public Holder[] holders = new Holder[4]; // top right bottom left

	public double centerPointY;
	public double centerPointX;
	public double angle = 0;
	public double startX=10;
	public double endX=-10;
	public boolean active = false;
	public double width;
	public double height;
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
		public Line line;
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