import javax.swing.JFrame;
public class Runner
{
	public static void main(String args[])
	{
		JFrame frame = new JFrame("FinalGame");
    	Screen canvas = new Screen();
	    frame.add(canvas);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
		frame.setVisible(true);
    	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        canvas.initialize();
	}
} 