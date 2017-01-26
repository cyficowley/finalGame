import java.awt.Graphics;
import java.awt.Color;
public class Block extends MainObject//this is going to be the base block of the worlds, a crap ton of these will make up the world
{
	//types == {0 : air; 1 : stone;}
	public int type;
	public int xIndex; //index overall
	public int yIndex;
	public int subXIndex; // index within chunk
	public int subYIndex;
	boolean collisionActive; // if it is possible for stuff to collide with this block;
	Chunk containingChunk; // the chunk that contains this blocks
	BlockCharacteristic characteristic;
	public Block(int type, int xIndex, int yIndex, int subXIndex, int subYIndex, Chunk chunk)
	{
		super(Screen.startX - xIndex * Screen.blockWidth,Screen.startY - yIndex * Screen.blockWidth, Screen.blockWidth, Screen.blockWidth); //sets it up as a main object
		this.type = type;
		containingChunk = chunk;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.subXIndex = subXIndex;
		this.subYIndex = subYIndex;
		if(type == 1)
		{
			characteristic = new StoneCharacteristic(this);
		}
		else
		{
			characteristic = new BlockCharacteristic(this);
		}
	}
	public void drawMe(Graphics g)
	{
		if(collisionActive)
		{
			characteristic.drawMe(g, Color.red);
		}
		else
		{
			characteristic.drawMe(g);
		}
	}
	public void rebuild(int type)
	{
		this.type = type;
		if(type == 1)
		{
			characteristic = new StoneCharacteristic(this);
		}
		else
		{
			characteristic = new BlockCharacteristic(this);
		}
	}
	public void setUp()
	{
		//check Above
		int total = 0;
		if(subYIndex == 0)
		{
			if(containingChunk.yIndex != 0)
			{
				if(Screen.chunks.get(containingChunk.xIndex).get(containingChunk.yIndex -1).blocks[subXIndex][containingChunk.chunkSize -1].type != 0)
				{
					total ++;
				}
			}	
		}
		else
		{
			if(containingChunk.blocks[subXIndex][subYIndex -1].type != 0)
			{
				total ++;
			}
		}
		//check Left
		if(subXIndex == 0)
		{
			if(containingChunk.xIndex != 0)
			{
				if(Screen.chunks.get(containingChunk.xIndex -1).get(containingChunk.yIndex).blocks[containingChunk.chunkSize -1][subYIndex].type != 0)
				{
					total ++;
				}
			}
		}
		else
		{
			if(containingChunk.blocks[subXIndex -1][subYIndex].type != 0)
			{
				total ++;
			}
		}
		//check right
		if(subXIndex == containingChunk.chunkSize -1)
		{
			if(containingChunk.xIndex != Screen.currentXChunks -1)
			{
				if(Screen.chunks.get(containingChunk.xIndex +1).get(containingChunk.yIndex).blocks[0][subYIndex].type != 0)
				{
					total ++;
				}
			}
		}
		else
		{
			if(containingChunk.blocks[subXIndex +1][subYIndex].type != 0)
			{
				total ++;
			}
		}
		// check below
		if(subYIndex == containingChunk.chunkSize -1)
		{
			if(containingChunk.yIndex != Screen.currentYChunks -1)
			{
				if(Screen.chunks.get(containingChunk.xIndex).get(containingChunk.yIndex +1).blocks[subXIndex][0].type != 0)
				{
					total ++;
				}
			}
		}
		else
		{
			if(containingChunk.blocks[subXIndex][subYIndex +1].type != 0)
			{
				total ++;
			}
		}
		if(total != 4)
		{
			collisionActive = true;
		}
	}
}