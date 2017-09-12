package Technik;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class VideoObject {
	
	BufferedImage Bild ; 
	public float Time ; 
	
	VideoObject(BufferedImage Bild , float Time)
	{
		this.Bild = Bild ; 
		this.Time = Time ; 
	}
	public void setImage(BufferedImage bild)
	{
		this.Bild = bild ; 
	}
	public BufferedImage getBufImage()
	{
		return this.Bild ; 
	}
	
	public Image getImage()
	{
		return (Image)this.Bild ; 
	}
	public float getTime()
	{
		return this.Time ; 
	}
}
