package Technik;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ContentPane extends JPanel{
	
	
	Rectangle test = new Rectangle(0,0,0,0); 
	
	public void change(int x , int y , int Width , int Height )
	{
		test.setLocation(x, y);
		test.setSize(Width, Height);
	}
	
	
	
    public ContentPane() {

        setOpaque(false);

    }

    @Override
    protected void paintComponent(Graphics g) {

        // Allow super to paint
        super.paintComponent(g);

        // Apply our own painting effect
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Meine Zeichnungen
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawRect(test.x, test.y, test.width, test.height);
		g2d.setColor(Color.BLACK);
        
        // 50% transparent Alpha
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));
        

        
        g2d.setColor(getBackground());
        g2d.fill(getBounds());

        g2d.dispose();

    }
}
