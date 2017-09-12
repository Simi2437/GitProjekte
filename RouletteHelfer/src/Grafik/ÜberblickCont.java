package Grafik;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Robot;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Technik.ImagePanel;
import Technik.RouletteDaten;

public class ÜberblickCont extends superContent {
	
	private Image akt ; 
	
	private JButton Richtig = new JButton("Richtig"); 
	private JButton Falsch = new JButton("Falsch");
	
	public JPanel content =new JPanel();
	JPanel Zahlen = new JPanel(); 
	public JLabel Zahl = new JLabel("Test");
	public JLabel[] Gesammelt = new JLabel[38];
	public ImagePanel bild = new ImagePanel(null); 
	
	public ÜberblickCont(String Name , RouletteDaten Daten) {
		super(Name , Daten);
		this.setLayout(new GridLayout(3 , 1));
		this.addContent(); 
	}

	@Override
	public void MakeFrame() {
		this.Owner.dispose();
		this.Owner.setTitle("Überblick info");
		this.Owner.setContentPane(this);
		this.Owner.setBackground(this.Owner.getContentPane().getBackground());
		this.Owner.setUndecorated(false);
		this.Owner.setLocation(this.getLocation());
		this.Owner.setSize(this.getSize());
		this.Owner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.Owner.requestFocus();
		this.Owner.pack();
		this.setVisible(true);
		
	}

	@Override
	public void update(float Time) {
		this.Owner.repaint();
	}

	@Override
	public void addContent() {
//		System.out.println(Zahlen);
		Zahlen.setLayout(new GridLayout(4 , 10));
		this.add(Zahlen); 
		for(int i = 0 ; i <=37 ; i++)
		{
			Gesammelt[i] = new JLabel( i + " : " + Daten.Gesammelt[i] + " ::::: ");
			Zahlen.add(Gesammelt[i]);
		}
//		this.add(Zahlen);
		
		//this.akt = akt.getScaledInstance(akt.getWidth() * 3, akt.getHeight() * 3, akt.SCALE_DEFAULT) ; 
		this.add(bild); 
		
		//System.out.println("wurde initialisiert");
		this.Zahl.setFont(new Font (Font.MONOSPACED , Font.CENTER_BASELINE , 30));
//		this.Zahl.setLocation(0, 100);
		this.Zahl.setVisible(true);
		this.add(Zahl); 
		
//		this.setPreferredSize(new Dimension(400, 400));
		
	}
	public void SammlungAnders()
	{
		for(int i = 0 ; i <=37 ; i++)
		{
			if( i < 37 ) Gesammelt[i].setText( i + " : " + Daten.Gesammelt[i] + " ::::: "); 
			else Gesammelt[i].setText( "Müll" + " : " + Daten.Gesammelt[i] + " ::::: ") ; 
		}
	}
	
	public void AngezeigteZahl(int i)
	{
		Zahl.setText("Dies ist die " + i  ); 
	}
	public void setPic(Image akt)
	{
		this.bild.setImage(akt);
		this.Owner.setVisible(true);
	}
}
