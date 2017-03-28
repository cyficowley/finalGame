package javaFiles.inventoryItems;
import javaFiles.*;
public class WhiteLongSword extends InventoryItem
{
	public WhiteLongSword()
	{
		super("White Long Sword");
	}
	@Override
	public void doAction()
	{
		Screen.mc.weapon = new Sword(Screen.mc);
	}
}