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
	

	public Button(double x, double y, double width, double height, String text){
		super(x,y,width,height);
		this.buttonText = text;
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
			buttonColor = new Color(60, 60, 60);
			if(Screen.mouseDown)
				clickedOn();
		} else{
			buttonColor = new Color(130, 130, 130);
		}
	}
	public void clickedOn(){
		Screen.closeWindow();
	}


}