package finalGame.inventoryStuff;
import finalGame.equipable.BlockPlacer;
import finalGame.Screen;
import finalGame.materialClasses.BlockCharacteristic;

public class ItemBlock extends InventoryObject
{
	BlockCharacteristic characteristic;
	public ItemBlock(BlockCharacteristic characteristic)
	{
		super(characteristic.name);
		this.characteristic = characteristic;
		loadImage(characteristic.img);
	}

	@Override
	public void onInInventoryClick()
	{
		Screen.mc.weapon.unSelected();
		Screen.mc.weapon = new BlockPlacer(characteristic, this);
	}
}