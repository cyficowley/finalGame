package finalGame.inventoryStuff;
import finalGame.equipable.Gun;
import finalGame.Screen;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class ItemGun extends InventoryObject
{
	public ItemGun()
	{
		super("Gun");
		try {
		    loadImage(ImageIO.read(new File("res/images/gunRight.png")));
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

	@Override
	public void onInInventoryClick()
	{
		Screen.mc.weapon.unSelected();
		Screen.mc.weapon = new Gun(Screen.mc);
	}
}