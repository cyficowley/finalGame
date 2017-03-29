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
public class Gun extends Weapon
{
	double cooldown;
	double maxCooldown;
	double angle;
	double speed = 15;
	boolean gunDirection; //true is right
	BufferedImage gunRight;
	BufferedImage gunLeft;
	boolean drawRight = false;
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public Gun(TestCharacter mc)
	{
		super(mc);
		maxCooldown = 20;
		cooldown = maxCooldown;
		try {
		    gunRight = ImageIO.read(new File("javaFiles/images/gunRight.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		try {
		    gunLeft = ImageIO.read(new File("javaFiles/images/gunLeft.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		damage = 25;
	}
	@Override
	public void moveMe()
	{
		angle = Math.atan2(Screen.mouseY - Screen.screenHeight/2,Screen.mouseX - Screen.screenWidth/2);
		if((Screen.mouseDown || Screen.space) && cooldown <0)
		{
			cooldown = maxCooldown;
			fire();
		}
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
		if(cooldown > 0 && cooldown < maxCooldown -3)
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
		cooldown--;
		if(gunDirection && Screen.a)
		{
			angle = Math.PI;
			drawRight = false;
		}
		else if(!gunDirection && Screen.d)
		{
			angle = 0;
			drawRight = true;
		}
	}
	public void fire()
	{
		if(gunDirection == mc.direction)
		{
			bullets.add(new Bullet(speed,true,20,mc.x + mc.width/2+ Math.cos(angle) * 30, mc.y + mc.height/2 + Math.sin(angle) * 30-6,angle,this));
		}
	}
	@Override
	public void drawMe(Graphics2D g)
	{
		g.setColor(Color.blue);
		if(drawRight)
		{
			rotateImage(gunRight,20+ mc.x + mc.width/2 - Screen.screenX,mc.y + mc.height/2 - Screen.screenY - 6,mc.x + mc.width/2- Screen.screenX,mc.y  + mc.height/2- Screen.screenY, 50,25, angle, g);
		}
		else
		{
			rotateImage(gunLeft,-70+ mc.x + mc.width/2 - Screen.screenX,mc.y + mc.height/2 - Screen.screenY - 6,mc.x + mc.width/2- Screen.screenX,mc.y  + mc.height/2- Screen.screenY, 50,25, angle-Math.PI, g);
		}
	}
	@Override
	public void collision()
	{
		for(int i = 0; i < bullets.size(); i ++)
		{
			if(bullets.get(i).touched.size() != 0)
			{
				bullets.get(i).collision();
			}
		}
	}
	public void rotateImage(BufferedImage image,double x, double y, double angle, Graphics2D g)
	{
		rotateImage(image,x,y,image.getWidth()/2 + x, image.getHeight()/2 + y,image.getWidth(),image.getHeight(), angle,g);
	}
	public void rotateImage(BufferedImage image,double x, double y, double rotationX, double rotationY, double width, double height, double angle, Graphics2D g)
	{
		AffineTransform old = g.getTransform();
        g.rotate(angle,rotationX , rotationY);
        g.drawImage(image, (int)x, (int)y,(int)width,(int)height, null);
        g.setTransform(old);
	}
}