import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.ArrayList;
public class Bullet extends MainObject
{
	public Bullet(double speed, boolean gravity, double radius, double x, double y)
	{
		super(x,y,radius, radius);
	}
}