package javaFiles;
import java.util.ArrayList;
public class WorldGenerator
{
	static int spawnPointX = 15;
	static int spawnPointY= 0;
	static int dirtLevel = 60;
	static int darkDirtLevel = 70;
	static int stoneLevel = 80;
	static Block pastBlock;//types == {0 : air; 1 : stone; 2 : lightDirt; 3 : mediumDirt; 4 : Grass; 5: darkDirt}
	static ArrayList<double[]> worldData = new ArrayList<double[]>(); // each one has a startXIndex, amplitude, a length
	static boolean ranOnce = false;
	static ArrayList<double[]> cavernSeeding = new ArrayList<double[]>(); // x, y, width of the cavern, direction, 
	static ArrayList<Double> stoneLevels = new ArrayList<Double>(); // x, y, height, width of the cavern, length
	public static void run(Block block)
	{
		if(block.xIndex >= 10 && block.xIndex <= 60 && block.yIndex <= 20)
		{
			block.rebuild(0);
		}
		else
		{
			block.rebuild(1);
		}
	}
	public static void finalChanges(){}
}