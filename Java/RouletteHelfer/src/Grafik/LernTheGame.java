package Grafik;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Technik.Converter;
import Technik.ImagePanel;
import Technik.RouletteDaten;
import Technik.VideoAlgorythmus;
import Technik.VideoObject;

public class LernTheGame extends superContent implements ActionListener, ChangeListener{
	JPanel VideoButtons = new JPanel(); 
	JPanel LernButtons = new JPanel(); 
	float VidTime = 0 ; 
	Graphics2D g ; 
	public static final int KugelLauf = 1 , Ellipse = 2;
	
//	für Ellipse
	boolean EChanged = false ; 
	public Point linkerP , rechterP , hochP , tiefP; 
	int EX , EY , EWidth , EHeight , ECory = 0 , ECorx = 0 , ECorH = 0 , ECorV = 0; 
	JPanel V = new JPanel(),
			VH= new JPanel(),
			HH = new JPanel(),
			H = new JPanel();
	JTextField Vertikal = new JTextField("Vertikal Anpassen"),
			Horizontal = new JTextField("Horizontal Anpassen");
	JButton VertikalP = new JButton("V strecken"),
			VertikalM = new JButton("V stauchen"),
			HorizontalP = new JButton("H strecken"),
			HorizontalM = new JButton("H stauchen");
	JSlider größe = new JSlider();
	float Skalierung = 1 ;
	
	public int Programm = 0; 
	public Point PixelPointer = new Point(0,0); 
	public List<Point> BufPointer = new ArrayList() ;
	JSlider Slider = new JSlider(); 
	
	boolean workdone = false; 
	
	Robot rob ; 
	
	JButton Aufnahme = new JButton("Aufnahme"),
			Stopp = new JButton("Stopp"),
			Vor = new JButton("Vor"),
			Konv = new JButton("Konv"),
			KugelLaufPunkte = new JButton("KugelLaufPunkteSetzen") ,
			LaufEllipse = new JButton("LaufEllipse erstellen"),
			Fertig = new JButton("Fertig");
	
	ImagePanel aktBiAnzeige = new ImagePanel(null , Daten.getVollBild());
	
	VideoObject aktBild ;
	
	
	VideoAlgorythmus Video ; 
	
	public LernTheGame(String Name, RouletteDaten Daten , Robot rob) {
		super(Name, Daten);
		this.setLayout(new GridLayout(2 , 1));
		Video = new VideoAlgorythmus(rob) ; 
		Video.Bereich(this.Daten.getVollBild());
		this.addContent();
	}

	@Override
	public void MakeFrame() {
		this.Owner.dispose();
		this.Owner.setTitle("Simulation");
		this.Owner.addMouseListener(this.Owner.mouse);
		this.Owner.addMouseMotionListener(this.Owner.mouse);
		this.Owner.addKeyListener(this.Owner.key);
		this.Owner.setBackground(this.getBackground());
		this.Owner.setUndecorated(false);
		this.Owner.setLocation(this.getLocation());
//		this.Owner.setSize(this.getSize());
		this.Owner.setContentPane(this);
		this.Owner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.addMouseListener(this.Owner.mouse);
		this.Owner.setSize(500 , 500);
		this.Owner.setVisible(true);

		this.aktBiAnzeige.addKeyListener(this.Owner.key);
		this.aktBiAnzeige.addMouseListener(this.Owner.mouse);
		this.aktBiAnzeige.addMouseMotionListener(this.Owner.mouse);
		this.addMouseListener(this.Owner.mouse);
		this.addMouseMotionListener(this.Owner.mouse);
	}

