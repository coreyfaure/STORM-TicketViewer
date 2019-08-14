package coreyfaure;

import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

	private JFrame frmBensSuperFantastical;
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

	private static String imageLinkCert = "";
	private static String imageLinkBefore = "";
	private static String imageLinkAfter = "";

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
					window.frmBensSuperFantastical.setVisible(true);
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
			default:
				break;
			}
		}
	}

	private static void createConfig() throws IOException {
		// TODO Auto-generated method stub
		FileWriter fw = new FileWriter("config.cfg");

		fw.write("DATA_PATH=" + dataPath);
		fw.write("TRUCK_PATH=" + truckPath);
		fw.write("LHPICS_PATH=" + lhPicsPath);

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

	static void updateView(Ticket t) throws IOException {
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
		lblImage1.setIcon(
				TicketImporter.getImageIconFromFile(imageLinkBefore, lblImage1.getWidth(), lblImage1.getHeight()));
		lblImage2.setIcon(
				TicketImporter.getImageIconFromFile(imageLinkAfter, lblImage2.getWidth(), lblImage2.getHeight()));
		lblImageCert.setIcon(
				TicketImporter.getImageIconFromFile(imageLinkCert, lblImageCert.getWidth(), lblImageCert.getHeight()));
		lblImageTruck.setIcon(TicketImporter.getImageIconFromFile(truckPath + t.truck + ".jpg",
				lblImageTruck.getWidth(), lblImageTruck.getHeight()));

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
		frmBensSuperFantastical = new JFrame();
		frmBensSuperFantastical.setTitle("Ticket Information Viewer");
		frmBensSuperFantastical.setBounds(100, 100, 1000, 657);
		frmBensSuperFantastical.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBensSuperFantastical.getContentPane().setLayout(null);

		fieldSearch = new JTextField();
		fieldSearch.setToolTipText("Ticket Number");
		fieldSearch.setBounds(10, 11, 271, 20);
		frmBensSuperFantastical.getContentPane().add(fieldSearch);
		fieldSearch.setColumns(10);

		// search for ticket with search button
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = fieldSearch.getText();
				System.out.println("Searching for ticket: " + text);
				Ticket t = getTicket(text);
				if (t != null) {
					try {
						updateView(t);
					} catch (IOException e2) {

					}
				} else {
					JOptionPane.showMessageDialog(frmBensSuperFantastical, "Ticket not found in data file.");
				}
				fieldSearch.setText("");
			}
		});
		btnSearch.setBounds(291, 10, 89, 23);
		frmBensSuperFantastical.getContentPane().add(btnSearch);

		JLabel lblTicketLabel = new JLabel("Ticket #:");
		lblTicketLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTicketLabel.setBounds(10, 42, 64, 20);
		frmBensSuperFantastical.getContentPane().add(lblTicketLabel);

		lblTicket = new JLabel("000000-000000000000");
		lblTicket.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTicket.setBounds(84, 42, 296, 20);
		frmBensSuperFantastical.getContentPane().add(lblTicket);

		JLabel lblBeforeImage = new JLabel("Before Image");
		lblBeforeImage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBeforeImage.setBounds(10, 73, 162, 20);
		frmBensSuperFantastical.getContentPane().add(lblBeforeImage);

		JLabel lblAfterImage = new JLabel("After Image");
		lblAfterImage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAfterImage.setBounds(291, 73, 130, 20);
		frmBensSuperFantastical.getContentPane().add(lblAfterImage);

		lblImage1 = new JLabel("image1");
		lblImage1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File afterBefore = new File(imageLinkBefore);
				try {
					Desktop.getDesktop().open(afterBefore);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		lblImage1.setBounds(10, 98, 250, 250);
		frmBensSuperFantastical.getContentPane().add(lblImage1);

		lblImage2 = new JLabel("image2");
		lblImage2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File afterLink = new File(imageLinkAfter);
				try {
					Desktop.getDesktop().open(afterLink);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		lblImage2.setBounds(291, 98, 250, 250);
		frmBensSuperFantastical.getContentPane().add(lblImage2);

		lblImageCert = new JLabel("cert image");
		lblImageCert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File certLink = new File(imageLinkCert);
				try {
					Desktop.getDesktop().open(certLink);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		lblImageCert.setBounds(10, 390, 250, 219);
		frmBensSuperFantastical.getContentPane().add(lblImageCert);

		lblImageTruck = new JLabel("truck image");
		lblImageTruck.setBounds(291, 390, 250, 219);
		frmBensSuperFantastical.getContentPane().add(lblImageTruck);

		JLabel lblCert = new JLabel("Certification");
		lblCert.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCert.setBounds(10, 365, 117, 20);
		frmBensSuperFantastical.getContentPane().add(lblCert);

		lblTruck = new JLabel("Truck");
		lblTruck.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTruck.setBounds(291, 365, 117, 20);
		frmBensSuperFantastical.getContentPane().add(lblTruck);

		chckbxMonitorClipboard = new JCheckBox("Monitor Clipboard");
		chckbxMonitorClipboard.setBounds(386, 10, 164, 23);
		frmBensSuperFantastical.getContentPane().add(chckbxMonitorClipboard);

		lblTicketInformation = new JLabel("Ticket Information:");
		lblTicketInformation.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTicketInformation.setBounds(611, 98, 200, 32);
		frmBensSuperFantastical.getContentPane().add(lblTicketInformation);

		textControl = new JLabel("Control: ");
		textControl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textControl.setBounds(611, 135, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textControl);

		textBefore = new JLabel("Before:");
		textBefore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textBefore.setBounds(611, 160, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textBefore);

		textTicket = new JLabel("Ticket #:");
		textTicket.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textTicket.setBounds(611, 185, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textTicket);

		textDate = new JLabel("Date: ");
		textDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textDate.setBounds(611, 210, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textDate);

		textProject = new JLabel("Project: ");
		textProject.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textProject.setBounds(611, 235, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textProject);

		textOriginTime = new JLabel("Origin Time: ");
		textOriginTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textOriginTime.setBounds(611, 260, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textOriginTime);

		textQAMonitor = new JLabel("QA Monitor: ");
		textQAMonitor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textQAMonitor.setBounds(611, 285, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textQAMonitor);

		textTruck = new JLabel("Truck:");
		textTruck.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textTruck.setBounds(611, 310, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textTruck);

		textSub = new JLabel("Sub:");
		textSub.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textSub.setBounds(611, 335, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textSub);

		textSub2 = new JLabel("Sub2:");
		textSub2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textSub2.setBounds(611, 360, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textSub2);

		textOwner = new JLabel("Owner:");
		textOwner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textOwner.setBounds(611, 385, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textOwner);

		textType = new JLabel("Type:");
		textType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textType.setBounds(611, 410, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textType);

		textSize = new JLabel("Size:");
		textSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textSize.setBounds(611, 435, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textSize);

		textLatitude = new JLabel("Latitude:");
		textLatitude.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textLatitude.setBounds(611, 460, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textLatitude);

		textLongitude = new JLabel("Longitude:");
		textLongitude.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textLongitude.setBounds(611, 485, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textLongitude);

		textVoided = new JLabel("Voided:");
		textVoided.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textVoided.setBounds(611, 510, 322, 23);
		frmBensSuperFantastical.getContentPane().add(textVoided);
	}
}
