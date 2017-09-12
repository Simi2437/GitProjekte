package Grafik;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Technik.BildUZahl;
import Technik.EventManager;
import Technik.ImagePanel;
import Technik.Keylistener;
import Technik.RouletteDaten;

public class ZahlWahlCont extends superContent implements ActionListener{
	EventManager EM ; 
	private boolean flag = false; 
	int Zahl ; 
	String eingabe = ""; 
	JLabel Frage = new JLabel("Welche Zahl ist das ?") ;
	JTextField Eingabe = new JTextField(20) ; 
	ImagePanel bild = new ImagePanel(null); 
//	Image aktBild = Daten.getAktZahl(); 
	JButton müll = new JButton("Müll"); 
	
	public ZahlWahlCont(String Name , RouletteDaten Daten) {
		super(Name , Daten);
		this.setLayout(new GridLayout(4 , 1));
		this.addContent();
	}

	@Override
	public void MakeFrame() {
//		System.out.println("test");
		this.Owner.setTitle("Wählen Sie die Zahl");
		this.Frage.setText("Welche Zahl ist das ?");
		this.Eingabe.requestFocus();
		this.Owner.setLocation(this.getLocation());
		this.Owner.setSize(this.getSize());
		this.Owner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.Owner.setContentPane(this);
		this.Eingabe.addKeyListener(this.Owner.key);
		bild.setImage(Daten.getAktZahl()/*.getScaledInstance(500, 250, Image.SCALE_FAST)*/);
		this.Owner.pack();
	}
	@Override
	public void addContent() {
		
		this.Frage.setFont(new Font (Font.MONOSPACED , FlowLayout.CENTER , 30));
		this.add(Frage);
		
		this.add(bild); 
		
		this.Eingabe.setFont(new Font (Font.MONOSPACED ,  FlowLayout.CENTER , 30));
		this.add(Eingabe); 
		
		this.müll.setFont(new Font (Font.MONOSPACED ,  FlowLayout.CENTER , 30));
		this.müll.addActionListener(this);
		this.add(müll); 
	}
	
	public void update()
	{
//		System.out.println("wird upgedatet"); 
		if(this.Owner.key.Keypressed[KeyEvent.VK_ENTER])
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
				this.Owner.EM.NextProgramm(EM.Anlernen);
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
		this.Owner.EM.NextProgramm(EventManager.Anlernen); 
		}
//		System.out.println("test");
		this.Owner.repaint();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand() == "Müll")
		{
			flag = true ; 
		}
		
	}
}
