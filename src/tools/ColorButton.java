package tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ColorButton extends JPanel {
	Color color;
	boolean pressed = false;
	
	public ColorButton(int i, int j, int k) {
		color = new Color(i, j, k);
		
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
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				pressed = false;
				repaint();
			}
		});
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
