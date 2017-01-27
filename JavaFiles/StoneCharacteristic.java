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
	static BufferedImage img;
	static BufferedImage img1;
	static BufferedImage img2;
	static BufferedImage img3;
	public static boolean hasLoaded = false;
	int rand;
	public StoneCharacteristic(Block block)
	{ // import randomly one of the two images here and rotate pi/2 * (int)(MAth.random() * 4) degrees then set it to the image it will draw
		super(block);

		rand = (int)(Math.random() * (3));

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
	}
	@Override
	public void drawMe(Graphics g)
	{
		//g.setColor(color); // make this not fill a rect but draw the image
		//g.fillRect((int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY), (int)Screen.blockWidth, (int)Screen.blockWidth);
		g.drawImage(img, (int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY),20,20, null);
	}
	@Override
	public void drawMe(Graphics g, Color color)
	{
		g.setColor(color); // leave this one
		g.fillRect((int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY), (int)Screen.blockWidth, (int)Screen.blockWidth);
	}

	public void loadImg(){
		img1 = null;
		img2 = null;
		img3 = null;

		try{
			img1 = ImageIO.read(new File("images/stone_1.png"));
		} catch (IOException e) {}
		try{
			img2 = ImageIO.read(new File("images/stone_2.png"));
		} catch (IOException e) {}
		try{
			img3 = ImageIO.read(new File("images/stone_3.png"));
		} catch (IOException e) {}
	}
}
