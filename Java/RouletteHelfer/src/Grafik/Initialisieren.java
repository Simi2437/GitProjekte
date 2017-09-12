package Grafik;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GrafikOld.InitFrame;
import Technik.ContentPane;
import Technik.EventManager;
import Technik.RouletteDaten;

public class Initialisieren extends superContent implements ActionListener{
	
	private int ablauf = 0 ; 
	
	
	private Rectangle Rect = new Rectangle(0,0,0,0) ; 
	
	private Point firstclick , secondRelease; 
	
	private boolean[] janein = { false , false };	
	
	private ContentPane BufPanel = new ContentPane();
	
	private JButton ja = new JButton("Ja!") , nein = new JButton("Nein:/");
	private JLabel /*Anweisung1 = new JLabel("Wählen Sie den Bereich in dem die gefallenen Zahlen erscheinen."),
				   Anweisung2 = new JLabel("Wählen Sie den Bereich in dem die Kugel dreht. (Obere Laufrine)"),
				   Anweisung3 = new JLabel("Wählen Sie den oberen Bereich in dem Sich das Ziffernblatt dreht. (Mindestens 1 Ziffernbreite)"),*/
				   Anweisung4 = new JLabel("Wählen Sie den Bereich um das Komplette Fenster."),
				   Checkbox = new JLabel("Sind Sie sicher ?")
	;
	public Initialisieren(String Name, RouletteDaten Daten) {
		super(Name, Daten);
		this.setLayout(new GridLayout(4 , 1));
		this.addContent();
	}
	
	public void update(float Time)
	{
		super.update(Time);
		VollBildRaum();
		
//		switch (ablauf)
//		{
//		case 0 :
////			this.Anweisung1.setVisible(false);
//			VollBildRaum();
//		case 1 : 
//			ZahlenRaum();
////			System.out.println(""+ this.Owner.mouse.mousetaste[MouseEvent.BUTTON1]);
//			break; 
//		case 2 :
//			KugelRaum();
//			break;
//		case 3 : 
//			ZifferRaum();
//			break;
//		}
		this.Owner.requestFocus();
	}
	
	@Override
	public void MakeFrame() {
		this.Owner.dispose();
		this.Owner.addMouseListener(this.Owner.mouse);
		this.Owner.setUndecorated(true);
		this.Owner.setContentPane(this);
		this.Owner.setTitle("Die Initialisierung");
		this.Owner.setBackground(new Color(0, 255, 0, 0));
		this.Owner.setSize( Toolkit.getDefaultToolkit().getScreenSize() );
		this.Owner.setVisible(true);
		this.Owner.requestFocus();
	}

