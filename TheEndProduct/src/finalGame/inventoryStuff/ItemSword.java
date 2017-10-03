package finalGame.inventoryStuff;
import finalGame.Screen;
import finalGame.equipable.Sword;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class ItemSword extends InventoryObject
{
	public ItemSword()
	{
		super("Sword");
		try {
		    loadImage(ImageIO.read(new File("res/images/longSwordWhite.png")));
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

	@Override
	public void onInInventoryClick()
	{
		Screen.mc.weapon.unSelected();
		Screen.mc.weapon = new Sword(Screen.mc);
	}
}