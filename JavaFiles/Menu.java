package javaFiles;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
public class Menu{
	public boolean visible = false;
	public double x;
	public double y;
	public double width;
	public double height;
	public boolean outlineVisibility = true;
	public static Color menuColor = new Color(0.2f, 0.2f, 0.2f, 0.4f);
	
	public Menu(double x, double y, double width, double height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public Menu(double x, double y, double width, double height, Color color){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.menuColor = color;
	}

	public void drawMe(Graphics2D g){
		if(visible){
			g.setColor(menuColor);
			g.fillRect((int)x,(int)y,(int)width,(int)height);
			if(outlineVisibility){
				g.setColor(Color.black);
				g.drawRect((int)x,(int)y,(int)width,(int)height);
			}
		}
	}

	public void toggleVisible(){
		visible = !visible;
	}
}