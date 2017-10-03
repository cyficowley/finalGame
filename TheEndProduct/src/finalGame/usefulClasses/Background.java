package finalGame.usefulClasses;

import finalGame.Screen;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by cyfic on 5/8/2017.
 */
public class Background
{
    ArrayList<Star> stars = new ArrayList<>();
    public Background()
    {
        for(int i = 0; i < 400; i ++)
        {
            stars.add(new Star(Math.random() * Screen.screenWidth,Math.random() * Screen.screenHeight,2,Math.random()*4 +2.5));
        }
        for(int i = 0; i < 200; i ++)
        {
            stars.add(new Star(Math.random() * Screen.screenWidth,Math.random() * Screen.screenHeight,3,Math.random()*4 +2.5));
        }
    }
    public void drawMe(Graphics2D g)
    {
        g.setColor(Color.black);
        g.fillRect(0,0,(int)Screen.screenWidth,(int)Screen.screenHeight);
        for (Star each :stars)
        {
            each.drawMe(g);
        }
    }
    public class Star
    {
        double x;
        double y;
        int radius;
        double change;
        public Star(double x, double y, int r, double change)
        {
            this.x = x;
            this.y = y;
            radius = r;
            this.change = change;
        }

        public void drawMe(Graphics2D g)
        {
            if(Screen.mc.y < 40*20*Screen.blockWidth)
            {
                g.setColor(new Color((int)(Math.random()*155) + 100,(int)((Math.random()*155 + 100)*(1-(Screen.mc.y/(Screen.currentYChunks*20*Screen.blockWidth)))),(int)((Math.random()*155 + 100)*(1-Screen.mc.y/(Screen.currentYChunks*20 *Screen.blockWidth)))));
            }
            else
            {
                g.setColor(new Color((int)(Math.random() * 155 +100),0,0));
            }
            g.fillRect((int)((x - Screen.screenX/change +1000000000) % Screen.screenWidth), (int)((y - Screen.screenY/change +1000000000) % Screen.screenHeight), radius,radius);
        }
    }
}