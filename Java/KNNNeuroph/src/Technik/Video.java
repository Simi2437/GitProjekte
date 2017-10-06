package Technik;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Video {
	
	static int Frames  = 0 ;
	private ArrayList<VideoImage> Vid;
	private long LastPicTime = 0 ; 
	
	Video()
	{
		 Vid = new ArrayList() ; 
	}
	
	public void addPic(BufferedImage Bild)
	{
		if(Vid.isEmpty())
		{
			VideoImage buf = new VideoImage(Bild , System.currentTimeMillis());
			Vid.add(buf);
		}
		else
		{
			VideoImage buf = new VideoImage(Bild , (short)((long)System.currentTimeMillis() - (long)Vid.get(0).getLongTime() ) );
		}
	}
	public void addPic(VideoImage Bild)
	{
		if(Vid.isEmpty())
		{
			Vid.add(Bild);
			LastPicTime = Bild.getLongTime() ; 
		}
		else
		{
			Bild.setShortTime(   (short) (Bild.getLongTime() - LastPicTime));
			LastPicTime += Bild.getShortTime() ; 
			Vid.add( Bild ) ; 
		}
	}
	public int getVFrames()
	{
		return Vid.size() ; 
	}
	
	public BufferedImage getFrame(int i )
	{
		return Vid.get(i - 1).getBufferedImage() ;
	}
	public BufferedImage getLastFrame()
	{
		return Vid.get(Vid.size() - 1).getBufferedImage() ; 
	}
	public ArrayList<VideoImage> getFrames()
	{
		return this.Vid ; 
	}
	public boolean isEmpty()
	{
		return this.Vid.isEmpty() ; 
	}
}
