package finalGame.inventoryStuff;
import finalGame.Screen;

import java.awt.*;

public class Inventory{
	public boolean visible = false;
	public static InventoryBlock[][] inventory = new InventoryBlock[4][8];
	Font ariel = new Font("Ariel", Font.PLAIN, 10);
	boolean first = false;
	FontMetrics fontMetrics;
	public Inventory(){
		for(int r = 0; r < inventory.length; r++)
		{
			for(int c = 0; c < inventory[r].length; c++)
			{
				inventory[r][c] = new InventoryBlock(c *(InventoryBlock.blockSize + 10) + 15, r *(InventoryBlock.blockSize + 10) + 15);
			}
		}
		inventory[0][0] = new InventoryBlock(15,15, new ItemSword());
		inventory[0][0].selected = true;
		inventory[0][0].empty = false;
	}
	public void drawInventory(Graphics2D g){
		if(!first)
		{
			fontMetrics = g.getFontMetrics(ariel);
			first = true;
		}
		if(visible)
		{
			g.setColor(new Color(155,155,155,100));
			g.fillRect(15,15,8 * (InventoryBlock.blockSize + 10) -10, 4 * (InventoryBlock.blockSize + 10) -10);
			g.setFont(ariel);
			for(int r = 0; r < inventory.length; r++)
			{
				for(int c = 0; c < inventory[r].length; c++)
				{
					inventory[r][c].drawMe(g ,fontMetrics);
				}
			}
		}
	}
	public void switchWeapon()
	{
		int[] current = new int[2];
		outerloop:
		for(int r = 0; r < inventory.length; r++)
		{
			for(int c = 0; c < inventory[r].length; c++)
			{
				if(inventory[r][c].selected)
				{
					current[0] = r;
					current[1] = c;
					break outerloop;
				}
			}
		}
		outerloop:
		for(int r = current[0]; r < current[0]+inventory.length; r++)
		{
			for(int c = current[1] +1; c%inventory[r % inventory.length].length != current[1]; c++)
			{
				if(!inventory[r%inventory.length][c%inventory[r% inventory.length].length].empty)
				{
					inventory[r%inventory.length][c%inventory[r% inventory.length].length].inventoryObject.onInInventoryClick();
					for(int i = 0; i < inventory.length; i++)
					{
						for(int p = 0; p < inventory[i].length; p++)
						{
							inventory[i][p].selected = false;
						}
					}
					inventory[r%inventory.length][c%inventory[r% inventory.length].length].selected = true;
					break outerloop;
				}
			}
		}
	}
	public void addObject(InventoryObject input)
	{
		boolean continuing = true;
		outerloop:
		for(int r = 0; r < inventory.length; r++)
		{
			for(int c = 0; c < inventory[r].length; c++)
			{
				if(!inventory[r][c].empty)
				{
					if(inventory[r][c].inventoryObject.name.equals(input.name))
					{
						inventory[r][c].inventoryObject.number ++;
						continuing = false;
						break outerloop;
					}
				}
			}
		}
		if(continuing)
		{
			outerloop1:
			for(int r = 0; r < inventory.length; r++)
			{
				for(int c = 0; c < inventory[r].length; c++)
				{
					if(inventory[r][c].empty)
					{
						inventory[r][c].inventoryObject = input;
						inventory[r][c].empty = false;
						break outerloop1;
					}
				}
			}
		}
	}

	public void collision()
	{
		if(visible)
		{
			for(int r = 0; r < inventory.length; r++)
			{
				for(int c = 0; c < inventory[r].length; c++)
				{
					inventory[r][c].checkCollision();
				}
			}
		}
	}
}