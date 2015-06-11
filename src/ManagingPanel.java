import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ScrollPaneUI;

public class ManagingPanel extends JPanel {

	private int heightScreen = (int) getToolkit().getScreenSize().getHeight();
	private int widthScreen = (int) getToolkit().getScreenSize().getWidth();
	private int width;
	private int height;

	private MyToolBar titleBar;
	private JLabel title;
	private JButton exit;

	private JPanel infoPanel;
	private JButton picture;
	private JLabel user;
	private JTextField status;
	private JButton addCon;
	private JButton addContact;

	private JPanel conPan;
	private JScrollPane scroll;

	private Inbox inbox;
	private Socket client ;
	private BufferedReader br ;
	private User me ;
	
	private ArrayList<MessagingFrame> messFrames ;
	
	
	public ManagingPanel(ManagingFrame mf, User user, Socket client,
			BufferedReader br ) {

		super();

		this.setSize(mf.getWidth(), mf.getHeight());
		this.setLocation(0 , 0);
		this.setLayout(null);
		width = mf.getWidth();
		height = mf.getHeight();

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

		inbox = new Inbox();

		this.client = client ;
		this.br = br ;
		me = user ;
		messFrames = new ArrayList<MessagingFrame>() ;
		
		titleBar = new MyToolBar();
		titleBar.setLayout(null);
		titleBar.setSize(this.getWidth(), this.getHeight() / 17);
		titleBar.setLocation(0, 0);
		titleBar.setBorder(BorderFactory.createEmptyBorder());
		titleBar.setFloatable(false);

		title = new JLabel("JChats");
		title.setSize(titleBar.getWidth() / 2, titleBar.getHeight() * 5 / 6);
		title.setLocation(titleBar.getWidth() * 3 / 7, titleBar.getHeight() / 6);
		title.setFont(new Font("FantasticFont", Font.PLAIN, 40));
		title.setForeground(Color.white);
		titleBar.add(title);

		ImageIcon icon = new ImageIcon(getClass().getResource("exit.png"));
		exit = new JButton(icon);
		exit.setSize(titleBar.getWidth() / 10, titleBar.getHeight());
		exit.setFocusPainted(false);
		exit.setLocation(0, 0);
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				mf.setParentVisible();
				PrintWriter pw;
				try {
					pw = new PrintWriter(client.getOutputStream() );
					pw.flush();
					pw.println( "terminate" ) ;
					pw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				mf.dispose();
			}
		});
		titleBar.add(exit);

		infoPanel = new JPanel();
		infoPanel.setSize(this.getWidth(), height / 8);
		infoPanel.setLocation(0, this.getHeight() / 17);
		infoPanel.setBackground(Color.decode("#35b38b"));
		infoPanel.setLayout(null);

		JToolBar g = new JToolBar();
		g.setSize(infoPanel.getWidth() / 4, infoPanel.getHeight());
		g.setLocation(0, 0);
		g.setLayout(null);
		g.setBorder(BorderFactory.createEmptyBorder());
		g.setFloatable(false);

		Icon image = new ImageIcon(getClass().getResource("user2.png"));
		picture = new JButton(image);
		picture.setSize(infoPanel.getWidth() / 4, infoPanel.getHeight());
		picture.setLocation(0, 0);
		picture.setFocusPainted(false);
		picture.setBorderPainted(false);
		g.add(picture);
		infoPanel.add(g);

		this.user = new JLabel(user.getName() + " " + user.getLastName());
		this.user.setSize(infoPanel.getWidth() *4/7 ,
				infoPanel.getHeight() / 3);
		this.user.setLocation(infoPanel.getWidth() / 4 + 10,
				infoPanel.getHeight() / 9);
		this.user.setForeground(Color.white);
		this.user.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		infoPanel.add(this.user);

		status = new JTextField();
		status.setSize(infoPanel.getWidth() * 2 / 3, infoPanel.getHeight() / 3);
		status.setLocation(infoPanel.getWidth() / 4 - 2 + 10,
				infoPanel.getHeight() / 2);
		status.setText(user.getStatus());
		status.setFont(new Font("Segoe Print", Font.PLAIN, 12));
		status.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				PrintWriter pw;
				try {
					pw = new PrintWriter(client.getOutputStream());
					pw.flush();
					pw.println("Change status");
					pw.flush();
					pw.println(user.getUserName());
					pw.flush();
					pw.println(status.getText());
					pw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		infoPanel.add(status);

		JToolBar addCon = new JToolBar();
		addCon.setSize(infoPanel.getWidth() / 6, infoPanel.getHeight() / 2);
		addCon.setLocation(infoPanel.getWidth() * 5 / 6,
				infoPanel.getHeight() / 30);
		addCon.setLayout(null);
		addCon.setBorder(BorderFactory.createEmptyBorder());
		addCon.setFloatable(false);

		Icon image1 = new ImageIcon(getClass().getResource("add.png"));
		addContact = new JButton(image1);
		addContact.setSize(addCon.getWidth(), addCon.getHeight());
		addContact.setLocation(0, 0);
		addContact.setFocusPainted(false);
		addContact.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					UIManager
							.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
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

				String contact = JOptionPane.showInputDialog(null,
						"Please enter a user name");
				
				if (contact != null) {
					PrintWriter pw;
					try {
						pw = new PrintWriter(client.getOutputStream());
						pw.flush();
						pw.println("Add contact");
						pw.flush();
						pw.println(contact);
						pw.flush();
						
						String message = decode("existing contact");

						if (message.equals("no"))
							JOptionPane.showMessageDialog(null,
									"This user does not exist");
						else {
							JOptionPane
									.showMessageDialog(null,
											"This contact is added. Wait for accepting");
							// user.setNumOfFreinds(user.get);
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		addCon.add(addContact);
		infoPanel.add(addCon);

		conPan = new JPanel();
		GridLayout layoutManger = new GridLayout(10 , 0);
		conPan.setLayout(layoutManger);
		conPan.setBackground(Color.white);

		addContact(user);
		
		scroll = new JScrollPane(conPan,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setSize(this.getWidth()*19/20, (this.getHeight() * 111 / 136)*25/26);
		scroll.setLocation(this.getWidth()/40, this.getHeight() * 25 / 136 + this.getHeight()/60 );
		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(16, 50));
		this.add(scroll);
		
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
		
		
		this.add(infoPanel);
		this.add(titleBar);

		// Checking message that received from server
		int t = 1000;
		new javax.swing.Timer(t, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				ServerMessage message = checkInbox("from server");

				
				
				if (message.getMessage().equals("Request for add")) {
					int x = JOptionPane.showConfirmDialog(
							null,
							message.getValue()
									+ " want to add you.Do you accept this request?");
					if (x == 0) {
						user.addFreind(message.getValue());
						user.setNumOfFreinds(user.getFreinds().size());
						layoutManger.setRows(layoutManger.getRows() + 1);

						addContact(message);
						try {
							PrintWriter pw = new PrintWriter(client
									.getOutputStream());
							pw.flush();
							pw.println("I accept adding");
							pw.flush();
							pw.println(message.getValue());
							pw.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else{
						
					}
				}

				if (message.getMessage().equals("You are accepted")) {
					JOptionPane.showMessageDialog(null, message.getValue()
							+ " accepted you");

					user.addFreind(message.getValue());
					user.setNumOfFreinds(user.getFreinds().size());
					layoutManger.setRows(layoutManger.getRows() + 1);

					addContact(message);
					try {
						PrintWriter pw = new PrintWriter(client
								.getOutputStream());
						pw.flush();
						pw.println("Add successfuly");
						pw.flush();
						pw.println(message.getValue());
						pw.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				if(message.getMessage().equals("Receive message") ){
					String from = message.getSender() ;
					String mess = message.getValue() ;
					try {
						PrintWriter pw = new PrintWriter(client.getOutputStream() ) ;
						pw.flush();
						pw.println("Delivery") ;
						pw.flush();
						pw.println(from) ;
						pw.flush();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					MessagingFrame temp = new MessagingFrame(from) ;
					if( messFrames.contains(temp) ){
						for( MessagingFrame mef : messFrames ){
							if( mef.equals(temp) ){
								mef.addMessage( mess ) ;
							}
						}
					}
					else{
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
						
						MessagingFrame mf = new MessagingFrame(client, br, user, from , messFrames) ;
						messFrames.add( mf ) ;
						mf.addMessage(mess);
						
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
					}
				}
			}
		}).start();
	}

	private String decode(String key) {

		int flag = 0;
		while (flag == 0) {
			for (int i = 0; i < inbox.getMessages().size(); i++) {
				if (key.equals(inbox.getMessages().get(i).getKey())) {
					flag = 1;
					String message = inbox.getMessages().get(i).getMessage();
					inbox.delMessage(i);
					return message;
				}
			}
		}

		return "no";
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
	public void paintComponent( Graphics g ){
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
		
		this.setBackground(Color.decode( "#d1f9e2"));
		infoPanel.repaint();
		titleBar.repaint();
		scroll.repaint();
	}
	
	private void addContact( User user ){
		try {
			UIManager
					.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
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
		
		
		for (int i = 0; i < user.getNumOfFreinds(); i++) {
			ImageIcon icon1 = new ImageIcon(getClass().getResource("user2.png"));
			JLabel label = new JLabel(user.getFreinds().get(i) , icon1, JLabel.LEFT ) ;
			label.setFont(new Font( "Comic Sans MS" , Font.PLAIN , 18));
			JButton con = new JButton();
			con.add(label) ;
			con.setFocusPainted(false);
			final String  dist1  = user.getFreinds().get(i) ;
			con.addActionListener(new ActionListener() {
				String dist = dist1 ;
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
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
					
					MessagingFrame mf = new MessagingFrame(client, br, user, dist , messFrames ) ;
					messFrames.add( mf ) ;
					
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
				}
			});
			
			JToolBar buttonBar = new JToolBar() ;
			buttonBar.setLayout(new BorderLayout() );
			buttonBar.setBorder(BorderFactory.createEtchedBorder());
			buttonBar.add( con , BorderLayout.CENTER ) ;
			buttonBar.setFloatable(false);
			//buttonBar.setBackground(Color.black);
			conPan.add(buttonBar);
		}
	}
	
	private void addContact( ServerMessage message ){
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
		
		ImageIcon icon1 = new ImageIcon(getClass().getResource("user2.png"));
		JLabel label = new JLabel(message.getValue() , icon1, JLabel.LEFT ) ;
		label.setFont(new Font( "Comic Sans MS" , Font.PLAIN , 18));
		JButton con = new JButton();
		con.add(label) ;
		con.setFocusPainted(false);
		con.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
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
				
				MessagingFrame mf = new MessagingFrame(client, br, me, message.getValue() , messFrames ) ;
				messFrames.add( mf ) ;
				
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
			}
		});
		JToolBar buttonBar = new JToolBar() ;
		buttonBar.setLayout(new BorderLayout() );
		buttonBar.setBorder(BorderFactory.createEtchedBorder());
		buttonBar.add( con , BorderLayout.CENTER ) ;
		buttonBar.setFloatable(false);
		
		conPan.add(buttonBar);
		conPan.revalidate();
		conPan.repaint();
		
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
	}
}
