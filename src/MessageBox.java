import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MessageBox extends JPanel {

	private String message;
	private Color color;
	private String alignment;
	private boolean isDelivered ;
	private Calendar cal ;
	public MessageBox(String message, Color color, String alignment) {
		super();
		this.message = message;
		this.color = color;
		this.alignment = alignment;
		isDelivered = false ;
		
	}

	public void setDelivery(){
		isDelivered = true ;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (alignment.equals("Left")) {
			Image image = new ImageIcon(getClass().getResource("user2.png"))
					.getImage();
			g2d.drawImage(image, this.getWidth() / 50, this.getHeight() / 20, null);

			g2d.setColor(color);
			g2d.fillRoundRect(this.getWidth() / 5, this.getHeight() / 10,
					this.getWidth() / 2, this.getHeight() / 2, 10, 10);

			g2d.setColor(Color.white);
			g2d.setFont(new Font("Segoe Print" , Font.PLAIN , 15));
			g2d.drawString(message, this.getWidth() / 5 + 5,
					this.getHeight() / 2);
			
			cal = cal.getInstance() ;
			String time = cal.getTime().toString() ;
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.drawString(time, this.getWidth() / 5 , this.getHeight()*8/10 );
			
			if( isDelivered == true ){
				Image tick = new ImageIcon(getClass().getResource("tick.png"))
				.getImage();
				g2d.drawImage(tick, this.getWidth()*72/100, this.getHeight() / 10, null);
			}

		} else {
			Image image = new ImageIcon(getClass().getResource("user2.png"))
					.getImage();
			g2d.drawImage(image, this.getWidth() *8/10, this.getHeight() / 20, null);

			g2d.setColor(color);
			g2d.fillRoundRect(this.getWidth() / 4, this.getHeight() / 10,
					this.getWidth() / 2, this.getHeight() / 2, 10, 10);

			g2d.setColor(Color.black);
			g2d.setFont(new Font("Segoe Print" , Font.PLAIN , 15));
			g2d.drawString(message, this.getWidth() / 4 + 5,
					this.getHeight() / 2);
		}

	}
}
