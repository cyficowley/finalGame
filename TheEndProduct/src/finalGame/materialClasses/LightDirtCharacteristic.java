package finalGame.materialClasses;
import finalGame.worldGen.Block;
import finalGame.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

public class LightDirtCharacteristic extends BlockCharacteristic // copy this example of a class for other materials
{
	Color color;
	static ArrayList<BufferedImage> imageArray = new ArrayList<BufferedImage>();
	public static boolean hasLoaded = false;
	int rand;
	public LightDirtCharacteristic(Block block)
	{ // import randomly one of the two images here and rotate pi/2 * (int)(MAth.random() * 4) degrees then set it to the image it will draw
		super(block);
		breakable = true;
		hardness = 80;

		name = "light dirt";
		rand = (int)(Math.random() * imageArray.size());

		if(hasLoaded == false){
			loadImg();
			hasLoaded = true;
		}

		img = imageArray.get(rand);
	}
	@Override
	public void drawMe(Graphics2D g)
	{
		g.drawImage(img,(int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY),null);
		super.drawMe(g);
	}
	@Override
	public void drawMe(Graphics2D g, Color color)
	{
		g.setColor(color); // leave this one
		g.fillRect((int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY), (int)Screen.blockWidth, (int)Screen.blockWidth);
	}

	public void loadImg()
	{
		try{
			imageArray.add(toCompatibleImage(ImageIO.read(new File("res/images/light_dirt_1.png"))));
			imageArray.add(rotate90(toCompatibleImage(ImageIO.read(new File("res/images/light_dirt_1.png")))));
			imageArray.add(rotate180(toCompatibleImage(ImageIO.read(new File("res/images/light_dirt_1.png")))));
			imageArray.add(rotate270(toCompatibleImage(ImageIO.read(new File("res/images/light_dirt_1.png")))));
			imageArray.add(toCompatibleImage(ImageIO.read(new File("res/images/light_dirt_2.png"))));
			imageArray.add(rotate90(toCompatibleImage(ImageIO.read(new File("res/images/light_dirt_2.png")))));
			imageArray.add(rotate180(toCompatibleImage(ImageIO.read(new File("res/images/light_dirt_2.png")))));
			imageArray.add(rotate270(toCompatibleImage(ImageIO.read(new File("res/images/light_dirt_2.png")))));
			imageArray.add(toCompatibleImage(ImageIO.read(new File("res/images/light_dirt_3.png"))));
			imageArray.add(rotate90(toCompatibleImage(ImageIO.read(new File("res/images/light_dirt_3.png")))));
			imageArray.add(rotate180(toCompatibleImage(ImageIO.read(new File("res/images/light_dirt_3.png")))));
			imageArray.add(rotate270(toCompatibleImage(ImageIO.read(new File("res/images/light_dirt_3.png")))));
		} catch (IOException e) {}

	}
	private BufferedImage toCompatibleImage(BufferedImage image)
	{
	    // obtain the current system graphical settings
	    GraphicsConfiguration gfx_config = GraphicsEnvironment.
	        getLocalGraphicsEnvironment().getDefaultScreenDevice().
	        getDefaultConfiguration();

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
 