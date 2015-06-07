import java.io.BufferedReader;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class StartPageFrame extends JFrame {
	private StartPagePanel sp ;
	private int heightScreen = (int)getToolkit().getScreenSize().getHeight() ;
	private int widthScreen = (int)getToolkit().getScreenSize().getWidth();
	private int width ;
	private int height ;

	
	public StartPageFrame(){
		super() ;
		
		
		this.setUndecorated(true);
		//this.setShape(new RoundRectangle2D.Double(0,0, widthScreen/4, heightScreen/2, 40, 40));
		this.setSize(widthScreen/3, heightScreen*2/3);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(widthScreen/2 - widthScreen/6, heightScreen/2 - heightScreen/3);
		this.setResizable(false);
		this.setLayout(null);
		width = widthScreen / 3 ;
		height = heightScreen*2/3 ;
		
		
		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ImageIcon icon1 = new ImageIcon(getClass().getResource("icon.png"));
		this.setIconImage(icon1.getImage());
		
		sp = new StartPagePanel(this) ;
		sp.setSize(width, height);
		sp.setLocation(0, 0);
		
		getContentPane().add(sp) ;
		setVisible(true);
	}
	
	
	
	
	
}
