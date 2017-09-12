package GrafikOld;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Technik.ContentPane;
import Technik.DrawPanel;
import Technik.EventManager;
import Technik.RouletteDaten;

public class InitFrame extends JFrame implements NeededFkt , ActionListener{
	
	public static final int Zahlen = 1  , Kugel = 2 , Ziffer = 3 ; 
	private int ablauf = 1 ; 
	
	RouletteDaten Daten;
	
	public Point firstclick ; 
	public Point secondRelease ; 
	public EventManager PManager;
	
	private boolean[] janein = { false , false };	
	private BufferStrategy strat ; 
	private JButton ja = new JButton("Ja!") , nein = new JButton("Nein:/");
	private JLabel Anweisung1 = new JLabel("Wählen Sie den Bereich in dem die gefallenen Zahlen erscheinen."),
				   Anweisung2 = new JLabel("Wählen Sie den Bereich in dem die Kugel dreht. (Obere Laufrine)"),
				   Anweisung3 = new JLabel("Wählen Sie den oberen Bereich in dem Sich das Ziffernblatt dreht. (Mindestens 1 Ziffernbreite)"),
				   Checkbox = new JLabel("Sind Sie sicher ?")
	;
	
	private Graphics2D g;
	private JPanel content;
	
	
	public InitFrame()
	{
		//System.out.println("InitFrame");

		this.addKeyListener(this.Key);
		this.addMouseListener(this.Mouse);
//		System.out.println(this.getKeyListeners());
		this.addMouseMotionListener(this.Mouse);
		this.setLayout(null);
		this.setLocation(0, 0);
		this.setSize( this.ScreenSize.width , this.ScreenSize.height);
		this.setUndecorated(true);
		this.setBackground(new Color(0, 255, 0, 0));
		//this.setOpacity(0.4f);
		this.setContentPane(new ContentPane());
		this.getContentPane().setBackground(Color.black);
		this.setVisible(true);
		init();
	}
	
	
	private void init() { 
		Anweisung1.setFont(new Font(Font.SANS_SERIF , Font.LAYOUT_LEFT_TO_RIGHT , 30));
		Anweisung1.setLocation( this.ScreenSize.width/2 - Anweisung1.WIDTH/2 , 20);
		Anweisung1.setVisible(false);
		this.getContentPane().add(Anweisung1);
		Anweisung2.setFont(new Font(Font.SANS_SERIF , Font.LAYOUT_LEFT_TO_RIGHT , 30));
		Anweisung2.setLocation(this.ScreenSize.width/2 - Anweisung2.WIDTH/2 , 20);
		Anweisung2.setVisible(false);
		this.getContentPane().add(Anweisung2);
		Anweisung3.setFont(new Font(Font.SANS_SERIF , Font.LAYOUT_LEFT_TO_RIGHT , 30));
		Anweisung3.setLocation(this.ScreenSize.width/2 - Anweisung3.WIDTH/2 , 20);
		Anweisung3.setVisible(false);
		this.getContentPane().add(Anweisung3);
		this.Checkbox.setFont(new Font(Font.SANS_SERIF , Font.LAYOUT_LEFT_TO_RIGHT , 30));
		this.Checkbox.setLocation(this.ScreenSize.width/2 - Checkbox.WIDTH/2 , 20);
		this.Checkbox.setVisible(false);
		this.getContentPane().add(Checkbox);
		
		
		this.ja.setFont(new Font(Font.SANS_SERIF , Font.CENTER_BASELINE , 30));
		this.ja.setBounds(this.ScreenSize.width/2 - ja.WIDTH , 50 , 100 , 50 );
		this.ja.addActionListener(this);
		this.ja.setVisible(false);
		this.getContentPane().add(ja);
		this.nein.setFont(new Font(Font.SANS_SERIF , Font.CENTER_BASELINE , 30));
		this.nein.setBounds(this.ScreenSize.width/2 , 50 , 100 , 50 );
		this.nein.addActionListener(this);
		this.nein.setVisible(false);
		this.getContentPane().add(nein);
		
		this.addKeyListener(Key);
		
		//this.createBufferStrategy(2);
		//strat = this.getBufferStrategy(); 
	}


