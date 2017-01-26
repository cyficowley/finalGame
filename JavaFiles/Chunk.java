import java.util.ArrayList;
public class Chunk // this will be like a chunk in minecraft so we can generate stuff and load things more efficiently
{
	int chunkSize = 20;
	int xIndex; //index overall in screen
	int yIndex;
	Block[][] blocks = new Block[chunkSize][chunkSize]; // 20 by 20, they are all squares
	public Chunk(int xIndex, int yIndex)
	{
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		for (int i = 0; i < chunkSize; i ++) {
			for(int j = 0; j < chunkSize;  j ++)
			{
				blocks[i][j] = new Block(0, xIndex * chunkSize + i, yIndex * chunkSize + j, i, j);
			}
		}
	}
}