package Bitfinex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import Technik.Downloader;
import Technik.HTMLHelper;

public class BitfinexAPI {
	
	public static final boolean GoDown = false , GoUp = true ; 
	
	private static RandomAccessFile KursSpeicher ;
	private RandomAccessFile Currencys ; 
	
	private String Börse = "Bitfinex" ; 
	private String MainLinkv1 =  "https://api.bitfinex.com/v1/" ;
	private String MainLinkv2 = "https://api.bitfinex.com/v2/" ; 
	
	URL akturl; 
	InputStream aktstr ; 
	
	private static File KursDest = new File("bin/Kurse");
	private File CurenDest = new File("bin/Curencies/Currencies.txt") ; 
	
	private List<String> allSymbols = new ArrayList() ; 
	private List<Candle> readedCandles = new ArrayList();
	
	List<Candle> Kurs = new ArrayList() ; 
	
@SuppressWarnings("deprecation")
//	Timestamps 1s = 1000ms ; 1min = 60000ms

	
//	SimpledayFormat schows time 
	static Date botStart = new Date(1510604640000l) ; // 01.01.2017  1510597440000 
	
	static int Minute = 60000  ;//ms
	int Tag = 86400000 ; //ms
	
	Calendar c = Calendar.getInstance() ; 
	
	public BitfinexAPI(boolean shouldDownload )
	{
		if(!KursDest.exists())
		{
			KursDest.mkdirs() ; 
		}
		
		try {
			this.getAllCurrencys(false) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		List<Downloader> Loader = new ArrayList(); 
		List<Thread> Threads = new ArrayList(); 
		if ( shouldDownload == true )
		{
		for(int i = 0 ; i<allSymbols.size() ; i++)
		{
			Downloader bufd = new Downloader(allSymbols.get(i) , this.getLowestKursFile(allSymbols.get(i)) , this.getNextLowestKursTime(allSymbols.get(i) ), Downloader.GoDown) ; 
//			Downloader bufu = new Downloader(allSymbols.get(i) , this.getHighestKursFile(allSymbols.get(i)) , this.getNextHighestTime(allSymbols.get(i) ), Downloader.GoUp) ;  muss noch überarbeitet werden
			Thread bufThd = new Thread(bufd) ; 
//			Thread bufThu = new Thread(bufu) ;
			Threads.add(bufThd) ;
//			Threads.add(bufThu);
		}
		for(int i = 0 ; i<Threads.size() ; i++)
		{
			Threads.get(i).start();  
		}
		}
	}
	
	public void Download(String Symbol , boolean direction)
	{
		Downloader bufd = new Downloader(Symbol , this.getLowestKursFile(Symbol) , this.getNextLowestKursTime(Symbol), direction) ;
		Thread bufThd = new Thread(bufd) ;
		bufThd.start();
	}
	

	public File getKursFolder()
	{
		return this.KursDest ; 
	}
	
	public List<String> getAllCurrencys(boolean download) throws IOException
	{
		if(!CurenDest.getParentFile().exists()) CurenDest.getParentFile().mkdirs() ; 
		this.Currencys = new RandomAccessFile(this.CurenDest , "rw") ; 
		
		
		
		if(download && (this.Currencys.length() == 0 || ( System.currentTimeMillis() - this.Currencys.readLong() ) > Tag ))  // 86400000 = 1 Tag 
		{
		akturl = new URL( this.MainLinkv1 + "symbols"); 
		
		aktstr = akturl.openStream() ; 
		
		String symb = new Scanner(aktstr).nextLine(); 

		String str = "t" ; 

		this.Currencys.writeLong(System.currentTimeMillis());
		
		for(int i = 0 ; symb.length() > i ; i++)
		{
			if(symb.charAt(i) == '[' || symb.charAt(i) == '"'|| symb.charAt(i) == ','|| symb.charAt(i) == ']')
			{
				if(str != "t")
					{
						allSymbols.add(str) ; 
						this.Currencys.writeBytes(str + System.lineSeparator());
						str = "t" ; 
					}
			}
			else
			{
				str = str + symb.toUpperCase().charAt(i) ; 
			}
		}
		return allSymbols;
		}
		else
		{
			this.Currencys.seek(16);
			
			boolean allStrings = false ; 
			
			if(this.allSymbols.isEmpty())
			while(!allStrings)
			{
				String buf = this.Currencys.readLine() ; 
				
				if(buf != null && !buf.equals(null) && !buf.equals("null") && !buf.equals("") )
				{
//					System.out.println(buf);
					this.allSymbols.add(buf) ; 
				}
				else
				{
					allStrings = true ; 
				}
			}
			
//			System.out.println(this.Currencys.readLine()) ; 
			return this.allSymbols ; 
		}
		
		
	}
	
	public boolean isOperating() throws IOException
	{
		try {
			akturl = new URL("https://api.bitfinex.com/v2/platform/status ") ;
		} catch (MalformedURLException e) {
			return true ;
		} 
		
		InputStream test = akturl.openStream() ; 
		String testtext = new Scanner(test).nextLine() ;
		
		if(testtext.equals("1") )
		{
			test.close();
			return true ;
		}
		else
		{
			test.close();
			System.out.println("Bitfinex in Maintance  " + testtext );
			return false ; 
		}
	}
	
	public static long getNextLowestKursTime(String Symbol)
	{
			File proof = new File(KursDest.getPath()+"/" + Symbol + "/") ;
			
			if(proof.exists()); 
			{
				File[] proofFiles = proof.listFiles() ; 
				
				long LowestValue = botStart.getTime() ; 
				
				if(proofFiles != null && proofFiles.length != 0 )
				{
				for(int y = 0 ; y < proofFiles.length ; y++ )
				{
					String str1 = "" ;
					String str = proofFiles[y].getName() ; 
					
					str1 = str.substring( 0 , str.indexOf(".")); 
					
					if( Long.parseLong(str1) <= botStart.getTime() && Long.parseLong(str1) <= LowestValue && proofFiles[y].length() != 0 )  LowestValue = Long.parseLong(str1); 
					
//					System.out.println(str + "  " + LowestValue);
				}
				
				try 
				{
					KursSpeicher = new RandomAccessFile(KursDest.getPath()+"/" + Symbol + "/" + LowestValue + ".kur" , "r") ;
					
					long Zeiger = 0 ; 
					if(KursSpeicher.length() != 0)
						{
							Zeiger = KursSpeicher.length() - 4 * 5 - 8 ; 
							
							KursSpeicher.seek(Zeiger);
							
							LowestValue = KursSpeicher.readLong() - Downloader.Minute ; 
						}
					
					else
					{
						return botStart.getTime() ; 
					}
					
//					System.out.println("" + LowestValue);
					 
					
				}
				
				catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();} }
			}
			return botStart.getTime();
	}
	
