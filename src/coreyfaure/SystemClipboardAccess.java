package coreyfaure;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;


public class SystemClipboardAccess {

    public static String getClipboardString() throws Exception
    {

        // Create a Clipboard object using getSystemClipboard() method
        Clipboard c=Toolkit.getDefaultToolkit().getSystemClipboard();

        // Get data stored in the clipboard that is in the form of a string (text)
        String clipTicket = "";
        try {
        	clipTicket = (String) c.getData(DataFlavor.stringFlavor);
        } catch(UnsupportedFlavorException e) {
        	clipTicket = "CLIPBOARD-ERROR";
        } finally {
        }
        return clipTicket;
        
    }
}
