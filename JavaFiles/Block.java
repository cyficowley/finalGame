public class Block extends MainObject//this is going to be the base block of the worlds, a crap ton of these will make up the world
{
	//types == {0 : air; 1 : stone;}
	int type;
	int xIndex; //index overall
	int yIndex;
	int subXIndex; // index within chunk
	int subYIndex;
	boolean blockActive;
	Chunk containingChunk; // the chunk that contains this blocks
	public Block(int type, int xIndex, int yIndex, int subXIndex, int subYIndex, Chunk chunk)
	{
		super(Screen.startX - xIndex * Screen.blockWidth,Screen.startY - yIndex * Screen.blockWidth, Screen.blockWidth, Screen.blockWidth); //sets it up as a main object
		this.type = type;
		containingChunk = chunk;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.subXIndex = subXIndex;
		this.subYIndex = subYIndex;
	}
}