package javaFiles;
import java.awt.Graphics2D;
public class EscapeMenu extends Menu{
	String[] menuButtons = {"Holll up","Quit"};
	public EscapeMenu(){
		super();
		super.setup((int)(Screen.screenWidth * 0.35), (int)(Screen.screenHeight * 0.1), (int)(Screen.screenWidth * 0.3), (int)(Screen.screenHeight * 0.8), menuButtons);
	}

	@Override
	public void drawMe(Graphics2D g){
		if(visible){
			g.setColor(menuColor);
			g.fillRect(x,y,width,height);
			for (int i = 0; i < buttons.size() ; i++ ) {
				buttons.get(i).drawMe(g);
				buttons.get(i).collision();
			}
		}
	}

	public void setVisible(boolean visible){
		super.visible = visible;
	}
}