import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MessagingPanel extends JPanel {

	private int heightScreen = (int) getToolkit().getScreenSize().getHeight();
	private int widthScreen = (int) getToolkit().getScreenSize().getWidth();
	private int width;
	private int height;

	private Socket client;
	private BufferedReader br;
	private User source;
	private String dist;

	private JPanel messageBoard;
	private JPanel dashBoard;
	private JTextField textBox;
	private JButton send;
	private JScrollPane scroll;
	private GridLayout layoutManager;

	private String buffer;

	private Inbox inbox;
	private ArrayList<MessageBox> myMessages;

	public MessagingPanel(MessagingFrame mf, Socket client, BufferedReader br,
			User source, String dist) {
		super();

		this.setSize(mf.getWidth(), mf.getHeight());
		this.setLocation(0, 0);
		this.setLayout(null);
		width = this.getWidth();
		height = this.getHeight();

		this.client = client;
		this.br = br;
		this.source = source;
		this.dist = dist;
		buffer = " ";
		inbox = new Inbox();
		myMessages = new ArrayList<MessageBox>();

		layoutManager = new GridLayout(2, 0);
		messageBoard = new JPanel();
		messageBoard.setSize(this.getWidth() * 19 / 20, height * 5 / 7);
		messageBoard.setLocation(width / 50, this.getHeight() / 30);
		messageBoard.setBackground(Color.white);
		messageBoard.setLayout(layoutManager);
		messageBoard.setBorder(BorderFactory.createLineBorder(
				Color.decode("#27b13e"), 1));

		dashBoard = new JPanel();
		dashBoard.setSize(this.getWidth() * 19 / 20, height / 9);
		dashBoard.setLocation(width / 50, height * 78 / 100);
		dashBoard.setLayout(null);
		dashBoard.setBackground(Color.white);
		dashBoard.setBorder(BorderFactory.createLineBorder(
				Color.decode("#27b13e"), 1));

		textBox = new JTextField();
		textBox.setSize(dashBoard.getWidth() * 9 / 10,
				dashBoard.getHeight() * 5 / 6);
		textBox.setLocation(dashBoard.getWidth() / 120,
				dashBoard.getHeight() / 12);
		textBox.setForeground(Color.decode("#a5a4a7"));
		textBox.setText("Send Message");
		textBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		textBox.setFocusable(false);

		textBox.setBorder(BorderFactory.createEmptyBorder());
		textBox.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				buffer = textBox.getText();
				textBox.setForeground(Color.decode("#a5a4a7"));
				textBox.setText("Send Message");
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if (textBox.getText().equals("Send Message")) {
					textBox.setForeground(Color.blue);
					textBox.setText("");
				}
			}
		});
		textBox.requestFocus(false);
		dashBoard.add(textBox);

		JToolBar g = new JToolBar();
		g.setSize(dashBoard.getWidth() / 12, dashBoard.getHeight() * 5 / 6);
		g.setLocation(dashBoard.getWidth() * 10 / 11,
				dashBoard.getHeight() / 12);
		g.setLayout(null);
		g.setBorder(BorderFactory.createEmptyBorder());
		g.setBackground(Color.white);
		g.setIgnoreRepaint(true);
		g.setFloatable(false);

		Icon image = new ImageIcon(getClass().getResource("send.png"));
		send = new JButton(image);
		send.setSize(g.getWidth(), g.getHeight());
		send.setLocation(0, 0);
		send.setFocusPainted(false);
		send.setBorderPainted(false);
		send.setBackground(Color.white);
		g.add(send);
		dashBoard.add(g);
		repaint();
		send.requestFocusInWindow();
		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!buffer.equals(" ")) {
					try {
						PrintWriter pw = new PrintWriter(client
								.getOutputStream());
						pw.flush();
						String message = buffer;

						pw.println("Send message");
						pw.flush();
						pw.println(dist);
						pw.flush();
						pw.println(message);
						pw.flush();
						addMessage(message, layoutManager);
						buffer = " ";
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		scroll = new JScrollPane(messageBoard,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setSize(this.getWidth() * 19 / 20, height * 5 / 7);
		scroll.setLocation(width / 50, this.getHeight() / 30);
		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(16, 50));

		scroll.setFocusable(true);
		scroll.requestFocusInWindow();
		this.add(dashBoard);
		this.add(scroll);
		textBox.setFocusable(true);

		int t = 1000;
		new javax.swing.Timer(t, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ServerMessage message = checkInbox("from server for you");

				if (message.getMessage().equals("Delivered")) {
					try {						
						myMessages.get(0).setDelivery();
						myMessages.get(0).revalidate();
						myMessages.get(0).repaint();
						messageBoard.revalidate();
						messageBoard.repaint();
						myMessages.remove(0);
					} catch (ArrayIndexOutOfBoundsException e1) {

					}
				}
				
				
			}
		}).start();
	}

	private ServerMessage checkInbox(String key) {
		for (int i = 0; i < inbox.getMessages().size(); i++) {
			if (key.equals(inbox.getMessages().get(i).getKey())) {
				ServerMessage message = inbox.getMessages().get(i);
				inbox.delMessage(i);
				return message;
			}
		}

		ServerMessage s = new ServerMessage();
		s.setMessage("null");

		return s;
	}

	@Override
	public void paintComponent(Graphics g) {
		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
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

		super.paintComponent(g);

		// this.setBackground(Color.decode( "#d1f9e2"));
		messageBoard.repaint();
		send.repaint();
		dashBoard.repaint();
	}

	private void addMessage(String mess, GridLayout lay) {
		lay.setRows(lay.getRows() + 1);
		MessageBox messBox = new MessageBox(mess, Color.blue, "Left");
		messBox.setBackground(Color.white);
		messBox.setMinimumSize(new Dimension(messageBoard.getWidth(),
				height / 5));
		messBox.setPreferredSize(new Dimension(messageBoard.getWidth(),
				height / 5));
		myMessages.add(messBox);
		messageBoard.add(messBox);
		messageBoard.revalidate();
		messageBoard.repaint();

	}

	public void addMessage(String mess) {
		layoutManager.setRows(layoutManager.getRows() + 1);
		MessageBox messBox = new MessageBox(mess, Color.YELLOW, "Right");
		messBox.setBackground(Color.white);
		messBox.setMinimumSize(new Dimension(messageBoard.getWidth(),
				height / 5));
		messBox.setPreferredSize(new Dimension(messageBoard.getWidth(),
				height / 5));
		messageBoard.add(messBox);
		messageBoard.revalidate();
		messageBoard.repaint();
	}
}
