package menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import editor.Editor;

@SuppressWarnings("serial")
public class Help extends JMenu {
	private JMenuItem about;
	
	public Help() {
		super("Help");
		
		about = new JMenuItem(new AbstractAction("About") {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(Editor.instance,"Pencil: LMB\nFlood fill: RMB\nScroll to zoom\nDrag outside the canvas to resize\n\nEmoji art\nCopyright Job Talle 2015");
			}
		});
		add(about);
	}
}
