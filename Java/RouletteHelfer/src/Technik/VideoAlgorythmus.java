package Technik;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import Technik.VideoObject;

public class VideoAlgorythmus {
	Robot rob ; 
	private Rectangle Bereich ; 
	float Zeit = 0 ; 
	int Zähler = 0 ; 
	List<VideoObject> Video = new ArrayList();
	
	
	public boolean Aufnahme = false ,
					Fertig = false ; 
	
	public VideoAlgorythmus(){}
	public VideoAlgorythmus(Robot rob)
	{
		this.rob = rob ; 
	}
	public void addFrame(VideoObject Bild)
	{
		this.Video.add(Bild);
	}
	public void Bereich(Rectangle rect)
	{
		this.Bereich = rect ; 
	}
	public int getAktObjNbr()
	{
		return this.Zähler ;
	}
	public BufferedImage getAktBufImage(int i)
	{
		if(i <= Zähler)
		return Video.get(i-1).getBufImage();
		else return null ; 
		
	}
	public VideoObject getVO(int i )
	{
		return Video.get(i - 1); 
	}
	public void Aufnahmestart()
	{
		Fertig = false ; 
		Zähler = 0 ; 
		Video.clear();
		this.Aufnahme = true ; 
	}
	public int getFrames()
	{
		return Zähler; 
	}
	public void Aufnahmestopp()
	{
		Fertig = true ; 
		this.Aufnahme = false ; 
	}
	public void update(float Time)
	{
		if(this.Aufnahme)
		{
			this.Zeit += Time ; 
//			System.out.println(Bereich);
			Video.add(new VideoObject(rob.createScreenCapture(Bereich) , Zeit));
			Zähler++; 
		}
	}
	public VideoAlgorythmus getConv()
	{
		
		
		
		return null ; 
	}
}
