package finalGame;
import com.sun.javafx.application.PlatformImpl;
import finalGame.enemies.Enemy;
import finalGame.inventoryStuff.Inventory;
import finalGame.inventoryStuff.InventoryBlock;
import finalGame.usefulClasses.*;
import finalGame.usefulClasses.Box;
import finalGame.worldGen.Block;
import finalGame.worldGen.Chunk;
import finalGame.worldGen.WorldGenerator;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Screen extends JPanel implements MouseMotionListener, MouseListener
{ // STATIC STUFF YOU CAN ACCESS AT ANY TIME IN ANY CLASS BY SAYING SCREEN.VARIABLE
	Action[] actions = new Action[24]; // for key bindings
	public static double screenWidth; // the width of the screen
	public static double screenHeight; // the hieght of the screen
	public static double screenX = 0; //  the x and y of the viewpoint of the screen
	public static double screenY = 0;

	public static String data = "Drawing"; // just some data that will display on the screen

	public static double mouseX= 0; // the x and y of the mouse, will get everytime the mouse is moved
	public static double mouseY= 0;
	public static MainObject mouseMainObject = new MainObject(0,0,0,0);
	public static boolean mouseDown= false; // whether mouse is pressed
	public static boolean mouseFirstDown = false;
	public static boolean mouseRightDown = false;

	double seed;

	Font ariel = new Font("Ariel", Font.PLAIN, 18); // current font to draw on the screen
	boolean started = false;

	public static boolean a; // if a is down and so down
	public static boolean s;
	public static boolean d;
	public static boolean w;
	public static boolean space;
	public static boolean e;
	public static boolean q;
	public static boolean esc;
	public static boolean endItAll;
	public static boolean pause = false;

	public static int startX = 0; // the starting x and y of the screen
	public static int startY = 0;
	public static double blockWidth = 40; // width in pixels of a block, default is 40

	public static double gravity = 0.2; // gravity

	public static ArrayList<CollisionContainer> collisions; // arraylist comtainer for all the collisions

	public static Chunk fakeChunk = new Chunk(0, 0);
	public static Block fakeBlock = new Block(0, 0,0,0,0, fakeChunk);

	boolean changed = false;

	double frames = 0;
	long timeLastChecked = 0;
	double currentFramrate= 0;

	public static double enemyScore = 0;
	public static double totalScore = 0;
	public static double depthScore = 0;


	long time;

	int endItAllCounter = 0;
	int level = 0;

	public static ArrayList<ArrayList<Chunk>> chunks; // all the chunks, in a 2d arraylist

	public static ArrayList<MovingObject> movingObjects; //all the moving objects
	public static ArrayList<Enemy> enemies;

	public static Inventory inventory;

	public static int currentXChunks = 5; //the current number of chunks in the x direction
	public static int currentYChunks = 60; //the current number of chunks in the y direction

	public static MainCharacter mc;

	public static ButtonMenu escMenu;

	public static Background background;

	public static StartScreen startScreen;

	public static WorldGenerator worldGenerator;

	public String[] names = new String[3];
	public int[] scores = new int[3];

	JTextField textField;

	public Screen()
	{
		setFocusable(true); //setup stuff
		addMouseListener(this);
		addMouseMotionListener(this);
		System.setProperty("sun.java2d.opengl", "true");

	}
	public Dimension getPreferredSize()
	{
		return new Dimension(640,480); //settup for default so it expands
	}
	public void initialize()
	{
		endItAll  = false;
		while(screenHeight < 600) // the while and try after it makes it so it expands and gets the screenWidth and height accuratly
		{
			try {Thread.sleep(10);}
			catch(InterruptedException ex) {}

			screenWidth = getWidth();
			screenHeight = getHeight();
		}
		try {Thread.sleep(300);}
			catch(InterruptedException ex) {}
		worldGenerator  = new WorldGenerator();
		chunks  = new ArrayList<ArrayList<Chunk>>();
		setKeyListener();
		for(int i = 0; i < currentXChunks; i ++)
		{
			chunks.add(new ArrayList<Chunk>());
			for(int j = 0; j < currentYChunks; j ++)
			{
				chunks.get(i).add(new Chunk(i,j));//setting up and adding the chunks to the 2d arraylist
			}
		}

		collisions = new ArrayList<CollisionContainer>();
		movingObjects  = new ArrayList<MovingObject>();
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
		background = new Background();
		mc = new MainCharacter(WorldGenerator.spawnPointX * blockWidth,WorldGenerator.spawnPointY * blockWidth, 72-9, 144 -4.5, 25);
		movingObjects.add(mc); 
		inventory = new Inventory();
		enemies = new ArrayList<Enemy>();

		String[] quitMenuLabels = {"Return to Game", "Quit"};

		File tfile = new File("res/scores.txt");

		try
		{
			Scanner reader = new Scanner(tfile);
			String temp = reader.nextLine();
			String newString = temp;
			String[] temp2 = new String[3];
			int lolz = 1;
			for(int i = 0; i < 3; i ++)
			{
				temp2[i] = newString.substring(lolz, newString.indexOf("&" , lolz +1));
				lolz = newString.indexOf("&", lolz +1) + 1;
			}
			for(int i = 0; i < 3; i ++)
			{
				names[i] = temp2[i].substring(0,temp2[i].indexOf("*"));
				scores[i] = Integer.parseInt(temp2[i].substring(temp2[i].indexOf("*") + 1, temp2[i].length()));
			}

			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			names[0] = "Highscore";
			names[1] = "file-is";
			names[2] = "broken";
		}
		escMenu = new ButtonMenu(screenWidth * 0.35, screenHeight * 0.3, screenWidth * 0.3, screenHeight * 0.4, quitMenuLabels);
		startScreen = new StartScreen();
		worldGenerator.finalChanges();
		pause = false;
		String bip = "res/music/theme.mp3";
		PlatformImpl.startup(() -> {});
		Media hit = new Media(new File(bip).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				mediaPlayer.seek(Duration.ZERO);
			}
		});
		mediaPlayer.play();
		animate();
	}
	boolean showCredits = false;
	public void paintComponent(Graphics gTemp)
	{
		if(started)
		{
			frames++;
			if(time - timeLastChecked > 5000) // updates frame rate every five seconds, should be something around 55
			{
				currentFramrate = frames/5;
				timeLastChecked = time;
				frames = 0;
			}
			time = System.currentTimeMillis(); // gets time when started drawing

			Graphics2D g = (Graphics2D)gTemp;
			if(startScreen.raising)
			{
				background.drawMe(g);
				for(int i = 0; i < chunks.size(); i ++)
				{
					for(int j = 0; j < chunks.get(i).size(); j++)
					{
						if(chunks.get(i).get(j).active) // draws only active chunks that are in or very near screen
						{
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
							for(int k = 0; k < chunks.get(i).get(j).containedObjects.size(); k ++)
							{
								chunks.get(i).get(j).containedObjects.get(k).drawMe(g); // draws everything within active range
							}
						}
					}
				}
				inventory.drawInventory(g);
				escMenu.drawMe(g);
				g.setFont(ariel);
				totalScore = depthScore + enemyScore;
				g.setColor(new Color(0,0,0));
				g.drawString("SCORE : " + Integer.toString((int)totalScore), (int)screenWidth - 149, (int)21);
				int addition = 0;
				if(inventory.visible)
				{
					addition += 8 * (InventoryBlock.blockSize + 10) +5;
				}
				g.drawString("HIGHSCORES", 9 + addition, 29);
				for(int i = 0; i < 3; i ++)
				{
					g.drawString(names[i],9 + addition,79 + 40 * i);
					g.drawString(Integer.toString(scores[i]),159 + addition,79 + 40 * i);

				}
				g.setColor(Color.red);
				g.drawString("HIGHSCORES", 10 + addition, 30);
				g.drawString("SCORE : " + Integer.toString((int)totalScore), (int)screenWidth - 150, (int)20);
				for(int i = 0; i < 3; i ++)
				{
					g.drawString(names[i],10 + addition,80 + 40 * i);
					g.drawString(Integer.toString(scores[i]),160 + addition,80 + 40 * i);

				}
			}


			startScreen.drawMe(g);

			if(endItAll)
			{
				if(endItAllCounter < screenHeight)
				{
					endItAllCounter ++;
				}
				g.setColor(Color.BLACK);
				g.fillRect(0,(int)(-screenHeight + endItAllCounter), (int)screenWidth,(int)screenHeight);
				if(!showCredits)
				{
					g.setColor(Color.RED);
					Font temp = new Font("Impact", Font.PLAIN, 200);
					Font temp2 = new Font("Impact", Font.PLAIN, 92);
					g.setFont(temp);
					FontMetrics fontMetrics = g.getFontMetrics(temp);
					int width = fontMetrics.stringWidth("You Died");
					g.drawString("You Died",(int)(Screen.screenWidth/2 -width/2), (int)screenHeight/2);
					g.setFont(temp2);
					fontMetrics = g.getFontMetrics(temp2);
					int width2 = fontMetrics.stringWidth("Your Score Was " + Integer.toString((int)totalScore));
					g.drawString("Your Score Was " + (int)totalScore,(int)(Screen.screenWidth/2 -width2/2), (int)screenHeight/4*3);

					Font yolo = new Font("Ariel", Font.PLAIN, 32);
					g.setFont(yolo);
					fontMetrics = g.getFontMetrics(yolo);
					width = fontMetrics.stringWidth("Press Q to see credits");
					g.drawString("Press Q to see credits",(int)(Screen.screenWidth/2 -width/2), (int)screenHeight - 10);

					if(q)
					{
						showCredits = true;
					}
				}
				else
				{
					if(e)
					{
						showCredits = false;
					}
					g.setColor(Color.red);
					Font temp2 = new Font("Impact", Font.PLAIN, 92);
					g.setFont(temp2);
					FontMetrics fontMetrics = g.getFontMetrics(temp2);
					int width = fontMetrics.stringWidth("Credits");
					g.drawString("Credits",(int)(Screen.screenWidth/2 -width/2), (int)screenHeight/3);
					Font yolo = new Font("Ariel", Font.PLAIN, 32);
					g.setFont(yolo);
					g.drawString("Designed and Coded by Cyrus Cowley", 50,(int)Screen.screenHeight/2- 40);

					g.drawString("     With help from ::", 50,(int)Screen.screenHeight/2);

					g.drawString("Andrew Young : All music", 50,(int)Screen.screenHeight/2 + 40);
					g.drawString("Mark : One dirt block and entertainment as developing, said we should have wavy lines as the ground", 50,(int)Screen.screenHeight/2 + 80);
					g.drawString("Shane : All the other blocks and the sword", 50,(int)Screen.screenHeight/2 + 120);
					g.drawString("Chris : Inventory UI", 50,(int)Screen.screenHeight/2 +160);
					g.drawString("Michael : Main character design", 50,(int)Screen.screenHeight/2 + 200);
					fontMetrics = g.getFontMetrics(yolo);
					width = fontMetrics.stringWidth("Press E to return");
					g.drawString("Press E to return",(int)(Screen.screenWidth/2 -width/2), (int)screenHeight);
				}
			}
		}
	}

	public void gridActive()
	{ //						This method sets the chunks near the screen active so they move and draw
		MainObject tempScreen = new MainObject(screenX-screenWidth*.35, screenY-screenHeight*.35,screenWidth*1.7,screenHeight*1.7);
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
				if(chunks.get(i).get(j).active)
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
//		if(mouseRightDown)
//		{
//			Block temp = getBlock((int)((mouseX + screenX)/blockWidth),(int)((mouseY + screenY)/blockWidth));
//			if(Math.sqrt(Math.pow(mc.x + mc.width/2 - temp.x -temp.width/2,2)+ Math.pow(mc.y + mc.height/2 - temp.y -temp.height/2,2)) < blockWidth * 7)
//			{
//				temp.characteristic.breakBlock(temp);
//			}
//		}
		for(Enemy each : enemies)
		{
			each.collision();
		}
		mc.weapon.collision();
		mc.actualWeapon.collision();
		inventory.collision();
	}
	boolean unchosen = true;
	MovingObject chosen;
	static String nameAdded;
	static boolean removeTextThing = false;
	public void animate()
	{ //					main loop that runs all the important stuff
		while(!pause)
		{

			if(startScreen.raising)
			{
				move();
				sort();
				collisionController();
				gridActive();
			}

			screenX = mc.x + mc.width/2- screenWidth/2; // sets screenx to the temporary main character
			screenY = mc.y + mc.height/2 - screenHeight/2;

			if(mc.health <= 0)
			{
				if(unchosen)
				{
					Block closest = getBlockXY(mc.x,mc.y);
					double closestLength = 1000;
					MovingObject closestObject = null;
					for(int i = -1; i <2; i ++)
					{
						for(int j = -1; j < 2; j ++)
						{
							try
							{
								for(MovingObject each : chunks.get(closest.containingChunk.xIndex - i).get(closest.containingChunk.yIndex - j).containedObjects)
								{
									if(each.type.equals("enemy"))
									{
										double swag = Math.sqrt(Math.pow(mc.x - each.x, 2) + Math.pow(mc.y - each.y, 2));
										if(swag < closestLength)
										{
											closestLength = swag;
											closestObject = each;
										}
									}
								}
							}catch(Exception e){}
						}
					}

					if(closestLength == 1000)
					{
						for(MovingObject each : movingObjects)
						{
							if(each.type.equals("enemy"))
							{
								chosen = each;
								break;
							}
						}
					}
					else
					{
						chosen = closestObject;
					}
					unchosen = false;

					if(totalScore > scores[2])
					{

						textField = new JTextField("Your name (max of 10 characters)");
						textField.setBackground(Color.RED);
						textField.setSelectionColor(Color.black);
						textField.setForeground(Color.BLACK);
						textField.setBounds((int)(screenWidth/2 - 100),(int)(screenHeight*4/5),200,30);
						textField.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {
								nameAdded = textField.getText();
								String toAdd = "";
								toAdd += "&";
								if(nameAdded.length() > 10)
								{
									nameAdded = nameAdded.substring(0,10);
								}
								int selector = 0;
								for(int i = 0; i < 3; i ++)
								{
									if(totalScore > scores[i] && selector == 0)
									{
										toAdd += nameAdded + "*" + Integer.toString((int)totalScore) + "&";
										selector --;
									}
									else
									{
										toAdd += names[i + selector] + "*" + Integer.toString(scores[i + selector]) + "&";
									}
								}
								try
								{
									File file = new File("res/scores.txt");
									FileWriter writer = new FileWriter(file);

									writer.write(toAdd);
									writer.flush();
									writer.close();
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
								removeTextThing = true;

							}
						});
						this.add(textField);

					}


				}
				if(removeTextThing)
				{
					this.remove(textField);
					removeTextThing = false;
				}
				screenX = chosen.x + chosen.width/2- screenWidth/2; // sets screenx to the temporary main character
				screenY = chosen.y + chosen.height/2 - screenHeight/2;
				endItAll = true;
			}
			repaint();
			try
			{
				Thread.sleep(16);
			} 
			catch(InterruptedException ex)
			{
				Thread.currentThread().interrupt();
			}
			if(mouseDown && mouseFirstDown)
			{
				mouseFirstDown = false;
			} 
			if(mouseDown && !changed)
			{
				mouseFirstDown = true;
				changed = true;
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
	public static int getBlockType(double x, double y)
	{ //                                                returns the block corresponding to the x and y index you submitted
		int xIndexInt = (int)(x/blockWidth);
		int yIndexInt = (int)(y/blockWidth);
		if(xIndexInt >= 0 && xIndexInt < chunks.size()*20 && yIndexInt >= 0 && yIndexInt < chunks.get(0).size()*20)
		{
			return chunks.get(xIndexInt / 20).get(yIndexInt/20).blocks[xIndexInt %20][yIndexInt %20].type;
		}
		return -1;
	}
	public static Block getBlockXY(double x, double y)
	{ //                                                returns the block corresponding to the x and y index you submitted
		int xIndexInt = (int)(x/blockWidth);
		int yIndexInt = (int)(y/blockWidth);
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
	static class EDown extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
			e = true;
			inventory.visible = !inventory.visible;
		}}
	static class EUp extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
			e = false;
		}}
	static class QDown extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
			q = true;
			inventory.switchWeapon();
		}}
	static class QUp extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
			q = false;
		}}
	static class EscDown extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
			esc = true;
			if(!inventory.visible)
			{
				escMenu.toggleVisible();// toggles visibility of escape menu
			}
			else
			{
				inventory.visible = false;
			}
		}}
	static class EscUp extends AbstractAction{ public void actionPerformed( ActionEvent tf ){
			esc = false;
		}}

	public void setKeyListener()
	{
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
		actions[18] = new EDown();
		this.getInputMap().put( KeyStroke.getKeyStroke( "E" ), "doEDown" );
		this.getActionMap().put( "doEDown", actions[18] );
		actions[19] = new EUp();
		this.getInputMap().put( KeyStroke.getKeyStroke( "released E" ), "doEUp" );
		this.getActionMap().put( "doEUp", actions[19] );
		actions[20] = new EscDown();
		this.getInputMap().put( KeyStroke.getKeyStroke( "ESCAPE" ), "doEscapeDown" );
		this.getActionMap().put( "doEscapeDown", actions[20] );
		actions[21] = new EscUp();
		this.getInputMap().put( KeyStroke.getKeyStroke( "released ESCAPE"), "doEscapeUp" );
		this.getActionMap().put( "doEscapeUp", actions[21] );  //Setting up all the classes for the keybindings
		actions[22] = new QDown();
		this.getInputMap().put( KeyStroke.getKeyStroke( "Q" ), "doQDown" );
		this.getActionMap().put( "doQDown", actions[22] );
		actions[23] = new QUp();
		this.getInputMap().put( KeyStroke.getKeyStroke( "released Q" ), "doQUp" );
		this.getActionMap().put( "doQUp", actions[23] );
	}

	public void mousePressed(MouseEvent e)
	{
		if(SwingUtilities.isRightMouseButton(e))
		{
			mouseRightDown = true;
		}
		else
		{
			mouseDown = true;
		}
	}
	public void mouseReleased(MouseEvent e)
	{
		mouseDown = false;
		changed = false;
		mouseRightDown = false;
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e){}
	public void mouseMoved(MouseEvent e) {
	   mouseY = e.getY();
	   mouseX = e.getX();
	   mouseMainObject.x = mouseX;
	   mouseMainObject.y = mouseY;
	}

	public void mouseDragged(MouseEvent e) {
	   mouseY = e.getY();
	   mouseX = e.getX();
	   mouseMainObject.x = mouseX;
	   mouseMainObject.y = mouseY;
	}
	public static void menuButtonClick(String button){ //menu button actions
		/* Example
		if(button.equals(<Button Label>)){
			<action>
		}
		*/
		if(button.equals("Quit")){
			Screen.closeWindow();
		}else if(button.equals("Return to Game")){
			escMenu.toggleVisible();
		}
	}

	public static void closeWindow(){ // closes the window
		System.exit(0);
	}
}