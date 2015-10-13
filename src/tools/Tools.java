package tools;

import javax.swing.JFrame;

import editor.Editor;

@SuppressWarnings("serial")
public class Tools extends JFrame {
	public Tools() {
		setTitle("Tools");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		
		setLocationRelativeTo(Editor.instance);
		setAlwaysOnTop(true);
		setVisible(true);
	}
}
