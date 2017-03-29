import java.awt.Graphics2D;
public abstract class Menu{
	private int x;
	private int y;
	private int width;
	private int height;
	public Menu(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void drawMe(Graphics2D g){
		
	}
}