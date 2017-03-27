package javaFiles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import inventoryItems.*;
public class Inventory
{
	boolean showing = true;
	Font ariel = new Font("Ariel", Font.PLAIN, 36);
	ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
	Color backgroundColor = new Color(100,100,100,155);
	int x; 
	int actualY; 
	int width;
	int actualHeight;

	public Inventory()
	{
		items.add(new InventoryItem("pswag"));
		items.add(new InventoryItem("pswag"));
		items.add(new InventoryItem("pswag"));
		items.add(new InventoryItem("pswag"));
		items.add(new InventoryItem("pswag"));
		items.add(new InventoryItem("pswag"));
		items.add(new InventoryItem("pswag"));
		items.add(new WhiteLongSword());
		x = (int)(Screen.screenWidth /16);
		actualY = (int)(Screen.screenHeight /16);
		width = (int)(Screen.screenWidth /8 *7);
		actualHeight = (int)(Screen.screenHeight /8*7);
	}

	public void drawMe(Graphics2D g)
	{
		if(showing)
		{
			g.setColor(backgroundColor);
			g.fillRect(x,actualY,width,actualHeight);
			FontMetrics metrics = g.getFontMetrics(ariel);
			int y = (int)(metrics.getHeight()*2 + Screen.screenHeight /16);
			int height = (int)(actualHeight + actualY - y);
			g.setColor(Color.black);
			g.setFont(ariel);
			g.drawString("Inventory",(int)(Screen.screenWidth/2 - metrics.stringWidth("Inventory") /2), (int)(metrics.getHeight() + Screen.screenHeight /16));
			if(showing)
			{
				for(int i = 0; i < items.size(); i ++)
				{
					items.get(i).x = x + width / 6 * (i%6) + width/80;
					items.get(i).y = y + height / 4 * (i/6) + height/80;
					items.get(i).width = width/6 - width/40;
					items.get(i).height = height/4 - height/40;
					items.get(i).drawMe(g);
				}
			}
		}
	}

	public void collision()
	{
		if(Screen.mouseFirstDown)
		{
			for(InventoryItem each : items)
			{
				if(each.x < Screen.mouseX&& each.x + each.width > Screen.mouseX && each.y + each.height > Screen.mouseY && each.y < Screen.mouseY)
				{
		            each.doAction();
				}
			}
		}
	}
}