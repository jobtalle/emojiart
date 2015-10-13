package menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import editor.Editor;

@SuppressWarnings("serial")
public class File extends JMenu {
	private JMenuItem exit;
	
	public File() {
		super("File");
		
		exit = new JMenuItem(new AbstractAction("Exit") {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(Editor.instance, "Exit Emoji Art?") == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		add(exit);
	}
}
