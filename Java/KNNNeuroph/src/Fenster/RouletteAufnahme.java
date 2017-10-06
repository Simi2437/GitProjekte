package Fenster;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Technik.RouletteDaten;

public class RouletteAufnahme extends JFrame implements KeyListener, MouseListener , MouseMotionListener , ActionListener{
    JPanel panel = new JPanel() {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g); 
			setOpaque(false);
			Graphics2D g2d = (Graphics2D) g.create();
//			g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
			g2d.setColor(new Color(getBackground().getRed() , getBackground().getGreen() , getBackground().getBlue() , (int) (255/1.5))); 
			
			if(View != null)
			{
			//links
			g2d.fillRect(0, 0, (int) View.getX(), getHeight()); 
			//oben
			g2d.fillRect( (int) View.getX() , 0,    (int) View.getWidth(),  (int) View.getY()); 
			//unten
			g2d.fillRect( (int) View.getX() ,(int) View.getY() + (int) View.getHeight() ,    (int) View.getWidth(), (int) (this.getHeight() - (int) View.getY() - View.getHeight()));
			//oben
			g2d.fillRect( (int) View.getX() + (int) View.getWidth() , 0,   this.getWidth() - (int) View.getX()  - (int) View.getWidth() , this.getHeight());
			}
			else
			{
				g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
			
			
			g2d.setColor(Color.black);
			g2d.setStroke(new BasicStroke(1));
			
//			for(int z = 0 ; z < Views.length ; z++)
//			{
//				g2d.drawRect(Views[z].x, Views[z].y, Views[z].width, Views[z].height);
//			}
			
//			g2d.drawRect((int)Views[0].getX() ,(int)Views[0].getY(),(int)Views[0].getWidth(),(int)Views[0].getHeight() ) ;
			
		}
		
    
    };
    
    Point Mouse = new Point();
    Point location1;
    Point location2;
    
    private JButton rec = new JButton("Aufnehmen") ,
    		newProjekt = new JButton("Neu"),
    		stop = new JButton("Stopp"),
    		save = new JButton("Speichern"),
    		load = new JButton("Laden"),
    		outputCh = new JButton("Outputs setzen"); 
    
    private JButton[] ProjektButtons ; 
    
    private JTextField FileNameIN = new JTextField(20) ;
    
    boolean Neu = false ,
    		geladen = false ,
    		laden = false ,
    		Speichern = false ,
