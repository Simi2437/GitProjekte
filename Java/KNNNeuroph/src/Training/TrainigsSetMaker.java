package Training;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.data.DataSet;

import Technik.ScreenCam;
import Technik.Video;
import Technik.VideoImage;

public class TrainigsSetMaker implements Runnable {
	
	private int x , y ;
	private int inputSize , OutputSize; 
	private int width , height ; 
	private String DateiName ; 
	
//	private List<float> Row = new ArrayList();
	double[] RowInput ; 
	double[] RowErgebnis;
	
	File Projekt ; 
	File Videos ; 
	File Properties ; 
	
	private int HowMuchFrames = 2 ; 
	
	private List<VideoImage> buf = new ArrayList(); 
	
	private int RGB ;
	
	DataSet Training; 
	ArrayList<VideoImage> Vid ; 
	
	public TrainigsSetMaker(ScreenCam Kamera , File Projekt , File Videos , int Output)
	{
		this.Projekt = Projekt ; 
		this.Videos = Videos ; 
		Vid = Kamera.getVideo().getFrames() ;
		this.OutputSize = Output ; 
		RowErgebnis = new double[Output];
		
		x = Kamera.getX() ; 
		y = Kamera.getY();
		width = Kamera.getWidth() ; 
		height = Kamera.getHeight() ; 
		inputSize = this.HowMuchFrames * ( 3* width*height) + (this.HowMuchFrames - 1) ;
		System.out.println("initialisiert Videospeichern");
		
		//LernReihe Output wird erstellt
		for(int z = 0 ; z < this.RowErgebnis.length ; z++)
		{
			this.RowErgebnis[z] = 0 ; 
		}
		
		
		Training = new DataSet( inputSize , RowErgebnis.length ) ;
		
		RowInput = new double[this.inputSize] ; 
	}
	
	public void run() {

		System.out.println("Saver wird gestartet" + Vid.size());
		
		while(true)
		{
//			System.out.println("" + Vid.size());
		//Buffer Füllen und Video verkleinern
		if(!Vid.isEmpty())
		{
			System.out.println("Buffer müsste größer werden " + "VId get:" + Vid.get(0));
			buf.add(Vid.get(0));
			Vid.remove(0); 
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// LernReihe Input wird erstellt
		if(buf.size() == 2)
		{
//			System.out.println("Wieviele Bilder sind im Buffer: " + buf.size());
			for(int nbr = 0 ; nbr < this.HowMuchFrames ; nbr++)
			{
				for(int x = 0 ; x < this.width ; x++)
				{
					for(int y = 0 ; y < this.height ; y++)
					{
						RGB = buf.get(nbr ).getBufferedImage().getRGB(x, y);
						RowInput[ nbr*3*height*width + 3*x*this.height + 3*y ] = (double)((RGB & 0x00FF0000) / 0x10000)/(double)255 ; 
						RowInput[ nbr*3*height*width + 3*x*this.height + 3*y + 1] =(double)((RGB &0x0000FF00) / 0x100)/(double)255 ;
						RowInput[ nbr*3*height*width + 3*x*this.height + 3*y + 2] =(double)(RGB &0x000000FF) / (double)255 ; 
						
//						System.out.println("" + x*height + "   " + y + "          gesammt: "  + (nbr*3*height*width + 3*x*this.height + 3*y )+ "    " + (nbr*3*height*width +3* x*this.height + 3*y+1)  + "    " + (nbr*3*height*width + 3*x*this.height + 3*y+2) );
//						System.out.println("" + x*height + " ");
						
//						System.out.println( nbr*3*height*width + x*this.height + 3*y);
//						System.out.println( nbr*3*height*width + x*this.height + 3*y + 1);
//						System.out.println( nbr*3*height*width + x*this.height + 3*y + 2);
						
//						System.out.print("R: " + RowInput[ nbr*3*height*width + x*this.height + 3*y] + " G: " +  RowInput[ nbr*3*height*width + x*this.height + 3*y + 1] + " B: " + RowInput[ nbr*3*height*width + x*this.height + 3*y + 2] );
//						System.out.println(); 
					}
				}
			}
			
			for(int z = 1 ; z < this.HowMuchFrames ; z++ )
			{
				RowInput[RowInput.length - z] = buf.get(1).getShortTime() ; 
			}
			buf.remove(0) ; 
			
			for(int z = 0 ; z < RowInput.length ; z++)
			{
//				System.out.println("" + RowInput[z]);
			}
			System.out.println("Sollte sein: " + (this.HowMuchFrames * ( 3* width*height) + (this.HowMuchFrames - 1)) + "    Ist: " + RowInput.length + "  KameraFrames:" + Vid.size() + "   Buffer:"+ buf.size());

			//DataSet Schreiben
			this.Training.addRow(RowInput , this.RowErgebnis) ;
			
		}
	}
	}
	public int getBufferSize()
	{
		return this.HowMuchFrames ; 
	}
	public int getInputSize()
	{
		return this.inputSize ; 
	}
	public int getOutputSize()
	{
		return this.OutputSize ; 
	}
	public DataSet getDataSet()
	{
		return this.Training ; 
	}
	public void Save()
	{
		Training.saveAsTxt(this.Videos.getPath() + "/VideoAsTxt.txt", ";" ) ; 
//		System.out.println(this.Videos.getPath() );
//		Training.save(   this.Videos.getPath() + "/Video.txt");
		System.out.println("Video GEspeichert ");
	}
	
}