	public File getLowestKursFile(String Symbol)
	{
		File proof = new File(this.KursDest.getPath()+"/" + Symbol + "/") ;

		if(proof.exists())
		{

			File[] proofFiles = proof.listFiles() ; 
			
			long LowestValue = this.botStart.getTime() ; 
			
			if(proofFiles != null && proofFiles.length != 0 )
			{
			for(int y = 0 ; y < proofFiles.length ; y++ )
			{
				String str1 = "" ;
				String str = proofFiles[y].getName() ; 
				
				str1 = str.substring( 0 , str.indexOf(".")); 
				
				if( Long.parseLong(str1) <= this.botStart.getTime() && Long.parseLong(str1) <= LowestValue && proofFiles[y].length() != 0 )  LowestValue = Long.parseLong(str1); 
				
			}
			}
			return new File(this.KursDest.getPath()+"/" + Symbol + "/" + LowestValue +".kur") ; 
		}
		else
		{
			return new File(this.KursDest.getPath()+"/" + Symbol + "/" + this.botStart.getTime() + ".kur") ; 
		}
	
	}

	public File getHighestKursFile(String Symbol)
	{
		File proof = new File(this.KursDest.getPath()+"/" + Symbol + "/") ;

		if(proof.exists())
		{

			File[] proofFiles = proof.listFiles() ; 
			
			long highest = this.botStart.getTime() ; 
			
			if(proofFiles != null && proofFiles.length != 0 )
			{
			for(int y = 0 ; y < proofFiles.length ; y++ )
			{
				String str1 = "" ;
				String str = proofFiles[y].getName() ; 
				
				str1 = str.substring( 0 , str.indexOf(".")); 
				
				if( Long.parseLong(str1) >= this.botStart.getTime() && Long.parseLong(str1) >= highest && proofFiles[y].length() != 0 )  highest = Long.parseLong(str1); 
				
			}
			}
			if(highest != this.botStart.getTime())
				{
					return new File(this.KursDest.getPath()+"/" + Symbol + "/" + highest +".kur") ; 
				}
			else
			{
				return new File(this.KursDest.getPath()+"/" + Symbol + "/" + (highest + Downloader.Minute) +".kur")  ;  
			}
		}
		else
		{
			return new File(this.KursDest.getPath()+"/" + Symbol + "/" + (this.botStart.getTime() + Downloader.Minute) + ".kur") ; 
		}
		
	}
	
