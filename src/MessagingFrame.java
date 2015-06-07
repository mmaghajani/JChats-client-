import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MessagingFrame extends JFrame {

	private int heightScreen = (int)getToolkit().getScreenSize().getHeight() ;
	private int widthScreen = (int)getToolkit().getScreenSize().getWidth();
	private int width ;
	private int height ;
	
	private MessagingPanel mp ;
	
	public MessagingFrame(Socket client, BufferedReader br, User source,
			String dist , ArrayList<MessagingFrame> messFrame) {
		
		super();

		//this.setUndecorated(true);
				// this.setShape(new RoundRectangle2D.Double(0,0, widthScreen/4,
				// heightScreen/2, 40, 40));
				this.setSize(widthScreen / 3, heightScreen /2 );
				this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosing(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent arg0) {
						// TODO Auto-generated method stub
						messFrame.remove(this) ;
					}
					
					@Override
					public void windowActivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				this.setLocation(widthScreen /5, heightScreen /6 );
				this.setResizable(false);
				this.setLayout(null);
				width = widthScreen / 3;
				height = heightScreen /2 ;
				
		try {
			UIManager
					.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		ImageIcon icon1 = new ImageIcon(getClass().getResource("icon.png"));
		this.setIconImage(icon1.getImage());
		this.setTitle(dist);
		
		mp = new MessagingPanel( this , client , br , source , dist) ;
		mp.setSize(width, height);
		mp.setLocation(0, 0);
		mp.setBackground(Color.decode("#d1f9e2") ) ;
		
		getContentPane().add(mp) ;
		setVisible(true);
	}
	
	public MessagingFrame(String dist){
		this.setTitle(dist);
	}
	
	public void addMessage( String mess ){
		mp.addMessage( mess ) ;
	}
	
	@Override
	
	public boolean equals( Object s ){
		try{
		if( this.getTitle().equals( ((MessagingFrame)s).getTitle() ) ){
			return true ;
		}
		else{
			return false ;
		}
		}catch( ClassCastException e){
			return super.equals(s) ;
		}
	}
	
}
