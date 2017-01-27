import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class StoneCharacteristic extends BlockCharacteristic // copy this example of a class for other materials
{
	Color color;
	BufferedImage img;
	public StoneCharacteristic(Block block)
	{ // import randomly one of the two images here and rotate pi/2 * (int)(MAth.random() * 4) degrees then set it to the image it will draw
		super(block);

		int rand = 0 + (int)(Math.random() * ((2 - 0) + 1));
		img = null;

		if(rand == 0){
			try{
				img = ImageIO.read(new File("images/stone_1.png"));
			} catch (IOException e) {}
		}
		if(rand == 1){
			try{
				img = ImageIO.read(new File("images/stone_2.png"));
			} catch (IOException e) {}
		}
		if(rand == 2){
			try{
				img = ImageIO.read(new File("images/stone_3.png"));
			} catch (IOException e) {}
		}

		int rgb = (int)(Math.random() * 100 + 50);
		color = new Color(rgb,rgb,rgb);
	}
	@Override
	public void drawMe(Graphics g)
	{
		//g.setColor(color); // make this not fill a rect but draw the image
		//g.fillRect((int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY), (int)Screen.blockWidth, (int)Screen.blockWidth);
		g.drawImage(img, (int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY), null);
	}
	@Override
	public void drawMe(Graphics g, Color color)
	{
		g.setColor(color); // leave this one
		g.fillRect((int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY), (int)Screen.blockWidth, (int)Screen.blockWidth);
	}
}
