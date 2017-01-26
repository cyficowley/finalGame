import java.util.ArrayList;
import java.awt.Graphics;
public class Chunk extends MainObject// this will be like a chunk in minecraft so we can generate stuff and load things more efficiently
{
	int chunkSize = 20;
	int xIndex; //index overall in screen
	int yIndex;
	Block[][] blocks = new Block[chunkSize][chunkSize]; // 20 by 20, they are all squares
	boolean chunkActive = true;
	public Chunk(int xIndex, int yIndex)
	{
		super(xIndex * Screen.blockWidth * Screen.blockWidth, yIndex * Screen.blockWidth * Screen.blockWidth, Screen.blockWidth * Screen.blockWidth,Screen.blockWidth * Screen.blockWidth);
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		for (int i = 0; i < chunkSize; i ++) {
			for(int j = 0; j < chunkSize;  j ++)
			{
				blocks[i][j] = new Block(1, xIndex * chunkSize + i, yIndex * chunkSize + j, i, j, this);
			}
		}
	}
	public void drawMe(Graphics g)
	{
		for (int i = 0; i < chunkSize; i ++) {
			for(int j = 0; j < chunkSize;  j ++)
			{
				blocks[i][j].drawMe(g);
			}
		}
	}
}