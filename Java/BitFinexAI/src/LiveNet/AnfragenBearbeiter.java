package LiveNet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AnfragenBearbeiter implements Runnable{
	
	Socket anfrager; 
	boolean stop = false ; 
	
	AnfragenBearbeiter(Socket anfrager)
	{
		this.anfrager = anfrager ; 
	}
	
	public void stopp()
	{
		this.stop = true ; 
	}
	
	@Override
	public void run() {
		
		Scanner in = null ; 
		PrintWriter out = null; 
		try {
			in = new Scanner( anfrager.getInputStream()) ;
			out = new PrintWriter(anfrager.getOutputStream()) ; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		while(!stop)
		{
		}
		
	}
	

}
