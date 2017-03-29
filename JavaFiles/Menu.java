package javaFiles;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
public class Menu{
	boolean visible = false;
	private int x;
	private int y;
	private int width;
	private int height;
	public static Color menuColor = new Color(0.2f, 0.2f, 0.2f, 0.4f);
	ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	public Menu(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		buttons.add(new MenuButton(x, y, width, 80, "Test"));
	}

	public void drawMe(Graphics2D g){
		if(visible){
			g.setColor(menuColor);
			g.fillRect(x,y,width,height);
			for (int i = 0; i < buttons.size() ; i++ ) {
				buttons.get(i).drawMe(g);
			}
		}
		
	}
}