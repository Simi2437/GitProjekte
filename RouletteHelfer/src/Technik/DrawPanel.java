package Technik;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class DrawPanel extends JPanel{
	
	Rectangle test ; 
	public DrawPanel(Rectangle test)
	{
		this.test = test ; 
	}
	public void change(int x , int y , int Width , int Height )
	{
		test.setLocation(x, y);
		test.setSize(Width, Height);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.BLACK);
	    g.fillRect(100,100,150,250);
		//System.out.println("Wird Aufgerufen"  + " x: " + test.x + "  y: " + test.y + " Width: " + test.width + " Height:" + test.height);
//		g2.setStroke(new BasicStroke(3));
//		g2.setColor(Color.BLACK);
//		g2.drawRect(test.x, test.y, test.width, test.height);
//		g2.drawString("Hallo", 100, 100);
//		
	}
	
}
