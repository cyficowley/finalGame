package finalGame.enemies;
import finalGame.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static finalGame.Screen.screenX;
import static finalGame.Screen.screenY;
import static java.awt.Color.red;

public class Sniper extends BasicAiEnemy
{
    public double sinceShot = 0;
    public double bulletSize = 15;
    public double differentiation;
    public double reloadTime = reloadTime = 800;
    public double bulletVelocity = 20 + Math.random() * 14;


    public Image icon;
    public Sniper(double x, double y, double width, double height, double damage)
    {
        super(x,y,width,height,damage + 20);
        differentiation = Math.sqrt(Math.pow(width/2 + bulletSize /2,2) + Math.pow(height/2 +bulletSize /2,2));
        try
        {
            icon = new ImageIcon(new File("res/images/sniper.png").toURI().toURL()).getImage();
        }catch(IOException e){System.out.println(e);}
        multiplier = 5;
        speed = 1.5;

    }
    @Override
    public void moveMe()
    {
        if(!moved)
        {
            if(Math.random() < Math.pow((double)sinceShot/reloadTime, 3))
            {
                double firstPart = Math.sqrt(Math.abs(Math.pow(bulletVelocity,4)-(Screen.gravity* -1)*((Screen.gravity* -1) * Math.pow((Screen.mc.x +Screen.mc.width/2 - bulletSize/2) - (x + width/2),2)+2*((Screen.mc.y + Screen.mc.height/2 - bulletSize /2) - (y + height/2)) * Math.pow(bulletVelocity,2))));
                if(Math.random() > .5 || bulletVelocity > 15)
                {
                    firstPart *= -1;
                }
                double angle = Math.atan((Math.pow(bulletVelocity,2) + firstPart)/((Screen.gravity* -1) * ((Screen.mc.x +Screen.mc.width/2 - bulletSize/2) - (x + width/2))));
                if((Screen.mc.x +Screen.mc.width/2) - (this.x + this.width/2) < 0)
                {
                    angle += Math.PI;
                }
                EnemyBullet temp = new EnemyBullet(x + width/2,y + height/2,bulletSize, angle, bulletVelocity, damage);
                sinceShot = 0;
            }
            sinceShot ++;
        }
        super.moveMe();
    }
    double timer = Math.random();
    @Override
    public void drawMe(Graphics2D g) {
        timer += .02;
        g.drawImage(icon,(int)(x - Screen.screenX), (int)(y- Screen.screenY), (int)width, (int)height,null);
        if(health < maxHealth)
        {
            g.setColor(red);
            g.fillRect((int) (x - width / 20 - screenX), (int) (y - height / 5 - screenY), (int) (width * 11 / 10), (int) (height / 20 * 3));
            g.setColor(new Color(19, 128, 30));
            g.fillRect((int) (x - width / 20 - screenX), (int) (y - height / 5 - screenY), (int) (width * 11 / 10 * health / maxHealth), (int) (height / 20 * 3));
        }
        g.setColor(new Color(0,0,0,100));
        g.fillOval((int)(x - Screen.screenX - 10 -Math.sin(timer) * 20),(int)(y- Screen.screenY + height/8),(int)(width + Math.sin(timer) * 40 +20), (int)(height * 3/4));
        g.fillOval((int)(x- Screen.screenX + width/8),(int)(y - Screen.screenY - Math.sin(timer) * 20 -10),(int)(width* 3/4),(int)(height + Math.sin(timer) * 40 +20));

        drawn = true;
    }
}