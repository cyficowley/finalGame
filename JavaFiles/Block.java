public class Block //this is going to be the base block of the worlds, a crap ton of these will make up the world
{
	//types == {0 : air; 1 : stone;}
	int type;
	int xIndex;
	int yIndex;
	public Block( int type, int xIndex, int yIndex)
	{
		this.type = type;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}
}