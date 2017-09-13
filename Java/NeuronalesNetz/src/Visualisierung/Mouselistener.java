package Visualisierung;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class Mouselistener extends MouseMotionAdapter implements MouseListener{

	public boolean[] mousetaste = new boolean[20];
	public Point movPoint = new Point(0,0);
	
 	public void mouseMoved(MouseEvent e)
 	{
 		movPoint.setLocation(e.getPoint());
 	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mousetaste[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mousetaste[e.getButton()] = false;
	}

}
