package GrafikOld;

import java.awt.Rectangle;
import java.awt.Toolkit;

import Technik.EventManager;
import Technik.Keylistener;
import Technik.Mouselistener;
import Technik.RouletteDaten;

interface NeededFkt {
	
	Rectangle ScreenSize =  new Rectangle( Toolkit.getDefaultToolkit().getScreenSize() );
	Keylistener Key = new Keylistener(); 
	Mouselistener Mouse = new Mouselistener(); 
	
	public void setEventManager(EventManager pn);
	public void setRouletteDaten(RouletteDaten Daten);
	public void update(); 
	
}