	public long getNextHighestTime(String Symbol)
	{
		File proof = new File(this.KursDest.getPath()+"/" + Symbol + "/") ;
		
		if(proof.exists()); 
		{
			File[] proofFiles = proof.listFiles() ; 
			
			long highest = this.botStart.getTime() ; 
			
			if(proofFiles != null && proofFiles.length != 0 )
			{
			for(int y = 0 ; y < proofFiles.length ; y++ )
			{
				String str1 = "" ;
				String str = proofFiles[y].getName() ; 
				
				str1 = str.substring( 0 , str.indexOf(".")); 
				
				if( Long.parseLong(str1) > this.botStart.getTime() && Long.parseLong(str1) > highest && proofFiles[y].length() != 0 )  highest = Long.parseLong(str1); 
				
//				System.out.println(str + "  " + highest);
			}
			
			try 
			{
				if(highest != this.botStart.getTime())
				{
				KursSpeicher = new RandomAccessFile(this.KursDest.getPath()+"/" + Symbol + "/" + highest + ".kur" , "r") ;
				
				long Zeiger = 0 ; 
				if(KursSpeicher.length() != 0)
					{
						Zeiger = KursSpeicher.length() - 4 * 5 - 8 ; 
						
						KursSpeicher.seek(Zeiger);
						
						highest = KursSpeicher.readLong() +  Minute ; 
					}
				
				
				
//				System.out.println("" + highest);
				
				return (highest) ; 
				}
				else
				{
//					System.out.println("es gab keine höhere Datei, so wurde die Zeit : " + (this.botStart.getTime() + Minute));
					return (this.botStart.getTime() + Minute) ; 
				}
			}
			
			catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();} }
		}
		return (this.botStart.getTime() + Minute) ;
	}
	
	
	public void isDateiConstant(File datei , boolean HighLow )
	{
		System.out.println("Dateikonstant wird aufgerufen");
		long lowFile = botStart.getTime() ; 
		long highFile = botStart.getTime() ; 
		
		try {
			if(  HighLow == GoUp)
			{
			RandomAccessFile file = new RandomAccessFile( datei , "r") ;
			
			file.seek(0);
			long Lauflong = file.readLong() ; 
			for(long i = 0 ; i < file.length()/(5*4 + 8); i++)
			{
//				System.out.print("Element " + i + " untersucht mit nummer : " );
				file.seek(0 + i *(5*4+8));
				Long am = file.readLong() ; 
//				System.out.println(" " + am ); 
				
				if(am == Lauflong)
				{
					Lauflong += Minute ; 
				}
				else
				{
					if( am < Lauflong)
					{
						System.out.println(" es gibt Dateien doppelt");
						Lauflong = am ; 
					}
						if(am > Lauflong)
						{
							System.out.println("es Fehlen Dateien");
							Lauflong = am ; 
						}
						
					
				}
			}
			System.out.println(datei + " ist nach oben Konstant. ");
			}
			
			
			//ende der if anweisung für auf 
			
			
			if(  HighLow == GoDown)
			{
			RandomAccessFile file = new RandomAccessFile(datei, "r") ;
			
			long FehlerDoppelt = 0 ;
			file.seek(0);
			long Lauflong = file.readLong() ; 
			
			int Fehler = 0 ; 
			int richtig = 0 ; 
			for(long i = 0 ; i < 1500/*file.length()/(5*4 + 8)*/; i++)
			{
//				System.out.print("Element " + i + " untersucht mit nummer : " );
				file.seek(0 + i *(5*4+8));
				Long am = file.readLong() ; 
//				System.out.println(" " + am ); 
				
				if(am == Lauflong)
				{
					richtig++ ; 
					System.out.println("Konstant bei " + file.readFloat() + "   " + file.readFloat() + "   " + file.readFloat() + "   " + file.readFloat() + "   " + file.readFloat() + "   ");
					Lauflong -= Minute ; 
				}
				else
				{
					Fehler++ ; 
					file.seek(0 + (i - 1)*(5*4+8));
					System.out.println(" Filev= " + file.readLong() );
					System.out.println(" File = " + am + " sollte sein = " + Lauflong + "    f:" + Fehler);
					file.seek(0 + (i + 1)*(5*4+8));
					System.out.println(" Filen= " + file.readLong() );
					if( am < Lauflong)
					{
//						System.out.println("es fehlen dateien ! in datei " + am + " sollte sein " + Lauflong);
						
						Lauflong = am- Minute  ; 
					}
					else
					{
//						System.out.println("Dateien sind doppelt denn in datei " + am + " sollte sein " + Lauflong);
						
						Lauflong = am - Minute; 
					}
					
				}
			}
			System.out.println(datei + " ist nach unten Konstant. ");
			}
			
			//ende der if für go Down
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	public static boolean isDateiConstantalt(String Symbol , boolean HighLow )
	{
		System.out.println("Dateikonstant wird aufgerufen");
		long lowFile = botStart.getTime() ; 
		long highFile = botStart.getTime() ; 
		File proof = new File(KursDest.getPath()+"/" + Symbol + "/")  ; 
		File[] kursFiles = proof.listFiles() ; 
		
		
		try {
			for(int i = 0 ; i < kursFiles.length ; i++) 
			{
				String buf = kursFiles[i].getName().substring(0 , kursFiles[i].getName().indexOf(".") );
				long buflong = Long.parseLong(buf) ; 
				if(buflong > highFile) highFile = buflong ; 
				if(buflong <= lowFile) lowFile = buflong ; 
			}
//			RandomAccessFile HighFile = new RandomAccessFile(proof.getPath() +"/"+ highFile + ".kur" , "r") ; 
			
			if(  HighLow == GoUp)
			{
			RandomAccessFile HighFile = new RandomAccessFile(proof.getPath() +"/"+ (botStart.getTime() + Minute) + ".kur" , "r") ;
			
			long Lauflong = highFile ; 
			for(long i = 0 ; i < HighFile.length()/(5*4 + 8); i++)
			{
//				System.out.print("Element " + i + " untersucht mit nummer : " );
				HighFile.seek(0 + i *(5*4+8));
				Long am = HighFile.readLong() ; 
//				System.out.println(" " + am ); 
				
				if(am == Lauflong)
				{
//					System.out.println("Konstant bei " + Lauflong );
					Lauflong += Minute ; 
				}
				else
				{
					System.out.println("Fehler Zeit File : " + am  + " sollte sein : " + Lauflong + " Das heist : ");
					if( am < Lauflong)
					{
						System.out.println( "mind. " + (1) +" File ist doppelt. ");
						long time = am ; 
						int j = 1 ; 
						while( time != Lauflong)
						{
							HighFile.seek((i + j) * (5*4+8));
							time = HighFile.readLong() ; 
							if(time < Lauflong)
							{
								j++ ; 
							}
							if( time > Lauflong)
							{
								System.out.println("ganz komisch die File dazwischen ist größer");
							}
							if(time == Lauflong)
							{
								System.out.println("nach " +  j + " Zeiten passt wieder alles.");
								
								i = i + j ; 
							}
						}
					}
						if(am > Lauflong)
						{
							System.out.println("Es Fehlt die Zeit von " + Lauflong+ " bis " + am + " das sind " + (am - Lauflong)/60000 + " Minuten");
//							HTMLHelper.getCandles(new URL("https://api.bitfinex.com/v2/candles/trade:1m:tBTCUSD/hist?start=1511052480000&limit=1&sort=1")) ; 
							Lauflong = am + Minute ; 
						}
						
					
				}
			}
			System.out.println(Symbol + " ist nach oben Konstant. ");
			}
			
			
			//ende der if anweisung für auf 
			
			
			if(  HighLow == GoDown)
			{
				System.out.println(proof.getPath() +"/"+ (botStart.getTime()) + ".kur" );
			RandomAccessFile LowFile = new RandomAccessFile(new File (proof.getPath() + "/"  + (botStart.getTime()) + ".kur" ), "r") ;
			
			long FehlerDoppelt = 0 ;
			long Lauflong = lowFile ; 
			for(long i = 0 ; i < LowFile.length()/(5*4 + 8); i++)
			{
//				System.out.print("Element " + i + " untersucht mit nummer : " );
				LowFile.seek(0 + i *(5*4+8));
				Long am = LowFile.readLong() ; 
//				System.out.println(" " + am ); 
				
				if(am == Lauflong)
				{
//					System.out.println("Konstant bei " + Lauflong );
					Lauflong -= Minute ; 
				}
				else
				{
					System.out.println("Fehler Zeit File : " + am  + " sollte sein : " + Lauflong);
					if( am > Lauflong)
					{
						long time = am ; 
						int j = 1 ; 
						while( time >= Lauflong)
						{
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
//							System.out.print("gehe lauflong weiter");
							LowFile.seek((i + j) * (5*4+8));
							time = LowFile.readLong() ; 
							if(time > Lauflong)
							{
								System.out.println( time +" existiert bereits " + FehlerDoppelt++);
								j++ ; 
							}
							if( time < Lauflong)
							{
								LowFile.seek((i + j - 1) * (5*4+8));
								long test = LowFile.readLong() ; 
								System.out.println("ganz komisch die File dazwischen ist größer also die vorherige File " + test + "   sollte sein  " + Lauflong );
							}
							if(time >= Lauflong)
							{
								System.out.println("nach " + (j) + " Zeiten passt wieder alles.");
								
								i = i + j ; 
							}
						}
					}
						if(am < Lauflong)
						{
							System.out.println("Es Fehlt die Zeit von " +  am+ " bis " + Lauflong + " das sind " + (Lauflong-am)/60000 + " Minuten");
							Lauflong = am - Minute ; 
						}
						
					
				}
			}
			System.out.println(Symbol + " ist nach unten Konstant. ");
			}
			
			//ende der if für go Down
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return true ; 
		
	}

	public static void repairFileDown(List<String> Symbol)
	{
		List<Candle> doppelt = new ArrayList(); 
		
		for(int i = 0 ; i < Symbol.size() ; i++)
		{
			try {
				RandomAccessFile oldfile = new RandomAccessFile(new File(KursDest.getPath()+ "/" + Symbol.get(i) + "/" + botStart.getTime()) + ".kur" , "rw" ) ;
				RandomAccessFile neufile = new RandomAccessFile(new File(KursDest.getPath()+ "/" + Symbol.get(i) + "/n" + botStart.getTime()) , "rw" ) ; 
				RandomAccessFile doppelfile = new RandomAccessFile(new File(KursDest.getPath()+ "/" + Symbol.get(i) + "/dp" + botStart.getTime()) , "rw" ) ; 
				
				oldfile.seek(0) ;
				long TimeZeiger = oldfile.readLong() ; 
//				System.out.println(TimeZeiger);
				int Fehler = 0 ; 
				if(neufile.length() == 0 )
				{
				for(int y = 0 ; y < oldfile.length()/(4*5+8) ; y++)
				{
					oldfile.seek(0 + y * (4*5+8));
					Candle Buf = new Candle(oldfile.readLong() , oldfile.readFloat() , oldfile.readFloat() , oldfile.readFloat() ,oldfile.readFloat() , oldfile.readFloat()) ; 
					
					if(Buf.getMTS() == TimeZeiger)
					{
//						System.out.println(" Candle: " + Buf );
						neufile.writeLong(Buf.getMTS());
						neufile.writeFloat(Buf.getopen());
						neufile.writeFloat(Buf.getclose());
						neufile.writeFloat(Buf.gethigh());
						neufile.writeFloat(Buf.getlow());
						neufile.writeFloat(Buf.getvolume());
						TimeZeiger -= Minute ; 
//						System.out.println("hängt");
					}
					else
					{
						if(Buf.getMTS() < TimeZeiger)
						{
							while( (Buf.getMTS()<TimeZeiger ) )
							{
								if(TimeZeiger > Buf.getMTS())
								{
									neufile.writeLong( TimeZeiger );
									neufile.writeFloat(0);
									neufile.writeFloat(0);
									neufile.writeFloat(0);
									neufile.writeFloat(0);
									neufile.writeFloat(0);
									Fehler++ ; 
									TimeZeiger -= Minute ; 
								}
								else
								{
									System.out.println("hängt");
								}
							}

							neufile.writeLong(Buf.getMTS());
							neufile.writeFloat(Buf.getopen());
							neufile.writeFloat(Buf.getclose());
							neufile.writeFloat(Buf.gethigh());
							neufile.writeFloat(Buf.getlow());
							neufile.writeFloat(Buf.getvolume());
							TimeZeiger -= Minute ; 
							
						}
						else
						{
							if(Buf.getMTS() > TimeZeiger)
							{
								doppelfile.writeLong(Buf.getMTS());
								doppelfile.writeFloat(Buf.getopen());
								doppelfile.writeFloat(Buf.getclose());
								doppelfile.writeFloat(Buf.gethigh());
								doppelfile.writeFloat(Buf.getlow());
								doppelfile.writeFloat(Buf.getvolume());
								Fehler++ ; 
							}
						}
					}
				}
				
				System.out.println(Symbol.get(i) + " wurde ausgebessert !");
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	public static void deleteUpperDats(List<String> Symbols)
	{
		for(int i = 0  ; Symbols.size() > i ; i++)
		{
			File delete = new File(KursDest.getPath()+"/" + Symbols.get(i) + "/" + (botStart.getTime() + Minute) + ".kur") ;
			delete.delete() ; 
			System.out.println( Symbols.get(i) + " Erfolgreich gelöscht");
		}
	}
}
