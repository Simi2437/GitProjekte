package Grafik;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Technik.FocusFrager;
import Technik.RouletteDaten;


// Laut Simple Math High Low Methode 
//Wert der Einzelnen Fälle
//_________________________
//Anzahl der Decks
//
//=
//<= 0 ---> 1x Einsatz
//<= 1 ---> 2x Einsatz
//<= 2 ---> 4x Einsatz
//<= 3 ---> 8x Einsatz
//<= 4 ---> 9x Einsatz
//
//


public class BlackJackZähler extends superContent implements ActionListener{
	public static final int KartenD = 52 ;
	
	
	FocusFrager Focus = new FocusFrager();
	
	boolean Arbeit = false ; 
	boolean focusFrame = true;
	
	JPanel Cont = new JPanel();
	JPanel Cont1 = new JPanel();
	JPanel Cont2 = new JPanel();
	JPanel Cont3 = new JPanel();
	JPanel Cont4 = new JPanel();
	JPanel ges = new JPanel();
	JPanel Eingabe = new JPanel();
	
	String Aktuel = "Aktuell: " ;	
	String NochID = "Noch im Deck: " ;	
	String Prozent = "Es kommt zu: " ,
			ZBS = "2 bis 6",
			SBN = "7 bis 9",
			Z = "10";	
	
	float DEins = 0,
			DZwei = 0 ,
			DDrei = 0 ,
			DVier = 0 ,
			DFünf = 0 ,
			DSechs = 0 ,
			DSieben = 0 ,
			DAcht = 0 ,
			DNeun = 0 ,
			DZehn = 0 ;
	
	int Eins = 4,
			Zwei = 4 ,
			Drei = 4 ,
			Vier = 4 ,
			Fünf = 4 ,
			Sechs = 4 ,
			Sieben = 4 ,
			Acht = 4 ,
			Neun = 4 ,
			Zehn = 20 ;

	int[] Feld = {0 , Eins , Zwei , Drei , Vier , Fünf, Sechs, Sieben, Acht, Neun, Zehn};
	int[] WNZ = {0 ,0,0,0,0} ;
	int WGutZug = 0; 
	int[] Simu ;
	int ProzentDealer = 0 ,
			ProzentIch = 0 ;
	
	int KD = 0,
			KGekommen = 0 ,
			KZS = 20 ,
			KSN = 12 ,
			KZ = 20 ,
			N = 8 ,
			AZS = 0 ,
			ASN = 0 ,
			AZ = 0 ,
			PZS = 0 ,
			PSN = 0 ,
			PZ = 0; 
	
	JButton Neu = new JButton("Neu");
	JButton Next = new JButton("Next");
	JButton ZweiSechs = new JButton("2 bis 6");
	JButton SiebenNeun = new JButton("7 bis 9");
	JButton Zehn1 = new JButton("10"); 
	
	int WslG = 0;
	int WslD = 0;
	int MeinKaW = 0 ;
	int DealKaW = 0 ; 
	JLabel MeinKW = new JLabel("Mein KartenWert: ");
	JLabel DealerKW = new JLabel("DealerKW: ");
	JTextField MKW = new JTextField();
	JTextField DKW = new JTextField();
	
	
	JLabel EinsL = new JLabel("Asse: " + Eins);
	JLabel ZweiL = new JLabel("Zwei: " + Zwei);
	JLabel DreiL = new JLabel("Drei: " + Drei);
	JLabel VierL = new JLabel("Vier: " + Vier);
	JLabel FünfL = new JLabel("Fünf: " + Fünf);
	JLabel SechsL = new JLabel("Sechs: " + Sechs);
	JLabel SiebenL = new JLabel("Sieben: " + Sieben);
	JLabel AchtL = new JLabel("Acht: " + Acht);
	JLabel NeunL = new JLabel("Neun: " + Neun);
	JLabel ZehnL = new JLabel("Zehn: " + Zehn);
	
	JLabel ZSA = new JLabel(Aktuel + "0"); 
	JLabel SNA = new JLabel(Aktuel + "0") ; 
	JLabel ZA = new JLabel(Aktuel + "0"); 
	
	JLabel ZSG = new JLabel(NochID + "0");
	JLabel SNG = new JLabel(NochID + "0");
	JLabel ZG = new JLabel(NochID + "0");

