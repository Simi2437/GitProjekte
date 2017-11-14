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
	
	private String Börse = "Bitfinex" ; 
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
		URL bufUrl = null;
		
		try {
			bufUrl = new URL("https://api.bitfinex.com/v2/candles/trade:1m:tBTCUSD/hist");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		
		List<String> bufStr = getElements(bufUrl) ; 
		
		for(int i = 0 ; i < bufStr.size() ; i++)
		{
			System.out.println(bufStr.get(i) + " 1 + i/4 = " + (1+i/6));
		}
		
	}
	
	
	
	
	private List<String> getElements(URL url)
	{
		
		try {
			aktstr = url.openStream() ;
		} catch (IOException e) {
			
			return null ; 
			
//			e.printStackTrace();
			
		} 
		
		List<String> StrList = new ArrayList();
		
//		System.out.println(new Scanner(aktstr).nextLine());
		
		String readedString = new Scanner(aktstr).nextLine(); 
		String buffer = "" ; 
		
		for(int i = 0 ; readedString.length() > i ; i++)
		{
			if(readedString.charAt(i) == '[' || readedString.charAt(i) == '"'|| readedString.charAt(i) == ',' || readedString.charAt(i) == ']')
			{
				if(buffer != "")
					{
						StrList.add(buffer) ; 
						buffer = "" ; 
					}
			}
			else
			{
				buffer = buffer + readedString.charAt(i) ; 
			}
		}
		return  StrList ; 
	}
	
	public List<String> getAllCurrencys() throws IOException
	{
		
		akturl = new URL( this.MainLinkv1 + "symbols"); 
		
		aktstr = akturl.openStream() ; 
		
		String symb = new Scanner(aktstr).nextLine(); 

		String str = "t" ; 
		
		for(int i = 0 ; symb.length() > i ; i++)
		{
			if(symb.charAt(i) == '[' || symb.charAt(i) == '"'|| symb.charAt(i) == ','|| symb.charAt(i) == ']')
			{
				if(str != "t")
					{
						allSymbols.add(str) ; 
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
