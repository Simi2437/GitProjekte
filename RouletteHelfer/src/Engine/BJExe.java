package Engine;

import Grafik.UniFrame;

public class BJExe {
	static BlackJack BJ ; 
	static UniFrame test = new UniFrame();
	static long t1 ;
	static long t2 ;
	static long t3 ;
	
	public static void main(String[] args) {
		
		BJ = new BlackJack(test);
		
		t1 = System.currentTimeMillis() ; 
		while(true)
		{
			t2 = System.currentTimeMillis();
			try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
			
			BJ.update();
			test.update(0);
			//System.out.println("Routine läuft");
			
			
			t3 = t2 - t1 ; t1 = t2 ; 
		}
	}

}
