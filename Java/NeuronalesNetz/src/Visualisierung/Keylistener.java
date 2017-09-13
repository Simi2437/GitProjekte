package Visualisierung;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keylistener implements KeyListener {

	public static boolean Keypressed[] = new boolean[600];
	public static boolean FlankenKeyPressed[] = new boolean[600];
	boolean Flanke = false ; 
	@Override
	public void keyPressed(KeyEvent e) {
		Keypressed[e.getKeyCode()] = true;
//		if(Flanke)
//		{
//			FlankenKeyPressed[e.getKeyCode()] = false ; 
//			System.out.println("Key false");
//		}
//		if(!Flanke)
//		{
//			System.out.println("Key true");
//			FlankenKeyPressed[e.getKeyCode()] = true;
//		}
	}

	
	public boolean getFlanke(int i)
	{
		if(!Flanke)
			{
//			System.out.println("müsste true sein");
			if(Keypressed[i])Flanke = true ; 
			return Keypressed[i] ; 
			}
		else
			return false ; 
	}
	public void keyReleased(KeyEvent e) {
		Keypressed[e.getKeyCode()] = false;
		
		Flanke = false ; 
	}

	@Override
	public void keyTyped(KeyEvent e) {
//		System.out.println("test");

	}

}
