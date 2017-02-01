import java.util.ArrayList;
public class WorldGenerator
{
	static int spawnPointX = 4;
	static int spawnPointY= 0;
	static int dirtLevel = 20;
	static int stoneLevel = 40;
	static Block pastBlock;//types == {0 : air; 1 : stone; 2 : lightDirt; 3 : darkDirt; 4 : Grass;}
	static ArrayList<double[]> worldData = new ArrayList<double[]>(); // each one has a startXIndex, previous amp, amplitude, a length
	static boolean ranOnce = false;
	public static void run(Block block)
	{
		if(!ranOnce)
		{
			ranOnce = !ranOnce;
			double previousAmplitude = 0;
			for(double currentX = 0; currentX < Screen.chunks.size() * 20;)
			{
				worldData.add(new double[]{currentX, previousAmplitude, Math.random()*4 +3, 20});
				previousAmplitude = worldData.get(worldData.size()-1)[2];
				currentX += 20;
			}
		}
		double previousAmplitude;
		double amplitude=0;
		double length=0;
		double startXIndex=0;
		for(double[] each : worldData)
		{
			if(each[0] + each[3] >= block.xIndex)
			{
				startXIndex = each[0];
				previousAmplitude = each[1];
				amplitude = each[2];
				length = each[3];
				break;
			}
		}
		if(block.yIndex < (int)((-Math.cos((block.xIndex-startXIndex) * 2 * Math.PI / length) +1) * amplitude + dirtLevel))
		{
			block.rebuild(0);
		}
		else if(block.yIndex < (int)((-Math.cos((block.xIndex-startXIndex) * 2 * Math.PI / length) +1) * amplitude + dirtLevel+1))
		{
			System.out.println(block.xIndex-startXIndex);
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