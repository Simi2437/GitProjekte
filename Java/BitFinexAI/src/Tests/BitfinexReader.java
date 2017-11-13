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
		
		String url = "https://api.bitfinex.com/v2/ticker/tBTCUSD" ; 
		
		String operation = "platform/status" ; 
		
		URL add = new URL(url) ;
//		Socket s = new Socket(add.getHost() , 80);
		
		
		
		InputStream is = add.openStream() ;
//		OutputStream os = s.getOutputStream() ; 
		
		
		System.out.println(new Scanner(is).nextLine());
		
		is.close();
		
		
	}
}
