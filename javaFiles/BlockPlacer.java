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
	ItemBlock itemBlock;

	public BlockPlacer(BlockCharacteristic characterisitic, ItemBlock itemBlock)
	{
		super(Screen.mc);
		this.itemBlock = itemBlock;
		this.characterisitic = characterisitic;
	}

	@Override
	public void moveMe()
	{
		if(Screen.mouseFirstDown)
		{
			Block temp = Screen.getBlock((int)((Screen.mouseX + Screen.screenX)/Screen.blockWidth),(int)((Screen.mouseY + Screen.screenY)/Screen.blockWidth));
			if(temp.type == 0)
			{
				if(Math.sqrt(Math.pow(mc.x + mc.width/2 - temp.x -temp.width/2,2)+ Math.pow(mc.y + mc.height/2 - temp.y -temp.height/2,2)) < Screen.blockWidth * 7)
				{
					boolean rebuild = true;
					for(MainObject each : temp.containingChunk.containedObjects)
					{
						if(each.collision(temp))
						{
							rebuild = false;
							break;
						}
					}
					if(rebuild)
					{
						temp.rebuild(characterisitic.number);
						itemBlock.number --;
						if(itemBlock.number <=0)
						{
							unSelected();
							Screen.mc.weapon = new Weapon(Screen.mc);
							for(int r = 0; r < Screen.inventory.inventory.length; r++)
							{
								for(int c = 0; c < Screen.inventory.inventory[r].length; c++)
								{
									if(Screen.inventory.inventory[r][c].inventoryObject == itemBlock)
									{
										Screen.inventory.inventory[r][c] = new InventoryBlock(c *(InventoryBlock.blockSize + 10) + 15, r *(InventoryBlock.blockSize + 10) + 15);
									}
								}
							}
						}
					}
				}
			}
		}
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