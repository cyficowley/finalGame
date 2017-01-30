public class WorldGenerator
{
	static int spawnPointX = 4;
	static int spawnPointY= 0;
	static int dirtLevel = 10;
	static int stoneLevel = 30;
	static Block pastBlock;//types == {0 : air; 1 : stone; 2 : lightDirt; 3 : darkDirt; 4 : Grass;}
	public static void run(Block block)
	{
		if(block.yIndex < (int)(Math.sin(block.xIndex /5.0)* 3) + dirtLevel)
		{
			block.rebuild(0);
		}
		else if(block.yIndex == (int)(Math.sin(block.xIndex/5.0)* 3) + dirtLevel)
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