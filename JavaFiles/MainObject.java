public class MainObject //everything must be a main object so we can put everything in a giant arraylist and access everything easily
{
	double x;
	double y;
	double width;
	double height;
	public MainObject(double x, double y, double width, double height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.number = number;
	}
	public boolean collision(MainObject one)
	{
        if(one.x < this.x + this.width && one.x + one.width > this.x && one.y + one.height > this.y && one.y < this.y + this.height)
            return true;
        else
            return false;
    }
}
