package javaFiles;
public class TouchData
{
	MainObject touched;
	double direction; // 0 = up, 1 = down, 2 = left, 3 = right
	public TouchData(double direction)
	{
		this.direction = direction;
	}
	public void add(MainObject input)
	{
		touched = input;
	}
}