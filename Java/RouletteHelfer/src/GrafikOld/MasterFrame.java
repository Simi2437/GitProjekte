package GrafikOld;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import Technik.EventManager;
import Technik.RouletteDaten;

public class MasterFrame extends JFrame implements NeededFkt{
	EventManager EM ;
	RouletteDaten Daten ; 
	BufferStrategy buf ; 
	Graphics2D g ; 
	
	MasterFrame()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 400);
//		this.setLocationRelativeTo(null);
	}
	
	public void Initold()
	{
		this.createBufferStrategy(2);
		this.buf = this.getBufferStrategy() ; 
	}
	
	@Override
	public void update() {
		
	}	
	
	@Override
	public void setEventManager(EventManager pn) {
		EM = pn ; 
	}
	@Override
	public void setRouletteDaten(RouletteDaten Daten) {
		this.Daten = Daten; 
	}



}
