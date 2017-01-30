import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class Chunk extends MainObject// this will be like a chunk in minecraft so we can generate stuff and load things more efficiently
{
	int chunkSize = 20;
	int xIndex; //index overall in screen
	int yIndex;
	Block[][] blocks = new Block[chunkSize][chunkSize]; // 20 by 20, they are all squares
	boolean active = true;

	ArrayList<MovingObject> containedObjects = new ArrayList<MovingObject>(); // all the moving objects within this chunk
	public Chunk(int xIndex, int yIndex)
	{
		super(xIndex * Screen.blockWidth * 20, yIndex * Screen.blockWidth * 20, Screen.blockWidth * 20,Screen.blockWidth * 20); //makes it a mainobject
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		for (int i = 0; i < chunkSize; i ++) {
			for(int j = 0; j < chunkSize;  j ++)
			{
				blocks[i][j] = new Block(1, xIndex * chunkSize + i, yIndex * chunkSize + j, i, j, this);
			}
		}
	}
	public void drawMe(Graphics2D g)
	{
		for (int i = 0; i < chunkSize; i ++) {
			for(int j = 0; j < chunkSize;  j ++)
			{
				blocks[i][j].drawMe(g);
			}
		}
	}
	public void setUp()
	{
		for (int i = 0; i < chunkSize; i ++) {
			for(int j = 0; j < chunkSize;  j ++)
			{
				blocks[i][j].setUp();
			}
		}
		for (int i = 0; i < chunkSize; i ++) {
			for(int j = 0; j < chunkSize;  j ++)
			{
				WorldGenerator.run(blocks[i][j]);
			}
		}
	}
}