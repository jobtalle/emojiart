package editor;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
		setTitle("Emoji Art!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		
		// Create wrapper
		wrapper = new JPanel();
		wrapper.setLayout(new GridBagLayout());
	}
	
	public void makeVisible() {
		// Show frame
		setSize(new Dimension(800, 600));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void addEditorComponents() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1;
		constraints.gridy = 0;
		
		// Add paint field
		constraints.gridx = 0;
		constraints.weightx = 0.9;
		wrapper.add(new PaintField(), constraints);
		
		// Add tools field
		constraints.gridx = 1;
		constraints.weightx = 0.1;
		wrapper.add(new Tools(), constraints);
		
		// Add menu
		setJMenuBar(new Menu());
		add(wrapper);
	}
}
