import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class EnrollmentPageFrame extends JFrame {

	private int heightScreen = (int) getToolkit().getScreenSize().getHeight(); 
	private int widthScreen = (int)getToolkit().getScreenSize().getWidth();
	private EnrollmentPagePanel backGround ;
	
	public EnrollmentPageFrame(){
		
		super( "Enrollment Form" ) ;
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
		
	//	this.setUndecorated(true);
		this.setSize(widthScreen/3, heightScreen*2/3);
		this.setLocation(widthScreen/2 , 40);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		ImageIcon icon = new ImageIcon(getClass().getResource("icon.png"));
		this.setIconImage(icon.getImage());
		
		backGround = new EnrollmentPagePanel(this) ;
		
		getContentPane().add( backGround , BorderLayout.CENTER) ;
		
		this.setVisible(true);
	}
	
	@Override
	public void paint( Graphics g ){
		super.paint(g);
		
		backGround.repaint();
		
		//backGroung.getComponent(0).repaint();
		
	}
}