	JLabel PZSG = new JLabel(Prozent + "0%");
	JLabel PANG = new JLabel(Prozent + "0%");
	JLabel PZG = new JLabel(Prozent + "0%");
	
	public BlackJackZähler(String Name, RouletteDaten Daten) {
		super(Name, Daten);
		KD = this.KartenD * N ;
		KZS = 20 * N ;
		KSN = 12 * N ;
		KZ = 20 * N ;
		
		for(int i = 0 ; i <= Feld.length ; i++ )
		{
			if(i == 0)Feld[i] = 52 *N ;
			if(i < 10 && i > 0)Feld[i]= 4 * N ;
			if(i == 10)Feld[i]= 20 * N ;
		}
		Eins = 4 * N;
		Zwei = 4 * N;
		Drei = 4 * N;
		Vier = 4 * N;
		Fünf = 4 * N;
		Sechs = 4 * N;
		Sieben = 4 * N;
		Acht = 4 * N;
		Neun = 4 * N;
		Zehn = 20 * N;
		addContent();
	}
	
	public void update()
	{
//		System.out.println("test");
//		this.Owner.requestFocus();
		
		if(!this.Arbeit)
		{
			this.Owner.requestFocus();
			this.Arbeit = true ;
		}
		
		if(this.focusFrame)
		{
		if(this.Owner.key.getFlanke(KeyEvent.VK_NUMPAD0)){
			Zehn--;
			Feld[10]-- ;
			Feld[0]--;
//			System.out.println("test");
			NeuBerechnen(); 
		}
		if(this.Owner.key.getFlanke(KeyEvent.VK_NUMPAD1)){
			Eins--;
			Feld[1]-- ;
			Feld[0]--;
			NeuBerechnen(); 
		}
		if(this.Owner.key.getFlanke(KeyEvent.VK_NUMPAD2)){
			Zwei--;
			Feld[2]-- ;
			Feld[0]--;
			NeuBerechnen(); 
		}
		if(this.Owner.key.getFlanke(KeyEvent.VK_NUMPAD3)){
			Drei--;
			Feld[3]-- ;
			Feld[0]--;
			NeuBerechnen(); 
		}
		if(this.Owner.key.getFlanke(KeyEvent.VK_NUMPAD4)){
			Vier--;
			Feld[4]-- ;
			Feld[0]--;
			NeuBerechnen(); 
		}
		if(this.Owner.key.getFlanke(KeyEvent.VK_NUMPAD5)){
			Fünf--;
			Feld[5]-- ;
			Feld[0]--;
			NeuBerechnen(); 
		}
		if(this.Owner.key.getFlanke(KeyEvent.VK_NUMPAD6)){
			Sechs--;
			Feld[6]-- ;
			Feld[0]--;
			NeuBerechnen(); 
		}
		if(this.Owner.key.getFlanke(KeyEvent.VK_NUMPAD7)){
			Sieben--;
			Feld[7]-- ;
			Feld[0]--;
			NeuBerechnen(); 
		}
		if(this.Owner.key.getFlanke(KeyEvent.VK_NUMPAD8)){
			Acht--;
			Feld[8]-- ;
			Feld[0]--;
			NeuBerechnen(); 
		}
		if(this.Owner.key.getFlanke(KeyEvent.VK_NUMPAD9)){
			Neun--;
			Feld[9]-- ;
			Feld[0]--;
			NeuBerechnen(); 
		}
			if (MKW.hasFocus())
			{
//				System.out.println("test");
				this.MKW.setText("");
				this.focusFrame = false ; 
			}
				
			if (this.DKW.hasFocus())
				{
				this.DKW.setText("");
				this.focusFrame = false ; 
				}
			
		}
		if(!this.focusFrame)
		{
			if (this.MKW.hasFocus())
			{
//				System.out.println("test");
				if(this.Owner.key.Keypressed[KeyEvent.VK_ENTER])
				{
					if(!MKW.getText().equals(""))this.MeinKaW = Integer.valueOf( MKW.getText() ); 
					this.focusFrame = true ; 
					this.NeuBerechnen();
					this.Owner.requestFocus();
				}
			}
			if (this.DKW.hasFocus())
			{
				if(this.Owner.key.Keypressed[KeyEvent.VK_ENTER])
				{
					if(!DKW.getText().equals(""))this.DealKaW = Integer.valueOf( DKW.getText() ); 
					this.focusFrame = true ;
					this.NeuBerechnen(); 
					this.Owner.requestFocus();
				}
			}

		}
		
		
		super.update(0);
	}
	