//    		hasProjektName = false ,
//    		getProjektName = false ,  
    		Aufnahme = false ,
    		outset = false;
    
    private String ProjektName ; 
    
    Graphics2D g ; 
    private Rectangle View ; 
    private Rectangle MainView ;
    int Rectangles = 0 ;
    
	JLabel Anweisung ; 
	JLabel FileNameEingeben = new JLabel("Projektnamen eingeben:");
			
	public RouletteAufnahme()
	{
		//Bild undekoriert
		this.setUndecorated(true);
		// Listener initialisieren
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		//Ganzer Bildschirm
		this.setSize(this.getToolkit().getScreenSize());
		//close
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Panel initialisieren
		panel.setLayout(new BoxLayout(panel , BoxLayout.Y_AXIS));
		panel.setDoubleBuffered(true);
		this.setContentPane(panel);
		//Fenster Unsichtbar machen
		this.setBackground(new Color(this.getBackground().getRed() , this.getBackground().getGreen() , this.getBackground().getBlue() , 0 ));
		//Buttons und Labels hinzufügen
		this.addall(); 
		//Sichtbar machen und immer im VorderGrund
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		this.requestFocus();
	}
	private void addall()
	{
		//Text 
		Anweisung= new JLabel();  
		Anweisung.setFont(new Font(Font.MONOSPACED , Font.BOLD , 50));
		Anweisung.setText( "Was wollen Sie machen"  ) ;
		panel.add(Anweisung , panel.BOTTOM_ALIGNMENT);
		
		this.newProjekt.addActionListener(this);
		panel.add(this.newProjekt,  panel.CENTER_ALIGNMENT);
		
		rec.addActionListener(this);
		panel.add(rec, Component.TOP_ALIGNMENT);
		
		stop.addActionListener(this);
		panel.add(stop, Component.TOP_ALIGNMENT);

		this.outputCh.addActionListener(this);
		panel.add(outputCh, Component.TOP_ALIGNMENT) ;
		
		save.addActionListener(this);
		panel.add(save, Component.TOP_ALIGNMENT);
		
		load.addActionListener(this);
		panel.add(load, Component.TOP_ALIGNMENT);
		
		this.FileNameIN.setMaximumSize(this.FileNameIN.getPreferredSize());
		this.FileNameIN.addActionListener(this);
		this.FileNameIN.setVisible(false);
		panel.add(this.FileNameIN , 1 );
		
		FileNameEingeben.setVisible(false);
		panel.add(this.FileNameEingeben , 1 ) ;
		
		this.requestFocus();
		this.validate();
	}
		
	public void update()
	{
		panel.repaint();
	}
	
	
	public void setAnweisung(String text)
	{
		Anweisung.setText(text);
	}
	@Override
	public void keyPressed(KeyEvent k) {
		
		
		//Schliesen
		if(k.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent k) {
		

		
	}

	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		
//		System.out.println("    " + m.getButton() + "   " + MouseEvent.BUTTON1) ;
		
		if(this.Neu && this.getMainRect() == null && this.getProjektName() != null)
		{
//			System.out.println("hä");
			if(m.getButton() == MouseEvent.BUTTON1)
			{	
				boolean Arbeit = true ; 
				if(this.location1 == null && Arbeit)
				{
//					System.out.println("   Test    ") ;
					this.location1 = new Point(m.getX() , m.getY());
					Arbeit = false ;
				}
				if(this.location1 != null && this.location2 == null && Arbeit)
				{
//					System.out.println("hä");
					this.location2 = new Point(m.getX() , m.getY());
					this.MainView = new Rectangle(this.location1.x , this.location1.y, this.location2.x - this.location1.x  , this.location2.y - this.location1.y);
					Arbeit = false  ; 
				}
			}
			if(m.getButton() == MouseEvent.BUTTON3)
			{
				if(this.location1 != null && this.location2 == null)
				{
					location1 = null ; 
					this.View.setBounds(0, 0, 0, 0);
					panel.repaint();
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent m) {
		
		if(this.location1 != null && this.location2 == null)
		{
			if(View == null) this.View = new Rectangle(this.location1.x , this.location1.y, m.getX() - this.location1.x  , m.getY() - this.location1.y) ; 
			else this.View.setBounds(this.location1.x , this.location1.y, m.getX() - this.location1.x  , m.getY() - this.location1.y);
			panel.repaint();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Neu
		if(e.getActionCommand() == "Neu" && !this.Neu)
		{
			resetFlags(); 
			this.Neu = true ; 
			
			//Rückstände löschen
			this.ProjektName = null ; 
			this.MainView = null ; 
			this.View = null ;
			
			//Eingabe anzeigen
			this.FileNameEingeben.setVisible(true);
			this.FileNameIN.setVisible(true);
			
			//Punkte für die View nullen
			this.location1 = null ; 
			this.location2 = null ; 
			this.revalidate();
		}
		//Laden
		if(e.getActionCommand() == "Laden")
		{
			this.FileNameEingeben.setVisible(false);
			this.FileNameIN.setVisible(false);
			this.resetFrame();  
			this.resetFlags();
			this.laden = true ; 
			
		}
		//FileNamen Abrufen
		if(this.Neu && e.getSource().equals(FileNameIN) && e.getActionCommand() != null )
		{
			this.ProjektName = e.getActionCommand() ; 
			this.FileNameIN.setEditable(false);
		}
		
		//Buttons abhören
		if(this.ProjektButtons != null && this.laden)
		{
//			System.out.println("ehhh");
			for(int i = 0 ; i < this.ProjektButtons.length; i++)
			{
				if(e.getActionCommand() == ProjektButtons[i].getText())
				{
//					System.out.println("ehhh");
					this.ProjektName = ProjektButtons[i].getText()  ;
					this.geladen = true ; 
					this.laden = false ; 
					for(int y = 0 ; y < this.ProjektButtons.length ; y++)
					{
						panel.remove(ProjektButtons[y]);
					}
				}
			}
		}
		if(e.getActionCommand() == "Speichern")
		{
			if(this.ProjektName != null && this.getMainRect() != null)
				this.Speichern = true ; 
		}
		if(e.getActionCommand() == "Aufnehmen" && this.MainView != null && this.View != null && this.ProjektName != null)
		{
			this.resetFlags(); 
			this.Aufnahme = true ; 
		}
		if(e.getActionCommand() == "Stopp")
		{
			this.Aufnahme = false ; 
		}
		if(e.getActionCommand() == "Outputs setzen")
		{
			this.resetFlags();
			this.outset = true ; 
		}
		this.requestFocus();
	}
	public void resetFrame()
	{
		this.MainView = null ; 
		this.View = null ; 
		this.ProjektName = null ; 
		this.ProjektButtons = null ; 
	}
	public void resetFlags()
	{
			Neu = false ;
	    	geladen = false ;
	    	laden = false ; 
	    	outset = false ; 
	    	Aufnahme = false ;
	}
	
	public boolean getNeu()
	{
		return this.Neu ; 
	}
	public boolean getLaden()
	{
		return this.laden ; 
	}
	public void setLaden(boolean flag)
	{
		this.laden = flag ; 
	}
	public boolean getgeLaden()
	{
		return this.geladen ; 
	}
	public Rectangle getMainRect()
	{
		return this.MainView ; 
	}
	public void setMainRect(Rectangle rect)
	{
//		System.out.println(rect);
		this.MainView = rect ; 
		this.View = rect ; 
		panel.repaint();
	}
	public void setRect(Rectangle rect)
	{
		this.View = rect ; 
	}
	public void setProjektName(String name)
	{
		this.ProjektName = name ; 
		this.FileNameIN.setText(this.ProjektName);
		this.FileNameIN.setEditable(false);
	}
	public String getProjektName()
	{
		return this.ProjektName ; 
	}
	public boolean getAufnahme()
	{
		return this.Aufnahme ; 
	}
	public void setNeu(boolean stat)
	{
		this.Neu = stat ; 
	}
	public boolean chooseBetween(File[] Projekte)
	{
//		System.out.println("eh");
		if(this.ProjektButtons == null)
		{
			ProjektButtons = new JButton[Projekte.length];
			for(int i = 0 ; i < Projekte.length ; i++)
			{
				ProjektButtons[i] = new JButton(Projekte[i].getName()) ;
				ProjektButtons[i].addActionListener(this);
				this.panel.add(ProjektButtons[i]);
			}
			this.revalidate();
		}
		return geladen ; 
	}
	public boolean getSpeichern()
	{
		return this.Speichern ; 
	}
	public void setSpeichern(boolean flag)
	{
		this.Speichern = flag ; 
	}
	public void setgeLaden(boolean flag)
	{
		this.geladen = flag ; 
	}
	public boolean getOutputSetzen()
	{
		return this.outset ; 
	}
	public boolean addContent(JPanel fremdpanel)
	{
		if(fremdpanel.getParent() == null)
		{
			this.panel.add(fremdpanel, Component.LEFT_ALIGNMENT) ;
			System.out.println("hä" + fremdpanel.getParent() == null );
			this.panel.repaint();
			this.revalidate();
		}
		

		if(fremdpanel.getParent().equals(this)) return true ; 
		else return false ; 
		
	}
}
