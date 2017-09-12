package Technik;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class EingabeListener implements KeyListener , MouseMotionListener, MouseListener{
	
	boolean MausKlick[] = new boolean[50];
	boolean TastenDruck[] = new boolean[400];
	Point MausPosition = new Point(0,0); 
	
	EingabeListener(JFrame Owner)
	{
		Owner.addMouseListener(this);
		Owner.addMouseMotionListener(this);
		Owner.addKeyListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		this.MausPosition.setLocation(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.TastenDruck[e.getKeyCode()] = true ; 
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.TastenDruck[e.getKeyCode()] = false; 
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
