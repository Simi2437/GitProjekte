package GrafikOld;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Technik.EventManager;
import Technik.RouletteDaten;

public class MainFrame extends JFrame implements ActionListener, NeededFkt{
	private RouletteDaten Daten ; 
	public EventManager PManager;
	private int Höhe = 0, Breite = 0; 
	JButton Neu = new JButton("Neu");
	
	public MainFrame()
	{
		init(); 
		this.setSize(this.Höhe , this.Breite);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(Neu); 
		this.setVisible(true);
	}
	
	public void update(){
		
	}
	
	private void init()
	{
		this.Höhe = 400 ; 
		this.Breite = 400 ; 
		Neu.addActionListener(this);
	}
	
	public void setEventManager(EventManager pn)
	{
		this.PManager = pn ; 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println(e.getActionCommand());
		if(e.getActionCommand() == "Neu")
		{
			this.PManager.NextProgramm(PManager.Initialisieren);
		}
		
	}

	@Override
	public void setRouletteDaten(RouletteDaten Daten) {
		this.Daten = Daten ; 
	}
	
	

}
