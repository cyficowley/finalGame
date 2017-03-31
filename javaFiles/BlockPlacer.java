package javaFiles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class BlockPlacer extends Weapon
{
	BlockCharacteristic characterisitic;
	boolean direction = false; //false = left, true = right

	public BlockPlacer(BlockCharacteristic characterisitic)
	{
		super(Screen.mc);
		this.characterisitic = characterisitic;
	}

	@Override
	public void moveMe()
	{
		
	}

	@Override
	public void drawMe(Graphics2D g)
	{
		if(mc.drawState == -1)
		{
			direction= false;
		}
		else if(mc.drawState == 1)
		{
			direction = true;
		}
		if(!direction)
		{
			g.drawImage(characterisitic.img,(int)(mc.x+mc.width/2 - Screen.screenX + 15), (int)(mc.y+mc.height/2 - Screen.screenY), null);
		}
		else
		{
			g.drawImage(characterisitic.img, (int)(mc.x+mc.width/2 - Screen.screenX - Screen.blockWidth* .7 -15), (int)(mc.y+mc.height/2 - Screen.screenY), null);
		}
	}
}