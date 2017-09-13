package Visualisierung;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UnsichtbaresFrame extends JFrame
{
	JLabel text = new JLabel("Bereich Makieren"); 
	JPanel content = new JPanel();
	
	public UnsichtbaresFrame()
	{
		this.setSize(this.getToolkit().getScreenSize());
		this.setUndecorated(true);
		this.setOpacity(0.55f);
		addComponents(this.getContentPane());
		this.setVisible(true);
		
	}
	
	private void addComponents(Container pane)
	{
		content.setOpaque(false);
		content.setLayout(new BoxLayout(content , BoxLayout.Y_AXIS));
		this.setContentPane(content);
		
		text.setAlignmentY(Component.TOP_ALIGNMENT);
		text.setForeground(Color.BLACK);
		text.setFont(new Font("test" , Font.BOLD , 50));
		content.add(text);
		this.repaint(); 
	}
}
