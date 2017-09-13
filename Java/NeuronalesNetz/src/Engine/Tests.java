package Engine;

import Neuronen.SichtFeld;
import Visualisierung.FrameHandler;
import Visualisierung.UnsichtbaresFrame;
import Visualisierung2.studie;


public class Tests {
	
	
	public static SichtFeld auge ; 
	
	
	static long T1 ;
	static long T2; 
	static int T3  ; 
	public static void main(String[] args) throws InterruptedException 
	{
		
		studie test = new studie(); 
		auge = new SichtFeld(test.getWidth() , test.getHeight());
		
		T1 = System.currentTimeMillis() ; 
		while(true)
		{
			T2 = T1 ; 
			Thread.sleep(60);
			
			
			test.update(T3);
			
			
			T1 = System.currentTimeMillis() ; 
			T3 = (int) (T1 - T2) ; 
		}
	}

}
