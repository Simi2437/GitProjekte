package Engine;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import Grafik.Initialisieren;
import Grafik.HauptMenu;
import Grafik.LernTheGame;
import Grafik.UniFrame;
import Grafik.ZahlWahlCont;
import Grafik.ÜberblickCont;
import Technik.BildUZahl;
import Technik.EventManager;
import Technik.RouletteDaten;

public class GrafikSymulation {
	static RouletteDaten RohDaten = new RouletteDaten();
	static EventManager EM = new EventManager(); 
	static Robot rob ;  
	static BufferedImage pic ; 
	static long t1 , t2 , t3 ; 
	static BildUZahl akt ; 
	
	//neuer Content
	static UniFrame Frame ; 
	static HauptMenu HauptMenu ; 
	static Initialisieren InitFrame ; 
	static ÜberblickCont Überblick ; 
	static ZahlWahlCont ZahlWahl ; 
	static LernTheGame Symulation ; 
	
	public static void main(String[] args) {
		
		Init();
		t1 = System.currentTimeMillis() ; 
		while(true)
		{
			t2 = System.currentTimeMillis();
			try {Thread.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
			
			CheckEM();
			Funktionen();
			
			BildAkt(0); 
			
//			System.out.println(t3);
			
			
			t3 = t2 - t1 ; t1 = t2 ; 
		}
		
	}
	private static void Funktionen()
	{
		if(EM.getProgramm() == EM.Anlernen && !EM.getChanged() )
		{
			Frame.update(0);
		}
	}
	
	private static void BildAkt(float time)
	{
		Frame.update(time);
	}
	
	private static void CheckEM()
	{
		if(EM.getChanged())
		{
			switch (EM.getProgramm())
			{
			case 0:
				Frame.chooseContent(HauptMenu.getNbr());
				EM.Changed();
				break ; 
			case 1:
				Frame.chooseContent(InitFrame.getNbr());
				EM.Changed();
				break ; 
			case 4:
				Frame.chooseContent(Symulation.getNbr());
				EM.Changed(); 
				break ; 
			case 3:
				Frame.chooseContent(ZahlWahl.getNbr());
				EM.Changed();
				break; 
				
			}}
	}
	
	private static void Init()
	{
		try {rob = new Robot() ;} catch (AWTException e) {e.printStackTrace();}
		//Erzeugen
		Frame = new UniFrame(); 
		Frame.setEventManager(EM);
		InitFrame = new Initialisieren("InitialFenster" , RohDaten);
		HauptMenu = new HauptMenu("HauptMenü" , RohDaten); 
		Überblick = new ÜberblickCont("Überblick" , RohDaten);
		ZahlWahl = new ZahlWahlCont("ZahlWahl" , RohDaten);
		Symulation = new LernTheGame("Symulation" , RohDaten , rob); 
		//Laden
		Frame.addContent(HauptMenu);
		Frame.addContent(InitFrame);
		Frame.addContent(Überblick);
		Frame.addContent(ZahlWahl);
		Frame.addContent(Symulation);
	}
	
}
