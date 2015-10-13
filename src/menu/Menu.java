package menu;

import javax.swing.JMenuBar;

import editor.Editor;

@SuppressWarnings("serial")
public class Menu extends JMenuBar {
	public Menu() {
		add(new File());
		add(new Help());
		
		Editor.instance.setJMenuBar(this);
	}
}
