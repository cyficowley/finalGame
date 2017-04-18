package javaFiles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class HyperGun extends Gun
{
	int maxBullets = 0;
	int currentBullets = 0;
	public HyperGun(TestCharacter mc)
	{
		super(mc, false);
		maxCooldown = 20;
		cooldown = maxCooldown;
		try {
		    gunRight = ImageIO.read(new File("javaFiles/images/hyperGunRight.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		try {
		    gunLeft = ImageIO.read(new File("javaFiles/images/hyperGunLeft.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		damage = 25;
	}
	@Override
	public void moveMe()
	{
		fire = true;
		angle = Math.atan2(Screen.mouseY - Screen.screenHeight/2,Screen.mouseX - Screen.screenWidth/2);
		if(angle > -Math.PI/2 && angle < Math.PI/2)
		{
			gunDirection = true;
			drawRight = true;
		}
		else
		{
			gunDirection = false;
			drawRight = false;
		}
		cooldown--;
		if(!Screen.d || !Screen.a)
		{
			if(gunDirection && Screen.a)
			{
				angle = Math.PI;
				drawRight = false;
				fire = false;
			}
			else if(!gunDirection && Screen.d)
			{
				angle = 0;
				drawRight = true;
				fire = false;
			}
		}

		if(cooldown > 0 && cooldown < maxCooldown -3 && fire)
		{
			if(gunDirection)
			{
				angle -= cooldown / maxCooldown * Math.PI/8;
			}
			else
			{
				angle += cooldown / maxCooldown * Math.PI/8;
			}
		}
		if((Screen.mouseDown || Screen.space) && cooldown <0)
		{
			cooldown = maxCooldown;
			fire();
		}
	}
	@Override
	public void fire()
	{
		if(fire)
		{
			bullets.add(new Bullet(speed,true,20,mc.x + mc.width/2+ Math.cos(angle) * 30, mc.y + mc.height/2 + Math.sin(angle) * 30-6,angle,this));
		}
	}
	@Override
	public void drawMe(Graphics2D g)
	{
		super.drawMe(g);
	}
	public class LittleFloatThing extends MainObject
	{
		int lifeSpan = 0;
		public LittleFloatThing(double x, double y, double width, double height, double angleOff)
		{
			super(x + Math.cos(angleOff)*4,y * Math.sin(angleOff)*4,width,height);
		}
		public void moveMe()
		{
			if(lifeSpan <=30)
			{
				x += Math.cos((double)(lifeSpan)/30 *Math.PI) +1;
			}
			
		}
		public void drawMe(Graphics2D g)
		{
			g.setColor(Color.black);
			g.fillRect((int)(x - Screen.screenX), (int)(y- Screen.screenY), (int)width, (int)height); // for testing
		}
	}
}