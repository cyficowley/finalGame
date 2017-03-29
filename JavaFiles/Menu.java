package javaFiles;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
public abstract class Menu{
	public static boolean visible = false;
	public int x;
	public int y;
	public int width;
	public int height;
	public int buttonHeight;
	public static Color menuColor = new Color(0.2f, 0.2f, 0.2f, 0.4f);
	ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	
	public Menu(){
		this.buttonHeight = 80;
	}

	public void drawMe(Graphics2D g){
		if(visible){
			g.setColor(menuColor);
			g.fillRect(x,y,width,height);
		}
	}

	public abstract void setVisible(boolean visible);

	public final void setup(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public final void setup(int x, int y, int width, int height, String[] buttonStrings){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		for(int i = 0; i < buttonStrings.length; i++){
			buttons.add(new MenuButton(x, y + (height - buttons.size() * (buttons.size() * buttonHeight))/(buttons.size()+1) + (i * buttonHeight), width, buttonHeight, buttonStrings[i]));
		}
	}
}