	public void ZahlenRaum()
	{

		
		if(this.firstclick == null && secondRelease == null)
		{

			if(InitFrame.Mouse.mousetaste[MouseEvent.BUTTON1])
			{
				firstclick = new Point(MouseInfo.getPointerInfo().getLocation());
				//System.out.println("Punkt erzeugt");
			}
		}
		//System.out.println("" + MouseInfo.getPointerInfo().getLocation().x); 
		if(this.firstclick != null&& this.secondRelease == null)
		{
			((ContentPane) this.getContentPane()).change(this.firstclick.x, this.firstclick.y, (MouseInfo.getPointerInfo().getLocation().x-this.firstclick.x),(MouseInfo.getPointerInfo().getLocation().y-this.firstclick.y));
		}
		if(this.firstclick != null && this.secondRelease != null) 
		{
			((ContentPane) this.getContentPane()).change(this.firstclick.x, this.firstclick.y, (this.secondRelease.x-this.firstclick.x), (this.secondRelease.y -this.firstclick.y));
		}
		
		if(this.firstclick != null && this.secondRelease == null)
			if(!this.Mouse.mousetaste[MouseEvent.BUTTON1]) 
			{
				//System.out.println("Punkt erzeugt");
				this.secondRelease = new Point(MouseInfo.getPointerInfo().getLocation());
			}
		//Wenn der Bereich gewählt ist
		if(this.firstclick != null && this.secondRelease != null)
		{
			Anweisung1.setVisible(false);
			this.Checkbox.setVisible(true);
			this.ja.setVisible(true);
			this.nein.setVisible(true);
			//System.out.println("If wird abgefragt");
			if(this.janein[0]) 
			{
				Daten.setZahlenRaum(new Rectangle(this.firstclick.x, this.firstclick.y, (this.secondRelease.x-this.firstclick.x), (this.secondRelease.y -this.firstclick.y)));
				ablauf++ ; 
				firstclick = null ; 
				secondRelease = null ;
				this.Checkbox.setVisible(false);
				this.ja.setVisible(false);
				this.nein.setVisible(false);
				((ContentPane) this.getContentPane()).change(0,0,0,0);
				this.janein[0] = false ; 
			}
			if(this.janein[1]) 
			{
				//System.out.println("if wurde aufgerufen");
				firstclick = null ; 
				secondRelease = null ;
				this.Checkbox.setVisible(false);
				this.ja.setVisible(false);
				this.nein.setVisible(false);
				((ContentPane) this.getContentPane()).change(0,0,0,0);
				this.janein[1] = false ; 
			}
		}
		else
		{
			Anweisung1.setVisible(true);
		}
		
		
		
		this.repaint();
	}
	
	public void KugelRaum()
	{

		
		if(this.firstclick == null && secondRelease == null)
		{

			if(InitFrame.Mouse.mousetaste[MouseEvent.BUTTON1])
			{
				firstclick = new Point(MouseInfo.getPointerInfo().getLocation());
				//System.out.println("Punkt erzeugt");
			}
		}
		//System.out.println("" + MouseInfo.getPointerInfo().getLocation().x); 
		if(this.firstclick != null&& this.secondRelease == null)
		{
			((ContentPane) this.getContentPane()).change(this.firstclick.x, this.firstclick.y, (MouseInfo.getPointerInfo().getLocation().x-this.firstclick.x),(MouseInfo.getPointerInfo().getLocation().y-this.firstclick.y));
		}
		if(this.firstclick != null && this.secondRelease != null) 
		{
			((ContentPane) this.getContentPane()).change(this.firstclick.x, this.firstclick.y, (this.secondRelease.x-this.firstclick.x), (this.secondRelease.y -this.firstclick.y));
		}
		
		if(this.firstclick != null && this.secondRelease == null)
			if(!this.Mouse.mousetaste[MouseEvent.BUTTON1]) 
			{
				//System.out.println("Punkt erzeugt");
				this.secondRelease = new Point(MouseInfo.getPointerInfo().getLocation());
			}
		//Wenn der Bereich gewählt ist
		if(this.firstclick != null && this.secondRelease != null)
		{
			Anweisung2.setVisible(false);
			this.Checkbox.setVisible(true);
			this.ja.setVisible(true);
			this.nein.setVisible(true);
			//System.out.println("If wird abgefragt");
			if(this.janein[0]) 
			{
				Daten.setKugelRaum(new Rectangle(this.firstclick.x, this.firstclick.y, (this.secondRelease.x-this.firstclick.x), (this.secondRelease.y -this.firstclick.y)));
				ablauf++ ; 
				firstclick = null ; 
				secondRelease = null ;
				this.Checkbox.setVisible(false);
				this.ja.setVisible(false);
				this.nein.setVisible(false);
				((ContentPane) this.getContentPane()).change(0,0,0,0);
				this.janein[0] = false ; 
			}
			if(this.janein[1]) 
			{
				//System.out.println("if wurde aufgerufen");
				firstclick = null ; 
				secondRelease = null ;
				this.Checkbox.setVisible(false);
				this.ja.setVisible(false);
				this.nein.setVisible(false);
				((ContentPane) this.getContentPane()).change(0,0,0,0);
				this.janein[1] = false ; 
			}
		}
		else
		{
			Anweisung2.setVisible(true);
		}
		
		
		
		this.repaint();
	}
	
