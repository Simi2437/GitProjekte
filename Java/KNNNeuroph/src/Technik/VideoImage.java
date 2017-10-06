package Technik;

import java.awt.image.BufferedImage;

public class VideoImage {
	
	BufferedImage Bild ; 
	private Long Time ; 
	private short dTime ; 
	
	VideoImage(BufferedImage Bild , Long Time)
	{
		this.Bild = Bild ;  
		this.Time = Time ; 
	}
	VideoImage(BufferedImage Bild , short Time)
	{
		this.Bild = Bild ; 
		this.dTime = Time ; 
	}
	public BufferedImage getBufferedImage()
	{
		return this.Bild ; 
	}
	public long getLongTime()
	{
		return Time ; 
	}
	public void setShortTime(short dTime )
	{
		this.Time = null ; 
		this.dTime = dTime ; 
	}
	public short getShortTime()
	{
		return dTime ; 
	}
}
