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
	int Z�hler = 0 ; 
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
		return this.Z�hler ;
	}
	public BufferedImage getAktBufImage(int i)
	{
		if(i <= Z�hler)
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
		Z�hler = 0 ; 
		Video.clear();
		this.Aufnahme = true ; 
	}
	public int getFrames()
	{
		return Z�hler; 
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
			Z�hler++; 
		}
	}
	public VideoAlgorythmus getConv()
	{
		
		
		
		return null ; 
	}
}
