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

public class DroppedItem extends MovingObject
{
	BlockCharacteristic characteristic;
	public DroppedItem(Block block)
	{ 
		super(block.x + Screen.blockWidth * .15, block.y +Screen.blockWidth * .15, Screen.blockWidth * .7, Screen.blockWidth * .7);
		if(block.characteristic.img!= null)
		{
			block.characteristic.img = toCompatibleImage(block.characteristic.img);
		}
		characteristic = block.characteristic; 
		characteristic.percentBroken = 0;
	}
	public void drawMe(Graphics2D g)
	{
		g.drawImage(characteristic.img,(int)(x - Screen.screenX), (int)(y- Screen.screenY),null);
	}

	@Override
	public void moveMe()
	{
		super.moveMe();
		for(TouchData each : touched)
		{
			if(each.touched.type.equals("mc"))
			{
				Screen.inventory.addObject(new ItemBlock(characteristic));
				Screen.movingObjects.remove(this);
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
	    BufferedImage new_image = gfx_config.createCompatibleImage((int)(Screen.blockWidth * .7), (int)(Screen.blockWidth * .7), image.getTransparency());

	    // get the graphics context of the new image to draw the old image on
	    Graphics2D g2d = (Graphics2D) new_image.getGraphics();

	    // actually draw the image and dispose of context no longer needed
	    g2d.drawImage(image, 0, 0,(int)(Screen.blockWidth*.7), (int)(Screen.blockWidth*.7),null);
	    g2d.dispose();

	    // return the new optimized image
	    return new_image; 
	}
}