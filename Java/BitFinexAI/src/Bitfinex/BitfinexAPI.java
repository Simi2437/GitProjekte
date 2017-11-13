package Bitfinex;

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
	
	private String MainLinkv1 =  "https://api.bitfinex.com/v1/" ;
	private String MainLinkv2 = "https://api.bitfinex.com/v2/" ; 
	
	URL akturl; 
	InputStream aktstr ; 
	
	private List<String> allSymbols = new ArrayList() ; 
	
	
@SuppressWarnings("deprecation")
//	Timestamps 1s = 1000ms ; 1min = 60000ms
	
//	SimpledayFormat schows time 
	Date botStart = new Date(1483228800000l) ; // 01.01.2017 
	Date Time1 = new Date(1510185600000l); 
	Date Time2 = new Date(1509580800000l) ; 
	
	Calendar c = Calendar.getInstance() ; 
	
	public BitfinexAPI()
	{
		System.out.println(Time1.toString() + "    " + Time2.toString());
	}
	
	public List
	
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
//				System.out.println( str  + " hinzugefügt");
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
