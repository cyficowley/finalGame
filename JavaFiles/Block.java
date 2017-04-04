package javaFiles;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
public class Block extends MainObject//this is going to be the base block of the worlds, a crap ton of these will make up the world
{
	//types == {0 : air; 1 : stone;}
	public int type;
	public int xIndex; //index overall
	public int yIndex;
	public int subXIndex; // index within chunk
	public int subYIndex;
	boolean highlight = false;
	boolean collisionActive; // if it is possible for stuff to collide with this block;
	Chunk containingChunk; // the chunk that contains this blocks
	BlockCharacteristic characteristic;
	ArrayList<TouchData> touched= new ArrayList<TouchData>(); // all the movingobjects touch
	public Block(int type, int xIndex, int yIndex, int subXIndex, int subYIndex, Chunk chunk)
	{
		super(xIndex * Screen.blockWidth,yIndex * Screen.blockWidth, Screen.blockWidth, Screen.blockWidth, true); //sets it up as a main object
		this.type = type;
		super.type = "block";
		containingChunk = chunk; // the chunk this block is in
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.subXIndex = subXIndex;
		this.subYIndex = subYIndex;
		if(type == 1)
		{
			characteristic = new StoneCharacteristic(this);
		}
		else if(type == 2)
		{
			characteristic = new DirtCharacteristic(this);
		}
		else if(type == 3)
		{
			characteristic = new LightDirtCharacteristic(this);
		}
		else if(type == 4)
		{
			characteristic = new GrassCharacteristic(this);
		}
		else if(type == 5)
		{
			characteristic = new DarkDirtCharacteristic(this);
		}
		else
		{
			characteristic = new BlockCharacteristic(this); // any thing that is empty should make it just a plain block characteristic
		}
	}
	public void drawMe(Graphics2D g)
	{
		x = xIndex * Screen.blockWidth;
		y = yIndex * Screen.blockWidth;
		if(type == 0)
		{
			collisionActive = false;
		}
		else if(highlight)
		{
			g.setColor(Color.magenta);
			g.fillRect((int)(xIndex * Screen.blockWidth - Screen.screenX), (int)(yIndex * Screen.blockWidth- Screen.screenY),(int)(Screen.blockWidth), (int)(Screen.blockWidth));
		}
		else
		{
			characteristic.drawMe(g);
		}
		touched.clear();
	}
	public void rebuild(int type) 
	{ // run this method if you are setting the block to a differnt type like empty or stone or something
		this.type = type;
		boolean checkIfThisAcitve = true;
		if(type == 1)
		{
			characteristic = new StoneCharacteristic(this);
		}
		else if(type == 2)
		{
			characteristic = new DirtCharacteristic(this);
		}
		else if(type == 3)
		{
			characteristic = new LightDirtCharacteristic(this);
		}
		else if(type == 4)
		{
			characteristic = new GrassCharacteristic(this);
		}
		else if(type == 5)
		{
			characteristic = new DarkDirtCharacteristic(this);
		}
		// else if( type == 2)
		// {
		// 	bla bla bla this is where we would put dirt
		// }
		else // this sets all the ones next to it to collisionActive:true because it is now air, just ignore the specifics here it is hard because of how the chunks are defined so you can't quite make it so that you easily access whatever is above or below because it is in seperate chunks so most of this is to deal with that
		{
			checkIfThisAcitve = false;
			characteristic = new BlockCharacteristic(this);
			if(yIndex != 0)
			{
				if(Screen.getBlock(xIndex, yIndex -1).type != 0)
				{
					Screen.getBlock(xIndex, yIndex -1).collisionActive = true;
				}
			}
			//check Left
			if(xIndex != 0)
			{
				if(Screen.getBlock(xIndex -1, yIndex).type != 0)
				{
					Screen.getBlock(xIndex -1, yIndex).collisionActive = true;
				}
			}
			//check right
			if(xIndex != Screen.chunks.size()*20-1)
			{
				if(Screen.getBlock(xIndex+1, yIndex).type != 0)
				{
					Screen.getBlock(xIndex+1, yIndex).collisionActive = true;
				}
			}
			// check below
			if(xIndex != Screen.chunks.get(0).size()*20-1)
			{
				if(Screen.getBlock(xIndex, yIndex +1).type != 0)
				{
					Screen.getBlock(xIndex, yIndex +1).collisionActive = true;
				}
			}
		}
		if(checkIfThisAcitve)
		{
			if(yIndex != 0)
			{
				if(Screen.getBlock(xIndex, yIndex -1).type == 0)
				{
					collisionActive = true;
				}
			}
			//check Left
			if(xIndex != 0)
			{
				if(Screen.getBlock(xIndex -1, yIndex).type == 0)
				{
					collisionActive = true;
				}
			}
			//check right
			if(xIndex != Screen.chunks.size()*20-1)
			{
				if(Screen.getBlock(xIndex+1, yIndex).type == 0)
				{
					collisionActive = true;
				}
			}
			// check below
			if(xIndex != Screen.chunks.get(0).size()*20-1)
			{
				if(Screen.getBlock(xIndex, yIndex +1).type == 0)
				{
					collisionActive = true;
				}
			}
		}
	}
	public void setUp()
	{
		//check Above
		int total = 0;// this sets all the ones next to it to collisionActive:true because it is now air, just ignore the specifics here it is hard because of how the chunks are defined so you can't quite make it so that you easily access whatever is above or below because it is in seperate chunks so most of this is to deal with that
		if(yIndex != 0)
		{
			if(Screen.getBlock(xIndex, yIndex -1).type !=0)
			{
				total ++;
			}
		}
		//check Left
		if(xIndex != 0)
		{
			if(Screen.getBlock(xIndex -1, yIndex).type !=0)
			{
				total ++;
			}
		}
		//check right
		if(xIndex != Screen.chunks.size()*20-1)
		{
			if(Screen.getBlock(xIndex +1, yIndex).type !=0)
			{
				total ++;
			}
		}
		// check below
		if(yIndex != Screen.chunks.get(0).size()*20-1)
		{
			if(Screen.getBlock(xIndex, yIndex+1).type !=0)
			{
				total ++;
			}
		}
		if(total != 4)
		{
			collisionActive = true;
		}
		//this whole thing just checks whether or not anything is empty next to it
	}
}