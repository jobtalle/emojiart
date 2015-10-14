package paintfield;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import tools.ColorButton;

@SuppressWarnings("serial")
public class PaintField extends JPanel {
	private static final Color backgroundColor = Color.LIGHT_GRAY;
	private static final int maxSize = 32;
	
	public static PaintField instance;
	
	private Dimension size = new Dimension(16, 16);
	private int zoomFactor = 16;
	private ColorButton[][] grid;
	private ColorButton current = null;
	
	private void paintAt(Point point) {
		Point actualPoint = new Point(point.x / zoomFactor, point.y / zoomFactor);
		
		if(actualPoint.x > 0 && actualPoint.x < maxSize && actualPoint.y > 0 && actualPoint.y < maxSize) {
			if(current != null && grid[actualPoint.x][actualPoint.y] != current) {
				grid[actualPoint.x][actualPoint.y] = current;
				repaint();
			}
		}
	}
	
	public void setColor(ColorButton color) {
		current = color;
	}
	
	public PaintField() {
		instance = this;
		
		grid = new ColorButton[maxSize][maxSize];
		
		// Set all pixels to unpainted
		for(int x = 0; x < maxSize; x++) {
			for(int y = 0; y < maxSize; y++) {
				grid[x][y] = null;
			}
		}
		
		// Add paint listeners
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				paintAt(arg0.getPoint());
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				
			}
		});
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				paintAt(arg0.getPoint());
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for(int x = 0; x < size.getWidth(); x++) {
			for(int y = 0; y < size.getHeight(); y++) {
				if(grid[x][y] == null) {
					g.setColor(backgroundColor);
				}
				else {
					g.setColor(grid[x][y].getColor());
				}
				
				g.fillRect(x * zoomFactor, y * zoomFactor, zoomFactor, zoomFactor);
			}
		}
	}
}
