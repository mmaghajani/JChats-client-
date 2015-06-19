import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Hoopoe extends JPanel{

	private int xSize ;
	private int ySize ;
	private int xPos ;
	private int yPos ;
	private Graphics g ;
	public Hoopoe( int width , int height , int xPos , int yPos  ){
		xSize = width ;
		ySize = height ;
		this.xPos = xPos ;
		this.yPos = yPos ;
		this.g = g ;
		repaint() ;
		
	}
	
	public int getWidth(){
		return xSize ;
	}
	
	public int getHeight(){
		return ySize ;
	}
	
	public void setSize( int width , int height ){
		xSize = width ;
		ySize = height ;
	}
	
	public void setLocation( int x , int y ){
		xPos = x ;
		yPos = y ;
	}
	
	public void paintComponent( Graphics g ){
		
		
		
		//Graphics2D g2d = (Graphics2D)g ;
	//	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Graphics2D g2d = (Graphics2D)g ;
		
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		
		BufferedImage image1 = null ;
		try {
			image1 = ImageIO
					.read(new File("src/hoopoe.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		g2d.drawImage(image1, xPos ,yPos ,xSize , ySize, null ) ;
	}
	
	public void repaint(Graphics g ){
		
		paintComponent(g) ;
	}
}
