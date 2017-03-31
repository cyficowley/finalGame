package javaFiles;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Color;
public class InventoryBlock extends MainObject{
	public static int blockSize = 64;
	public Color blockColor;
	public InventoryObject inventoryObject;
	boolean empty = false;
	boolean first = true;
	int yChange = 0;
	int maxHeight = 0;

	public InventoryBlock(int x, int y){
		super(x,y,blockSize,blockSize);
		blockColor = new Color(0.6f, 0.6f, 0.6f, 0.5f);
		empty = true;
	}

	public InventoryBlock(int x, int y, InventoryObject inventoryObject){
		super(x,y,blockSize,blockSize);
		blockColor = new Color(0.6f, 0.6f, 0.6f, 0.5f);
		this.inventoryObject = inventoryObject;
	}
	public void drawMe(Graphics2D g , FontMetrics fontMetrics){
		g.setColor(blockColor);
		g.fillRect((int)x, (int)y, (int)blockSize, (int)blockSize);
		if(first && !empty)
		{
			while(fontMetrics.stringWidth(inventoryObject.name) > width)
			{
				inventoryObject.name = inventoryObject.name.substring(0, inventoryObject.name.length() -2);
			}
			yChange = fontMetrics.getMaxDescent();
			maxHeight = fontMetrics.getHeight();
			first = false;
		}
		if(!empty)
		{
			g.drawImage(inventoryObject.image, (int)(x + blockSize/8), (int)(y + blockSize/32), (blockSize *3/4), (blockSize * 3/4), null);
			g.setColor(Color.black);
			g.drawString(inventoryObject.name, (int)x,(int)(y + height - yChange));
			g.drawString(Integer.toString(inventoryObject.number),(int)x,(int)(y + maxHeight));
		}

	}
	public InventoryObject setInventoryObject(InventoryObject input)
	{
		InventoryObject temp = inventoryObject;
		inventoryObject = input;
		return temp;
	}
	public void checkCollision()
	{
		if(Screen.mouseFirstDown && collision(Screen.mouseMainObject))
		{
			inventoryObject.onInInventoryClick();
		}
	}
}