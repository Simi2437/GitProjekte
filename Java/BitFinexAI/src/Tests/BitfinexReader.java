package Tests;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;

public class BitfinexReader {

	
	static InetAddress adresse;
	
	public static void main(String args[]) throws UnknownHostException, IOException
	{
		URL add = new URL("https://api.bitfinex.com/v2") ;
		Socket s = new Socket(add.getHost() , 80);
		
		InputStream is = s.getInputStream() ;
		OutputStream os = s.getOutputStream() ; 
		
		
		PrintWriter write = new PrintWriter(os);
		Scanner read = new Scanner(is) ;
		
		write.printf("{\r\n" + 
				"  \"event\": \"subscribe\",\r\n" + 
				"  \"channel\": \"ticker\",\r\n" + 
				"  \"symbol\": \"tBTCUSD\"\r\n" + 
				"}");
		
		while(read.hasNextLine())
		{
			System.out.println(read.nextLine()) ;
		}
		
	}
}