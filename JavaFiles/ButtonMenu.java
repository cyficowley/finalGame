package javaFiles;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
public class ButtonMenu extends Menu{
	String[] buttonStrings;
	double buttonHeight;
	ArrayList<Button> buttons = new ArrayList<Button>();

	public ButtonMenu(double x, double y, double width, double height, String[] buttonStrings){
		super(x,y,width,height);
		this.buttonStrings = buttonStrings;
		buttonHeight = (height - (20 * (buttonStrings.length-1)))/buttonStrings.length;
		System.out.println("ButtonHeight: " + buttonHeight + "MenuHeight: " + height);
		for(int i = 0; i < buttonStrings.length; i++){
			buttons.add(new Button(x, 
				y + i*(buttonHeight + 20),
				width, 
				buttonHeight,
				buttonStrings[i]));
		}
	}
	
	@Override
	public void drawMe(Graphics2D g){
		if(visible){
			for(int i = 0; i < buttonStrings.length; i++){
				buttons.get(i).drawMe(g);
				buttons.get(i).collision();
			}
			if(outlineVisibility){
				g.setColor(Color.black);
				g.drawRect((int)x,(int)y,(int)width,(int)height);
			}
		}
	}
}