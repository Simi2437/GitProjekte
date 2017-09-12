package Engine;

import Grafik.BlackJackZähler;
import Grafik.UniFrame;
import Grafik.UniversalFrame;

public class BlackJack {
	UniFrame Frame;
	BlackJackZähler test = new BlackJackZähler("BlackJack" , null) ;
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
