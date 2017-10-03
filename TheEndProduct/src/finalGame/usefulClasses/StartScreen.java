package finalGame.usefulClasses;
//  Created by Cyrus Cowley on 5/25/2017.

import finalGame.MainObject;
import finalGame.Screen;
import sun.management.snmp.util.SnmpTableCache;

import java.awt.*;

public class StartScreen extends MainObject
{
    public boolean raising = false;
    double frames = 0;
    double maxFrames = 400;
    boolean first = true;
    String input = "DESCENT";
    Letter[] letters = new Letter[input.length()];
    boolean instructions = false;
    public StartScreen()
    {
        super(0,0, Screen.screenWidth,Screen.screenHeight);
    }
    public void drawMe(Graphics2D g)
    {

        if(raising)
        {
            if(y < height)
            {
                y -= height/120;
            }
        }
        if(y < height)
        {
            g.setColor(Color.black);
            g.fillRect((int)x,(int)y,(int)width,(int)height);
            if(!instructions)
            {
                if(Screen.space)
                {
                    raising = true;
                }
                g.setColor(Color.RED);
                Font temp = new Font("Impact", Font.PLAIN, 200);
                g.setFont(temp);
                if(first)
                {
                    FontMetrics fontMetrics = g.getFontMetrics(temp);
                    int widthString = fontMetrics.stringWidth(input);
                    int[] startX = new int[input.length()];
                    int[] widthLetter = new int[input.length()];
                    int[] endX = new int[input.length()];
                    for(int i = 0; i < input.length(); i ++)
                    {
                        int start = (int)(Screen.screenWidth/2 - widthString/2);
                        widthLetter[i] = fontMetrics.stringWidth(Character.toString(input.charAt(i)));
                        for(int j = 0; j < i; j ++)
                        {
                            start += widthLetter[j];
                        }
                        startX[i] = start;
                        endX[i] = (int)((startX[i] - Screen.screenWidth/2) *4 + Screen.screenWidth/2);
                    }
                    for(int i = 0; i < input.length(); i ++)
                    {
                        letters[i] = new Letter(startX[i],Screen.screenHeight/2,Character.toString(input.charAt(i)),endX[i],this);
                    }
                    first = false;
                }

                for(int i = 0; i < letters.length; i ++)
                {
                    letters[i].drawMe(g);
                }

                frames ++;
                Font temp2 = new Font("Ariel", Font.PLAIN, 32);
                FontMetrics fontMetrics = g.getFontMetrics(temp2);
                int widthString = fontMetrics.stringWidth("SPACE to start        I for instructions");
                g.setFont(temp2);
                g.setColor(Color.red);
                g.drawString("SPACE to start        E for instructions", (int)(Screen.screenWidth/2 - widthString/2 + x), (int)(Screen.screenHeight * 7/8 + y));
                if(Screen.e && !raising)
                {
                    instructions = true;
                }
            }
            else
            {
                Font temp2 = new Font("Ariel", Font.PLAIN, 24);
                g.setFont(temp2);
                g.setColor(Color.red);
                g.drawString("Use WASD or arrow keys to move",50,150);
                g.drawString("Use SPACE to shoot your jetpack which you can aim, allowing you to control yourself in air",50,200);
                g.drawString("Click with the mouse to use whatever item you are holding",50,250);
                g.drawString("You start out with a sword that is good in close situations but does less damage than the jetpack bullets",50,300);
                g.drawString("You can break blocks with your jetpack bullets then touch the items they drop to add them to inventory",50,350);
                g.drawString("Press E to open your inventory and select items, press Q to cycle between items in your inventory",50,400);

                g.drawString("The goal is to obtain the highest score possible, which is a combination of your depth and enemies killed",50,500);

                FontMetrics fontMetrics = g.getFontMetrics(temp2);
                int widthString = fontMetrics.stringWidth("E to return");
                g.drawString("SPACE to start", (int)(Screen.screenWidth/2 - widthString/2), (int)(Screen.screenHeight * 7/8));
                if(Screen.space)
                {
                    instructions = false;
                    Screen.inventory.visible = false;
                }

            }
        }
    }
    public void startGame()
    {
        raising = true;
    }
    public class Letter extends MainObject
    {
        String letter;
        double currentSpot = 0;
        double rate = .02 + Math.random() * .05;
        double velocity;
        StartScreen startScreen;
        public Letter(double x, double y, String letter, double endX, StartScreen startScreen)
        {
            super(endX,y,0,0);
            this.letter = letter;
            velocity = (endX - x)/maxFrames;
            this.startScreen = startScreen;
        }
        public void drawMe(Graphics2D g)
        {
            g.setColor(new Color((int)(77*Math.cos(currentSpot)) +177, 0,0));
            if((frames < maxFrames) || currentSpot % (Math.PI *2) <= Math.PI *2 - rate)
            {
                currentSpot += rate;
            }

            g.drawString(letter,(int)(x + startScreen.x),(int)( y+ startScreen.y));
            if(frames < maxFrames)
            {
                x -= velocity;
            }
        }
    }
}
