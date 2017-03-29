package javaFiles;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
public class MenuButton{
	Font arial = new Font("Arial", Font.BOLD, 16);
	public String buttonText;
	public int x;
	public int y;
	public int width;
	public int height;
	public int textX;
	public int textY;
	public int textWidth;
	public int textHeight;
	boolean setup = false;
	

	public MenuButton(int x, int y, int width, int height, String text){

		this.buttonText = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
		g.setColor(Color.gray);
		g.fillRect(x,y,width, height);
		g.setColor(Color.white);
		g.drawString(buttonText,textX,textY);
	}


}