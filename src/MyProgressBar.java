import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class MyProgressBar {

	private int height;
	private int[] widths;
	private int limit;
	private boolean visible;

	public MyProgressBar(int x, int height) {
		widths = new int[4];
		limit = x;
		widths[0] = limit * 2 / 5;
		widths[1] = limit * 47 / 100;
		widths[2] = limit * 54 / 100;
		widths[3] = limit * 3 / 5;
		visible = false;
		this.height = height;

	}

	public void paint(Graphics g) {
		if (visible == true) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.decode("#0cbb56"));
			g2d.fillRoundRect(widths[0], height, limit / 60, limit / 60 , 60 , 60);
			g2d.fillRoundRect(widths[1], height, limit / 60, limit / 60 , 60 , 60);
			g2d.fillRoundRect(widths[2], height, limit / 60, limit / 60 , 60 , 60);
			g2d.fillRoundRect(widths[3], height, limit / 60, limit / 60 , 60 , 60);
		}
	}

	public void repaint(Graphics g) {
		paint(g);
	}

	public void setWidthAt(int index, int value) {
		widths[index] = value;
	}

	public int getWidthAt(int index) {
		return widths[index];
	}
	
	public void setVisible(boolean x ){
		visible = x ;
	}
}
