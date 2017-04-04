package javaFiles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class ItemGun extends InventoryObject
{
	public ItemGun()
	{
		super("Gun");
		try {
		    loadImage(ImageIO.read(new File("javaFiles/images/gunRight.png")));
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

	@Override
	public void onInInventoryClick()
	{
		Screen.mc.weapon.unSelected();
		Screen.mc.weapon = new Gun(Screen.mc);
	}
}