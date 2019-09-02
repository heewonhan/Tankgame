package tankgame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

public class HealthBar extends JLabel{
	int total,current;
	
	public HealthBar(int total,int current) {
		this.total = total;
		this.current = current;
	}
	
	public void setCurrent(int current) {
		this.current = current;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		int height = getHeight();
		int width = getWidth();
		
		
		g.setColor(Color.RED);
		g.fillRect(0, 0, width, height);
		
		
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, (int)(width * (current*1.0/total)), height);
	}
}
