import java.util.ArrayList;
public class Chunk // this will be like a chunk in minecraft so we can generate stuff and load things more efficiently
{
	ArrayList<Block> blocks = new ArrayList<Block>();
	int chunkSize = 20;
	public Chunk()
	{
		for (int i = 0; i < chunkSize* chunkSize; i ++) {
			
		}
	}
}