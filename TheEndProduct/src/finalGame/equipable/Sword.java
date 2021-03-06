package finalGame.equipable;
import finalGame.*;
import finalGame.enemies.Enemy;
import finalGame.usefulClasses.Box;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Sword extends Weapon
{
	boolean swinging;

	double angle = Math.PI /4;

	double length = 150;

	Box boxRight;
	Box boxLeft; //all swords must be 16 pixels wide, 8 in blade, 4 on each side for guard, they can be super long though

	BufferedImage sword;

	public Sword(MainCharacter mc)
	{
		super(mc);
		try {
			sword = ImageIO.read(new File("res/images/longSwordWhite.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.damage = mc.damage *.6;
		boxRight = new Box(length,20,mc.x + mc.width/2,mc.y + mc.height/2, mc.x + mc.width/2 - length/2 + 20,mc.y + mc.height/2 - 40,0);
		boxLeft = new Box(length,20,mc.x + mc.width/2,mc.y + mc.height/2, mc.x + mc.width/2 + length/2 -20,mc.y + mc.height/2 - 40,0);
	}

	@Override
	public void moveMe()
	{
		if((Screen.mouseFirstDown) && !swinging)
		{
			swinging = true;
		}
		if(swinging)
		{
			if(angle < Math.PI)
			{
				angle +=.15;
			}
			else
			{
				swinging = false;
				angle = Math.PI /4;
			}
		}
		if(mc.direction)
		{
			boxRight.angle = angle;
			boxRight.centerPointX = mc.x + mc.width/2;
			boxRight.centerPointY = mc.y + mc.height/2;
			boxRight.calculateMe();
		}
		else
		{
			boxLeft.angle = -angle;
			boxLeft.centerPointX = mc.x + mc.width/2;
			boxLeft.centerPointY = mc.y + mc.height/2;
			boxLeft.calculateMe();
		}	
	}

	@Override
	public void drawMe(Graphics2D g)
	{
		boolean temp = false;
		if(!Screen.a || !Screen.d)
		{
			temp = mc.direction;
		}
		else
		{
			if(mc.drawState ==-1)
			{
				temp = true;
			}
			else if(mc.drawState ==1)
			{
				temp = false;
			}
		}
		if(temp)
		{
			rotateImage(sword,mc.x + mc.width/2 - Screen.screenX +20, mc.y + mc.height/2 - Screen.screenY - boxRight.width + boxRight.height, mc.x + mc.width/2 - Screen.screenX, mc.y + mc.height/2 - Screen.screenY, boxRight.height+20, boxRight.width, boxRight.angle - Math.PI/2, g);
		}
		else
		{
			rotateImage(sword,mc.x + mc.width/2 - Screen.screenX -boxLeft.height*2 - 20, mc.y + mc.height/2 - Screen.screenY - boxLeft.width + boxLeft.height, mc.x + mc.width/2 - Screen.screenX, mc.y + mc.height/2 - Screen.screenY, boxLeft.height+20, boxLeft.width, boxLeft.angle + Math.PI/2, g);
		}
	}
	@Override
	public void collision()
	{
		for(int i = 0; i < Screen.chunks.size(); i ++)
		{
			for(int j = 0; j < Screen.chunks.get(i).size(); j ++)
			{
				if(Screen.chunks.get(i).get(j).blocksActive)
				{
					for(MovingObject each : Screen.chunks.get(i).get(j).containedObjects)
					{
						if(each.type.equals("mc"))
						{
							checkAdjacents(i,j);
						}
					}
				}
			}
		}
	}
	public void checkAdjacents(int one, int two) 
	{										//checks the movingobjects in the adjacent chunks to see if they are colliding with the sword
		for(int i = -1; i < 2; i ++)
		{
			for(int j = -1; j < 2; j ++)
			{
				for(MovingObject each : Screen.getChunk(one + i, two + j).containedObjects)
				{
					if(each.type.equals("enemy"))
					{
						collision((Enemy)each);
					}
				}
			}
		}
	}

	@Override
	public void collision(Enemy enemy)
	{
		if(mc.direction)
		{
			if(swinging && boxMainObjectCollision(boxRight, enemy))
			{
				hit(enemy);
			}
		}
		else
		{
			if(swinging && boxMainObjectCollision(boxLeft, enemy))
			{
				hit(enemy);
			}
		}
	}

	public boolean boxMainObjectCollision(Box one, MainObject two)
	{
		Box temp = new Box(two.width, two.height, two.x + two.width/2, two.y + two.height/2, two.x + two.width/2, two.y + two.height/2,0);
		temp.calculateMe();
		return boxCollision(one,temp);
	}

	public boolean boxCollision(Box one, Box two)
	{
		for(int i = 0; i < one.holders.length; i ++)
		{
			for(int j = 0; j < two.holders.length; j ++)
			{
				double[] temp = new double[] {(two.holders[j].line.yInt-one.holders[i].line.yInt)/(one.holders[i].line.slope-two.holders[j].line.slope), 
					(two.holders[j].line.yInt-one.holders[i].line.yInt)/(one.holders[i].line.slope-two.holders[j].line.slope) * one.holders[i].line.slope + one.holders[i].line.yInt};
				if(one.holders[i].line.startX < temp[0] && temp[0] < one.holders[i].line.endX && two.holders[j].line.startX < temp[0] && temp[0] < two.holders[j].line.endX)
				{
					one.active = true; 
					two.active = true;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void hit(Enemy enemy)
	{
		if(enemy.invulnerablilityCount < 0)
		{
			int xMult = 1;
			if(mc.x + mc.width /2 < enemy.x + enemy.width/2)
			{
				xMult = -1;
			}
			enemy.xVelocity -= damage / enemy.defense/4 * xMult * Math.pow(1000/enemy.mass, .2);
			enemy.yVelocity -= damage / enemy.defense/4 * Math.pow(1000/enemy.mass, .2);
			enemy.invulnerablilityCount = 20;
			enemy.health -= damage / enemy.defense;
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