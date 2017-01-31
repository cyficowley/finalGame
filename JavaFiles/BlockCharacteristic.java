import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.Object;
import java.awt.geom.AffineTransform;

public class BlockCharacteristic // superClass to hold everything and make it easier to do
{
	
	int randrotation; //integer used to determine random rotation
	int width;
	int length;
	Block block; // this will have the characteristics of the block like the color the type the interactions you can have with it
	public BlockCharacteristic(Block block)
	{ // we don't want to put that all into the block class so now we can make a bunch of these classes and have it be more consise
		this.block = block;
	}
	public void drawMe(Graphics2D g)
	{}
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





		
			
	


}