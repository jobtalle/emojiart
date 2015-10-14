package editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.JOptionPane;

public class Config implements Serializable {
	private static final long serialVersionUID = 5003914772220155107L;
	private static final String filename = "prefs.lol";
	
	public String dirPalette;
	
	public void save() {
		try {
			GZIPOutputStream outputStream = new GZIPOutputStream(new FileOutputStream(filename));
			ObjectOutputStream objectStream = new ObjectOutputStream(outputStream);
			
			// Write compressed configuration to file
			objectStream.writeObject(this);
			
			objectStream.close();
			outputStream.close();
		}
        catch(IOException e) {
        	JOptionPane.showMessageDialog(Editor.instance, "Failed saving config");
        }
	}
	
	private static Config createDefault() {
		Config config = new Config();
		
		config.dirPalette = "";
		
		return config;
	}
	
	public static Config loadConfig() {
		Config c = null;
		
		// Load config file or create default config
		File config = new File(filename);
		if(!config.exists()) {
			c = createDefault();
		}
		else {
			try {
				GZIPInputStream inputStream = new GZIPInputStream(new FileInputStream(filename));
				ObjectInputStream inputObject = new ObjectInputStream(inputStream);
				
				c = (Config) inputObject.readObject();
				
				inputObject.close();
				inputStream.close();
				
			} catch (IOException | ClassNotFoundException e) {
				c = createDefault();
			}
		}
		
		return c;
	}
}
