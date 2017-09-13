package Technik;

import java.awt.image.BufferedImage;

public class VideoImage {
	
	BufferedImage Bild ; 
	Long Time ; 
	short dTime ; 
	
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
}
