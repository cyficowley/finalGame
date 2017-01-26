import java.awt.Color;
import java.awt.Graphics;
public class StoneCharacteristic extends BlockCharacteristic
{
	Color color;
	public StoneCharacteristic(Block block)
	{
		super(block);
		int rgb = (int)(Math.random() * 100 + 50);
		color = new Color(rgb,rgb,rgb);
	}
	@Override
	public void drawMe(Graphics g)
	{
		g.setColor(color);
		g.fillRect((int)(block.xIndex * Screen.blockWidth - Screen.screenX), (int)(block.yIndex * Screen.blockWidth- Screen.screenY), (int)Screen.blockWidth, (int)Screen.blockWidth);
	}
}