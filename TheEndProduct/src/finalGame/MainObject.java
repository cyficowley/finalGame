package finalGame;
import java.util.ArrayList;
import java.awt.Graphics2D;
public class MainObject //everything must be a main object so we can put everything in a giant arraylist and access everything easily
{
	public double x; // also it will make it easy to do collision between anything
	public double y;
	public double width;
	public double height;
	public boolean fixed;
	public String type = "";
	public boolean removeMe= false;
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
        return one.x < this.x + this.width && one.x + one.width > this.x && one.y + one.height > this.y && one.y < this.y + this.height;
    }
    public void removeMe()
    {
    	Screen.movingObjects.remove(this);
    	Screen.enemies.remove(this);
    }
}