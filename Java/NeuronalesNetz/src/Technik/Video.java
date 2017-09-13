package Technik;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Video {
	
	private List<VideoImage> Vid;
	
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
			VideoImage buf = new VideoImage(Bild , (short)((long)System.currentTimeMillis() - (long)Vid.get(0).Time ) );
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
}
