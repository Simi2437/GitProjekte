package Engine;

import java.io.File;
import java.io.IOException;

import Fenster.DSRepresenter;
import Fenster.RouletteAufnahme;
import Technik.RouletteDaten;
import Technik.ScreenCam;
import Training.TrainigsSetMaker;

public class Aufnahme {
	
	static long T1 ;
	static long T2; 
	static int T3  ; 
	
	static File VideoSpeicher ; 
	
	static DSRepresenter VisualDS  ;
	
	static RouletteDaten Roulette; 
	static RouletteAufnahme Fenster= new RouletteAufnahme();
	static ScreenCam Kamera = new ScreenCam(Fenster);
	
	
	static TrainigsSetMaker Trainer ;
	
	static Thread TrainerRun  ; 
	static Thread KameraRun = new Thread(Kamera) ; 
	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException, IOException {
		
		
		T1 = System.currentTimeMillis() ; 
		while(true)
		{
			T2 = T1 ; 
			
			Thread.sleep(60);
			
			Fenster.update();
			
			//Roulette Projekt Anlegen 
			if(Fenster.getNeu() && Fenster.getMainRect() != null && Fenster.getProjektName() != null)
			{
				Roulette = new RouletteDaten(Fenster.getProjektName() , Fenster.getMainRect());
				Fenster.setNeu(false);
			}
			//Roulette wurde geLaden
			if(Fenster.getLaden() || Fenster.getgeLaden())
			{
//				System.out.println("eh");
				if(Fenster.chooseBetween(Roulette.getGrundVerzeichnis().listFiles()))
				{
//					System.out.println("eh");
					Roulette = new RouletteDaten(Fenster.getProjektName()) ; 
					Fenster.setMainRect(Roulette.getMainView());
					Fenster.setgeLaden(false) ;
				}
			}
			//Daten Speichern
			if(Fenster.getSpeichern())
			{
				Roulette.saveProjekt();
				Fenster.setSpeichern(false);
			}
			//Aufnehmen 
			if(Fenster.getAufnahme() && Fenster.getMainRect() != null && Fenster.getProjektName() != null && !Kamera.getStatus())
			{
				if(!Kamera.hasArea()) Kamera.setArea(Roulette.getMainView());
				if(!Kamera.getStatus())Kamera.startAufnahme(); 
				
				//Speicherer zuweisen
				if(Trainer == null)
					{
					Trainer = new TrainigsSetMaker(Kamera , Roulette.getProjektName() , Roulette.getVideoFiles() , RouletteDaten.output);
					Roulette.setBufferSize(Trainer.getBufferSize());
					Roulette.saveProjekt();
					TrainerRun = new Thread(Trainer) ;
					}
				
				//Threads Starten
				if(!KameraRun.isAlive()) KameraRun.start();
				System.out.println("Trainner wird gestartet");
				if(!TrainerRun.isAlive()) TrainerRun.start();
			}
			//Aufnahme stoppen
			if(!Fenster.getAufnahme() && Fenster.getMainRect() != null && Fenster.getProjektName() != null && Kamera.getStatus())
			{
//				System.out.println("stoooop");
				Kamera.removeArea();
				Kamera.stopAufnahme();
				if(Kamera.getVideo().isEmpty()) 
				{
					Roulette.addTrainingsSet(Trainer.getDataSet());
					KameraRun.stop();
					TrainerRun.stop();
				}
			}
			//Video Laden
			if(Fenster.getOutputSetzen())
			{
				if(VisualDS == null)VisualDS = new DSRepresenter(Roulette.getMainView().width , Roulette.getMainView().height , Roulette.getStatusStrings() , Roulette.output) ; 
				if(!Fenster.addContent(VisualDS));
			}
			
			
			T1 = System.currentTimeMillis() ; 
			T3 = (int) (T1 - T2) ; 
		}
	}

}
