package paintfield;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import tools.ColorButton;

@SuppressWarnings("serial")
public class PaintField extends JPanel {
	private static final Color backgroundColor = Color.LIGHT_GRAY;
	private static final Color lineColor = Color.WHITE;
	private static final int maxSize = 32;
	private static final int gridLimit = 3;
	
	public static PaintField instance;
	
	private int lastMouseButton = MouseEvent.BUTTON1;
	private Dimension size = new Dimension(16, 16);
	private int zoomFactor = 16;
	private ColorButton[][] grid;
	private ColorButton current = null;
	
	private void paintAt(Point point) {
		Point actualPoint = new Point(point.x / zoomFactor, point.y / zoomFactor);
		
		if(actualPoint.x >= 0 && actualPoint.x < size.getWidth() && actualPoint.y >= 0 && actualPoint.y < size.getHeight()) {
			if(current != null && grid[actualPoint.x][actualPoint.y] != current) {
				grid[actualPoint.x][actualPoint.y] = current;
				repaint();
			}
		}
	}
	
	private void flood(Point point, ColorButton overwrite, ColorButton color) {
		if(point.x < 0 || point.y < 0 || point.x >= size.getWidth() || point.y >= size.getHeight()) return;
		
		if(grid[point.x][point.y] == overwrite) {
			grid[point.x][point.y] = color;
			
			flood(new Point(point.x - 1, point.y), overwrite, color);
			flood(new Point(point.x + 1, point.y), overwrite, color);
			flood(new Point(point.x, point.y - 1), overwrite, color);
			flood(new Point(point.x, point.y + 1), overwrite, color);
		}
	}
	
	private void floodFill(Point point) {
		Point actualPoint = new Point(point.x / zoomFactor, point.y / zoomFactor);
		
		if(actualPoint.x >= 0 && actualPoint.x < size.getWidth() && actualPoint.y >= 0 && actualPoint.y < size.getHeight()) {
			if(current != null && grid[actualPoint.x][actualPoint.y] != current) {
				flood(actualPoint, grid[actualPoint.x][actualPoint.y], current);
				repaint();
			}
		}
	}
	
	public void export() {
		StringBuilder result = new StringBuilder();
		StringSelection selection;
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		for(int y = 0; y < size.getHeight(); y++) {
			for(int x = 0; x < size.getWidth(); x++) {
				result.append(grid[x][y].getString());
			}
			
			if(y != size.getHeight() - 1) result.append("\n");
		}
		
		selection = new StringSelection(result.toString());
		clipboard.setContents(selection, selection);
	}
	
	public void setColor(ColorButton color) {
		current = color;
	}
	
	public void clearColor(ColorButton color) {
		// Set all pixels to color
		for(int x = 0; x < maxSize; x++) {
			for(int y = 0; y < maxSize; y++) {
				grid[x][y] = color;
			}
		}
	}
	
	public void triggerPaint(MouseEvent e) {
		switch(lastMouseButton) {
		case MouseEvent.BUTTON1:
			paintAt(e.getPoint());
			break;
		case MouseEvent.BUTTON3:
			floodFill(e.getPoint());
			break;
		}
	}
	
	public PaintField() {
		instance = this;
		
		grid = new ColorButton[maxSize][maxSize];
		
		// Add paint listeners
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				triggerPaint(arg0);
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				
			}
		});
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				triggerPaint(arg0);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				lastMouseButton = arg0.getButton();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
		});
		
		this.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				if(arg0.getWheelRotation() < 0) {
					zoomFactor++;
					repaint();
				}
				else {
					if(zoomFactor > 1) {
						zoomFactor--;
						repaint();
					}
				}
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
		
		if(zoomFactor >= gridLimit) {
			g.setColor(lineColor);
			for(int x = 0; x <= size.getWidth(); x++) {
				for(int y = 0; y <= size.getHeight(); y++) {
					g.drawLine(0, y * zoomFactor, (int)size.getWidth() * zoomFactor, y * zoomFactor);
					g.drawLine(x * zoomFactor, 0, x * zoomFactor, (int)size.getHeight() * zoomFactor);
				}
			}
		}
	}
}
