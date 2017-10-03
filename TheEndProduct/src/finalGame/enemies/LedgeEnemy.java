package finalGame.enemies;
//  Created by Cyrus Cowley on 5/22/2017.

import finalGame.Screen;
import finalGame.usefulClasses.TouchData;
import finalGame.worldGen.Block;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LedgeEnemy extends BasicAiEnemy
{
    boolean trueDirection= false;
    int ledgeY = -1;
    public LedgeEnemy(double x, double y, double width, double height, double damage)
    {
        super(x,y,width,height,damage);
        this.ledgeY = ledgeY;
        multiplier = 1.2;
        try
        {
            icon = new ImageIcon(new File("res/images/ledgeEnemy.gif").toURI().toURL()).getImage();
        }catch(IOException e){System.out.println(e);}
    }
    @Override
    public void moveMe()
    {
        if(ledgeY == -1 && touched.size() > 0 && touched.get(0).touched.type.equals("block") && touched.get(0).direction == 1)
        {
            ledgeY = ((Block)touched.get(0).touched).yIndex;
        }
        if(Math.round((y+height +Screen.blockWidth/2)/Screen.blockWidth) == (ledgeY+1))
        {
            direction = trueDirection;
            if(direction && Screen.getBlockType(x+ width+1,y + height +1) ==0)
            {
                trueDirection = false;
            }
            else if(!direction && Screen.getBlockType(x-1,y + height +1) ==0)
            {
                trueDirection = true;
            }
        }

        super.moveMe();
    }
}
