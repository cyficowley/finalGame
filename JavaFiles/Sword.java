import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.ArrayList;
public class Sword extends Weapon
{
	boolean swinging;

	double angle = Math.PI /4;

	double length = 100;

	Box box;

	public Sword(TestCharacter mc)
	{
		super(mc);
		this.damage = mc.damage;
		box = new Box(length,10,mc.x + mc.width/2,mc.y + mc.height/2, mc.x + mc.width/2 -40,mc.y + mc.height/2 - length/2,0);
	}

	@Override
	public void moveMe()
	{
		if((Screen.mouseDown || Screen.space) && !swinging)
		{
			swinging = true;
		}
		if(swinging)
		{
			if(angle < Math.PI)
			{
				angle +=.1;
			}
			else
			{
				swinging = false;
				angle = Math.PI /4;
			}
		}
		box.angle = angle;
		box.centerPointX = mc.x + mc.width/2;
		box.centerPointY = mc.y + mc.height/2;	
		box.calculateMe();
	}

	@Override
	public void drawMe(Graphics g)
	{
		box.drawMe(g);
	}

	@Override
	public void collision(Enemy enemy)
	{
		if(swinging && boxMainObjectCollision(box, enemy))
		{
			hit(enemy);
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
			enemy.xVelocity -= damage / mc.defense/4 * xMult;
			enemy.yVelocity -= damage / enemy.defense/4;
			enemy.invulnerablilityCount = 20;
		}
    }
}