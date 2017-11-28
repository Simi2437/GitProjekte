package LiveNet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
	
	private ServerSocket server ;
	
	List<AnfragenBearbeiter> anfragen = new ArrayList() ; 
	List<Thread> arbeiter = new ArrayList() ; 
	
	
	public Server()
	{
		try {
			server = new ServerSocket(80) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	
	@Override
	public void run() 
	{
		Socket client ; 
		while(true)
		{
			client = null ; 
		try {
			client = server.accept() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if(client != null)
		{
			anfragen.add(new AnfragenBearbeiter(client)) ; 
			arbeiter.add(new Thread(anfragen.get(anfragen.size() - 1)) ); 
			arbeiter.get(arbeiter.size() - 1).start();  
		}
		
		if(arbeiter.size() > 25)
		{
			arbeiter.get(0);
		}
		}
	} 
	
	

}
