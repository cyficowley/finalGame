import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.*;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Screen extends JPanel implements MouseMotionListener, MouseListener
{
    Action[] actions = new Action[18];
    public static double screenWidth;
    public static double screenHeight;
    public static double screenX = 0;
    public static double screenY = 0;

    public static String data = "Drawing";

    public static double mouseX= 0;
    public static double mouseY= 0;
    public static boolean mouseDown= false;

    Font ariel = new Font("Ariel", Font.PLAIN, 20);
    boolean started = false;

    public static boolean a;
    public static boolean s;
    public static boolean d;
    public static boolean w;
    public static boolean space;

    public static int startX = 0;
    public static int startY = 0; 
    public static double blockWidth = 20;

    public static boolean keyControl = false;

    public static double gravity = 0.048;

    public static ArrayList<CollisionContainer> collisions = new ArrayList<CollisionContainer>();

    long time;

    public static ArrayList<ArrayList<Chunk>> chunks = new ArrayList<ArrayList<Chunk>>(); // all the chunks

    public static ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>();

    public static int currentXChunks = 10; //the current number of chunks in the x direction
    public static int currentYChunks = 10; //the current number of chunks in the y direction

    public Screen()
    {
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        for(int i = 0; i < currentXChunks; i ++)
        {
            chunks.add(new ArrayList<Chunk>());
            for(int j = 0; j < currentYChunks; j ++)
            {
                chunks.get(i).add(new Chunk(i,j));
            }
        }
    }
    public Dimension getPreferredSize()
    {
        return new Dimension(640,480);
    }
    public void initialize()
    {
        while(screenHeight < 600)
        {
            try {Thread.sleep(10);}
            catch(InterruptedException ex) {}

            screenWidth = getWidth();
            screenHeight = getHeight();
        }
        try {Thread.sleep(300);}
            catch(InterruptedException ex) {}

        actions[0] = new WDown();
        this.getInputMap().put( KeyStroke.getKeyStroke( "W" ), "doWDown" );
        this.getActionMap().put( "doWDown", actions[0] );
        actions[1] = new WUp();
        this.getInputMap().put( KeyStroke.getKeyStroke( "released W" ), "doWUp" );
        this.getActionMap().put( "doWUp", actions[1] );
        actions[2] = new ADown();
        this.getInputMap().put( KeyStroke.getKeyStroke( "A" ), "doADown" );
        this.getActionMap().put( "doADown", actions[2] );
        actions[3] = new AUp();
        this.getInputMap().put( KeyStroke.getKeyStroke( "released A" ), "doAUp" );
        this.getActionMap().put( "doAUp", actions[3] );
        actions[4] = new SDown();
        this.getInputMap().put( KeyStroke.getKeyStroke( "S" ), "doSDown" );
        this.getActionMap().put( "doSDown", actions[4] );
        actions[5] = new SUp();
        this.getInputMap().put( KeyStroke.getKeyStroke( "released S" ), "doSUp" );
        this.getActionMap().put( "doSUp", actions[5] );
        actions[6] = new DDown();
        this.getInputMap().put( KeyStroke.getKeyStroke( "D" ), "doDDown" );
        this.getActionMap().put( "doDDown", actions[6] );
        actions[7] = new DUp();
        this.getInputMap().put( KeyStroke.getKeyStroke( "released D" ), "doDUp" );
        this.getActionMap().put( "doDUp", actions[7] );
        actions[8] = new SpaceDown();
        this.getInputMap().put( KeyStroke.getKeyStroke( "SPACE" ), "doSpaceDown" );
        this.getActionMap().put( "doSpaceDown", actions[8] );
        actions[9] = new SpaceUp();
        this.getInputMap().put( KeyStroke.getKeyStroke( "released SPACE" ), "doSpaceUp" );
        this.getActionMap().put( "doSpaceUp", actions[9] );

        actions[10] = new WDown();
        this.getInputMap().put( KeyStroke.getKeyStroke( "UP" ), "doWDown" );
        this.getActionMap().put( "doWDown", actions[10] );
        actions[11] = new WUp();
        this.getInputMap().put( KeyStroke.getKeyStroke( "released UP" ), "doWUp" );
        this.getActionMap().put( "doWUp", actions[11] );
        actions[12] = new ADown();
        this.getInputMap().put( KeyStroke.getKeyStroke( "LEFT" ), "doADown" );
        this.getActionMap().put( "doADown", actions[12] );
        actions[13] = new AUp();
        this.getInputMap().put( KeyStroke.getKeyStroke( "released LEFT" ), "doAUp" );
        this.getActionMap().put( "doAUp", actions[13] );
        actions[14] = new SDown();
        this.getInputMap().put( KeyStroke.getKeyStroke( "DOWN" ), "doSDown" );
        this.getActionMap().put( "doSDown", actions[14] );
        actions[15] = new SUp();
        this.getInputMap().put( KeyStroke.getKeyStroke( "released DOWN" ), "doSUp" );
        this.getActionMap().put( "doSUp", actions[15] );
        actions[16] = new DDown();
        this.getInputMap().put( KeyStroke.getKeyStroke( "RIGHT" ), "doDDown" );
        this.getActionMap().put( "doDDown", actions[16] );
        actions[17] = new DUp();
        this.getInputMap().put( KeyStroke.getKeyStroke( "released RIGHT" ), "doDUp" );
        this.getActionMap().put( "doDUp", actions[17] );

        screenWidth = getWidth();
        screenHeight = getHeight();
        System.out.println(screenWidth);
        started = true;

        for(int i = 0; i < chunks.size(); i ++)
        {
            for(int j = 0;  j < chunks.get(i).size(); j ++)
            {
                chunks.get(i).get(j).setUp();
            }
        }
        for(int i = 0; i < 20; i ++)
        {
            for(int j = 0;  j < 20; j ++)
            {
                chunks.get(1).get(1).blocks[i][j].rebuild(0);
            }
        }
        movingObjects.add(new TestCharacter(401,401, 50,50)); // for testing
        movingObjects.add(new MovingObject(500,460, 50,50));
        movingObjects.add(new MovingObject(500,520, 50,50));
        movingObjects.add(new MovingObject(500,580, 50,50));
        animate();
    }

	public void paintComponent(Graphics g)
    {
        if(started)
        {
        	time = System.currentTimeMillis();
            super.paintComponent(g);
            int chunksDrawn = 0;
            for(int i = 0; i < chunks.size(); i ++)
            {
                for(int j = 0;  j < chunks.get(i).size(); j ++)
                {
                	if(chunks.get(i).get(j).active)
                	{
                		chunksDrawn ++;
                		chunks.get(i).get(j).drawMe(g);
                	}
                }
            }
            for(MovingObject each : movingObjects)
            {
                each.drawMe(g);
            }
            data += (" " +(Integer.toString(chunksDrawn)));
            g.setColor(Color.red);
            g.setFont(ariel);
            data += (" " + Long.toString(System.currentTimeMillis() - time)); // this will draw the chunks active and the time taken to draw it
            g.drawString(data,(int)screenWidth - 100,50);
            data = "";
        }    
    }

    public void gridActive()
    {
    	MainObject tempScreen = new MainObject(screenX-screenWidth*.2, screenY-screenHeight*.2,screenWidth*1.4,screenHeight*1.4);
    	for(int i = 0; i < chunks.size(); i ++)
        {
            for(int j = 0;  j < chunks.get(i).size(); j ++)
            {
                if(tempScreen.collision(chunks.get(i).get(j)))
                {
                    chunks.get(i).get(j).active = true;
                }
                else
                {
                    chunks.get(i).get(j).active = false;
                }
            }
        }
    }

    public void move()
    {
        // if(a)
        // {
        //     screenX -= 5;
        // }
        // if(d)
        // {
        //     screenX += 5;
        // }
        // if(s)
        // {
        //     screenY += 5;
        // }
        // if(w)
        // {
        //     screenY -= 5;
        // }
        for(int i = 0; i < chunks.size(); i ++)
        {
            for(int j = 0;  j < chunks.get(i).size(); j ++)
            {
                if(chunks.get(i).get(j).active)
                {
                    for(MovingObject each : chunks.get(i).get(j).containedObjects)
                    {
                        each.moveMe();
                    }
                }
            }
        }
            
    }

    public void sort()
    {
    	for(int i = 0; i < chunks.size(); i ++)
        {
            for(int j = 0; j < chunks.get(i).size(); j ++)
            {
                chunks.get(i).get(j).containedObjects.clear();
            }
        }
    	for(MovingObject each : movingObjects)
        {
        	each.moveMe();
        }
        for(MovingObject each: movingObjects)
        {
            int startXIndex = (int)((each.x -startX)/(blockWidth * blockWidth));
            int startYIndex = (int)((each.y -startY)/(blockWidth * blockWidth));
            if(startXIndex >= 0 && startYIndex >= 0)
            {
                for(int i = startXIndex; i * (blockWidth* blockWidth) < each.width + each.x && i <chunks.size(); i ++)
                {
                    for(int j = startYIndex; j * (blockWidth* blockWidth) < each.height + each.y && j < chunks.get(i).size(); j ++)
                    {
                        chunks.get(i).get(j).containedObjects.add(each);
                        each.moved = false;
                        each.drawn = false;
                    }
                }
            }
        }
    }

    public void collisionController()
    {
    	for(int i = 0; i < chunks.size(); i ++)
        {
            for(int j = 0; j < chunks.get(i).size(); j ++)
            {
                if(chunks.get(i).get(j).active)
                {
                	for(MovingObject each : chunks.get(i).get(j).containedObjects)
                	{
                		each.collision(chunks.get(i).get(j));
                	}
                }
            }
        }
        for(CollisionContainer each : collisions)
        {
            each.run();
        }
        collisions.clear();
        for(int i = 0; i < chunks.size(); i ++)
        {
            for(int j = 0; j < chunks.get(i).size(); j ++)
            {
                if(chunks.get(i).get(j).active)
                {
                	for(MovingObject each : chunks.get(i).get(j).containedObjects)
                	{
                		each.collision2(chunks.get(i).get(j));
                	}
                }
            }
        }
        for(CollisionContainer each : collisions)
        {
            each.run();
        }
        collisions.clear();
    }

    public void animate()
    {
    	while(true)
        {
            
            move();
            sort();
            collisionController();
            gridActive();

			screenX = movingObjects.get(0).x + movingObjects.get(0).width/2- screenWidth/2;
			screenY = movingObjects.get(0).y + movingObjects.get(0).height/2 - screenHeight/2;
            repaint();
            try 
            {
                Thread.sleep(16);
            } 
            catch(InterruptedException ex) 
            {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    static class ADown extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
            a = true;
        }}
    static class SDown extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
            s = true;
        }}
    static class DDown extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
            d = true;
        }}
    static class WDown extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
            w = true;
        }}
    static class SpaceDown extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
            space = true;
        }}
    static class AUp extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
            a = false;
        }}
    static class SUp extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
            s= false;
        }}
    static class DUp extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
            d= false;
        }}
    static class WUp extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
            w= false;
        }}
    static class SpaceUp extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
            space = false;
        }}


    public void mousePressed(MouseEvent e) 
    {
        mouseDown = true;
    }
    public void mouseReleased(MouseEvent e) 
    {
        mouseDown = false;
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e){}
    public void mouseMoved(MouseEvent e) {
       mouseY = e.getY();
       mouseX = e.getX();
    }

    public void mouseDragged(MouseEvent e) {
       mouseY = e.getY();
       mouseX = e.getX();
    }
}