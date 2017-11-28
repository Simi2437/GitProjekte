package Technik;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Bitfinex.Candle;

public class Downloader implements Runnable {
	
	public static final int Minute = 60000 , Download = 1 , Reparieren = 2 ;
	public static final boolean GoDown = false , GoUp = true; 
	
	int Programm = 0 ; 
	boolean flag = false ; 
	String Symbol = null ; 
	File KursFile = null ;
	File KursFolder = null ; 
	long TimeStart , TimeStop ; 
	boolean Fertig = false ; 
	
	static int Tag = 86400000; 
	
	public Downloader(String Symbol , File KursFile , long TimeFileStart , boolean flag)
	{
		Programm = this.Download ; 
		this.flag = flag ; 
		this.Symbol = Symbol ; 
		this.KursFile = KursFile ; 
		this.TimeStart = TimeFileStart ; 
	}
	public Downloader(String Symbol , File KursFolder ,long MTSstart , long MTSend)
	{
		Programm = this.Reparieren ; 
		this.TimeStart = MTSstart ; 
		this.TimeStop = MTSend ; 
		this.Symbol = Symbol ; 
		this.KursFolder = KursFolder ; 
	}
	public void NetloadCandelsDown() 
	{
		URL bufUrl = null;
		
		
		
		boolean firstTry = true ; 
		
		int counts = 1 ; 
		
		
	 	
	 	if( ! new File(KursFile.getParentFile().getPath()).exists())
	 	{
	 		new File(KursFile.getParentFile().getPath()).mkdirs() ; 
	 	}

	 	RandomAccessFile KursSpeicher = null;
		try {
			KursSpeicher = new RandomAccessFile(KursFile, "rw");
			KursSpeicher.seek(KursSpeicher.length());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
	 	List<Candle> readedCandles = new ArrayList(); 
	 	
	 	if(flag == this.GoUp && TimeStart < System.currentTimeMillis())
	 	{
	 		while(!Fertig && TimeStart < System.currentTimeMillis())
			{
				
	 			try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					

//					System.out.println("Am downloader kommt die Zeit : " + TimeStart);
					
				 	//geortnet von neu oben und alt unten 
					if(KursSpeicher.length() != 0)
				 	{
						bufUrl = new URL("https://api.bitfinex.com/v2/candles/trade:1m:" +  Symbol + "/hist?end=" + this.getLastCandleTime(KursSpeicher)+Minute );
				 	}
					else
					{
						bufUrl = new URL("https://api.bitfinex.com/v2/candles/trade:1m:" +  Symbol + "/hist?end=" + this.TimeStart ) ;
					}
				 	List<String> bufStr = HTMLHelper.getElements(bufUrl) ; 
				 	
				 	if(bufStr != null && bufStr.size()%6 != 0)
				 	{
				 	int ZElemente = bufStr.size() / 6 ; 
				 	
				 	TimeStart = TimeStart + ZElemente * Minute ; 
				 	
				 	//Elemente in Zahlen umwandeln und in Candle Speichern.
				 	for(int i = 0  ; i < bufStr.size()/6 ; i++)
				 	{
				 		
				 		Candle CBuffer = new Candle( Long.parseLong(bufStr.get(0+i*6)) , Float.parseFloat(bufStr.get(1+i*6))  , Float.parseFloat(bufStr.get(2+i*6))  , Float.parseFloat(bufStr.get(3+i*6))  , Float.parseFloat(bufStr.get(4+i*6))  , Float.parseFloat(bufStr.get(5+i*6)) ) ; 
				 		
				 		readedCandles.add(CBuffer) ; 
				 		
				 	}
				 	bufStr.clear();
				 	
				 	for(int i = 0 ; i < readedCandles.size() ; i++)
				 	{
				 		try {
				 			KursSpeicher.writeLong(readedCandles.get(i).getMTS());
				 			KursSpeicher.writeFloat(readedCandles.get(i).getopen());
				 			KursSpeicher.writeFloat(readedCandles.get(i).getclose());
				 			KursSpeicher.writeFloat(readedCandles.get(i).gethigh());
				 			KursSpeicher.writeFloat(readedCandles.get(i).getlow());
				 			KursSpeicher.writeFloat(readedCandles.get(i).getvolume());
				 			
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 	}
					if(readedCandles.size() == 0)
					{
						System.out.println(Symbol + " ist 0 bei t = " + new Date(TimeStart));
						Thread.sleep(600000);
					}
					
					String statString = "" ; 
					
					if(readedCandles.size() - 1 == -1)
					System.out.println("Exeption bei " + Symbol + "   und  " + new Date(TimeStart));
					else
					statString = Symbol + " Up " + " : Es wurden " + readedCandles.size() + " Kerzen aufgezeichnet. Das sind: " + KursSpeicher.length()  + " Byte | " + "    Ausgeführte Anfragen = " + counts +"   Das Entspricht   -->  " + KursSpeicher.length() / (5*4+8)/60/24/30/12 + " Jahre "+ KursSpeicher.length() / (5*4+8)/60/24/30%12 + " Monate " + KursSpeicher.length() / (5*4+8)/60/24%30 + " Tage " + KursSpeicher.length()/(5*4+8)/60%24 + " h " +  KursSpeicher.length()/(5*4+8)%60 + " m " + readedCandles.get(readedCandles.size() - 1); 
//				 	System.out.print(Symbol + " Up " + " : Es wurden " + readedCandles.size() + " Kerzen aufgezeichnet. Das sind: " + KursSpeicher.length()  + " Byte | ");
//					System.out.print( "    Ausgeführte Anfragen = " + counts);
//					System.out.print("   Das Entspricht   -->  " + KursSpeicher.length() / (5*4+8)/60/24/30/12 + " Jahre "+ KursSpeicher.length() / (5*4+8)/60/24/30%12 + " Monate " + KursSpeicher.length() / (5*4+8)/60/24%30 + " Tage " + KursSpeicher.length()/(5*4+8)/60%24 + " h " +  KursSpeicher.length()/(5*4+8)%60 + " m " );
//					System.out.println(readedCandles.get(readedCandles.size() - 1));
					
//					kcoinServlet.addString(statString);
					System.out.println(statString);
					counts++ ; 
				 	readedCandles.clear(); 
				 	
				 	
				 	Thread.sleep(1000);
				 	}
			 	} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				}}
	 	if(this.TimeStart > System.currentTimeMillis() ) this.Fertig = true ;
	 	
	 	if(flag == this.GoDown && !Fertig)
	 	{
		while(!Fertig)
		{
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				
			 	//geortnet von neu oben und alt unten 
				if(KursSpeicher.length() != 0)
			 	{
					bufUrl = new URL("https://api.bitfinex.com/v2/candles/trade:1m:" +  Symbol + "/hist?end=" + (this.getLastCandleTime(KursSpeicher)-Minute) );

//					System.out.println(" er frägt den neuen kurs" )  ; 
			 	}
				else
				{
					bufUrl = new URL("https://api.bitfinex.com/v2/candles/trade:1m:" +  Symbol + "/hist?end=" + this.TimeStart ) ;
				}
			 	List<String> bufStr = HTMLHelper.getElements(bufUrl) ; 
//				System.out.println( " Zahl ist " +bufStr.get(0))  ; 


			 	if(bufStr != null && (bufStr.size()%6) == 0 )
			 	{
//			 		System.out.println("er will starten " + (bufStr != null && (bufStr.size()%6) == 0) );
			 		int ZElemente = bufStr.size() / 6 ; 
			 	
			 		long oldStart = TimeStart ; 
			 		TimeStart = TimeStart - ZElemente * Minute ; 
			 	
			 		//Elemente in Zahlen umwandeln und in Candle Speichern.
			 		int nuller = 0; 
			 		for(int i = 0  ; i < bufStr.size()/6-nuller ; i++)
			 		{
//			 			System.out.println(" ist bufstr gleich oldstart ? " + (Long.parseLong(bufStr.get(0+i*6+nuller*6)) == (this.getLastCandleTime(KursSpeicher) - Minute - i*Minute)));
			 			if( Long.parseLong(bufStr.get(0)) == oldStart ||Long.parseLong(bufStr.get(0+i*6)) == (this.getLastCandleTime(KursSpeicher) - Minute - i*Minute - nuller*Minute ) ) // Pluss minus relevant
			 			{
			 				Candle CBuffer = new Candle( Long.parseLong(bufStr.get(0+i*6)) , Float.parseFloat(bufStr.get(1+i*6))  , Float.parseFloat(bufStr.get(2+i*6))  , Float.parseFloat(bufStr.get(3+i*6))  , Float.parseFloat(bufStr.get(4+i*6))  , Float.parseFloat(bufStr.get(5+i*6)) ) ; 
			 				readedCandles.add(CBuffer) ; 
			 			}
			 			else
			 			{
			 				if(Long.parseLong(bufStr.get(0+i*6)) < this.getLastCandleTime(KursSpeicher) - Minute - i*Minute - nuller * Minute )
			 				{
//			 					System.out.println("Es gibt keine Datei bei " + (this.getLastCandleTime(KursSpeicher) - Minute - i*Minute - nuller*Minute) ); //Pluss Minus relevant
//			 					Thread.sleep(250);
			 					readedCandles.add(new Candle((this.getLastCandleTime(KursSpeicher) - Minute - i*Minute  - nuller*Minute) ,0,0,0,0,0)) ;
			 					nuller++ ; 
			 					i--;
			 				}
			 				else
			 				{
			 					i++ ; 
			 				}
			 			}
			 		
			 		}
			 	bufStr.clear();
			 	
			 	for(int i = 0 ; i < readedCandles.size() ; i++)
			 	{
			 		try {
			 			KursSpeicher.writeLong(readedCandles.get(i).getMTS());
			 			KursSpeicher.writeFloat(readedCandles.get(i).getopen());
			 			KursSpeicher.writeFloat(readedCandles.get(i).getclose());
			 			KursSpeicher.writeFloat(readedCandles.get(i).gethigh());
			 			KursSpeicher.writeFloat(readedCandles.get(i).getlow());
			 			KursSpeicher.writeFloat(readedCandles.get(i).getvolume());
			 			
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
				if(readedCandles.size() == 0)
				{
					System.out.println(Symbol + " ist 0 bei t = " + new Date(TimeStart) + "   " + TimeStart);
					Thread.sleep(600000);
				}
				else
				{
					String statString = Symbol + " Down " + " : Es wurden " + readedCandles.size() + " Kerzen aufgezeichnet. Das sind: " + KursSpeicher.length()  + " Byte | " + "    Ausgeführte Anfragen = " + counts +"   Das Entspricht   -->  " + KursSpeicher.length() / (5*4+8)/60/24/30/12 + " Jahre "+ KursSpeicher.length() / (5*4+8)/60/24/30%12 + " Monate " + KursSpeicher.length() / (5*4+8)/60/24%30 + " Tage " + KursSpeicher.length()/(5*4+8)/60%24 + " h " +  KursSpeicher.length()/(5*4+8)%60 + " m " + readedCandles.get(readedCandles.size() - 1 ) + "nuller = " + nuller ; 
//			 	System.out.print(Symbol + " Down "+" : Es wurden " + readedCandles.size() + " Kerzen aufgezeichnet. Das sind: " + KursSpeicher.length()  + " Byte | ");
//				System.out.print( "    Ausgeführte Anfragen = " + counts);
//				System.out.print("   Das Entspricht   -->  " + KursSpeicher.length() / (5*4+8)/60/24/30/12 + " Jahre "+ KursSpeicher.length() / (5*4+8)/60/24/30%12 + " Monate " + KursSpeicher.length() / (5*4+8)/60/24%30 + " Tage " + KursSpeicher.length()/(5*4+8)/60%24 + " h " +  KursSpeicher.length()/(5*4+8)%60 + " m " );
//				System.out.println(readedCandles.get(readedCandles.size() - 1));
					
//					kcoinServlet.addString(statString);
					System.out.println(statString);
				counts++ ; 
				}
			 	readedCandles.clear(); 
			 	
			 	
			 	Thread.sleep(1000);
			 	}
			 	else
			 	{
//			 		System.out.println(" Strings sind entweder null oder leer" + Symbol);
			 	}
		 	} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			}}
	}
	
	
	public long getLastCandleTime(RandomAccessFile Candles) 
	{
		
		try {
			if(Candles.length() != 0 )
			{
				Candles.seek(Candles.length() - (4*5 + 8));
				long buf = Candles.readLong() ; 
//				System.out.println("" + buf);
				Candles.seek(Candles.length());
				return buf;
			}
			else 
			{
				return this.TimeStart ; 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return 0 ; 
	}
	
	//alt
	public void repairData()
	{
		
	for(long i = this.TimeStart ; i <= this.TimeStop ; i += Minute) 
	{	
	 	try {
			URL bufUrl = new URL("https://api.bitfinex.com/v2/candles/trade:1m:" +  Symbol + "/hist?start=" + i + "&sort=1" );
			
			List<String> bufStr = null ; 
			
			int TimeWaited = 0 ; 
		 	while( bufStr == null  ) 
		 			{
		 				System.out.println("Versucht elemte aus Url zu lesen " + "https://api.bitfinex.com/v2/candles/trade:1m:" +  Symbol + "/hist?start=" + i +"&limit=1" + "&sort=1" );
		 				bufStr = HTMLHelper.getElements(bufUrl) ; 
		 				
		 				if(bufStr == null || bufStr.isEmpty())
		 				{
		 					System.out.println("bufStr ist entweder leer oder null  ");
		 				try {
		 					TimeWaited += Minute ; 
							Thread.sleep(60000);
			 				System.out.println(Symbol + " wartet schon " + TimeWaited / Minute + " Minuten auf den Link mit der Zeit " + i);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		 				}
		 			}
			
		 	Candle CBuffer  = null; 
		 	if(bufStr != null && !bufStr.isEmpty()  )
		 		CBuffer = new Candle( Long.parseLong(bufStr.get(0)) , Float.parseFloat(bufStr.get(1))  , Float.parseFloat(bufStr.get(2))  , Float.parseFloat(bufStr.get(3))  , Float.parseFloat(bufStr.get(4))  , Float.parseFloat(bufStr.get(5)) ) ; 
		 	else
		 	{
		 		System.out.println("Es gibt kein Element bei " + i + " SystemZeit = " + new Date().getTime());
		 	}
		 	if(CBuffer.getMTS() != i)
		 	{
		 		System.out.println(Symbol + " hat bei  " + new Date(i) + "  " + i + " den Falschen Wert zurückgegeben. --->" + CBuffer.getMTS() ) ;
		 	}
		 	
//		 	System.out.println(this.KursFile.getPath() + MTS +  ".kur" );
			RandomAccessFile KursSpeicher = new RandomAccessFile(this.KursFolder.getPath() + "/" + i +  ".kur", "rw");
			
			if(KursSpeicher.length() != 0 )
				{
				System.out.println("Datei " + KursSpeicher.toString() + " Existiert bereits ");
				}
			else
			{
	 			KursSpeicher.writeLong(CBuffer.getMTS());
	 			KursSpeicher.writeFloat(CBuffer.getopen());
	 			KursSpeicher.writeFloat(CBuffer.getclose());
	 			KursSpeicher.writeFloat(CBuffer.gethigh());
	 			KursSpeicher.writeFloat(CBuffer.getlow());
	 			KursSpeicher.writeFloat(CBuffer.getvolume());
	 			System.out.println(" Candle " + new Date(i)+ "   " + i + " wurde erfolgreich nachgeladen. " + KursSpeicher.toString());
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			
			System.out.println(Symbol + " am " + new Date(i) + " existiert nicht. ");
			
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	}


	@Override
	public void run() {
		
		while(true)
		{
			 if( this.Programm == this.Download) this.NetloadCandelsDown(); 
			 
			 if(this.Programm == this.Reparieren )this.repairData() ;
//			 System.out.println("läuft");
			 Thread.currentThread().stop();
		}
		
	}
	
}
