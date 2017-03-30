package javaFiles;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
public class Button extends MainObject{
	Font arial = new Font("Arial", Font.BOLD, 32);
	String buttonText;
	double textX;
	double textY;
	double textWidth;
	double textHeight;
	boolean setup = false;
	Color buttonColor = new Color(130, 130, 130);
	Color[] buttonColorChange = new Color[2];
	

	public Button(double x, double y, double width, double height, String text){
		super(x,y,width,height);
		this.buttonText = text;
		buttonColorChange[0] = buttonColor;
		buttonColorChange[1] = buttonColor.darker();

	}
	public Button(double x, double y, double width, double height, String text, Color color){
		super(x,y,width,height);
		this.buttonText = text;
		this.buttonColor = color;
		buttonColorChange[0] = buttonColor;
		buttonColorChange[1] = buttonColor.darker();
	}

	public void drawMe(Graphics2D g){
		if(!setup){
			FontMetrics arialMetrics = g.getFontMetrics(arial);
			this.textHeight = arialMetrics.getHeight();
			this.textWidth = arialMetrics.stringWidth(buttonText); 
			this.textX = x + (width - textWidth)/2;
			this.textY = y + (height - textHeight)/2 + textHeight;
			setup = true;
		}
		g.setFont(arial);
		g.setColor(buttonColor);
		g.fillRect((int)x,(int)y,(int)width, (int)height);
		g.setColor(Color.white);
		g.drawString(buttonText,(int)textX,(int)textY);
	}

	public void collision(){
		
		if(Screen.mouseX >= x
			&& Screen.mouseX <= x + width
			&& Screen.mouseY >= y
			&& Screen.mouseY <= y + height)
		{
			buttonColor = buttonColorChange[1];
			if(Screen.mouseDown)
				Screen.menuButtonClick(buttonText);
		} else{
			buttonColor = buttonColorChange[0];
		}
	}

}