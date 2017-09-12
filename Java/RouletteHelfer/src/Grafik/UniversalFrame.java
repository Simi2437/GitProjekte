package Grafik;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Technik.EventManager;
import Technik.Keylistener;
import Technik.Mouselistener;

public class UniversalFrame extends JFrame implements ComponentListener{
	public Keylistener key = new Keylistener();
	public Color FrameBackground ; 
	public Mouselistener mouse = new Mouselistener();
	public int witchContent = 0 ;
	public EventManager EM ; 
	superContent aktContent ; 
	List<superContent> Contents = new ArrayList(); 
	
	public UniversalFrame()
	{
		this.addKeyListener(key);
		this.addMouseListener(this.mouse);
		this.addMouseMotionListener(mouse);
	}
	public int getContentNbr()
	{
		return aktContent.getNbr(); 
	}
	public void addContent(superContent ContentPane)
	{
//		System.out.println(ContentPane.getNbr());
		ContentPane.setFrame(this); 
		Contents.add(ContentPane); 
		aktContent = ContentPane ; 
	}
	public boolean chooseContent(int aktContNbr)
	{
		for(int i = 0 ; i <= Contents.size() ; i++)
		{
//			System.out.println(i);
//			System.out.println(Contents.isEmpty());
			if(!Contents.isEmpty())
			{
//				System.out.println(Contents.get(i).getNbr());
			if(Contents.get(i).checkNumber(aktContNbr)) 
			{
//				System.out.println("Aktcontent macht Frame");
				aktContent = Contents.get(i) ; 
				aktContent.MakeFrame();
//				this.pack();
//				this.revalidate();
				return true ; 
			}
			}
		}
		return false ; 
	}
	public void update(float time)
	{
		aktContent.update(time);
//		System.out.println("test");
		if(key.Keypressed[KeyEvent.VK_ESCAPE]) System.exit(0); 
	}
	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentMoved(ComponentEvent e) {
		System.out.println("Component moved");
		this.aktContent.setLocation(this.getLocation());
	}
	@Override
	public void componentResized(ComponentEvent arg0) {
		this.aktContent.setPreferredSize(this.getSize());
//		this.requestFocus();
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void setEventManager(EventManager EM)
	{
		this.EM = EM; 
	}
}
