package Visualisierung;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrameHandler{
	
	Keylistener key = new Keylistener();
	KeyHandler KH = new KeyHandler(this , key);
	Mouselistener mouse = new Mouselistener();
	JLabel text = new JLabel("Bereich Makieren"); 
	JFrame frame ; 
    JPanel panel = new JPanel() {
    		protected void paintComponent(Graphics g) {
    			super.paintComponent(g); 
    			setOpaque(false);
    			Graphics2D g2d = (Graphics2D) g.create();
    			g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
    			g2d.setColor(getBackground());
    			g2d.fillRect(0, 0, getWidth(), getHeight());}};
	
	public FrameHandler()
	{
		frame = new JFrame(); 
		frame.addKeyListener(key);
		frame.addMouseListener(mouse);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(new BoxLayout(panel , BoxLayout.Y_AXIS));
		frame.setContentPane(panel);
	}
	
	public void update()
	{
		frame.requestFocus();
		KH.update();
	}
	
	public void showTransparent()
	{
		frame.setUndecorated(true);
		frame.setBackground(new Color(frame.getBackground().getGreen()/256  , frame.getBackground().getGreen()/256 , frame.getBackground().getBlue()/256 , 0.0f ));
		frame.setSize(frame.getToolkit().getScreenSize());
		addButtonsT();
		frame.setVisible(true);
	}
	private void addButtonsT()
	{
		text.setAlignmentY(Component.TOP_ALIGNMENT);
		text.setForeground(Color.BLACK);
		text.setFont(new Font("test" , Font.BOLD , 50));
		panel.add(text);
	}
	public void setText(String str)
	{
		text.setText(str);
		frame.repaint();
	}
	
}
