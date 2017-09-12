package Engine;

import Simulator.Simulator;

public class RouletteSimulator {
	
	static long T1 ;
	static long T2;
	static float T3 = 0; 
	
	public static void main(String[] args) {
		
		Simulator test = new Simulator();
		
		T1 = System.currentTimeMillis() ; 
		while(true)
		{
			
			try {Thread.sleep(18);} catch (InterruptedException e) {e.printStackTrace();}
			
			test.update(T3); 
			
			T2 = System.currentTimeMillis() ; 
			T3 =  T2 - T1 ; 
//			System.out.println(T3);
			T1 = T2 ; 
		}
	}

}
