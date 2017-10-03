package finalGame.usefulClasses;
import java.awt.Color;
import java.awt.Graphics2D;
public class Menu{
	boolean visible = false; //visiblity of the menu
	public double x; // x position of the menu
	public double y; // y position of the menu
	public double width; // width of the menu
	public double height; // height of the menu
	boolean outlineVisibility = true; // outline visibility of the menu
	Color menuColor = new Color(0.2f, 0.2f, 0.2f, 0.4f); // defualt menu color
	
	public Menu(double x, double y, double width, double height){ //sets up a basic menu
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public Menu(double x, double y, double width, double height, Color color){ // sets up a menu with a custom color
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.menuColor = color;
	}

	public void drawMe(Graphics2D g){ // draws the menu box
		if(visible){
			g.setColor(menuColor);
			g.fillRect((int)x,(int)y,(int)width,(int)height);
			if(outlineVisibility){ // draws the menu outline if visible
				g.setColor(Color.black);
				g.drawRect((int)x,(int)y,(int)width,(int)height);
			}
		}
	}

	public void toggleVisible(){ // toggles the visibility of the menu
		visible = !visible;
	}
}