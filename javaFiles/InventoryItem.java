package javaFiles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
public class InventoryItem extends MainObject
{
	String label = "";
	Font ariel = new Font("Ariel", Font.PLAIN, 18);
	public InventoryItem(String label)
	{
		super(0,0,0,0);
		this.label = label;
	}
	public void drawMe(Graphics2D g)
	{
		
		long timeToDraw = System.nanoTime();
		g.setColor(Color.white);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
		g.setFont(ariel);
		FontMetrics metrics = g.getFontMetrics(ariel);
		g.setColor(Color.black);
		g.drawString(label, (int)(x + 10), (int)(y + height - metrics.getMaxDescent()));
		System.out.println((System.nanoTime() - timeToDraw)/1000000.0);

	}
	public void doAction()
	{
		System.out.println(label);
	}
}