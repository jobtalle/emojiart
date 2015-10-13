package editor;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import menu.Menu;
import paintfield.PaintField;
import tools.Tools;

@SuppressWarnings("serial")
public class Editor extends JFrame {
	public static Editor instance;
	private JPanel wrapper;
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		// Instantiate editor
		Editor.instance = new Editor();
		
		// Add components
		Editor.instance.addEditorComponents();
		
		// Make visible
		Editor.instance.makeVisible();
	}
	
	public Editor() {
		// Configure frame
		setTitle("Emoji art!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		
		// Create wrapper
		wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.LINE_AXIS));
	}
	
	public void makeVisible() {
		// Show frame
		setSize(new Dimension(800, 600));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void addEditorComponents() {
		// Add paint field
		wrapper.add(new PaintField());
		wrapper.add(new Tools());
		
		// Add menu
		setJMenuBar(new Menu());
		add(wrapper);
	}
}
