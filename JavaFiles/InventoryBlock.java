package javaFiles;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
public class InventoryBlock{
	public static int blockSize = 64;
	public int xPos;
	public int yPos;
	public Color blockColor;
	public InventoryObject inventoryObject;
	boolean empty = false;

	public InventoryBlock(int x, int y){
		this.xPos = x;
		this.yPos = y;
		blockColor = new Color(0.2f, 0.2f, 0.2f, 0.3f);
		empty = true;
	}

	public InventoryBlock(int x, int y, InventoryObject inventoryObject){
		this.xPos = x;
		this.yPos = y;
		blockColor = new Color(0.2f, 0.2f, 0.2f, 0.3f);
		this.inventoryObject = inventoryObject;
	}
	public void drawMe(Graphics2D g){
		g.setColor(blockColor);
		g.fillRect(xPos, yPos, blockSize, blockSize);
	}
}