package javaFiles;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
public class InventoryObject
{
	BufferedImage image;
	String name;
	int number = 1;

	public InventoryObject(String name)
	{
		this.name = name;
	}

	public void loadImage(BufferedImage image)
	{
		int height = image.getHeight();
		int width = image.getWidth();
		BufferedImage canvas;
		if(height > width)
		{
			canvas = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = canvas.createGraphics();
			g2d.drawImage(image, (height - width)/2, 0, null);
		}
		else if(width > height)
		{
			canvas = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = canvas.createGraphics();
			g2d.drawImage(image, 0, (width - height)/2, null);
		}
		else
		{
			canvas = image;
		}
		this.image = canvas;
	}

	public void actionLeft()
	{}

	public void actionRight()
	{}

	public void onInInventoryClick()
	{}
}