	public void ZifferRaum()
	{

		
		if(this.firstclick == null && secondRelease == null)
		{

			if(InitFrame.Mouse.mousetaste[MouseEvent.BUTTON1])
			{
				firstclick = new Point(MouseInfo.getPointerInfo().getLocation());
				//System.out.println("Punkt erzeugt");
			}
		}
		//System.out.println("" + MouseInfo.getPointerInfo().getLocation().x); 
		if(this.firstclick != null&& this.secondRelease == null)
		{
			((ContentPane) this.getContentPane()).change(this.firstclick.x, this.firstclick.y, (MouseInfo.getPointerInfo().getLocation().x-this.firstclick.x),(MouseInfo.getPointerInfo().getLocation().y-this.firstclick.y));
		}
		if(this.firstclick != null && this.secondRelease != null) 
		{
			((ContentPane) this.getContentPane()).change(this.firstclick.x, this.firstclick.y, (this.secondRelease.x-this.firstclick.x), (this.secondRelease.y -this.firstclick.y));
		}
		
		if(this.firstclick != null && this.secondRelease == null)
			if(!this.Mouse.mousetaste[MouseEvent.BUTTON1]) 
			{
				//System.out.println("Punkt erzeugt");
				this.secondRelease = new Point(MouseInfo.getPointerInfo().getLocation());
			}
		//Wenn der Bereich gewählt ist
		if(this.firstclick != null && this.secondRelease != null)
		{
			Anweisung3.setVisible(false);
			this.Checkbox.setVisible(true);
			this.ja.setVisible(true);
			this.nein.setVisible(true);
			//System.out.println("If wird abgefragt");
			if(this.janein[0]) 
			{
				Daten.setScheibeRaum(new Rectangle(this.firstclick.x, this.firstclick.y, (this.secondRelease.x-this.firstclick.x), (this.secondRelease.y -this.firstclick.y)));
				ablauf++ ; 
				firstclick = null ; 
				secondRelease = null ;
				this.Checkbox.setVisible(false);
				this.ja.setVisible(false);
				this.nein.setVisible(false);
				((ContentPane) this.getContentPane()).change(0,0,0,0);
				this.janein[0] = false ; 
				this.ablauf = 1 ; 
				this.PManager.NextProgramm(EventManager.Anlernen);
			}
			if(this.janein[1]) 
			{
				//System.out.println("if wurde aufgerufen");
				firstclick = null ; 
				secondRelease = null ;
				this.Checkbox.setVisible(false);
				this.ja.setVisible(false);
				this.nein.setVisible(false);
				((ContentPane) this.getContentPane()).change(0,0,0,0);
				this.janein[1] = false ; 
			}
		}
		else
		{
			Anweisung3.setVisible(true);
		}
		
		
		
		this.repaint();
	}
/*	public void paint(Graphics g)
	{
		//System.out.println("Wird Aufgerufen");
		g.setColor(Color.BLACK);
		((Graphics2D) g).setStroke(new BasicStroke(3));
		if(this.firstclick != null&& this.secondRelease == null)
		{
			g.drawRect(this.firstclick.x, this.firstclick.y, this.firstclick.x+(this.Mouse.movPoint.x-this.firstclick.x), this.firstclick.x+(this.Mouse.movPoint.y -this.firstclick.y));
		}
		if(this.firstclick != null && this.secondRelease != null) g.drawRect(this.firstclick.x, this.firstclick.y, this.firstclick.x+(this.secondRelease.x-this.firstclick.x), this.firstclick.x+(this.secondRelease.y -this.firstclick.y));
	}*/
	
	@Override
	public void update() {
		
		switch (ablauf)
		{
		case 1 : 
			ZahlenRaum();
			break; 
		case 2 :
			KugelRaum();
			break;
		case 3 : 
			ZifferRaum();
			break;
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		//System.out.println(e.getActionCommand());
		
		
		if(e.getActionCommand() == "Ja!")
		{
			this.janein[0] = true ; 
			this.requestFocus();
		}
		
		if(e.getActionCommand() == "Nein:/")
		{
			//System.out.println("if wurde aufgerufen");
			this.janein[1]= true ;
			this.requestFocus();
			//System.out.println(this.getKeyListeners());
		}
	}


	@Override
	public void setEventManager(EventManager pn) {
		
		this.PManager = pn ; 
		
	}


	@Override
	public void setRouletteDaten(RouletteDaten Daten) {
		this.Daten = Daten ; 
	}
	
	

}
