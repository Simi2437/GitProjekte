package Engine;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import Grafik.Initialisieren;
import Grafik.HauptMenu;
import Grafik.UniFrame;
import Grafik.ZahlWahlCont;
import Grafik.�berblickCont;
import Technik.BildUZahl;
import Technik.EventManager;
import Technik.RouletteDaten;

public class V2 {
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
	static �berblickCont �berblick ; 
	static ZahlWahlCont ZahlWahl ; 
	
	public static void main(String[] args) {
		
		Init();
		t1 = System.currentTimeMillis() ; 
		while(true)
		{
			t2 = System.currentTimeMillis();
			try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
			
			CheckEM();
			Funktionen();
			
			BildAkt(0); 
			
			//System.out.println("Routine l�uft");
			
			
			t3 = t2 - t1 ; t1 = t2 ; 
		}
		
	}
	private static void Funktionen()
	{
		if(EM.getProgramm() == EM.Anlernen && !EM.getChanged() )
		{
//			System.out.println(RohDaten.getZahlenRaum());
			pic = rob.createScreenCapture(RohDaten.getZahlenRaum());
			�berblick.setPic(pic);
			RohDaten.setAktZahl(pic);
			akt = RohDaten.doesZahlfit(); 
			if(akt == null)
			{
				try {Thread.sleep(2500);} catch (InterruptedException e) {e.printStackTrace();}
				pic = rob.createScreenCapture(RohDaten.getZahlenRaum());
				�berblick.setPic(pic);
				RohDaten.setAktZahl(pic);
				akt = RohDaten.doesZahlfit(); 
				EM.NextProgramm(EM.ZahlWahl);
			}
			else
			{
				�berblick.AngezeigteZahl(akt.Wert);
			}
			Frame.update(0);
			
		}
		if(EM.getProgramm() == EM.ZahlWahl)
		{
//			System.out.println("test");
			if(Frame.getContentNbr() != ZahlWahl.getNbr())
			{

//				System.out.println("test");
//				 Frame.chooseContent(ZahlWahl.getNbr());
			}
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
			case 2:
				Frame.chooseContent(�berblick.getNbr());
				EM.Changed(); 
				break ; 
			case 3:
				Frame.chooseContent(ZahlWahl.getNbr());
				EM.Changed();
				break; 
			case 4:
				Frame.chooseContent(�berblick.getNbr());
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
		HauptMenu = new HauptMenu("HauptMen�" , RohDaten); 
		�berblick = new �berblickCont("�berblick" , RohDaten);
		ZahlWahl = new ZahlWahlCont("ZahlWahl" , RohDaten);
		//Laden
		Frame.addContent(HauptMenu);
		Frame.addContent(InitFrame);
		Frame.addContent(�berblick);
		Frame.addContent(ZahlWahl);
	}
	
}
