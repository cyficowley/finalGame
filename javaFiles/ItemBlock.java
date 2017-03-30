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
public class ItemBlock extends InventoryObject
{
	BlockCharacteristic characteristic;
	public ItemBlock(BlockCharacteristic characteristic)
	{
		super("Block");
		this.characteristic = characteristic;
		loadImage(characteristic.img);
	}

	@Override
	public void onInInventoryClick()
	{
		Screen.mc.weapon.unSelected();
	}
}