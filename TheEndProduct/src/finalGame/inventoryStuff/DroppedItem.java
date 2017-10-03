package finalGame.inventoryStuff;
import finalGame.MovingObject;
import finalGame.Screen;
import finalGame.inventoryStuff.ItemBlock;
import finalGame.materialClasses.BlockCharacteristic;
import finalGame.usefulClasses.TouchData;
import finalGame.worldGen.Block;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;

public class DroppedItem extends MovingObject
{
	BlockCharacteristic characteristic;
	public DroppedItem(Block block)
	{ 
		super(block.x + Screen.blockWidth * .15, block.y +Screen.blockWidth * .15, Screen.blockWidth * .7, Screen.blockWidth * .7);
		type = "itemBlock";
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