	@Override
	public void addContent() {
		this.BufPanel.setLayout(new FlowLayout());
		
//		Anweisung1.setFont(new Font(Font.SANS_SERIF , Font.LAYOUT_LEFT_TO_RIGHT , 30));
//		Anweisung1.setLocation( this.getWidth()/2 - Anweisung1.WIDTH/2 , 20);
//		Anweisung1.setVisible(false);
//		BufPanel.add(Anweisung1);
//		Anweisung2.setFont(new Font(Font.SANS_SERIF , Font.LAYOUT_LEFT_TO_RIGHT , 30));
//		Anweisung2.setLocation(this.getWidth()/2 - Anweisung2.WIDTH/2 , 20);
//		Anweisung2.setVisible(false);
//		BufPanel.add(Anweisung2);
//		Anweisung3.setFont(new Font(Font.SANS_SERIF , Font.LAYOUT_LEFT_TO_RIGHT , 30));
//		Anweisung3.setLocation(this.getWidth()/2 - Anweisung3.WIDTH/2 , 20);
//		Anweisung3.setVisible(false);
//		BufPanel.add(Anweisung3);
		Anweisung4.setFont(new Font(Font.SANS_SERIF , Font.LAYOUT_LEFT_TO_RIGHT , 30));
		Anweisung4.setLocation(this.getWidth()/2 - Anweisung4.WIDTH/2 , 20);
		Anweisung4.setVisible(false);
		BufPanel.add(Anweisung4);
		this.Checkbox.setFont(new Font(Font.SANS_SERIF , Font.LAYOUT_LEFT_TO_RIGHT , 30));
		this.Checkbox.setLocation(this.getWidth()/2 - Checkbox.WIDTH/2 , 20);
		this.Checkbox.setVisible(false);
		BufPanel.add(Checkbox);
		
		this.ja.setFont(new Font(Font.SANS_SERIF , Font.CENTER_BASELINE , 30));
		this.ja.setBounds(this.getWidth()/2 - ja.WIDTH , 50 , 100 , 50 );
		this.ja.addActionListener(this);
		this.ja.setVisible(false);
		this.BufPanel.add(ja);
		this.nein.setFont(new Font(Font.SANS_SERIF , Font.CENTER_BASELINE , 30));
		this.nein.setBounds(this.getWidth()/2 , 50 , 100 , 50 );
		this.nein.addActionListener(this);
		this.nein.setVisible(false);
		this.BufPanel.add(nein);
		
		this.add(BufPanel);
	}
    protected void paintComponent(Graphics g) {

        // Allow super to paint
        super.paintComponent(g);

        // Apply our own painting effect
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Meine Zeichnungen
//        System.out.println("test");
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRect(this.Rect.x, this.Rect.y, this.Rect.width, this.Rect.height);
		g2d.setColor(Color.BLACK);
        
        // 50% transparent Alpha
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        

        
        g2d.setColor(getBackground());
        g2d.fill(getBounds());

        g2d.dispose();

    }

