package Visualisierung;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class KeyHandler {
	
	FrameHandler frame ; 
	Keylistener key ;
	
	KeyHandler(FrameHandler frame , Keylistener key )
	{
		this.frame = frame ; 
		this.key = key ; 
	}
	
	public void update()
	{
		if(key.Keypressed[KeyEvent.VK_ESCAPE])
		{
			frame.frame.dispose();
		}
	}
	

}
