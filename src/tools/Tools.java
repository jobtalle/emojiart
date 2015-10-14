package tools;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import paintfield.PaintField;

@SuppressWarnings("serial")
public class Tools extends JPanel {
	private JPanel export;
	private JPanel size;
	private JPanel colors;
	
	private void fillExport() {
		JButton copy = new JButton("Copy");
		JButton load = new JButton("Palette");
		
		// Configure copy
		copy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PaintField.instance.export();
			}
		});
		
		// Configure palette
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Palette");
			}
		});
		
		// Add UI
		export.setLayout(new GridLayout(2, 1));
		export.add(copy);
		export.add(load);
	}
	
	private void defaultColors() {
		colors.setLayout(new GridLayout(4, 2));
		
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 2; y++) {
				colors.add(new ColorButton(0, 0, 0, ":void:"));
			}
		}
		
		// Clear image
		PaintField.instance.clearColor((ColorButton)colors.getComponent(0));
	}
	
	private void fillColors() {
		defaultColors();
	}
	
	public Tools() {
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		// Common constraints
		constraints.gridx = 0;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.BOTH;
		
		// Create clipboard exporter
		export = new JPanel();
		export.setBorder(BorderFactory.createTitledBorder("Copy"));
		fillExport();
		
		// Add export
		constraints.gridy = 0;
		constraints.weighty = 0.2;
		add(export, constraints);
		
		// Create size editor
		size = new JPanel();
		size.setBorder(BorderFactory.createTitledBorder("Size"));
		
		// Add size
		constraints.gridy = 1;
		constraints.weighty = 0.2;
		add(size, constraints);
		
		// Create color chooser
		colors = new JPanel();
		colors.setBorder(BorderFactory.createTitledBorder("Colors"));
		fillColors();
		
		// Add colors
		constraints.gridy = 2;
		constraints.weighty = 0.8;
		add(colors, constraints);
	}
}
