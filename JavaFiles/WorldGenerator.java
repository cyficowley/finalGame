import java.util.ArrayList;
public class WorldGenerator
{
	static int spawnPointX = 5;
	static int spawnPointY= 0;
	static int dirtLevel = 60;
	static int stoneLevel = 70;
	static Block pastBlock;//types == {0 : air; 1 : stone; 2 : lightDirt; 3 : darkDirt; 4 : Grass;}
	static ArrayList<double[]> worldData = new ArrayList<double[]>(); // each one has a startXIndex, amplitude, a power,
	static boolean ranOnce = false;
	static ArrayList<double[]> cavernSeeding = new ArrayList<double[]>(); // x, y, width of the cavern, direction, 
	static ArrayList<Double> stoneLevels = new ArrayList<Double>(); // x, y, height, width of the cavern, length
	public static void run(Block block)
	{
		if(!ranOnce)
		{
			ranOnce = !ranOnce;
			for(double currentX = 0; currentX < Screen.chunks.size() * 20;)
			{
				double amplitude;
				double length;
				double power;
				if(Math.random() > .6)//flatish area;
				{
					amplitude = Math.pow(Math.random(),2) *4;
					power = (Math.random() + .8)/(Math.random() + .8);
					length = 10 + Math.random() * 15;
				}
				else if(Math.random() > .3) // small hill
				{
					amplitude = Math.pow(Math.random(),.5) *5;
					power = (Math.random() + .5)/(Math.random() + .5);
					length = 10 + amplitude *2;
				}
				else // large hill
				{
					amplitude = Math.pow(Math.random(),.7) *14;
					power = (Math.random() + .3)/(Math.random() + .3);
					length = 10 + amplitude *2;
				}
				worldData.add(new double[]{currentX, amplitude, length, power}); // this controls the height and width of the hills
				currentX += worldData.get(worldData.size()-1)[2];
			}
		}
		double previousAmplitude;
		double amplitude=0;
		double length=0;
		double startXIndex=0;
		double power = 0;
		for(double[] each : worldData)
		{
			if(each[0] + each[2] >= block.xIndex)
			{
				startXIndex = each[0];
				amplitude = each[1];
				length = each[2];
				power = each[3];
				break;
			}
		}
		double dirt = -Math.pow((Math.cos((block.xIndex-startXIndex) * 2 * Math.PI / length + Math.PI) +1),power) * amplitude + dirtLevel;
		double stone = -Math.pow((Math.cos((block.xIndex-startXIndex) * 2 * Math.PI / length + Math.PI) +1),power) * amplitude/1.5 + stoneLevel;
		if(block.yIndex < (int)(dirt))
		{
			block.rebuild(0);
		}
		else if(block.yIndex < (int)(dirt+1))
		{
			block.rebuild(4);
			stoneLevels.add(stone); // so it only runs once
		}
		else if(block.yIndex < (int)(stone))
		{
			if(Math.random() * (stone - dirt) < block.yIndex - dirt)
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
	public static void finalChanges()
	{
		for(int i = 0; i < stoneLevels.size(); i ++)
		{
			if(Math.random() < .03)
			{
				double[] tempArray = new double[6];
				int maxYIndex = Screen.chunks.get(0).size() *20;
				tempArray[0] = i;
				tempArray[1] = (int)((maxYIndex - stoneLevels.get(i)) * Math.random() + stoneLevels.get(i));
				tempArray[2] = 4 + Math.random() * 4;
				tempArray[3] = 2 * Math.PI * Math.random();
				tempArray[4] = 3 + Math.pow(Math.random(),4)* 20;// x, y, width of the cavern, direction, length
				cavernSeeding.add(tempArray);
			}
		}
		for(double[] each : cavernSeeding)
		{	
			ArrayList<Block> gonnaBeDetroyed = new ArrayList<Block>();
			for(double k = 0; k < each[4]; k ++)	
			{								
				double startX = each[0]- Math.cos(each[3]) * k-each[2];// x, y, width of the cavern, direction, length
				double endX = each[0]-Math.cos(each[3]) * k+each[2];
				double startY = each[1]-Math.sin(each[3]) * k-each[2];
				double endY = each[1]- Math.sin(each[3]) * k+each[2];
				for(double i = startX; i <= endX; i++)
				{
					for(double j = startY; j <= endY; j ++)
					{
						Block tempBlock = Screen.getBlock(i,j);
						if(tempBlock !=null)
						{
							if(Math.sqrt((double)Math.pow(startX +each[2]- tempBlock.xIndex,2) + (double)Math.pow(startY +each[2]- tempBlock.yIndex,2)) <= each[2])
							{
								gonnaBeDetroyed.add(tempBlock);
							}
						}
					}
				}
			}
			for(Block each2: gonnaBeDetroyed)
			{
				each2.rebuild(0);
			}
		}
	}
}