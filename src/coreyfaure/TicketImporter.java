package coreyfaure;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class TicketImporter {

	public static ArrayList<Ticket> getTicketsFromFile(String path) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(path));
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			//System.out.println(line);
			String[] a = line.split("\t");
			if(a.length>5) {
				tickets.add(new Ticket(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7], a[8], a[9], a[10], a[12], a[13], a[14], a[15], a[18]));
			}
		}
		sc.close();
		return tickets;
	}
	
	public static ImageIcon getImageIconFromFile(String path, int w, int h) throws IOException {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(path));
		    BufferedImage imgScaled = scale(img, w, h);
		    return new ImageIcon(imgScaled);
		} catch (IOException e) {
			img = ImageIO.read(TicketImporter.class.getResourceAsStream("/resources/noimage.jpg"));
		    BufferedImage imgScaled = scale(img, w, h);
		    return new ImageIcon(imgScaled);
			//System.out.println("Picture not found.");
		}
		//return null;
		
	}
	
	private static BufferedImage scale(BufferedImage src, int w, int h)
	{
	    BufferedImage img = 
	            new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    int x, y;
	    int ww = src.getWidth();
	    int hh = src.getHeight();
	    int[] ys = new int[h];
	    for (y = 0; y < h; y++)
	        ys[y] = y * hh / h;
	    for (x = 0; x < w; x++) {
	        int newX = x * ww / w;
	        for (y = 0; y < h; y++) {
	            int col = src.getRGB(newX, ys[y]);
	            img.setRGB(x, y, col);
	        }
	    }
	    return img;
	}
}
