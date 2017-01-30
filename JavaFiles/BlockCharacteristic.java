import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class BlockCharacteristic // superClass to hold everything and make it easier to do
{
	Block block; // this will have the characteristics of the block like the color the type the interactions you can have with it
	public BlockCharacteristic(Block block)
	{ // we don't want to put that all into the block class so now we can make a bunch of these classes and have it be more consise
		this.block = block;
	}
	public void drawMe(Graphics2D g)
	{}
	public void drawMe(Graphics2D g, Color color) 
	{} // this is for specific overide things
}