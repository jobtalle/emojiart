package tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import paintfield.PaintField;

@SuppressWarnings("serial")
public class ColorButton extends JPanel {
	Color color;
	String str;
	boolean pressed = false;
	
	public ColorButton(int i, int j, int k, String emoji) {
		final ColorButton self = this;
		
		color = new Color(i, j, k);
		str = emoji;
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				pressed = true;
				PaintField.instance.setColor(self);
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				pressed = false;
				repaint();
			}
		});
	}
	
	public Color getColor() {
		return color;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if(pressed) {
			g.setColor(Color.WHITE);
		}
		else {
			g.setColor(color);
		}
		
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
