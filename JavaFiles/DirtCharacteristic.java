import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment; 
import java.awt.RenderingHints; 
import java.awt.Transparency; 
import java.net.URL; 
import javax.swing.ImageIcon; 

public class DirtCharacteristic extends BlockCharacteristic // copy this example of a class for other materials
{
	Color color;
	BufferedImage img;
	static BufferedImage img1;
	static BufferedImage img2;
	static BufferedImage img3;
	static BufferedImage img4;
	static BufferedImage img5;
	static BufferedImage img6;
	static BufferedImage img7;
	static BufferedImage img8;
	static BufferedImage img9;
	static BufferedImage img10;
	static BufferedImage img11;
	static BufferedImage img12;
	public static boolean hasLoaded = false;
	int rand;
	public DirtCharacteristic(Block block)
	{ // import randomly one of the two images here and rotate pi/2 * (int)(MAth.random() * 4) degrees then set it to the image it will draw
		super(block);

		rand = (int)(Math.random() * (12));

		if(hasLoaded == false){
			loadImg();
			hasLoaded = true;
		}

		if(rand == 0){
			img = img1;
		}
		if(rand == 1){
			img = img2;
		}
		if(rand == 2){
			img = img3;
		
		}
		if(rand == 3){
			img = img4;
		}
		if(rand == 4){
			img = img5;
		}
		if(rand == 5){
			img = img6;
		}
		if(rand == 6){
			img = img7;
		}
		if(rand == 7){
			img = img8;
		}
		if(rand == 8){
			img = img9;
		}
		if(rand == 9){
			img = img10;
		}
		if(rand == 10){
			img = img11;
		}
		if(rand == 11){
			img = img12;

		}
	}
	@Override
	public void drawMe(Graphics2D g)
	{
		g.drawImage(img,(int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY),null);
	}
	@Override
	public void drawMe(Graphics2D g, Color color)
	{
		g.setColor(color); // leave this one
		g.fillRect((int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY), (int)Screen.blockWidth, (int)Screen.blockWidth);
	}

	public void loadImg(){
		img1 = null;
		img2 = null;
		img3 = null;
		img4 = null;
		img5 = null;
		img6 = null;
		img7 = null;
		img8 = null;
		img9 = null;
		img10 = null;
		img11 = null;
		img12 = null;

		try{
			img1 = toCompatibleImage(ImageIO.read(new File("images/dirt_1.png")));
		} catch (IOException e) {}
		try{
			img2 = rotate90(toCompatibleImage(ImageIO.read(new File("images/dirt_1.png"))));
		} catch (IOException e) {}
		try{
			img3 = rotate180(toCompatibleImage(ImageIO.read(new File("images/dirt_1.png"))));
		} catch (IOException e) {}
		try{
			img4 = rotate270(toCompatibleImage(ImageIO.read(new File("images/dirt_1.png"))));
		} catch (IOException e) {}
		try{
			img5 = toCompatibleImage(ImageIO.read(new File("images/dirt_2.png")));
		} catch (IOException e) {}
		try{
			img6 = rotate90(toCompatibleImage(ImageIO.read(new File("images/dirt_2.png"))));
		} catch (IOException e) {}
		try{
			img7 = rotate180(toCompatibleImage(ImageIO.read(new File("images/dirt_2.png"))));
		} catch (IOException e) {}
		try{
			img8 = rotate270(toCompatibleImage(ImageIO.read(new File("images/dirt_2.png"))));
		} catch (IOException e) {}
		try{
			img9 = toCompatibleImage(ImageIO.read(new File("images/dirt_3.png")));
		} catch (IOException e) {}
		try{
			img10 = rotate90(toCompatibleImage(ImageIO.read(new File("images/dirt_3.png"))));
		} catch (IOException e) {}
		try{
			img11 = rotate180(toCompatibleImage(ImageIO.read(new File("images/dirt_3.png"))));
		} catch (IOException e) {}
		try{
			img12 = rotate270(toCompatibleImage(ImageIO.read(new File("images/dirt_3.png"))));
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
 