	public void MakeFrame() {
		this.Owner.setContentPane(this);
		this.Owner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.Owner.addKeyListener(this.Owner.key);
		this.Owner.setVisible(true);
		this.Neu.addKeyListener(this.Owner.key);
		this.Next.addKeyListener(this.Owner.key);
		this.Cont.addKeyListener(this.Owner.key);
		this.Cont1.addKeyListener(this.Owner.key);
		this.Cont2.addKeyListener(this.Owner.key);
		this.Cont3.addKeyListener(this.Owner.key);
		this.Cont4.addKeyListener(this.Owner.key);
		this.ges.addKeyListener(this.Owner.key);
		this.Eingabe.addKeyListener(this.Owner.key);
		this.MKW.addKeyListener(this.Owner.key);
		this.DKW.addKeyListener(this.Owner.key);
		this.Owner.requestFocus();
	}

	@Override
	public void addContent() {

		//Links
		Cont2.setLayout(new GridLayout(2 , 1));
		
		Cont1.setLayout(new GridLayout(1,1));
		Cont1.add(Neu);
		Cont1.add(Next); 
		Cont2.add(Cont1);
		
		
		ZweiSechs.addActionListener(this);
		SiebenNeun.addActionListener(this);
		Zehn1.addActionListener(this);
		Neu.addActionListener(this);
		Next.addActionListener(this);
		
		MKW.addFocusListener(Focus);
		
		Cont.setLayout(new GridLayout(3 , 4));
		Cont.add(ZweiSechs);
		Cont.add(ZSA);
		Cont.add(ZSG);
		Cont.add(PZSG);
		Cont.add(SiebenNeun);
		Cont.add(SNA);
		Cont.add(SNG);
		Cont.add(PANG);
		Cont.add(Zehn1);
		Cont.add(ZA);
		Cont.add(ZG);
		Cont.add(PZG);
		Cont2.add(Cont);
		
		//Rechts
		Cont3.setLayout(new GridLayout(10,1));
		Cont3.add(EinsL);
		Cont3.add(ZweiL);
		Cont3.add(DreiL);
		Cont3.add(VierL);
		Cont3.add(FünfL);
		Cont3.add(SechsL);
		Cont3.add(SiebenL);
		Cont3.add(AchtL);
		Cont3.add(NeunL);
		Cont3.add(ZehnL);
		
		//Eingabe
		Eingabe.setLayout(new GridLayout(2,2));
		Eingabe.add(this.MeinKW);
		Eingabe.add(MKW);
		Eingabe.add(this.DealerKW);
		Eingabe.add(DKW);
		
		
		//Links+Rechts
		Cont4.setLayout(new GridLayout(1,2));
		Cont4.add(Cont2);
		Cont4.add(Cont3);
		
		
		//Gesammt
		ges.setLayout(new GridLayout(2,1));
		ges.add(Cont4);
		ges.add(Eingabe);
		
		
		this.add(ges);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		
		
		if(e.getActionCommand() == ZBS)
		{
			KZS-- ; 
			AZS ++ ;
			KD-- ;
			KGekommen++ ;
			NeuBerechnen(); 
		}
		if(e.getActionCommand() == SBN)
		{
			KSN-- ; 
			ASN++;
			KD-- ;
			KGekommen++ ;
			NeuBerechnen(); 
		}
		if(e.getActionCommand() == Z)
		{
			KZ-- ; 
			AZ++ ; 
			KD-- ;
			KGekommen++ ;
			NeuBerechnen(); 
		}
		if(e.getActionCommand() == "Neu")
		{
			KD = this.KartenD * N ;
			KZS = 20 * N ;
			KSN = 12 * N ;
			KZ = 20 * N ;
			AZS = 0 ;
			ASN = 0 ;
			AZ = 0 ;		
			for(int i = 0 ; i <= Feld.length ; i++ )
			{
				if(i == 0)Feld[i] = 52 *N ;
				if(i < 10 && i > 0)Feld[i]= 4 * N ;
				if(i == 10)Feld[i]= 20 * N ;
			}
			NeuBerechnen(); 
		}
		if(e.getActionCommand() == "Next")
		{
			AZS = 0 ;
			ASN = 0 ;
			AZ = 0 ;
			NeuBerechnen(); 
		}
	}
	
