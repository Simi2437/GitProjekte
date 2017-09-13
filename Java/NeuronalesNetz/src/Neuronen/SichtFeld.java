package Neuronen;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.List;

public class SichtFeld {
	
	private Dimension dim  ; 
	private long[] Pixels ; 
	private DataBuffer buf ; 
	
	public SichtFeld(int witdh , int height ){
		dim = new Dimension(witdh , height);
	}
	
//	public void setPic(BufferedImage Bild)
//	{
//		buf = Bild.getData().getDataBuffer() ; 
//	}
	
	private short getrotAnteil(long Pix)
	{
		return (short) (Pix & 0x00FF0000 / 0x00010000) ; 
	}
	private short getgruenAnteil(long Pix)
	{
		return (short) (Pix & 0x0000FF00 / 0x00000100) ; 
	}	
	private short getblauAnteil(long Pix)
	{
		return (short) (Pix & 0x000000FF ) ; 
	}
	
	public DataBuffer getSWBuffer(BufferedImage Bild)
	{
		
		buf = Bild.getData().getDataBuffer() ; 
		
		for(int i = 0 ; i < buf.getSize() ; i++)
		{
			//Dunkle Pixel
			if(this.getrotAnteil(buf.getElem(i)) < 200 && this.getgruenAnteil(buf.getElem(i)) < 200 && this.getblauAnteil(buf.getElem(i)) < 250)
			{
				buf.setElem(i, 0);
			}
			else buf.setElem(i, 0x00FFFFFF);
		}
		
		return null ; 
	}
	
}
