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
	int maxBullets = 5;
	int currentBullets = 0;
	ArrayList<LittleFloatThing> littleFloatThings = new ArrayList<>();
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
		damage = 15;
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
		if((Screen.mouseDown || Screen.space))
		{
			if(cooldown <0)
			{
				cooldown = maxCooldown;
				if(currentBullets < maxBullets)
				{
					currentBullets ++;
					littleFloatThings.add(new LittleFloatThing(currentBullets, this));
				}
			}
		}
		else
		{
			if(currentBullets > 0)
			{
				currentBullets = 0;
				for(LittleFloatThing each : littleFloatThings)
				{			
					bullets.add(new Bullet(speed,false,20,each.x, each.y,Math.atan2(Screen.mouseY +Screen.screenY - each.y,Screen.mouseX + Screen.screenX - each.x),this));
				}
				littleFloatThings = new ArrayList<>();
			}
		}
		for(LittleFloatThing each : littleFloatThings)
		{
			each.moveMe();
		}
	}
	@Override
	public void drawMe(Graphics2D g)
	{
		AffineTransform old = g.getTransform();
		if(drawRight)
		{
			g.rotate(angle,mc.x + mc.width/2- Screen.screenX , mc.y  + mc.height/2- Screen.screenY);
        	g.drawImage(gunRight, (int)(20+ mc.x + mc.width/2 - Screen.screenX), (int)(mc.y + mc.height/2 - Screen.screenY - 6),50,25, null);
		}
		else
		{
			g.rotate(angle -Math.PI,mc.x + mc.width/2- Screen.screenX , mc.y  + mc.height/2- Screen.screenY);
        	g.drawImage(gunLeft, (int)(-70+ mc.x + mc.width/2 - Screen.screenX), (int)(mc.y + mc.height/2 - Screen.screenY - 6),50,25, null);
		}
		for(LittleFloatThing each : littleFloatThings)
		{
			each.drawMe(g);
		}
		
        g.setTransform(old);
	}
	public class LittleFloatThing extends MainObject
	{
		int lifeSpan = 0;
		double trueLifeSpan = 0;
		double pulsationRate;
		double angle = 0;
		double trueAngle = 0;
		double difference = 0;
		HyperGun hyperGun;
		public LittleFloatThing(int number, HyperGun hyperGun)
		{
			super(0,0,20,20);
			trueAngle = number * Math.PI *4/5;
			pulsationRate =Math.random() * .3 + .7;
			this.hyperGun = hyperGun;
		}
		public void moveMe()
		{
			trueLifeSpan ++;
			angle = trueAngle + Math.sin(trueLifeSpan* pulsationRate / 15) /5;
			if(lifeSpan <30)
			{
				difference += Math.cos((double)(lifeSpan)/30 *Math.PI) +1;
				lifeSpan ++;
			}
			if(hyperGun.drawRight)
			{
				x = 20+ Screen.mc.x + Screen.mc.width/2 + Math.cos(angle) * difference;
				y = mc.y + mc.height/2 - 6 + Math.sin(angle) * difference;
			}
			else
			{
				x = -70+ Screen.mc.x + Screen.mc.width/2 + Math.cos(angle) * difference;
				y = mc.y + mc.height/2 - 6 + Math.sin(angle) * difference;
			}
		}
		public void drawMe(Graphics2D g)
		{
			g.setColor(new Color((int)(((double)lifeSpan /40.0 + Math.cos(trueLifeSpan/15 * pulsationRate)/4)*255), 0,0));
			g.fillRect((int)(x - Screen.screenX), (int)(y- Screen.screenY), (int)width, (int)height); // for testing
		}
	}
}