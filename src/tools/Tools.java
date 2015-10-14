package tools;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import editor.Editor;
import paintfield.PaintField;

@SuppressWarnings("serial")
public class Tools extends JPanel {
	private JPanel export;
	private JPanel size;
	private JPanel colors;
	
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
			BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile().getAbsolutePath()));
			String line;
			PaletteReadState state = PaletteReadState.IDENTIFIER;
			ArrayList<ColorButton> buttons = new ArrayList<ColorButton>();
			
			// Buffered new color
			String identifier = null;
			int red = 0;
			int green = 0;
			int blue = 0;
			
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
			
			for(int i = 0; i < buttons.size(); i++) {
				colors.add(buttons.get(i));
			}
			
			colors.repaint();
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
