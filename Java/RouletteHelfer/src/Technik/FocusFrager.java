package Technik;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FocusFrager implements FocusListener{
	
	public Object test  ; 
	
	public void focusGained(FocusEvent e) {
		e.getComponent().requestFocus();
	}

	@Override
	public void focusLost(FocusEvent arg0) {
	}

}
