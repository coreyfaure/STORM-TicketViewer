package coreyfaure;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TicketViewer {

	static String dataPath = "L:\\LH Ticket Analysis\\TicketViewerData.txt";
	static String truckPath = "L:\\TruckRes\\";
	static String lhPicsPath = "L:\\LH Pics\\";
	static String API_KEY = "PUT-KEY-HERE";

	private JFrame viewerWindow;
	static JTextField fieldSearch;
	static JLabel lblTicket;
	static JLabel lblImage1;
	static JLabel lblImage2;
	static JLabel lblImageCert;
	static JLabel lblImageTruck;
	static JLabel lblTruck;
	static JButton btnSearch;
	static JCheckBox chckbxMonitorClipboard;
	static ArrayList<Ticket> tickets;
	private JLabel lblTicketInformation;

	private static JLabel textControl;
	private static JLabel textStatus;
	private static JLabel textBefore;
	private static JLabel textTicket;
	private static JLabel textDate;
	private static JLabel textProject;
	private static JLabel textOriginTime;
	private static JLabel textQAMonitor;
	private static JLabel textTruck;
	private static JLabel textSub;
	private static JLabel textSub2;
	private static JLabel textOwner;
	private static JLabel textType;
	private static JLabel textSize;
	private static JLabel textLatitude;
	private static JLabel textLongitude;
	private static JLabel textVoided;
	private static JLabel lblImageSatellite;
	private static JLabel lblImageRoad;

	private static String imageLinkCert = "";
	private static String imageLinkBefore = "";
	private static String imageLinkAfter = "";
	private static String ticketCoords = "";
	
	final int c1 = 10;
	final int c2 = 280;
	final int c3 = 560;

	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		try {
			getConfig();
			tickets = TicketImporter.getTicketsFromFile(dataPath);
		} catch (FileNotFoundException e1) {
			System.out.println("Could not succefully import data.");
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicketViewer window = new TicketViewer();
					window.viewerWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Thread.sleep(2000);
		String lastClipboard = "0000";
		while (true) {
			Thread.sleep(200);
			String currentClipboard = "0000";
			currentClipboard = getClipboard();
			if (!currentClipboard.equals(lastClipboard)) {
				lastClipboard = getClipboard();
				System.out.println("Clipboard: " + lastClipboard);
				if (chckbxMonitorClipboard.isSelected()) {
					fieldSearch.setText(lastClipboard);
					btnSearch.doClick();
				}
			}
		}
	}

	private static String getClipboard() throws InterruptedException {
		String clipboard = "ERR";
		while (clipboard.contentEquals("ERR")) {
			try {
				return SystemClipboardAccess.getClipboardString().trim();
			} catch (Exception e) {
				clipboard = "ERR";
				Thread.sleep(100);
			}
		}
		return clipboard;
	}

	private static void getConfig() throws IOException {
		File cf = new File("config.cfg");
		System.out.println(cf.getAbsoluteFile());
		if (!cf.getAbsoluteFile().exists()) {
			dataPath = "L:\\LH Ticket Analysis\\TicketViewerData.txt\n";
			truckPath = "L:\\TruckRes\\\n";
			lhPicsPath = "L:\\LH Pics2\\\n";
			API_KEY = "PUT-KEY-HERE";
			createConfig();
		}
		Scanner s = null;
		try {
			s = new Scanner(new File("./config.cfg"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (s.hasNextLine()) {
			String line = s.nextLine();
			String command = line.split("=")[0];
			switch (command) {
			case "DATA_PATH":
				dataPath = line.split("=")[1];
				break;
			case "TRUCK_PATH":
				truckPath = line.split("=")[1];
				break;
			case "LHPICS_PATH":
				lhPicsPath = line.split("=")[1];
				break;
			case "API_KEY":
				API_KEY = line.split("=")[1];
			default:
				break;
			}
		}
	}

	private static void createConfig() throws IOException {
		FileWriter fw = new FileWriter("config.cfg");

		fw.write("DATA_PATH=" + dataPath);
		fw.write("TRUCK_PATH=" + truckPath);
		fw.write("LHPICS_PATH=" + lhPicsPath);
		fw.write("API_KEY=" + API_KEY);
		fw.close();
	}

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public TicketViewer() throws Exception {
		initialize();

	}

	static void updateView(Ticket t) {
		lblTicket.setText(t.ticket);
		lblTruck.setText("Truck " + t.truck);
		textControl.setText("Control: " + t.control);
		textBefore.setText("Before: " + t.before);
		textTicket.setText("Ticket #:" + t.ticket);
		textDate.setText("Date: " + t.date);
		textProject.setText("Project: " + t.project);
		textOriginTime.setText("Origin Time: " + t.originTime);
		textQAMonitor.setText("QA Monitor: " + t.qaMonitor);
		textTruck.setText("Truck: " + t.truck);
		textSub.setText("Sub: " + t.sub);
		textSub2.setText("Sub2: " + t.sub2);
		textOwner.setText("Owner: " + t.owner);
		textType.setText("Type: " + t.type);
		textSize.setText("Size: " + t.size);
		textLatitude.setText("Latitude: " + t.latitude);
		textLongitude.setText("Longitude: " + t.longitude);
		textVoided.setText("Voided: " + t.voided);
		imageLinkCert = truckPath + t.truck + " 001.jpg";
		imageLinkBefore = lhPicsPath + t.before + ".jpg";
		imageLinkAfter = lhPicsPath + t.ticket + ".jpg";
		ticketCoords = t.latitude+","+t.longitude;
		try {
			lblImage1.setIcon(
					TicketImporter.getImageIconFromFile(imageLinkBefore, lblImage1.getWidth(), lblImage1.getHeight()));
		} catch (IOException e) {
			System.out.println("Before cut image missing!");
		}
		try {
			lblImage2.setIcon(
					TicketImporter.getImageIconFromFile(imageLinkAfter, lblImage2.getWidth(), lblImage2.getHeight()));
		} catch (IOException e) {
			System.out.println("After cut image missing!");
		}
		try {
			lblImageCert.setIcon(
					TicketImporter.getImageIconFromFile(imageLinkCert, lblImageCert.getWidth(), lblImageCert.getHeight()));
		} catch (IOException e) {
			System.out.println("Certification missing!");
		}
		try {
			lblImageTruck.setIcon(TicketImporter.getImageIconFromFile(truckPath + t.truck + ".jpg",
					lblImageTruck.getWidth(), lblImageTruck.getHeight()));
		} catch (IOException e) {
			System.out.println("Truck missing!");
		}
		try {
			lblImageSatellite.setIcon(new ImageIcon(ImageIO.read(StaticMapURL.getURLSatellite(t, API_KEY))));
		} catch (MalformedURLException e) {
			System.out.println("Invalid URL.");
		} catch (IOException e) {
			System.out.println("Error getting sattelite image!");
		}
		try {
			lblImageRoad.setIcon(new ImageIcon(ImageIO.read(StaticMapURL.getURLRoadmap(t, API_KEY))));
		} catch (MalformedURLException e) {
			System.out.println("Invalid URL.");
		} catch (IOException e) {
			System.out.println("Error getting roadmap image!");
		}

	}

	static Ticket getTicket(String ticket) {
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i).ticket.equals(ticket)) {
				System.out.println("Found ticket!");
				return tickets.get(i);
			}
		}
		return null;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		viewerWindow = new JFrame();
		viewerWindow.setTitle("Ticket Information Viewer");
		viewerWindow.setBounds(100, 100, 840, 900);
		viewerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewerWindow.getContentPane().setLayout(null);

		fieldSearch = new JTextField();
		fieldSearch.setToolTipText("Ticket Number");
		fieldSearch.setBounds(10, 11, 271, 20);
		viewerWindow.getContentPane().add(fieldSearch);
		fieldSearch.setColumns(10);

		// search for ticket with search button
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = fieldSearch.getText();
				System.out.println("Searching for ticket: " + text);
				Ticket t = getTicket(text);
				if (t != null) {
					textStatus.setText("");
					updateView(t);
				} else {
					updateView(Ticket.getNullTicket());
					textStatus.setText("<html><font color='red'>TICKET NOT FOUND</font></html>");
					//JOptionPane.showMessageDialog(viewerWindow, "Ticket not found in data file.");
				}
				fieldSearch.setText("");
			}
		});
		btnSearch.setBounds(c2, 10, 89, 23);
		viewerWindow.getContentPane().add(btnSearch);

		JLabel lblTicketLabel = new JLabel("Ticket #:");
		lblTicketLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTicketLabel.setBounds(c1, 42, 64, 20);
		viewerWindow.getContentPane().add(lblTicketLabel);

		lblTicket = new JLabel("000000-000000000000");
		lblTicket.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTicket.setBounds(84, 42, 296, 20);
		viewerWindow.getContentPane().add(lblTicket);

		JLabel lblBeforeImage = new JLabel("Before Image");
		lblBeforeImage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBeforeImage.setBounds(c1, 73, 162, 20);
		viewerWindow.getContentPane().add(lblBeforeImage);

		JLabel lblAfterImage = new JLabel("After Image");
		lblAfterImage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAfterImage.setBounds(c2, 73, 130, 20);
		viewerWindow.getContentPane().add(lblAfterImage);

		lblImage1 = new JLabel("image1");
		lblImage1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File afterBefore = new File(imageLinkBefore);
				try {
					Desktop.getDesktop().open(afterBefore);
				} catch (IOException e1) {
					System.out.println("Could not open before image.");
				}
			}
		});
		lblImage1.setBounds(c1, 98, 250, 250);
		viewerWindow.getContentPane().add(lblImage1);

		lblImage2 = new JLabel("image2");
		lblImage2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File afterLink = new File(imageLinkAfter);
				try {
					Desktop.getDesktop().open(afterLink);
				} catch (IOException e1) {
					System.out.println("Could not open after image.");
				}
			}
		});
		lblImage2.setBounds(c2, 98, 250, 250);
		viewerWindow.getContentPane().add(lblImage2);

		lblImageCert = new JLabel("cert image");
		lblImageCert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File certLink = new File(imageLinkCert);
				try {
					Desktop.getDesktop().open(certLink);
				} catch (IOException e1) {
					System.out.println("Could not open cert image.");
				}
			}
		});
		lblImageCert.setBounds(c1, 390, 250, 219);
		viewerWindow.getContentPane().add(lblImageCert);

		lblImageTruck = new JLabel("truck image");
		lblImageTruck.setBounds(c2, 390, 250, 219);
		viewerWindow.getContentPane().add(lblImageTruck);
		
		lblImageSatellite = new JLabel("satellite image");
		lblImageSatellite.setBounds(10, 625, 400, 200);
		lblImageSatellite.setBorder(BorderFactory.createMatteBorder(
                1, 1, 1, 1, Color.gray));
		lblImageSatellite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					try {
						Desktop.getDesktop().browse(new URI("http://www.google.com/maps/search/"+ticketCoords));
					} catch (URISyntaxException e1) {
						System.out.println("Invalid Maps URI.");
					}
				} catch (IOException e1) {
					System.out.println("Could not open Maps webpage.");
				}
			}
		});
		viewerWindow.getContentPane().add(lblImageSatellite);
		
		lblImageRoad = new JLabel("road image");
		lblImageRoad.setBounds(415, 625, 400, 200);
		lblImageRoad.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		viewerWindow.getContentPane().add(lblImageRoad);

		JLabel lblCert = new JLabel("Certification");
		lblCert.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCert.setBounds(c1, 365, 117, 20);
		viewerWindow.getContentPane().add(lblCert);

		lblTruck = new JLabel("Truck");
		lblTruck.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTruck.setBounds(c2, 365, 117, 20);
		viewerWindow.getContentPane().add(lblTruck);

		chckbxMonitorClipboard = new JCheckBox("Monitor Clipboard");
		chckbxMonitorClipboard.setBounds(386, 10, 164, 23);
		viewerWindow.getContentPane().add(chckbxMonitorClipboard);
		
		textStatus = new JLabel("");
		textStatus.setFont(new Font("Tahoma", Font.BOLD, 25));
		textStatus.setBounds(c3,68,322,35);
		viewerWindow.getContentPane().add(textStatus);

		lblTicketInformation = new JLabel("Ticket Information:");
		lblTicketInformation.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTicketInformation.setBounds(c3, 98, 200, 32);
		viewerWindow.getContentPane().add(lblTicketInformation);

		textControl = new JLabel("Control: ");
		textControl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textControl.setBounds(c3, 135, 322, 23);
		viewerWindow.getContentPane().add(textControl);
		

		textBefore = new JLabel("Before:");
		textBefore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textBefore.setBounds(c3, 160, 322, 23);
		viewerWindow.getContentPane().add(textBefore);

		textTicket = new JLabel("Ticket #:");
		textTicket.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textTicket.setBounds(c3, 185, 322, 23);
		viewerWindow.getContentPane().add(textTicket);

		textDate = new JLabel("Date: ");
		textDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textDate.setBounds(c3, 210, 322, 23);
		viewerWindow.getContentPane().add(textDate);

		textProject = new JLabel("Project: ");
		textProject.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textProject.setBounds(c3, 235, 322, 23);
		viewerWindow.getContentPane().add(textProject);

		textOriginTime = new JLabel("Origin Time: ");
		textOriginTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textOriginTime.setBounds(c3, 260, 322, 23);
		viewerWindow.getContentPane().add(textOriginTime);

		textQAMonitor = new JLabel("QA Monitor: ");
		textQAMonitor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textQAMonitor.setBounds(c3, 285, 322, 23);
		viewerWindow.getContentPane().add(textQAMonitor);

		textTruck = new JLabel("Truck:");
		textTruck.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textTruck.setBounds(c3, 310, 322, 23);
		viewerWindow.getContentPane().add(textTruck);

		textSub = new JLabel("Sub:");
		textSub.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textSub.setBounds(c3, 335, 322, 23);
		viewerWindow.getContentPane().add(textSub);

		textSub2 = new JLabel("Sub2:");
		textSub2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textSub2.setBounds(c3, 360, 322, 23);
		viewerWindow.getContentPane().add(textSub2);

		textOwner = new JLabel("Owner:");
		textOwner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textOwner.setBounds(c3, 385, 322, 23);
		viewerWindow.getContentPane().add(textOwner);

		textType = new JLabel("Type:");
		textType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textType.setBounds(c3, 410, 322, 23);
		viewerWindow.getContentPane().add(textType);

		textSize = new JLabel("Size:");
		textSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textSize.setBounds(c3, 435, 322, 23);
		viewerWindow.getContentPane().add(textSize);

		textLatitude = new JLabel("Latitude:");
		textLatitude.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textLatitude.setBounds(c3, 460, 322, 23);
		viewerWindow.getContentPane().add(textLatitude);

		textLongitude = new JLabel("Longitude:");
		textLongitude.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textLongitude.setBounds(c3, 485, 322, 23);
		viewerWindow.getContentPane().add(textLongitude);

		textVoided = new JLabel("Voided:");
		textVoided.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textVoided.setBounds(c3, 510, 322, 23);
		viewerWindow.getContentPane().add(textVoided);
		
		
	}
}
