package javaFiles;
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

	double strength =1;

	int drawState = 0; //-1 is left, 0 is no direction, 1 is right

	public Weapon weapon = new Gun(this);

	public TestCharacter(double x, double y, double width, double height, double damage)
	{
		super(x,y,width,height, damage); // this entire class is just temporary before we get a real main character class going

		try {
		    characterDefault = ImageIO.read(new File("javaFiles/images/characterDefault.png"));
		    characterRight = ImageIO.read(new File("javaFiles/images/characterRight.png"));
		    characterLeft = ImageIO.read(new File("javaFiles/images/characterLeft.png"));
		    characterBack = ImageIO.read(new File("javaFiles/images/characterBack.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		this.speed = 3.7;
		this.type = "mc";
		this.strength = 5;
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
		if (Screen.d || (super.xVelocity > 0.3 && !Screen.a)) {
			drawState = -1;
			g.drawImage(characterRight, (int) (x - Screen.screenX -4.5), (int) (y - Screen.screenY-4.5), (int) width+9, (int) (height+4.5), null);
		} else if (Screen.a || (super.xVelocity < -0.3 && !Screen.d)) {
			drawState = 1;
			g.drawImage(characterLeft, (int) (x - Screen.screenX -4.5), (int) (y - Screen.screenY-4.5), (int) width+9, (int) (height+4.5), null);
		} else {
			drawState = 0;
			g.drawImage(characterDefault, (int) (x - Screen.screenX -4.5), (int) (y - Screen.screenY-4.5), (int) width+9, (int) (height+4.5), null);
		}
		weapon.drawMe(g);
		super.drawMe(g);
	}
}