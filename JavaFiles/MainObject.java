package javaFiles;
import java.util.ArrayList;
import java.awt.Graphics2D;
public class MainObject //everything must be a main object so we can put everything in a giant arraylist and access everything easily
{
	double x; // also it will make it easy to do collision between anything
	double y;
	double width;
	double height;
	boolean fixed;
	String type = "";
	boolean removeMe= false;
	public MainObject(double x, double y, double width, double height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public MainObject(double x, double y, double width, double height, boolean fixed)
	{
		this.fixed = fixed;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public boolean collision(MainObject one)
	{
        if(one.x < this.x + this.width && one.x + one.width > this.x && one.y + one.height > this.y && one.y < this.y + this.height)
            return true;
        else
            return false;
    }
    public void removeMe()
    {
    	Screen.movingObjects.remove(this);
    	Screen.enemies.remove(this);
    }
}