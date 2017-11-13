package Bitfinex;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class BitfinexAPI {
	
	private String B�rse = "Bitfinex" ; 
	private String MainLinkv1 =  "https://api.bitfinex.com/v1/" ;
	private String MainLinkv2 = "https://api.bitfinex.com/v2/" ; 
	
	URL akturl; 
	InputStream aktstr ; 
	
	private File KursDest = new File("bin/Kurs");
	
	private List<String> allSymbols = new ArrayList() ; 
	
	List<Candle> Kurs = new ArrayList() ; 
	
@SuppressWarnings("deprecation")
//	Timestamps 1s = 1000ms ; 1min = 60000ms
	
//	SimpledayFormat schows time 
	Date botStart = new Date(1510604640000l) ; // 01.01.2017 
	Date TimeHigh = new Date(1510604640000l); 
	Date TimeLow = new Date(1510604640000l) ; 
	
	Calendar c = Calendar.getInstance() ; 
	
	public BitfinexAPI()
	{
		if(!KursDest.exists())
		{
			KursDest.mkdirs() ; 
		}
	}
	
	public void loadCandels()
	{
		
	}
	
	public List<String> getAllCurrencys() throws IOException
	{
		
		akturl = new URL( this.MainLinkv1 + "symbols"); 
		
		aktstr = akturl.openStream() ; 
		
		String symb = new Scanner(aktstr).nextLine(); 

		String str = "t" ; 
		
		for(int i = 0 ; symb.length() > i ; i++)
		{
			

//			System.out.println("Symbole lesen");
			
			if(symb.charAt(i) == '[' || symb.charAt(i) == '"'|| symb.charAt(i) == ',')
			{
				if(str != "t")
					{
//						System.out.println( str );
						allSymbols.add(str) ; 
						str = "t" ; 
					}
			}
			else
			{
				str = str + symb.toUpperCase().charAt(i) ; 
//				System.out.println( str  + " hinzugef�gt");
			}
		}
		
//		for(int x = 0 ; x < allSymbols.size() ; x++)
//		{
//			System.out.println(allSymbols.get(x));
//		}
		
		return allSymbols;
	}
	
	public boolean isOperating() throws IOException
	{
		try {
			akturl = new URL(this.MainLinkv2 + "platform/status" ) ;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		InputStream test = akturl.openStream() ; 
		String testtext = new Scanner(test).nextLine() ;
		
		if(testtext.equals("[1]") )
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
}
