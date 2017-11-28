package Engine;

import LiveNet.Server;

public class NetTest {

	public static void main(String[] args) {
		
		Server serv = new Server(); 
		
		Thread test = new Thread(serv) ;
		
		test.start(); 
		
		
	}

}
