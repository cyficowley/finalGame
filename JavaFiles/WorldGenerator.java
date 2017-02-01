import java.util.ArrayList;
public class WorldGenerator
{
	static int spawnPointX = 4;
	static int spawnPointY= 0;
	static int dirtLevel = 20;
	static int stoneLevel = 40;
	static Block pastBlock;//types == {0 : air; 1 : stone; 2 : lightDirt; 3 : darkDirt; 4 : Grass;}
	static ArrayList<double[]> worldData = new ArrayList<double[]>(); // each one has a startXIndex, amplitude, a divizor
	static boolean ranOnce = false;
	public static void run(Block block)
	{
		if(!ranOnce)
		{
			ranOnce = !ranOnce;
			for(double currentX = 0; currentX < Screen.chunks.size() * 20;)
			{
				worldData.add(new double[]{currentX, Math.random() * 5, Math.random() * 20 +8});
				currentX += worldData.get(worldData.size() -1)[2];
			}
		}
		double amplitude =0;
		double startXIndex=0;
		double length=0;
		for(double[] each : worldData)
		{
			if(each[0] > block.xIndex)
			{
				startXIndex = each[0];
				amplitude = each[1];
				length = each[2];
				break; 
			}
		}

		if(block.yIndex < (int)((-Math.cos((block.xIndex-startXIndex)/(length/(Math.PI * 2)))+1)* amplitude) + dirtLevel)
		{
			block.rebuild(0);
		}
		else if(block.yIndex < (int)((-Math.cos((block.xIndex-startXIndex)/(length/(Math.PI * 2)))+1)* amplitude) + dirtLevel +1)
		{
			block.rebuild(4);
		}
		else if(block.yIndex < stoneLevel)
		{
			if(Math.random() * (stoneLevel - dirtLevel) < block.yIndex - dirtLevel)
			{
				block.rebuild(2);
			}
			else
			{
				block.rebuild(3);
			}
		}
		else
		{
			block.rebuild(1);
		}
		pastBlock = block;
	}
}