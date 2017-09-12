package GrafikOld;

import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Robot;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Technik.ImagePanel;

public class LearnFrame extends MasterFrame{
	public JPanel content =new JPanel(); ; 
	public JLabel Zahl = new JLabel("Test");
	public JLabel[] Gesammelt = new JLabel[38];
	public ImagePanel bild = new ImagePanel(null); 
	Robot rob ; 
	Image akt ; 
	
	public LearnFrame()
	{
		super();
		try {rob = new Robot();} catch (AWTException e) {e.printStackTrace();} 
		this.setLocation(0, 0);
		this.setContentPane(content);
		this.setLayout(new FlowLayout());
		this.setVisible(true);
		for(int i = 0 ; i <=37 ; i++)
		{
			Gesammelt[i] = new JLabel( i + " : " + Daten.Gesammelt[i] + " ::::: ");
			this.add(Gesammelt[i]);
		}
//		System.out.println("Man müsste es sehen");
	}
	
	public void Init()
	{

		//this.akt = akt.getScaledInstance(akt.getWidth() * 3, akt.getHeight() * 3, akt.SCALE_DEFAULT) ; 
		if(bild != null)
		{
		bild = new ImagePanel(akt); 
		this.bild.setVisible(true);
		this.bild.setLocation(0, 0);
		//this.setSize(bild.getSize());
		this.getContentPane().add(bild); 
		}
		//System.out.println("wurde initialisiert");
		this.Zahl.setFont(new Font (Font.MONOSPACED , Font.CENTER_BASELINE , 30));
		this.Zahl.setLocation(0, 100);
		this.Zahl.setVisible(true);
		this.getContentPane().add(Zahl); 
		
		
		this.setSize(this.getContentPane().getSize());
	}
	private int i  = 0; 
	public Image LookforZahl()
	{
		akt = rob.createScreenCapture(Daten.getZahlenRaum());
		bild.setImage(akt);
		if(i == 0)
		{
			Init(); 
			i++ ; 
		}
			
			
		return akt ; 
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
	
	public void update()
	{
		//System.out.println("" + Zahl.isVisible());
		this.repaint(); 
		
	}
}
