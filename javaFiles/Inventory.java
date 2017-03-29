package javaFiles;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
public class Inventory{
	boolean visible = false;
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
		inventory[0][0] = new InventoryBlock(15,15, new ItemGun());
		inventory[0][1] = new InventoryBlock(15 + 74,15, new ItemSword());
	}
	public void drawInventory(Graphics2D g){
		if(!first)
		{
			fontMetrics = g.getFontMetrics(ariel);
			first = true;
		}
		if(visible)
		{
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