package finalGame.usefulClasses;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
public class ButtonMenu extends Menu{
	String[] buttonStrings; //contains all strings for every button needed
	double buttonHeight; //the height of every button
	ArrayList<Button> buttons = new ArrayList<Button>(); //arraylist of buttons, contains all buttons for each menu

	public ButtonMenu(double x, double y, double width, double height, String[] buttonStrings){ //sets up multiple buttons for this menu with text
		super(x,y,width,height);
		this.buttonStrings = buttonStrings;
		buttonHeight = (height - (20 * (buttonStrings.length-1)))/buttonStrings.length;
		for(int i = 0; i < buttonStrings.length; i++){
			buttons.add(new Button(x, y + i*(buttonHeight + 20), width, buttonHeight, buttonStrings[i]));
		}
	}
	public ButtonMenu(double x, double y, double width, double height, Color color, String[] buttonStrings){ //sets up multiple buttons for this menu with text and a custom color
		super(x,y,width,height);
		this.buttonStrings = buttonStrings;
		buttonHeight = (height - (20 * (buttonStrings.length-1)))/buttonStrings.length;
		System.out.println("ButtonHeight: " + buttonHeight + "MenuHeight: " + height);
		for(int i = 0; i < buttonStrings.length; i++){
			buttons.add(new Button(x, 
				y + i*(buttonHeight + 20),
				width, 
				buttonHeight,
				buttonStrings[i],
				color));
		}
	}
	
	@Override
	public void drawMe(Graphics2D g){
		if(visible){
			for(int i = 0; i < buttonStrings.length; i++){
				buttons.get(i).drawMe(g); //draws each button
				buttons.get(i).collision(); //checks collision for each button
			}
			if(outlineVisibility){ //draws an outline around the menu
				g.setColor(Color.black);
				g.drawRect((int)x,(int)y,(int)width,(int)height);
			}
		}
	}
}