	@Override
	public void actionPerformed(ActionEvent e) {
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
	public void VollBildRaum()
	{

		
		if(this.firstclick == null && secondRelease == null)
		{

			if(this.Owner.mouse.mousetaste[MouseEvent.BUTTON1])
			{
				firstclick = new Point(MouseInfo.getPointerInfo().getLocation());
//				System.out.println("Punkt erzeugt");
			}
		}
		//System.out.println("" + MouseInfo.getPointerInfo().getLocation().x); 
		if(this.firstclick != null&& this.secondRelease == null)
		{
			this.Rect.setBounds(this.firstclick.x, this.firstclick.y, (MouseInfo.getPointerInfo().getLocation().x-this.firstclick.x),(MouseInfo.getPointerInfo().getLocation().y-this.firstclick.y));
		}
		if(this.firstclick != null && this.secondRelease != null) 
		{
			this.Rect.setBounds(this.firstclick.x, this.firstclick.y, (this.secondRelease.x-this.firstclick.x), (this.secondRelease.y -this.firstclick.y));
		}
		
		if(this.firstclick != null && this.secondRelease == null)
			if(!this.Owner.mouse.mousetaste[MouseEvent.BUTTON1]) 
			{
				//System.out.println("Punkt erzeugt");
				this.secondRelease = new Point(MouseInfo.getPointerInfo().getLocation());
			}
		//Wenn der Bereich gewählt ist
		if(this.firstclick != null && this.secondRelease != null)
		{
//			Anweisung1.setVisible(false);
			this.Checkbox.setVisible(true);
			this.ja.setVisible(true);
			this.nein.setVisible(true);
			//System.out.println("If wird abgefragt");
			if(this.janein[0]) 
			{
				Daten.setVollBild(this.Rect);
				ablauf++ ; 
				firstclick = null ; 
				secondRelease = null ;
				this.Checkbox.setVisible(false);
				this.ja.setVisible(false);
				this.nein.setVisible(false);
				this.Rect.setBounds(0,0,0,0);
				this.janein[0] = false ; 
				this.Owner.EM.NextProgramm(EventManager.Automatic);
			}
			if(this.janein[1]) 
			{
				//System.out.println("if wurde aufgerufen");
				firstclick = null ; 
				secondRelease = null ;
				this.Checkbox.setVisible(false);
				this.ja.setVisible(false);
				this.nein.setVisible(false);
				this.Rect.setBounds(0,0,0,0);
				this.janein[1] = false ; 
			}
		}
		else
		{
			Anweisung4.setVisible(true);
		}
		
		
		
		this.repaint();
	}
//	public void ZahlenRaum()
//	{
//
//		
//		if(this.firstclick == null && secondRelease == null)
//		{
//
//			if(this.Owner.mouse.mousetaste[MouseEvent.BUTTON1])
//			{
//				firstclick = new Point(MouseInfo.getPointerInfo().getLocation());
////				System.out.println("Punkt erzeugt");
//			}
//		}
//		//System.out.println("" + MouseInfo.getPointerInfo().getLocation().x); 
//		if(this.firstclick != null&& this.secondRelease == null)
//		{
//			this.Rect.setBounds(this.firstclick.x, this.firstclick.y, (MouseInfo.getPointerInfo().getLocation().x-this.firstclick.x),(MouseInfo.getPointerInfo().getLocation().y-this.firstclick.y));
//		}
//		if(this.firstclick != null && this.secondRelease != null) 
//		{
//			this.Rect.setBounds(this.firstclick.x, this.firstclick.y, (this.secondRelease.x-this.firstclick.x), (this.secondRelease.y -this.firstclick.y));
//		}
//		
//		if(this.firstclick != null && this.secondRelease == null)
//			if(!this.Owner.mouse.mousetaste[MouseEvent.BUTTON1]) 
//			{
//				//System.out.println("Punkt erzeugt");
//				this.secondRelease = new Point(MouseInfo.getPointerInfo().getLocation());
//			}
//		//Wenn der Bereich gewählt ist
//		if(this.firstclick != null && this.secondRelease != null)
//		{
//			Anweisung4.setVisible(false);
//			this.Checkbox.setVisible(true);
//			this.ja.setVisible(true);
//			this.nein.setVisible(true);
//			//System.out.println("If wird abgefragt");
//			if(this.janein[0]) 
//			{
//				Daten.setZahlenRaum(this.Rect);
//				ablauf++ ; 
//				firstclick = null ; 
//				secondRelease = null ;
//				this.Checkbox.setVisible(false);
//				this.ja.setVisible(false);
//				this.nein.setVisible(false);
//				this.Rect.setBounds(0,0,0,0);
//				this.janein[0] = false ; 
//			}
//			if(this.janein[1]) 
//			{
//				//System.out.println("if wurde aufgerufen");
//				firstclick = null ; 
//				secondRelease = null ;
//				this.Checkbox.setVisible(false);
//				this.ja.setVisible(false);
//				this.nein.setVisible(false);
//				this.Rect.setBounds(0,0,0,0);
//				this.janein[1] = false ; 
//			}
//		}
//		else
//		{
////			System.out.println("test");
//			Anweisung1.setVisible(true);
//		}
//		
//		
//		
//		this.repaint();
//	}
//	
//	public void KugelRaum()
//	{
//
//		
//		if(this.firstclick == null && secondRelease == null)
//		{
//
//			if(this.Owner.mouse.mousetaste[MouseEvent.BUTTON1])
//			{
//				firstclick = new Point(MouseInfo.getPointerInfo().getLocation());
//				//System.out.println("Punkt erzeugt");
//			}
//		}
//		//System.out.println("" + MouseInfo.getPointerInfo().getLocation().x); 
//		if(this.firstclick != null&& this.secondRelease == null)
//		{
//			this.Rect.setBounds(this.firstclick.x, this.firstclick.y, (MouseInfo.getPointerInfo().getLocation().x-this.firstclick.x),(MouseInfo.getPointerInfo().getLocation().y-this.firstclick.y));
//		}
//		if(this.firstclick != null && this.secondRelease != null) 
//		{
//			this.Rect.setBounds(this.firstclick.x, this.firstclick.y, (this.secondRelease.x-this.firstclick.x), (this.secondRelease.y -this.firstclick.y));
//		}
//		
//		if(this.firstclick != null && this.secondRelease == null)
//			if(!this.Owner.mouse.mousetaste[MouseEvent.BUTTON1]) 
//			{
//				//System.out.println("Punkt erzeugt");
//				this.secondRelease = new Point(MouseInfo.getPointerInfo().getLocation());
//			}
//		//Wenn der Bereich gewählt ist
//		if(this.firstclick != null && this.secondRelease != null)
//		{
//			Anweisung2.setVisible(false);
//			this.Checkbox.setVisible(true);
//			this.ja.setVisible(true);
//			this.nein.setVisible(true);
//			//System.out.println("If wird abgefragt");
//			if(this.janein[0]) 
//			{
//				Daten.setKugelRaum(new Rectangle(this.Rect));
//				ablauf++ ; 
//				firstclick = null ; 
//				secondRelease = null ;
//				this.Checkbox.setVisible(false);
//				this.ja.setVisible(false);
//				this.nein.setVisible(false);
//				this.Rect.setBounds(0,0,0,0);
//				this.janein[0] = false ; 
//			}
//			if(this.janein[1]) 
//			{
//				//System.out.println("if wurde aufgerufen");
//				firstclick = null ; 
//				secondRelease = null ;
//				this.Checkbox.setVisible(false);
//				this.ja.setVisible(false);
//				this.nein.setVisible(false);
//				this.Rect.setBounds(0,0,0,0);
//				this.janein[1] = false ; 
//			}
//		}
//		else
//		{
//			Anweisung2.setVisible(true);
//		}
//		
//		
//		
//		this.repaint();
//	}
//	
//	public void ZifferRaum()
//	{
//
//		
//		if(this.firstclick == null && secondRelease == null)
//		{
//
//			if(this.Owner.mouse.mousetaste[MouseEvent.BUTTON1])
//			{
//				firstclick = new Point(MouseInfo.getPointerInfo().getLocation());
//				//System.out.println("Punkt erzeugt");
//			}
//		}
//		//System.out.println("" + MouseInfo.getPointerInfo().getLocation().x); 
//		if(this.firstclick != null&& this.secondRelease == null)
//		{
//			this.Rect.setBounds(this.firstclick.x, this.firstclick.y, (MouseInfo.getPointerInfo().getLocation().x-this.firstclick.x),(MouseInfo.getPointerInfo().getLocation().y-this.firstclick.y));
//		}
//		if(this.firstclick != null && this.secondRelease != null) 
//		{
//			this.Rect.setBounds(this.firstclick.x, this.firstclick.y, (this.secondRelease.x-this.firstclick.x), (this.secondRelease.y -this.firstclick.y));
//		}
//		
//		if(this.firstclick != null && this.secondRelease == null)
//			if(!this.Owner.mouse.mousetaste[MouseEvent.BUTTON1]) 
//			{
//				//System.out.println("Punkt erzeugt");
//				this.secondRelease = new Point(MouseInfo.getPointerInfo().getLocation());
//			}
//		//Wenn der Bereich gewählt ist
//		if(this.firstclick != null && this.secondRelease != null)
//		{
//			Anweisung3.setVisible(false);
//			this.Checkbox.setVisible(true);
//			this.ja.setVisible(true);
//			this.nein.setVisible(true);
//			//System.out.println("If wird abgefragt");
//			if(this.janein[0]) 
//			{
//				Daten.setScheibeRaum(this.Rect);
//				ablauf++ ; 
//				firstclick = null ; 
//				secondRelease = null ;
//				this.Checkbox.setVisible(false);
//				this.ja.setVisible(false);
//				this.nein.setVisible(false);
//				this.Rect.setBounds(0,0,0,0);
//				this.janein[0] = false ; 
//				this.ablauf = 0 ; 
//				this.Owner.EM.NextProgramm(EventManager.Anlernen);
//			}
//			if(this.janein[1]) 
//			{
//				//System.out.println("if wurde aufgerufen");
//				firstclick = null ; 
//				secondRelease = null ;
//				this.Checkbox.setVisible(false);
//				this.ja.setVisible(false);
//				this.nein.setVisible(false);
//				this.Rect.setBounds(0,0,0,0);
//				this.janein[1] = false ; 
//			}
//		}
//		else
//		{
//			Anweisung3.setVisible(true);
//		}
//		
//		
//		
//		this.repaint();
//	}
}
