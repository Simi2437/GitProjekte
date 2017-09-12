package Grafik;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Technik.EventManager;
import Technik.RouletteDaten;

public abstract class superContent extends JPanel {
	RouletteDaten Daten ; 
	
	private int x = 0 , y = 0  ;
	
	private static int Nummern = 0 ; 
	public static List<String> Namen = new ArrayList();  
	
	UniversalFrame Owner ; 
	
	private int Nummer ; 
	private String Name ; 
	
	superContent(String Name , RouletteDaten Daten)
	{
		this.Daten = Daten ; 
		Namen.add(Name);
		Nummer = Nummern ; 
		Nummern++; 

//		System.out.println(this.Nummer);
	}
	public void setFrame(UniversalFrame Owner)
	{
		this.Owner = Owner ; 
	}
	public int getNbr()
	{
		return this.Nummer ;
	}
	public boolean checkNumber( int i )
	{
//		System.out.println(i + "  " + this.Nummer);
		if(i == Nummer) return true ; 
		else return false ; 
	}
	public void setVisible(boolean stat)
	{
		Owner.setVisible(stat);
	}

//	public void setX(int x ) 
//	{
//		this.x = x ; 
//	}
//	public void setX(int x ) 
//	{
//		this.x = x ; 
//	}
//	public void setX(int x ) 
//	{
//		this.x = x ; 
//	}
//	public void setX(int x ) 
//	{
//		this.x = x ; 
//	}
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	public abstract void MakeFrame();
	
	public void update(float Time)
	{
		this.Owner.repaint();
	}
	public abstract void addContent(); 
}
