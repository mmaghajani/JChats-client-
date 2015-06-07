import java.awt.Color;
import java.io.BufferedReader;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class ManagingFrame extends JFrame {

	private ManagingPanel mp ;
	private int heightScreen = (int)getToolkit().getScreenSize().getHeight() ;
	private int widthScreen = (int)getToolkit().getScreenSize().getWidth();
	private int width ;
	private int height ;
	private StartPageFrame spf ;
	
	public ManagingFrame(StartPageFrame spf , User user , Socket client , BufferedReader br ){
		super() ;
		
		this.spf = spf ;
		this.setUndecorated(true);
		//this.setShape(new RoundRectangle2D.Double(0,0, widthScreen/4, heightScreen/2, 40, 40));
		this.setSize(widthScreen/5, heightScreen*3/4);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(widthScreen*3/4,heightScreen/8);
		this.setResizable(false);
		this.setLayout(null);
		width = widthScreen / 5 ;
		height = heightScreen*3/4 ;
		
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
		
		Listener listener = new Listener(br) ;
		mp = new ManagingPanel(this , user , client , br ) ;
		mp.setSize(width, height);
		mp.setLocation(0,0);
		mp.setBackground(Color.decode("#d1f9e2"));
		
		getContentPane().add( mp ) ;
		
		setVisible(true);
		
		listener.run();
		
	}
	
	public void setParentVisible(){
		spf.setVisible( true ) ;
	}
}
