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

public class StoneCharacteristic extends BlockCharacteristic // copy this example of a class for other materials
{
	Color color;
	BufferedImage img;
	TexturePaint tp;
	static BufferedImage img1;
	static BufferedImage img2;
	static BufferedImage img3;
	static TexturePaint tp0;
	static TexturePaint tp1;
	static TexturePaint tp2;

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
			tp = tp0;
		}
		if(rand == 1){
			img = img2;
			tp = tp1;
		}
		if(rand == 2){
			img = img3;
			tp = tp2;
		}
	}
	@Override
	public void drawMe(Graphics2D g)
	{
		g.setPaint(tp1);
		g.fillRect((int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY), (int)Screen.blockWidth, (int)Screen.blockWidth);
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

		try{
			img1 = ImageIO.read(new File("images/stone_1.png"));
		} catch (IOException e) {}
		try{
			img2 = ImageIO.read(new File("images/stone_2.png"));
		} catch (IOException e) {}
		try{
			img3 = ImageIO.read(new File("images/stone_3.png"));
		} catch (IOException e) {}

		tp0 = new TexturePaint(img1, new Rectangle(0,0,(int)Screen.blockWidth, (int)Screen.blockWidth));
		tp1 = new TexturePaint(img2, new Rectangle(0,0,(int)Screen.blockWidth, (int)Screen.blockWidth));
		tp2 = new TexturePaint(img3, new Rectangle(0,0,(int)Screen.blockWidth, (int)Screen.blockWidth));
	}
}
