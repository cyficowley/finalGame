import java.awt.Graphics;
public class Block extends MainObject//this is going to be the base block of the worlds, a crap ton of these will make up the world
{
	//types == {0 : air; 1 : stone;}
	public int type;
	public int xIndex; //index overall
	public int yIndex;
	public int subXIndex; // index within chunk
	public int subYIndex;
	boolean collisionActive;
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
		characteristic.drawMe(g);
	}
}