	public void NeuBerechnen()
	{
//		System.out.println((int)(KZS*100));
		KZS = Feld[2]+Feld[3]+Feld[4]+Feld[5]+Feld[6] ;
		KSN = Feld[7]+Feld[8]+Feld[9];
		KZ = Feld[10];
		PZS = (int)(KZS*100)/(Feld[0]) ;
		PSN = (KSN*100)/(Feld[0]) ;
		PZ = (Feld[10]*100)/(Feld[0]) ;
		
		//Wslkeiten Für mich
		WGutZug = 0 ;
		for(int A = 1 ; A <= 10 ; A++)
		{
			if(Feld[A]!= 0)
				if(this.MeinKaW + A <= 21)
				{
					this.WGutZug += (Feld[A]*100)/Feld[0] ; 
				}
		}
		System.out.println("Das wird zu: " + WGutZug + "% ein guter Zug");
		
		//Dealer
//		this.createSimu(Feld);
		for(int i = 0 ; i < WNZ.length ; i++)WNZ[i]= 0;
		int SchlagA = 0;
		int SchlagB = 0 ;
		for(boolean fertig= false ; !fertig ; )
		{
			int Durchlauf = 1 ;
			for(int A = 1 ; A <=10 ; A++)
			{
				if(this.Feld[A]!= 0)
				{
					if(this.DealKaW + A >= this.MeinKaW  && this.DealKaW + A<= 21) 
					{
//						SchlagA += Feld[A];
						this.WNZ[1] += (Feld[A]*100)/Feld[0] ;
					}
					else
					{
						Feld[A]--;
						Feld[0]-- ;
						if(this.DealKaW + A<= 21 && this.DealKaW + A < 17)
						{
							for(int B = 1 ; B <=10 ; B++)
							{
								if(this.Feld[B]!= 0)
								{
									if(this.DealKaW + A + B >= this.MeinKaW  && this.DealKaW + A + B<= 21) 
									{
										this.WNZ[2] += ((Feld[A]*100)/(Feld[0] + 1)) * ((Feld[B]*100)/Feld[0])/100 ;
									}
									else
									{
										Feld[B]--;
										if(this.DealKaW + A +  B<= 21 && this.DealKaW + A + B < 17)
										{
											
										}
										Feld[B]++;
									}
									}
							}
						}
						Feld[0]++;
						Feld[A]++;
					}
					}
			}
			System.out.println("Wsl zug 1 " + WNZ[1] + "%" + " TestRechnung: " + this.MeinKaW + "  " + this.DealKaW );
			System.out.println("Wsl zug 2 " + WNZ[2] + "%" + " TestRechnung: " + this.MeinKaW + "  " + this.DealKaW);
			WslD = WNZ[1] + WNZ[2];
			System.out.println(WslD);
			fertig = true ;
		}
		
		
		ZweiL.setText("Zwei: " + Feld[2]);
		 DreiL.setText("Drei: " + Feld[3]);
		 VierL.setText("Vier: " + Feld[4]) ;
		 FünfL.setText("Fünf: " + Feld[5]);
		 SechsL.setText("Sechs: " + Feld[6]);
		 SiebenL.setText("Sieben: " + Feld[7]);
		 AchtL.setText("Acht: " + Feld[8]) ;
		 NeunL.setText("Neun: " + Feld[9]);
		 ZehnL.setText("Zehn: " + Feld[10]);
		
		 ZSA.setText(Aktuel + AZS); 
		 SNA.setText(Aktuel + ASN);
		 ZA.setText(Aktuel + AZ); 

		 ZSG.setText(NochID + KZS); 
		 SNG.setText(NochID + KSN);
		 ZG.setText(NochID + Feld[10]); 

		 PZSG.setText(Prozent + PZS + "%"); 
		 PANG.setText(Prozent + PSN + "%");
		 PZG.setText(Prozent + PZ + "%"); 
	}
	public void createSimu(int[] rein)
	{
		this.Simu = new int[rein.length] ;
		for(int i = 0 ; i <= rein.length ; i++)
		{
			Simu[i] = rein[i] ;
		}
	}
}
