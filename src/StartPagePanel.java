import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class StartPagePanel extends JPanel {
	private JButton signIn;
	private JButton signUp;
	private JTextField userName;
	private JPasswordField passWord;
	private JToolBar titleBar;
	private JButton exit;
	private JButton minimize;
	private JLabel title;
	private int heightScreen = (int) getToolkit().getScreenSize().getHeight();
	private int widthScreen = (int) getToolkit().getScreenSize().getWidth();
	private int width;
	private int height;
	private Image i;
	private Hoopoe h;
	private MyProgressBar mpb;
	int flag = 0;
	private Socket client;
	private BufferedReader br;

	public static int basse = 0;

	public StartPagePanel(StartPageFrame f , String hostName) {

		super();

		this.setSize(widthScreen / 3, heightScreen * 2 / 3);
		this.setLocation(widthScreen / 2 - widthScreen / 6, heightScreen / 2
				- heightScreen / 3);
		this.setLayout(null);
		width = widthScreen / 3;
		height = heightScreen * 2 / 3;

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

		titleBar = new MyToolBar();
		titleBar.setLayout(null);
		titleBar.setSize(this.getWidth(), this.getHeight() / 14);
		titleBar.setLocation(0, 0);
		titleBar.setBorder(BorderFactory.createEmptyBorder());
		titleBar.setFloatable(false);
		// titleBar.setBackground(Color.decode("#f72451"));

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

				f.dispose();
			}
		});
		titleBar.add(exit);

		// x.add( exit ) ;
		h = new Hoopoe(0, 0, width / 4, height / 8);

		int t = 5;
		new javax.swing.Timer(t, new ActionListener() {
			int xSize = 0;
			int ySize = 50;
			int flag = 0;

			public void actionPerformed(ActionEvent e) {
				if (xSize < width * 4 / 7 && flag == 0) {
					xSize += width / 100;
					ySize += width / 100;
					h.setSize(xSize, ySize);
					repaint();
				} else if (xSize > width / 2) {
					flag = 1;
					xSize -= width / 100;
					ySize -= width / 100;
					h.setSize(xSize, ySize);
					repaint();
				} else {

					((javax.swing.Timer) e.getSource()).stop(); // stop the
																// timer

				}
			}
		}).start();

		userName = new JTextField(150);
		userName.setLocation(width / 2 - width / 5, height * 5 / 7);
		userName.setSize(width * 2 / 5, height / 15);
		userName.setText("User Name");
		userName.setFont(new Font("Segoe Print", Font.PLAIN, 14));
		userName.setForeground(Color.decode("#a5a4a7"));
		userName.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if (userName.getText().equals("")) {
					userName.setForeground(Color.decode("#a5a4a7"));
					userName.setText("User Name");
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if (userName.getText().equals("User Name")) {
					userName.setForeground(Color.blue);
					userName.setText("");
				}
			}
		});

		passWord = new JPasswordField(150);
		passWord.setLocation(width / 2 - width / 5, height * 4 / 5);
		passWord.setSize(width * 2 / 5, height / 15);
		passWord.setText("Password");
		passWord.setFont(new Font("Segoe Print", Font.PLAIN, 14));
		passWord.setForeground(Color.decode("#a5a4a7"));
		char s = (char) (8226);
		passWord.setEchoChar((char) 0);
		passWord.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if (passWord.getText().equals("")) {
					passWord.setEchoChar((char) 0);
					passWord.setForeground(Color.decode("#a5a4a7"));
					passWord.setText("Password");
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if (passWord.getText().equals("Password")) {
					passWord.setEchoChar(s);
					passWord.setForeground(Color.BLUE);
					passWord.setText("");
				}
			}
		});

		mpb = new MyProgressBar(width, height * 8 / 9);

		signIn = new JButton("SignIn");
		signIn.setSize(width / 6, height / 15);
		signIn.setLocation(width * 9 / 11, height * 11 / 12);
		signIn.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		signIn.setBackground(Color.decode("#18964f"));
		signIn.setForeground(Color.decode("#eef937"));
		signIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String isAccept = " ";
				try {
					try {
						byte[] x = new byte[4];
						x[0] = (byte) 169;
						x[1] = (byte) 254;
						x[2] = 119;
						x[3] = 107;
						Socket client = new Socket(InetAddress.getByName(hostName),
								5000);
						BufferedReader br = new BufferedReader(
								new InputStreamReader(client.getInputStream()));

						StartPagePanel.this.client = client;
						StartPagePanel.this.br = br;

						isAccept = br.readLine();
					} catch (ConnectException e) {
						JOptionPane.showMessageDialog(null, "Connection error");
					}
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (isAccept.equals("busy")) {
					JOptionPane.showMessageDialog(null, "Server is too busy.Please try again.");
				} else {
					if (userName.getText().equals("User Name"))
						JOptionPane.showMessageDialog(null,
								"Please enter user name");
					else {
						javax.swing.Timer s = null;
						mpb.setVisible(true);
						int t = 10;
						if (flag == 0) {
							// flag = 1;

							s = new javax.swing.Timer(t, new ActionListener() {
								int i = 0;

								public void actionPerformed(ActionEvent e) {
									if (mpb.getWidthAt(0) > width * 45 / 100
											&& mpb.getWidthAt(0) < width * 55 / 100)
										mpb.setWidthAt(0, mpb.getWidthAt(0)
												+ width / 250);
									else
										mpb.setWidthAt(0, mpb.getWidthAt(0)
												+ width / 80);

									if (mpb.getWidthAt(1) > width * 45 / 100
											&& mpb.getWidthAt(1) < width * 55 / 100)
										mpb.setWidthAt(1, mpb.getWidthAt(1)
												+ width / 250);
									else
										mpb.setWidthAt(1, mpb.getWidthAt(1)
												+ width / 80);

									if (mpb.getWidthAt(2) > width * 45 / 100
											&& mpb.getWidthAt(2) < width * 55 / 100)
										mpb.setWidthAt(2, mpb.getWidthAt(2)
												+ width / 250);
									else
										mpb.setWidthAt(2, mpb.getWidthAt(2)
												+ width / 80);

									if (mpb.getWidthAt(3) > width * 45 / 100
											&& mpb.getWidthAt(3) < width * 55 / 100)
										mpb.setWidthAt(3, mpb.getWidthAt(3)
												+ width / 250);
									else
										mpb.setWidthAt(3, mpb.getWidthAt(3)
												+ width / 80);

									if (mpb.getWidthAt(0) > width) {
										mpb.setWidthAt(0, mpb.getWidthAt(0)
												% width);
									}
									if (mpb.getWidthAt(1) > width) {
										mpb.setWidthAt(1, mpb.getWidthAt(1)
												% width);
									}
									if (mpb.getWidthAt(2) > width) {
										mpb.setWidthAt(2, mpb.getWidthAt(2)
												% width);
									}
									if (mpb.getWidthAt(3) > width) {
										mpb.setWidthAt(3, mpb.getWidthAt(3)
												% width);
									}

									repaint();
									i++;

									if (basse == 1) {

										mpb.setVisible(false);
										repaint();
										basse = 0;
										((javax.swing.Timer) e.getSource())
												.stop(); //

									}

								}
							});

							s.start();
						}

						// TODO Auto-generated method stub

						if (!isExistUser()) {
							new Thread() {
								public void run() {
									try {
										this.sleep(1500);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									mpb.setVisible(false);
									repaint();
									JOptionPane.showMessageDialog(null,
											"This user does not exist!");
								};
							}.start();

						} else {
							if (!isValidPassword()) {
								new Thread() {
									public void run() {
										try {
											this.sleep(1500);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										mpb.setVisible(false);
										repaint();
										JOptionPane.showMessageDialog(null,
												"Inavalid password");
									};
								}.start();

							} else {
								new Thread() {
									public void run() {
										try {
											// JOptionPane.showMessageDialog(null,
											// "Entering...") ;
											this.sleep(1500);
											basse = 1;
											f.setVisible(false);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										ManagingFrame mf = new ManagingFrame(f,
												getUserFromServer(userName
														.getText()), client, br);
									};
								}.start();

							}
						}
					}
				}
				// s.stop() ;
			}
		});

		signUp = new JButton("SignUp");
		signUp.setSize(width / 6, height / 15);
		signUp.setLocation(width / 40, height * 11 / 12);
		signUp.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		signUp.setBackground(Color.decode("#18964f"));
		signUp.setForeground(Color.decode("#eef937"));
		signUp.addActionListener(new ActionListener() {

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

				EnrollmentPageFrame EPF = new EnrollmentPageFrame(hostName);

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
			}
		});

		this.add(signUp);
		this.add(signIn);
		this.add(passWord);
		this.add(userName);
		this.add(titleBar);

	}

	public void paintComponent(Graphics g) {
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

		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Image image = new ImageIcon(getClass().getResource("back.jpg"))
				.getImage();
		g2d.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);

		h.repaint(g2d);
		mpb.repaint(g2d);
		signUp.repaint();
		userName.repaint();
		titleBar.repaint();
		signIn.repaint();
	}

	private boolean isExistUser() {
		PrintWriter pw;
		try {
			pw = new PrintWriter(client.getOutputStream());
			pw.flush();
			pw.println("Is exist this user");
			pw.flush();
			pw.println(userName.getText());
			pw.flush();

			String message = br.readLine();

			if (message.equals("yes"))
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	private boolean isValidPassword() {
		try {
			PrintWriter pw = new PrintWriter(client.getOutputStream());
			pw.flush();
			pw.println("Is valid password");
			pw.flush();
			pw.println(userName.getText());
			pw.flush();
			pw.println(passWord.getText());
			pw.flush();

			String message = br.readLine();
			if (message.equals("yes"))
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	private User getUserFromServer(String userName) {
		PrintWriter pw;
		User user = new User();
		try {
			pw = new PrintWriter(client.getOutputStream());
			pw.flush();
			pw.println("Get user");
			pw.flush();
			pw.println(userName);
			pw.flush();

			user.setName(br.readLine());
			user.setLastName(br.readLine());
			user.setBirthDay(br.readLine());
			user.setDegree(br.readLine());
			user.setPass(br.readLine());
			user.setUserName(br.readLine());
			user.setStatus(br.readLine());
			user.setNumOfFreinds(Integer.parseInt(br.readLine()));
			for (int i = 0; i < user.getNumOfFreinds(); i++) {
				user.addFreind(br.readLine());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}
}
