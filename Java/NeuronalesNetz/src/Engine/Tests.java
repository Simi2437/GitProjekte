package Engine;

import Visualisierung.FrameHandler;
import Visualisierung.UnsichtbaresFrame;

public class Tests {
	
	public static void main(String[] args) 
	{
		FrameHandler test = new FrameHandler();
		test.showTransparent();
		
		
		
		while(true)
		{
			test.update();
		}
	}

}
