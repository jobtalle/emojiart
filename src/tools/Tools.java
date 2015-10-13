package tools;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Tools extends JPanel {
	private JPanel size;
	private JPanel colors;
	
	public Tools() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Create size editor
		size = new JPanel();
		size.setBorder(BorderFactory.createTitledBorder("Size"));
		add(size);
		
		// Create color chooser
		colors = new JPanel();
		colors.setBorder(BorderFactory.createTitledBorder("Colors"));
		colors.setLayout(new GridLayout(4, 2));
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 2; y++) {
				colors.add(new JButton("X"));
			}
		}
		
		add(colors);
	}
}
