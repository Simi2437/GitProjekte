package Technik;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Bitfinex.Candle;

public class HTMLHelper {
	
	static List<Candle> getCandles(URL url)
	{
		List<String> bufStr  = null ; 
		Candle buf  = null ; 
		List<Candle> readedCandles = new ArrayList(); 
		while(bufStr == null)
		{
			bufStr = getElements(url) ; 
			if( bufStr.size() % 6 != 0)
			{
				bufStr = null ; 
			}
			else
			{
			 	for(int i = 0  ; i < bufStr.size()/6 ; i++)
			 	{
			 		
			 		Candle CBuffer = new Candle( Long.parseLong(bufStr.get(0+i*6)) , Float.parseFloat(bufStr.get(1+i*6))  , Float.parseFloat(bufStr.get(2+i*6))  , Float.parseFloat(bufStr.get(3+i*6))  , Float.parseFloat(bufStr.get(4+i*6))  , Float.parseFloat(bufStr.get(5+i*6)) ) ; 
			 		
			 		readedCandles.add(CBuffer) ; 
			 		
			 	}
			}
		}
		
		
		return readedCandles ; 
	}
	
	
	static List<String> getElements(URL url)
	{
		InputStream aktstr ; 
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
		
		if(readedString == null || readedString.equals(""))
		{
			System.out.println("in der HTMLHelper Klasse wurde vom Scanner null oder leerer String übergeben");
			return null ; 
		}
		
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
		try {
			aktstr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if((StrList.size() % 6) == 0 )
			return  StrList ; 
		else
			return null ; 
	}
}
