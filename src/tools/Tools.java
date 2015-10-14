package tools;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import editor.Editor;
import paintfield.PaintField;

@SuppressWarnings("serial")
public class Tools extends JPanel {
	public static Tools instance;
	
	private JPanel export;
	private JPanel size;
	private JPanel colors;
	
	private JLabel sizeDesc = new JLabel();
	private JLabel countDesc = new JLabel();
	
	private void loadPaletteFromFile(String fname) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fname));
		String line;
		PaletteReadState state = PaletteReadState.IDENTIFIER;
		ArrayList<ColorButton> buttons = new ArrayList<ColorButton>();
		
		// Buffered new color
		String identifier = null;
		int red = 0;
		int green = 0;
		int blue = 0;
		
		// Save palette to config
		Editor.config.dirPalette = fname;
		Editor.config.save();
		
		// Read first line
		line = reader.readLine();
		
		while(line != null) {
			switch(state) {
			case IDENTIFIER:
				identifier = line;
				state = PaletteReadState.RED;
				break;
			case RED:
				red = Integer.decode(line);
				state = PaletteReadState.GREEN;
				break;
			case GREEN:
				green = Integer.decode(line);
				state = PaletteReadState.BLUE;
				break;
			case BLUE:
				blue = Integer.decode(line);
				
				// Add new color
				buttons.add(new ColorButton(red, green, blue, identifier));
				
				state = PaletteReadState.IDENTIFIER;
				break;
			}
			
			// Read next line
			line = reader.readLine();
		}
		
		// Close input stream
		reader.close();
		
		// Activate palette
		colors.removeAll();
		
		// Recalculate grid
		int gridWidth = (int)Math.sqrt(buttons.size() / 2);
		int gridHeight = (int)Math.ceil(buttons.size() / gridWidth);
		colors.setLayout(new GridLayout(gridHeight, gridWidth));
		
		for(int i = 0; i < buttons.size(); i++) {
			colors.add(buttons.get(i));
		}
		
		colors.revalidate();
		colors.repaint();
		
		// Clear image
		PaintField.instance.clearColor((ColorButton)colors.getComponent(0));
	}
	
	private void loadPalette() throws IOException {
		JFileChooser chooser = null;
		int response;
		
		try {
			chooser = new JFileChooser(new File(Editor.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
		} catch (URISyntaxException e) {
			JOptionPane.showMessageDialog(Editor.instance, "Couldn't get the executable path");
		}
		
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("TXT Palette configurations", "txt"));
		
		// Request txt file
		response = chooser.showOpenDialog(Editor.instance);
		
		if(response == JFileChooser.APPROVE_OPTION) {
			loadPaletteFromFile(chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
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
				try {
					loadPalette();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(Editor.instance, "Error when reading file");
				}
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
		if(Editor.config.dirPalette != null) {
			try {
				loadPaletteFromFile(Editor.config.dirPalette);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(Editor.instance, "Error when reading file");
			}
		}
		else {
			if(JOptionPane.showConfirmDialog(Editor.instance, "No palette has been loaded.\nDo you want to load one now?") == JOptionPane.YES_OPTION) {
				try {
					loadPalette();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(Editor.instance, "Error when reading file");
				}
			}
			else {
				defaultColors();
			}
		}
	}
	
	public void updateText() {
		StringBuilder sb = new StringBuilder();
		
		sb.append((int)PaintField.instance.getSize().getWidth());
		sb.append("x");
		sb.append((int)PaintField.instance.getSize().getHeight());
		
		// Update size description
		sizeDesc.setText(sb.toString());
		
		sb = new StringBuilder();
				
		sb.append((int)(PaintField.instance.getSize().getWidth() * PaintField.instance.getSize().getHeight()));
		sb.append(" emoji's");
		
		// Update count description
		countDesc.setText(sb.toString());
	}
	
	public Tools() {
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		instance = this;
		
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
		size.setLayout(new BoxLayout(size, BoxLayout.PAGE_AXIS));
		size.add(sizeDesc);
		size.add(countDesc);
		
		// Add size
		constraints.gridy = 1;
		constraints.weighty = 0.1;
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
