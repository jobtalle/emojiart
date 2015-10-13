package paintfield;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import editor.Editor;

@SuppressWarnings("serial")
public class PaintField extends JPanel {
	public PaintField() {
		Editor.instance.add(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
