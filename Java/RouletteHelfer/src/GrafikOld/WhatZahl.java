package GrafikOld;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Technik.BildUZahl;
import Technik.ContentPane;
import Technik.EventManager;
import Technik.ImagePanel;
import Technik.Keylistener;
import Technik.RouletteDaten;

public class WhatZahl extends JFrame implements NeededFkt , ActionListener{
	EventManager EM ; 
	private boolean flag = false; 
	int Zahl ; 
	Keylistener key ;
	RouletteDaten Daten;
	String eingabe = ""; 
	JPanel content = new JPanel();
	JLabel Frage = new JLabel("Welche Zahl ist das ?") ;
	JTextField Eingabe = new JTextField(20) ; 
	ImagePanel bild ; 
	Robot rob ; 
	Image akt ; 
	JButton müll = new JButton("Müll"); 
	
	
	public WhatZahl()
	{
		this.addKeyListener(this.Key);
		this.addMouseListener(this.Mouse);
		this.addMouseMotionListener(this.Mouse);
		
		this.setContentPane(content);
		this.setLayout(new BorderLayout());
		this.setLocation(0, 0);
		this.setSize( 400 , 400);
		this.setVisible(true);
		this.requestFocus();
	}
	@SuppressWarnings("deprecation")
	public void ChoosePic(BufferedImage akt)
	{
		//this.akt = akt.getScaledInstance(akt.getWidth() * 3, akt.getHeight() * 3, akt.SCALE_DEFAULT) ; 
		bild = new ImagePanel(akt); 
		this.bild.setVisible(true);
		this.bild.setLocation(0, 0);
		//this.setSize(bild.getSize());
		this.getContentPane().add(bild , BorderLayout.CENTER); 
		
		//System.out.println("wurde initialisiert");
		this.Frage.setFont(new Font (Font.MONOSPACED , FlowLayout.CENTER , 30));
//		this.Frage.setLocation(0, 100);
		this.Frage.setVisible(true);
		this.getContentPane().add(Frage , BorderLayout.NORTH); 
		
		
		this.Eingabe.setFont(new Font (Font.MONOSPACED ,  FlowLayout.CENTER , 30));
		this.Eingabe.addKeyListener(Key);
//		this.Eingabe.setLocation(0, 100);
		this.Eingabe.setVisible(true);
		this.getContentPane().add(Eingabe, BorderLayout.SOUTH); 
		
		this.müll.setFont(new Font (Font.MONOSPACED ,  FlowLayout.CENTER , 30));
		this.müll.setLocation(0, 100);
		this.müll.addActionListener(this);
		this.müll.setVisible(true);
		this.getContentPane().add(müll, BorderLayout.WEST); 
		
//		this.setSize(this.getContentPane().preferredSize());
	}
	
	
	
	public void update()
	{
		System.out.println("" + key.Keypressed[KeyEvent.VK_ENTER]);
		if(this.key.Keypressed[KeyEvent.VK_ENTER])
		{
			//System.out.println("Enter ?");
			this.eingabe = Eingabe.getText() ; 
			
			if(!flag && !this.eingabe.equals(""))
			{
				Zahl = Integer.valueOf(this.eingabe) ;
				Frage.setText("Sie haben " + Zahl + " gewählt.");
				Daten.SaveZahlAs(Zahl); 
				Daten.Gesammelt[Zahl]++ ;
				Eingabe.setText(null);
				this.EM.NextProgramm(EM.Anlernen);
//				System.out.println("" + Daten.Gesammelt[Zahl]);
			}
			this.eingabe = ""; 
		}
		if(flag)
		{
		Frage.setText("Müll");
		Daten.SaveZahlAs(BildUZahl.Müll);
		flag = false ; 
		Daten.Gesammelt[BildUZahl.Müll]++; 
		this.EM.NextProgramm(EM.Anlernen); 
		}
//		this.toFront();
//		Eingabe.requestFocus();
		//System.out.println(this.getContentPane());
		this.repaint();
	}
	@Override
	public void setEventManager(EventManager pn) {
		this.EM = pn ; 
	}
	@Override
	public void setRouletteDaten(RouletteDaten Daten) {
		// TODO Auto-generated method stub
		this.Daten = Daten ; 
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand() == "Müll")
		{
			flag = true ; 
		}
		
		
	}
}
