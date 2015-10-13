package menu;

import javax.swing.JMenuBar;

@SuppressWarnings("serial")
public class Menu extends JMenuBar {
	public Menu() {
		add(new File());
		add(new Help());
	}
}