	@Override
	public void addContent() {
		this.setLayout(new BorderLayout());
		Aufnahme.addActionListener(this);
		Stopp.addActionListener(this);
		Vor.addActionListener(this);
		Konv.addActionListener(this);
		KugelLaufPunkte.addActionListener(this);
		Fertig.addActionListener(this);
		LaufEllipse.addActionListener(this);
		VertikalM.addActionListener(this);
		VertikalP.addActionListener(this);
		HorizontalM.addActionListener(this);
		HorizontalP.addActionListener(this);
		
		
		V.setLayout(new GridLayout(2,1));
		Vertikal.setEditable(false);
		V.add(Vertikal);
		VH.setLayout(new GridLayout(1,2));
		VH.add(VertikalM);
		VH.add(VertikalP);
		V.add(VH);
		
		H.setLayout(new GridLayout(2,1));
		Horizontal.setEditable(false);
		H.add(Horizontal);
		HH.setLayout(new GridLayout(1,2));
		HH.add(HorizontalM);
		HH.add(HorizontalP);
		H.add(HH);
		
		VideoButtons.setLayout(new GridLayout(1,4));
		VideoButtons.add(Aufnahme);
		VideoButtons.add(Stopp);
		VideoButtons.add(Vor);
		VideoButtons.add(Konv);
//		Pan.addMouseListener(this.Owner.mouse);
		this.add(VideoButtons , BorderLayout.NORTH);
		
		LernButtons.setLayout(new GridLayout(4 , 1));
		LernButtons.add(KugelLaufPunkte);
		LernButtons.add(LaufEllipse);
		this.add(LernButtons , BorderLayout.EAST);
		
		this.add(aktBiAnzeige , BorderLayout.CENTER);
//		aktBiAnzeige.addMouseListener(this.Owner.mouse);
		
		this.größe.addChangeListener(this);
		größe.setMaximum(200);
		größe.setMajorTickSpacing(1);
		größe.setValue(100);
		
		Slider.addChangeListener(this);
		Slider.setMinimum(1);
		Slider.setMajorTickSpacing(1);
		this.add(Slider , BorderLayout.SOUTH);
		
		
		
	}
	public void update(float Time )
	{
		super.update(Time);

//		this.Owner.requestFocus();
		

		if(Video.Aufnahme)
		{
		this.VidTime += Time ; 
		Video.update(this.VidTime);
		aktBiAnzeige.setImage(Video.getVO(Video.getAktObjNbr()).getImage());
//		Pan1.repaint();
		this.Slider.setMaximum(Video.getFrames());
		this.Slider.setValue(Video.getFrames());
		}		
		
		switch(Programm)
		{
		case 0 : break ;
		
		case 1 : 
		{
			MakeKugelLine(); 
			try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
			break;
		}
		case 2 :
		{
//			try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
			MakeEllipse();
			break;
		}
		}
	}
	public void MakeEllipse()
	{
		
		if(this.Owner.mouse.movPoint.distanceSq(PixelPointer) != 0 )
		{
//			System.out.println("test");
			this.PixelPointer.setLocation(this.Owner.mouse.movPoint );
//			aktBiAnzeige.setImage(Video.getVO(Slider.getValue()).getImage());
			g = (Graphics2D) this.aktBiAnzeige.getGraphics() ; 
			
			this.aktBiAnzeige.repaint();
		}
		if(this.linkerP == null || this.rechterP == null)
		{
			g.setColor(Color.GREEN);
			g.drawLine(PixelPointer.x, PixelPointer.y - 100, PixelPointer.x, PixelPointer.y + 10);
		}
		else 
		{
			g.setColor(Color.GREEN);
			g.drawLine(PixelPointer.x - 100, PixelPointer.y, PixelPointer.x+10, PixelPointer.y);
		}
		
//		rechten Punkt wählen 
		
		if(this.linkerP != null && this.rechterP == null)
		{
			this.LaufEllipse.setText("Den Rechten Punkt wählen");
			if(this.Owner.mouse.mousetaste[MouseEvent.BUTTON1])
			{
				rechterP = new Point(this.Owner.mouse.movPoint.getLocation());
				this.Owner.mouse.mousetaste[MouseEvent.BUTTON1] = false ;
			}
		}
		
//		linken punkt wählen 
		
		if(linkerP == null)
		{	
//			g.setStroke(new BasicStroke(3));
			this.LaufEllipse.setText("Den Linken Punkt wählen");
			if(this.Owner.mouse.mousetaste[MouseEvent.BUTTON1])
			{
				linkerP = new Point(this.Owner.mouse.movPoint.getLocation());
				this.Owner.mouse.mousetaste[MouseEvent.BUTTON1] = false ;
			}
		}
		else g.drawLine(linkerP.x, linkerP.y - 10, linkerP.x, linkerP.y + 10);
		
//		Oberen Punkt wählen 
		if(this.linkerP != null && this.rechterP != null && this.hochP == null)
		{
			this.LaufEllipse.setText("Den Oberen Punkt wählen");

			if(this.Owner.mouse.mousetaste[MouseEvent.BUTTON1])
			{
				this.hochP = new Point(this.Owner.mouse.movPoint.getLocation());
				this.Owner.mouse.mousetaste[MouseEvent.BUTTON1] = false ;
			}
		}
		
//		Unteren Punkt wählen 
		if(this.linkerP != null && this.rechterP != null && this.hochP != null && this.tiefP == null)
		{
			this.LaufEllipse.setText("Den Unteren Punkt wählen");
			if(this.Owner.mouse.mousetaste[MouseEvent.BUTTON1])
			{
				this.tiefP = new Point(this.Owner.mouse.movPoint.getLocation());
				this.Owner.mouse.mousetaste[MouseEvent.BUTTON1] = false ;
			}
		}
		
//		Bestätigen
		if(this.linkerP != null && this.rechterP != null && this.hochP != null && this.tiefP != null)
		{
			
			this.LaufEllipse.setText("LaufEllipse erstellen");
			LernButtons.remove(this.LaufEllipse);
			
			if(this.Owner.key.Keypressed[KeyEvent.VK_DOWN])
			{
				this.ECory++ ;
			}
			
			if(this.Owner.key.Keypressed[KeyEvent.VK_UP])
			{
				this.ECory-- ;
			}
			if(this.Owner.key.Keypressed[KeyEvent.VK_LEFT])
			{
				this.ECorx-- ;
			}
			if(this.Owner.key.Keypressed[KeyEvent.VK_RIGHT])
			{
				this.ECorx++ ;
			}
			
			this.EWidth = (int)((this.rechterP.x - this.linkerP.x) * this.Skalierung + ECorH);
			this.EHeight = (int)((this.tiefP.y - this.hochP.y) * this.Skalierung + ECorV); 
			this.EX = linkerP.x + ((this.rechterP.x - this.linkerP.x)-EWidth)/2 + ECorx; 
			this.EY = hochP.y + ((this.tiefP.y - this.hochP.y)-EHeight)/2  + ECory;
			
			g.setColor(Color.green);
			g.drawArc(EX,EY,EWidth,EHeight, 0 , 360);
			
			
			if(this.Owner.key.Keypressed[KeyEvent.VK_DOWN]) 
			{
				this.aktBiAnzeige.repaint();
				this.Owner.key.Keypressed[KeyEvent.VK_DOWN] = false ;
				
			}
			if(this.Owner.key.Keypressed[KeyEvent.VK_UP]) 
			{
				this.aktBiAnzeige.repaint();
				this.Owner.key.Keypressed[KeyEvent.VK_UP] = false ;
			}
			if(this.Owner.key.Keypressed[KeyEvent.VK_LEFT]) 
			{
				this.aktBiAnzeige.repaint();
				this.Owner.key.Keypressed[KeyEvent.VK_LEFT] = false ;
			}
			if(this.Owner.key.Keypressed[KeyEvent.VK_RIGHT]) 
			{
				this.aktBiAnzeige.repaint();
				this.Owner.key.Keypressed[KeyEvent.VK_RIGHT] = false ;
			}
			if(this.EChanged)
			{
				this.aktBiAnzeige.repaint();
				this.EChanged = false ; 
			}
		}
		
//		Punkte zeichnen 
		if(this.rechterP != null) g.drawLine(rechterP.x, rechterP.y - 10, rechterP.x, rechterP.y + 10);
		if(this.hochP != null) g.drawLine(hochP.x-10, hochP.y , hochP.x + 10, hochP.y);
		if(this.tiefP != null) g.drawLine(tiefP.x-10, tiefP.y , tiefP.x + 10, tiefP.y);
		
	}
	public void MakeKugelLine()
	{
		if(this.Owner.mouse.mousetaste[MouseEvent.BUTTON1])
		{
			PixelPointer.setLocation(this.Owner.mouse.movPoint);
			this.BufPointer.add(PixelPointer) ; 
			g = (Graphics2D) this.aktBiAnzeige.getGraphics() ;
			if(this.BufPointer.size() == 1)
			{
				g.setColor(Color.GREEN);
				g.fillRect(BufPointer.get(0).x - 3, BufPointer.get(0).y - 3, 6, 6);
			}
			if(this.BufPointer.size() > 1)
			{
				for(int i = 0 ; i < this.BufPointer.size() ; i++)
				{
					g.setColor(Color.GREEN);
					g.fillRect(BufPointer.get(i).x - 3, BufPointer.get(i).y - 3, 6, 6);
//					this.aktBiAnzeige.getGraphics().fillRect(BufPointer.get(i).x - 3, BufPointer.get(i).y - 3, 6, 6);
					if(i + 1 < this.BufPointer.size())
					{
//						g.setFont();
						g.setStroke(new BasicStroke(2));
						g.drawLine(BufPointer.get(i).x, BufPointer.get(i).y, BufPointer.get(i+1).x, BufPointer.get(i+1).y);
						g.fillRect(BufPointer.get(i+1).x - 3, BufPointer.get(i+1).y - 3, 6, 6);
					}
				}
			}
//			if(PixelPointer.x <= this.Daten.getVollBild().getWidth() && PixelPointer.y <= this.Daten.getVollBild().getHeight())Converter.getPixelFarbe(Video.getAktBufImage(Slider.getValue()), PixelPointer.x, PixelPointer.y) ;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
//		System.out.println(e.getActionCommand());
		
		if(e.getActionCommand() == "Aufnahme")
		{
//			System.out.println("test");
			Video.Aufnahmestart();
		}
		if(e.getActionCommand() == "Stopp")
		{
			Video.Aufnahmestopp();
		}
		if(e.getActionCommand() == "Vor")
		{
			if(!Video.Aufnahme)
			{
				Slider.setValue(Slider.getValue() + 1);
			}
		}
		if(e.getActionCommand() == "Konv")
		{
			Video.getVO(Video.getAktObjNbr()).setImage(Converter.ConvertPic( (BufferedImage) Video.getVO(Video.getAktObjNbr()).getImage() , Converter.RouletteScheibenTest) );
		}
		if(e.getActionCommand() == "KugelLaufPunkteSetzen")
		{
			Programm = this.KugelLauf ; 
			LernButtons.removeAll();
			LernButtons.add(Fertig);
			LernButtons.revalidate();
		}
		if(e.getActionCommand() == "Fertig")
		{
			LernButtons.removeAll();
			if(this.Programm == this.KugelLauf)
			{
				LernButtons.add(this.KugelLaufPunkte);
				LernButtons.add(this.LaufEllipse);
			}
			if(this.Programm == this.Ellipse)
			{
				LernButtons.add(this.KugelLaufPunkte);
				LernButtons.add(this.LaufEllipse);
			}
			
			
			Programm = 0 ;
		}
		if(e.getActionCommand() == "LaufEllipse erstellen")
		{
			Programm = this.Ellipse ; 
			LernButtons.removeAll();
			LernButtons.add(Fertig);
			LernButtons.add(V);
			LernButtons.add(H);
		}
		if(e.getActionCommand() == "V strecken")
		{
			ECorV++;
			this.EChanged = true ; 
		}
		if(e.getActionCommand() == "V stauchen")
		{
			ECorV--;
			this.EChanged = true ; 
		}
		if(e.getActionCommand() == "H strecken")
		{
			ECorH++;
			this.EChanged = true ; 
		}
		if(e.getActionCommand() == "H stauchen")
		{
			ECorH--;
			this.EChanged = true ; 
		}
		LernButtons.revalidate();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		if(!Video.Aufnahme && Video.Fertig)
		{
			aktBiAnzeige.setImage(Video.getVO(Slider.getValue()).getImage());
//			System.out.println(Slider.getValue());
		}
		
		this.Skalierung = (float) this.größe.getValue() / (float) 100 ;
		System.out.println(Skalierung);
		
	}

}
