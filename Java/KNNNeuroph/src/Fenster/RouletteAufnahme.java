package Fenster;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RouletteAufnahme extends JFrame {
    JPanel panel = new JPanel() {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g); 
			setOpaque(false);
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
			g2d.setColor(getBackground());
			g2d.fillRect(0, 0, getWidth(), getHeight());}
		
    
    };
	
			JLabel[] Text = new JLabel[6] ; 
			
	public RouletteAufnahme()
	{
		this.setUndecorated(true);
		this.setOpacity(0.5f);
		this.setSize(this.getToolkit().getScreenSize());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(new BoxLayout(panel , BoxLayout.Y_AXIS));
		this.setContentPane(panel);
		this.setVisible(true);
		this.addall(); 
	}
	
	private void addall()
	{
		//Text 
		Text[0].setText( "Wählen Sie den Sichtbereich!"  ) ;
		panel.add(Text[0]);
	}
}
