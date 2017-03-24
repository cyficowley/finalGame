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
	BufferedImage characterRight;
	BufferedImage characterLeft;
	BufferedImage characterBack;

	Weapon weapon = new Gun(this);

	public TestCharacter(double x, double y, double width, double height, double damage)
	{
		super(x,y,width,height, damage); // this entire class is just temporary before we get a real main character class going

		try {
		    characterDefault = ImageIO.read(new File("images/characterDefault.png"));
		    characterRight = ImageIO.read(new File("images/characterRight.png"));
		    characterLeft = ImageIO.read(new File("images/characterLeft.png"));
		    characterBack = ImageIO.read(new File("images/characterBack.png"));
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
		if (Screen.d || super.xVelocity > 0.3) {
			g.drawImage(characterRight, (int) (x - Screen.screenX), (int) (y - Screen.screenY), (int) width, (int) height, null);
		} else if (Screen.a || super.xVelocity < -0.3) {
			g.drawImage(characterLeft, (int) (x - Screen.screenX), (int) (y - Screen.screenY), (int) width, (int) height, null);
		} else {
			g.drawImage(characterDefault, (int) (x - Screen.screenX), (int) (y - Screen.screenY), (int) width, (int) height, null);
		}
		weapon.drawMe(g);
		super.drawMe(g);
	}
}