package Engine;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Grafik.UniFrame;
import Grafik.ZahlWahlCont;
import Grafik.ÜberblickCont;
import GrafikOld.InitFrame;
import GrafikOld.LearnFrame;
import GrafikOld.MainFrame;
import GrafikOld.WhatZahl;
import Technik.BildUZahl;
import Technik.EventManager;
import Technik.Keylistener;
import Technik.RouletteDaten;

public class V1 {
	static RouletteDaten RohDaten = new RouletteDaten();
	static EventManager EM = new EventManager(); 
	static Robot rob ; 
	static MainFrame MainFrame ;
	static InitFrame BereichWahl ; 
	static LearnFrame ManuelStatusFrame ; 
	static WhatZahl MenschenPrüfung ; 
	static BufferedImage pic ; 
	static long t1 , t2 , t3 ; 
	static BildUZahl akt ; 
	
	//neuer Content
	static UniFrame Frame ; 
	static ÜberblickCont Überblick ; 
	static ZahlWahlCont ZahlWahl; 
	
	public static void main(String[] args) {
		init(); 
		
		t1 = System.currentTimeMillis() ; 
		while(true)
		{
			t2 = System.currentTimeMillis();
			try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
			
			
			PrüfeAblauf();
			UpdateFrame(); 
			//System.out.println("Routine läuft");
			
			
			t3 = t2 - t1 ; t1 = t2 ; 
		}
		
	}
	
	private static void UpdateFrame() {
		
		if(MainFrame != null && !EM.getChanged()) MainFrame.update();  
		if(BereichWahl != null && !EM.getChanged() ) BereichWahl.update();
		if(EM.getProgramm() == EM.Anlernen && !EM.getChanged() )
		{
			pic = rob.createScreenCapture(RohDaten.getZahlenRaum());
			Überblick.setPic(pic);
			RohDaten.setAktZahl(pic);
			akt = RohDaten.doesZahlfit(); 
			if(akt == null)
			{
				try {Thread.sleep(2500);} catch (InterruptedException e) {e.printStackTrace();}
				pic = rob.createScreenCapture(RohDaten.getZahlenRaum());
				Überblick.setPic(pic);
				RohDaten.setAktZahl(pic);
				akt = RohDaten.doesZahlfit(); 
				EM.NextProgramm(EM.ZahlWahl);
			}
			else
			{
				Überblick.AngezeigteZahl(akt.Wert);
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
			Frame.update(0);
		}
	}

	private static void PrüfeAblauf() {
		//Prüfe ob eine Aktion erforderlich ist.
		if(EM.getChanged())
		{
			switch (EM.getProgramm())
			{
			case 0:
				MainFrame = new MainFrame();
				MainFrame.setEventManager(EM);
				EM.Changed();
				break ; 
			case 1:
				MainFrame.dispose();
				BereichWahl = new InitFrame(); 
				BereichWahl.setEventManager(EM);
				BereichWahl.setRouletteDaten(RohDaten);
				EM.Changed();
				break ; 
			case 2:
//				System.out.println("test");
				BereichWahl.dispose(); 
				Frame.chooseContent(Überblick.getNbr());
				Überblick.SammlungAnders();
				EM.Changed(); 
				break ; 
			case 3:
//				System.out.println("test");
				Frame.chooseContent(ZahlWahl.getNbr());
				EM.Changed();
				break; 
				
			}}
		
		
		//System.out.println("Escape check");
		if(Keylistener.Keypressed[KeyEvent.VK_ESCAPE])
			System.exit(0); 
	
	}

	public static void init()
	{
		Frame = new UniFrame(); 
		Frame.setEventManager(EM);
		Überblick = new ÜberblickCont("ÜberblicksInfo" , RohDaten);
		ZahlWahl = new ZahlWahlCont("Zahl wählen", RohDaten);
		Frame.addContent(Überblick);
		Frame.addContent(ZahlWahl);
		
		try {rob = new Robot();}catch (AWTException e) {e.printStackTrace();} 
//		Frame = new UniFrame(); 
	}
}
