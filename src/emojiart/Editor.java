package emojiart;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Editor {
	private JFrame frame;
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Editor();
	}
	
	public Editor() {
		// Configure frame
		frame = new JFrame();
		frame.setTitle("Emoji art!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		
		// Show frame
		frame.setSize(new Dimension(800, 600));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
