package Technik;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class ScreenCam {
	
	boolean StatAufnahme = false ; 
	Video Vid = new Video();
	private Rectangle LookArea ;
	private Robot Kamera;
	
	ScreenCam(JFrame Master)
	{
		try {Kamera = new Robot( Master.getGraphicsConfiguration().getDevice() );} catch (AWTException e) {e.printStackTrace();}
	}
	
	public void setArea(int x , int y , int Width , int Height)
	{
		this.LookArea = new Rectangle(x , y , Width , Height ) ; 
	}
	
	public BufferedImage shot()
	{
		if(LookArea == null ) 
			{
			System.out.println("keine Area eingegeben.");
			return null ; 
			}
		else return Kamera.createScreenCapture(LookArea);
	}
	public void update()
	{
		if(StatAufnahme)
		{
			Vid.addPic(shot());
		}
	}
	public void startAufnahme()
	{
		StatAufnahme = true ; 
	}
	public void stopAufnahme()
	{
		StatAufnahme = false ;
	}
}
