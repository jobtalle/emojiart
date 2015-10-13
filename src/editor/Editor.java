package editor;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import menu.Menu;
import paintfield.PaintField;
import tools.Tools;

@SuppressWarnings("serial")
public class Editor extends JFrame {
	public static Editor instance;
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		// Instantiate editor
		Editor.instance = new Editor();
		
		// Add components
		Editor.instance.addEditorComponents();
		
		// Make visible
		Editor.instance.makeVisible();
		
		// Create tools
		new Tools();
	}
	
	public Editor() {
		// Configure frame
		setTitle("Emoji art!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
	}
	
	public void makeVisible() {
		// Show frame
		setSize(new Dimension(800, 600));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void addEditorComponents() {
		// Add menu
		new Menu();
		
		// Add paint field
		new PaintField();
	}
}
