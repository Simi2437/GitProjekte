package Technik;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.List;

import javax.swing.JFrame;

public class ScreenCam implements Runnable{
	
	boolean StatAufnahme = false ; 
	Video Vid = new Video();
	private Rectangle LookArea ;
	private Robot Kamera;
	private DataBuffer alt ;  
	
	
	
	public ScreenCam(JFrame Master)
	{
		try {Kamera = new Robot( Master.getGraphicsConfiguration().getDevice() );} catch (AWTException e) {e.printStackTrace();}
	}
	
	public void setArea(int x , int y , int Width , int Height)
	{
		this.LookArea = new Rectangle(x , y , Width , Height ) ; 
	}
	public void setArea(Rectangle rect)
	{
		this.LookArea = rect ; 
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
	private VideoImage shotVid()
	{
		if(LookArea == null ) 
		{
		System.out.println("keine Area eingegeben.");
		return null ; 
		}
	else 
		{
		return new VideoImage(Kamera.createScreenCapture(LookArea) , System.currentTimeMillis() );
		}
	}
	public void update()
	{
		if(StatAufnahme)
		{
			VideoImage buf = shotVid();
			
			if(!LikeLastFrame(buf))
			{
				Vid.addPic(buf);
//				System.out.println("hä" + Vid.getFrames().size());
			}

//			System.out.println("hä" + Vid.getFrames().size());
			
		}
//		System.out.println("hä" + Vid.getFrames().size() + this.StatAufnahme);
	}
	public boolean LikeLastFrame(VideoImage buf)
	{
		
		BufferedImage buf1 = buf.getBufferedImage() ; 
		DataBuffer neu = buf1.getData().getDataBuffer() ; 
		
		if(alt != null)
		{
		for(int z = 0 ; z < neu.getSize() ; z++)
		{
			if( neu.getElem(z) != alt.getElem(z)) 
				{
				alt = neu ; 
				return false ; 
				}
		}
		}
		if(alt == null)
		{
		alt = neu ; 
		return false ; 
		}
		return true ; 
		
		
		
	}
	
	public void startAufnahme()
	{
		StatAufnahme = true ; 
	}
	public void stopAufnahme()
	{
		StatAufnahme = false ;
	}
	public boolean hasOwner()
	{
		if(Kamera != null)return true;
		else return false ;
	}
	public boolean hasArea()
	{
		if(this.LookArea == null) return false ;
		else return true ; 
	}
	public boolean getStatus()
	{
		return this.StatAufnahme ; 
	}
	public Video getVideo()
	{
		return this.Vid ;
	}
	public int getX()
	{
		return this.LookArea.x ;
	}
	public int getY()
	{
		return this.LookArea.y ;
	}
	public int getWidth()
	{
		return (int) this.LookArea.getWidth() ; 
	}
	public int getHeight()
	{
		return (int) this.LookArea.getHeight() ; 
	}

	@Override
	public void run() {
		while(true)
		{
			this.update();
		}
//		System.out.println("Läuft");
	}
	public void removeArea()
	{
		this.LookArea = null ; 
	}
}
