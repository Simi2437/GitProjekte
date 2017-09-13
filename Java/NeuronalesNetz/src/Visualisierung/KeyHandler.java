package Visualisierung;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class KeyHandler {
	boolean lookRec = false ;
	FrameHandler frame ; 
	Keylistener key ;
	Mouselistener mouse ;
	Point KlickPos = new Point(0 , 0);
	
	KeyHandler(FrameHandler frame , Keylistener key , Mouselistener mouse)
	{
		this.frame = frame ; 
		this.key = key ; 
		this.mouse = mouse ; 
	}
	
	public void Rectangle()
	{
		lookRec = true ;
	}
	
	
	public void update()
	{
		if(key.Keypressed[KeyEvent.VK_ESCAPE])
		{
			frame.frame.dispose();
		}
		
		
		if(lookRec == true)
		{
			if(mouse.mousetaste[MouseEvent.BUTTON1]);
		}
			
		
	}
	

}
