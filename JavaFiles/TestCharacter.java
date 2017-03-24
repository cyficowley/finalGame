import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TestCharacter extends Enemy
{
	BufferedImage characterDefault;

	Weapon weapon = new Gun(this);

	public TestCharacter(double x, double y, double width, double height, double damage)
	{
		super(x,y,width,height, damage); // this entire class is just temporary before we get a real main character class going

		try {
		    characterDefault = ImageIO.read(new File("images/characterDefault.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		this.speed = 4;
		this.type = "mc";
	}
	@Override
	public void moveMe()
	{
		super.moveMe();
		if(Screen.w)
		{
			jump();
		}
		if(Screen.d)
		{
			moveRight();
			direction = true;
		}
		if(Screen.a)
		{
			moveLeft();
			direction = false;
		}
		weapon.moveMe();
	}

	@Override
	public void drawMe(Graphics2D g) {
		g.drawImage(characterDefault, (int) (x - Screen.screenX -4.5), (int) (y - Screen.screenY - 4.5), (int) (width+9), (int) (height+4.5), null);
		weapon.drawMe(g);
		super.drawMe(g);
	}
}