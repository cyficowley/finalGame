package javaFiles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.Object;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment; 
import javax.imageio.ImageIO;

public class BlockCharacteristic // superClass to hold everything and make it easier to do
{
	double percentBroken = 0; // how broken the block is
	double hardness = 120; // how hard the block is to break
	int randrotation; //integer used to determine random rotation
	int width;
	int length;
	String name = "";
	boolean breakable = false;
	BufferedImage img;
	Block block; // this will have the characteristics of the block like the color the type the interactions you can have with it
	static boolean loaded = false; 
	static BufferedImage[] breaking = new BufferedImage[3];
	public BlockCharacteristic(Block block)
	{ // we don't want to put that all into the block class so now we can make a bunch of these classes and have it be more consise
		this.block = block;
		if(!loaded)
		{
			try{
				breaking[0] = toCompatibleImage(ImageIO.read(new File("javaFiles/images/break_0.png")));
				breaking[1] = toCompatibleImage(ImageIO.read(new File("javaFiles/images/break_1.png")));
				breaking[2] = toCompatibleImage(ImageIO.read(new File("javaFiles/images/break_2.png")));
			}catch(IOException e){}
			loaded = true;
		}
	}
	public void drawMe(Graphics2D g)
	{
		if(percentBroken * 4.0 >3)
		{
			g.drawImage(breaking[2],(int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY),null);
		}
		else if(percentBroken * 4.0 >2)
		{
			g.drawImage(breaking[1],(int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY),null);
		}
		else if(percentBroken *4.0 >1)
		{
			g.drawImage(breaking[0],(int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY),null);
		}
	}
	public void drawMe(Graphics2D g, Color color) 
	{} // this is for specific overide things

	public BufferedImage rotate90(BufferedImage image)
	{
		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage biFlip = new BufferedImage(height, width, image.getType());
		for(int i=0; i<width; i++)
        	for(int j=0; j<height; j++)
	            biFlip.setRGB(height-1-j, width-1-i, image.getRGB(i, j));
	    return biFlip;
	}

	public BufferedImage rotate180(BufferedImage image)
	{
		//rotate 180
		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage biFlip = new BufferedImage(height, width, image.getType());
		for(int i=0; i<width; i++)
        	for(int j=0; j<height; j++)
	            biFlip.setRGB(height-1-j, i, image.getRGB(i, j));
	    return biFlip;
			
	}

	public BufferedImage rotate270(BufferedImage image)
	{
		//rotate 270
		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage biFlip = new BufferedImage(height, width, image.getType());
		for(int i=0; i<width; i++)
        	for(int j=0; j<height; j++)
	            biFlip.setRGB(height-1-j, width-1-i, image.getRGB(j, i));
	    return biFlip;
	}
	public void breakBlock(Block block)
	{
		if(breakable)
		{
			percentBroken += 1/hardness;
			if(percentBroken >1)
			{
				Screen.movingObjects.add(new DroppedItem(block));
				Screen.movingObjects.get(Screen.movingObjects.size()-1).yVelocity = -2.5;
				block.rebuild(0);
			}
		}
	}
	private BufferedImage toCompatibleImage(BufferedImage image)
	{
	    // obtain the current system graphical settings
	    GraphicsConfiguration gfx_config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

	    /*
	     * if image is already compatible and optimized for current system 
	     * settings, simply return it
	     */
	    if (image.getColorModel().equals(gfx_config.getColorModel()))
	    {
	        return image;
	    }

	    // image is not optimized, so create a new image that is
	    BufferedImage new_image = gfx_config.createCompatibleImage((int)Screen.blockWidth, (int)Screen.blockWidth, image.getTransparency());

	    // get the graphics context of the new image to draw the old image on
	    Graphics2D g2d = (Graphics2D) new_image.getGraphics();

	    // actually draw the image and dispose of context no longer needed
	    g2d.drawImage(image, 0, 0,(int)Screen.blockWidth, (int)Screen.blockWidth,null);
	    g2d.dispose();

	    // return the new optimized image
	    return new_image; 
	}
}