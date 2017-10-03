package finalGame.enemies;
//  Created by Cyrus Cowley on 5/22/2017.

import finalGame.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static finalGame.Screen.screenX;
import static finalGame.Screen.screenY;
import static java.awt.Color.red;

public class FlyingEnemy extends Enemy
{
    Image icon;
    public FlyingEnemy(double x, double y, double width, double height, double damage)
    {
        super(x, y, width, height,  damage);
        floating = true;
        speed = .2;
        multiplier = 1.7;
        try
        {
            icon = new ImageIcon(new File("res/images/flyingEnemy.gif").toURI().toURL()).getImage();
        }catch(IOException e){System.out.println(e);}
    }
    @Override
    public void moveMe()
    {
        angle = Math.atan2(Screen.mc.y +Screen.mc.height/2 -(y + height/2),Screen.mc.x +Screen.mc.width/2 - (x + width/2));
        if(Math.sqrt(Math.pow(Screen.mc.x +Screen.mc.width/2 - (x + width/2),2) + Math.pow(Screen.mc.y + Screen.mc.height/2 - (y + height/2),2)) < 500)
        {
            xVelocity += speed * Math.cos(angle);
            yVelocity += speed * Math.sin(angle);
        }
        else
        {
            double vAngle = Math.atan2(yVelocity,(xVelocity));
            double currentVelocity = Math.sqrt(Math.pow(xVelocity,2) + Math.pow(yVelocity,2));
            double deltaV = -(currentVelocity - speed * 7.5)/(100);
            xVelocity += deltaV * Math.cos(vAngle);
            yVelocity += deltaV * Math.sin(vAngle);
        }
        for(int i = 0; i < touched.size(); i ++)
        {
            if(touched.get(i).direction < 2)
            {
                yVelocity = - pastYVelocity;
            }
            else
            {
                xVelocity = - pastXVelocity;
            }
        }
        super.moveMe();
    }
    @Override
    public void drawMe(Graphics2D g) {
        g.drawImage(icon,(int)(x - width/2- Screen.screenX), (int)(y- Screen.screenY), (int)width *2, (int)height,null);
        if(health < maxHealth)
        {
            g.setColor(red);
            g.fillRect((int) (x - width / 20 - screenX), (int) (y - height / 5 - screenY), (int) (width * 11 / 10), (int) (height / 20 * 3));
            g.setColor(new Color(19, 128, 30));
            g.fillRect((int) (x - width / 20 - screenX), (int) (y - height / 5 - screenY), (int) (width * 11 / 10 * health / maxHealth), (int) (height / 20 * 3));
        }

        drawn = true;
    }
}
