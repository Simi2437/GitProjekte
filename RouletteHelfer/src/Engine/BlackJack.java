package Engine;

import Grafik.BlackJackZ�hler;
import Grafik.UniFrame;
import Grafik.UniversalFrame;

public class BlackJack {
	UniFrame Frame;
	BlackJackZ�hler test = new BlackJackZ�hler("BlackJack" , null) ;
	BlackJack(UniFrame Frame)
	{
		this.Frame =  Frame ;
		Frame.addContent(test);
		Frame.chooseContent(test.getNbr());
		
	}
	public void update()
	{
		test.update();
	}
}
