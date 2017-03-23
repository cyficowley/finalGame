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
{ // STATIC STUFF YOU CAN ACCESS AT ANY TIME IN ANY CLASS BY SAYING SCREEN.VARIABLE
    Action[] actions = new Action[18]; // for key bindings
    public static double screenWidth; // the width of the screen
    public static double screenHeight; // the hieght of the screen
    public static double screenX = 0; //  the x and y of the viewpoint of the screen
    public static double screenY = 0;

    public static String data = "Drawing"; // just some data that will display on the screen

    public static double mouseX= 0; // the x and y of the mouse, will get everytime the mouse is moved
    public static double mouseY= 0;
    public static boolean mouseDown= false; // whether mouse is pressed

    Font ariel = new Font("Ariel", Font.PLAIN, 15); // current font to draw on the screen
    boolean started = false;

    public static boolean a; // if a is down and so down
    public static boolean s;
    public static boolean d;
    public static boolean w;
    public static boolean space;

    public static int startX = 0; // the starting x and y of the screen
    public static int startY = 0;
    public static double blockWidth = 40; // width in pixels of a block, default is 40

    public static double gravity = 0.2; // gravity

    public static ArrayList<CollisionContainer> collisions = new ArrayList<CollisionContainer>(); // arraylist comtainer for all the collisions

    static Chunk fakeChunk = new Chunk(0, 0);
    static Block fakeBlock = new Block(0, 0,0,0,0, fakeChunk);


    long time;

    public static ArrayList<ArrayList<Chunk>> chunks = new ArrayList<ArrayList<Chunk>>(); // all the chunks, in a 2d arraylist

    public static ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>(); //all the moving objects
    public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    public static int currentXChunks = 10; //the current number of chunks in the x direction
    public static int currentYChunks = 10; //the current number of chunks in the y direction

    static TestCharacter mc;

    public Screen()
    {
        setFocusable(true); //setup stuff
        addMouseListener(this);
        addMouseMotionListener(this);
        for(int i = 0; i < currentXChunks; i ++)
        {
            chunks.add(new ArrayList<Chunk>());
            for(int j = 0; j < currentYChunks; j ++)
            {
                chunks.get(i).add(new Chunk(i,j));//setting up and adding the chunks to the 2d arraylist
            }
        }
    }
    public Dimension getPreferredSize()
    {
        return new Dimension(640,480); //settup for default so it expands
    }
    public void initialize()
    {
        while(screenHeight < 600) // the while and try after it makes it so it expands and gets the screenwidth and height accuratly
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
        this.getActionMap().put( "doDUp", actions[17] );  //Setting up all the classes for the keybindings

        screenWidth = getWidth();
        screenHeight = getHeight();
        started = true; //starts drawing

        for(int i = 0; i < chunks.size(); i ++)
        {
            for(int j = 0;  j < chunks.get(i).size(); j ++)
            {
                chunks.get(i).get(j).setUp(); // this sets up all the chunks
            }
        }
        mc = new TestCharacter(WorldGenerator.spawnPointX * blockWidth,WorldGenerator.spawnPointY * blockWidth, 72, 144, 25);
        movingObjects.add(mc); 


        // movingObjects.add(new BasicAiEnemy(800,20, 72,72,25)); 
        // enemies.add((Enemy)movingObjects.get(movingObjects.size()-1));

        movingObjects.add(new BasicAiEnemy(1200,20, 128,128,25)); 
        enemies.add((Enemy)movingObjects.get(movingObjects.size()-1));
        movingObjects.add(new MovingObject(chunks.get(3).get(0).x +100,chunks.get(3).get(0).y + 100, 72,72)); 
        movingObjects.add(new MovingObject(chunks.get(3).get(0).x +100,chunks.get(3).get(0).y + 210, 72,72)); 
        WorldGenerator.finalChanges();
        animate();
    }

	public void paintComponent(Graphics gTemp)
    {
        if(started)
        {
        	time = System.currentTimeMillis(); // gets time when started drawing
            super.paintComponent(gTemp);
            Graphics2D g = (Graphics2D)gTemp;
            int chunksDrawn = 0;
            for(int i = 0; i < chunks.size(); i ++)
            {
                for(int j = 0;  j < chunks.get(i).size(); j ++)
                {
                	if(chunks.get(i).get(j).active) // draws only active chunks that are in or very near screen
                	{
                		chunksDrawn ++; //adds a counter of how many chunks we are drawing
                		chunks.get(i).get(j).drawMe(g); //draws the chunks
                	}
                }
            }
            for(int i = 0; i < chunks.size(); i ++)
            {
                for(int j = 0;  j < chunks.get(i).size(); j ++)
                {
                	if(chunks.get(i).get(j).active) // draws only active chunks that are in or very near screen
                	{
                		for(MovingObject each : chunks.get(i).get(j).containedObjects)
			            {
			                each.drawMe(g); // draws everything within active range
			            }
                	}
                }
            }
            g.setColor(Color.red);
            g.setFont(ariel);
            data += (Long.toString(System.currentTimeMillis()-time));
            g.drawString(data,(int)20,50);
            data = "";
        }
    }

    public void gridActive()
    { //						This method sets the chunks near the screen active so they move and draw
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
    	for(int i = 0; i < chunks.size(); i ++) // this jus moves all the blocks
        {
            for(int j = 0;  j < chunks.get(i).size(); j ++)
            {
                if(chunks.get(i).get(j).active)
                {
                    for(MovingObject each : chunks.get(i).get(j).containedObjects)
                    {
                        if(!each.moved)
                        {
                            each.moveMe();
                        }
                    }
                }
            }
        }
    }

    public void sort()
    {//						THis sorts all the movingObjects into the right places
    	for(int i = 0; i < chunks.size(); i ++)
        {
            for(int j = 0; j < chunks.get(i).size(); j ++)
            {
                chunks.get(i).get(j).containedObjects.clear(); // clears out all the moving objects
            }
        }
        for(MovingObject each: movingObjects)
        {
            int startXIndex = (int)((each.x)/(blockWidth *20)); // gets start index
            int startYIndex = (int)((each.y)/(blockWidth *20));
            each.active = false;
            if(startXIndex >= 0 && startYIndex >= 0)
            {
                for(int i = startXIndex; i * (blockWidth*20) < each.width + each.x && i <chunks.size(); i ++)
                {
                    for(int j = startYIndex; j * (blockWidth*20) < each.height + each.y && j < chunks.get(i).size(); j ++)
                    {
                        chunks.get(i).get(j).containedObjects.add(each); //this makes it so the movingObjects can be in multiple chunks at once
                        each.moved = false;
                        each.drawn = false;
                        chunks.get(i).get(j).blocksActive=true;
                    }
                }
            }
        }
    }

    public void collisionController()
    { //								This method does all the collision stuff
    	for(int i = 0; i < chunks.size(); i ++)
        {
            for(int j = 0; j < chunks.get(i).size(); j ++)
            {
                if(chunks.get(i).get(j).blocksActive)
                {
                	for(MovingObject each : chunks.get(i).get(j).containedObjects)
                	{
                		each.collision(chunks.get(i).get(j)); // gets all collisions between blocks and movingObjects
                    }
                }
            }
        }
        for(CollisionContainer each : collisions)
        {
            each.run(); // runs all the collsions
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
                		each.collision2(chunks.get(i).get(j)); // gets all the collisions between multiple movingObjects
                	}
                }
            }
        }
        for(CollisionContainer each : collisions)
        {
            each.run(); // runs all the moving objects
        }
        collisions.clear(); // removes all collisions
        if(mouseDown)
        {
            Block temp = getBlock((int)((mouseX + screenX)/blockWidth),(int)((mouseY + screenY)/blockWidth));
            if(temp !=null)
            {
                if(Math.sqrt(Math.pow(mc.x + mc.width/2 - temp.x -temp.width/2,2)+ Math.pow(mc.y + mc.height/2 - temp.y -temp.height/2,2)) < blockWidth * 7)
                {
                    temp.rebuild(0);
                }
            }
        }
        for(Enemy each : enemies)
        {
            each.collision();
        }
        mc.weapon.collision();
    }

    public void animate()
    { //					main loop that runs all the important stuff
    	while(true)
        {      
            move();
            sort();
            collisionController();
            gridActive();

			screenX = movingObjects.get(0).x + movingObjects.get(0).width/2- screenWidth/2; // sets screenx to the temporary main character
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

    public boolean boxCollision(Box one, Box two)
    {
        for(int i = 0; i < one.holders.length; i ++)
        {
            for(int j = 0; j < two.holders.length; j ++)
            {
                double[] temp = new double[] {(two.holders[j].line.yInt-one.holders[i].line.yInt)/(one.holders[i].line.slope-two.holders[j].line.slope), (two.holders[j].line.yInt-one.holders[i].line.yInt)/(one.holders[i].line.slope-two.holders[j].line.slope) * one.holders[i].line.slope + one.holders[i].line.yInt};
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

    public void boxMainObjectCollision(Box one, MainObject two)
    {
        Box temp = new Box(two.width, two.height, two.x + two.width/2, two.y + two.height/2, two.x + two.width/2, two.y + two.height/2,0);
        temp.calculateMe();
        if(boxCollision(one,temp))
        {
            // System.out.println("collided");
        }
    }

    public static Block getBlock(double xIndex, double yIndex)
    { //                                                returns the block corresponding to the x and y index you submitted
        int xIndexInt = (int)xIndex;
        int yIndexInt = (int)yIndex;
        if(xIndexInt >= 0 && xIndexInt < chunks.size()*20 && yIndexInt >= 0 && yIndexInt < chunks.get(0).size()*20)
        {
            return chunks.get(xIndexInt / 20).get(yIndexInt/20).blocks[xIndexInt %20][yIndexInt %20];
        }
        return fakeBlock;
    }
    public static Chunk getChunk(double xIndex, double yIndex)
    {
        if(xIndex >= 0 && xIndex < chunks.size() &&yIndex >= 0 && yIndex < chunks.get(0).size())
        {
            return chunks.get((int)xIndex).get((int)yIndex);
        }
        return fakeChunk;
    }

    // dont touch below here this is just for key bindings and moustouching
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