package Visualisierung2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class studie extends JFrame{
	
	BufferStrategy strat ; 
	Graphics2D g ;
	
	int Time = 0 ;
	int UmdpM = 60; 
	int Kx = 0 , Ky = 0  , Kx1 = 0 , Ky1 = 0 ; 
	int KW = 200, KH = 200;
	
	public studie()
	{
		this.setSize(400 , 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		Init();
	}
	
	
	private void Init()
	{
		this.createBufferStrategy(2);
		strat = this.getBufferStrategy() ; 
	}
	
	public void update(int Time)
	{
		this.Time += Time ; 
		if(g != null)g.dispose();
		g = (Graphics2D) strat.getDrawGraphics() ; 
		Kugelupdate(); 
		paint(g);
		strat.show(); 
		
	}
	
	private void paint(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1000, 1000);
		g.setColor(Color.white);
//		g.drawArc( 100 , 100 , KW  , KH , 0 , 360);
//		g.drawArc(20, 160, 400 , 400 , 0 , 360);
		g.fillArc(Kx, Ky , 20, 20 , 0 , 360);
//		g.fillArc(Kx1, Ky1 , 20, 20 , 0 , 360);
	}
	private void Kugelupdate()
	{
		System.out.println( ((float)this.Time / 1000) +"     "+ this.Time);
		Kx = (int) (200 +  KW/2 * Math.sin( (float)(2*Math.PI*this.UmdpM ) * ((float) this.Time / 1000 / 60 ))) ; 
		Ky = (int) (200 + KH/2 * Math.cos( (float)(2*Math.PI * UmdpM) * ((float) this.Time / 1000 / 60 ))) ;
//		Kx1 = (int) (210  +  400/2 * Math.sin( (Math.PI * UmdpM) * ( (float) this.Time / 1000 / 60 ))) ; 
//		Ky1 = (int) ( 350 + 400/2 * Math.cos( (Math.PI * UmdpM) * ((float) this.Time / 1000 / 60 ))) ;
				}
}
