package Simulator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Simulator extends JFrame{
	
	BufferStrategy strat ; 
	Graphics2D g ;
	
	int Time = 0 ;
	int UmdpM = 40; 
	int Kx = 0 , Ky = 0  , Kx1 = 0 , Ky1 = 0 ; 
	int KW = 400 , KH = 100 ;
	
	public Simulator()
	{
		super("Roulette Simulation");
		this.setSize(440 , 1000);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setVisible(true);
		Init();
	}
	private void Init()
	{
		this.createBufferStrategy(2);
		strat = this.getBufferStrategy();
	}
	
	public void update(float Time)
	{ 
		this.Time += Time ; 
		Kugelupdate();
		g = (Graphics2D) this.strat.getDrawGraphics();
		paintthis(g);
		strat.show();
		g.clearRect(0 , 0 , 2000 , 2000);
		g.dispose(); 
	}
	
	private void paintthis(Graphics2D g)
	{
		g.fillRect(0, 0, 1000, 1000);
		g.setColor(Color.white);
		g.drawArc( 20 , 60, KW  , KH , 0 , 360);
		g.drawArc(20, 160, 400 , 400 , 0 , 360);
		g.fillArc(Kx, Ky , 20, 20 , 0 , 360);
		g.fillArc(Kx1, Ky1 , 20, 20 , 0 , 360);
	}
	private void Kugelupdate()
	{
		System.out.println( ( (float )this.Time / 1000 / 60 ));
		Kx = (int) (210  +  KW/2 * Math.sin( (Math.PI * UmdpM) * ( (float) this.Time / 1000 / 60 ))) ; 
		Ky = (int) ( 100 + KH/2 * Math.cos( (Math.PI * UmdpM) * ((float) this.Time / 1000 / 60 ))) ;
		Kx1 = (int) (210  +  400/2 * Math.sin( (Math.PI * UmdpM) * ( (float) this.Time / 1000 / 60 ))) ; 
		Ky1 = (int) ( 350 + 400/2 * Math.cos( (Math.PI * UmdpM) * ((float) this.Time / 1000 / 60 ))) ;
				}
}
