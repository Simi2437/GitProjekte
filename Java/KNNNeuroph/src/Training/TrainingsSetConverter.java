package Training;

import java.awt.image.BufferedImage;
import java.io.File;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public class TrainingsSetConverter implements Runnable {
	
	private int width , height , Inputs , Outputs ,StringLength , X , Y ; 
	private char lastChar ; 
	private static double[] Pixel ; 
	
	DataSet Train ; 
	DataSetRow DRow ; 
	float[] BildDaten ; 
	
	
	private File ProjektsDirektory ;
	private File[] Projekte ; 
	
	
	public static BufferedImage getIMGFromDS(DataSetRow row , int width , int height)
	{
		Pixel = row.getInput() ;
		
		BufferedImage bild = new BufferedImage(width , height, BufferedImage.TYPE_INT_RGB);
		
//		zeichnet das Bild 
		for(int x = 0 ; x < width ; x++)
		{
			for(int y = 0 ; y <height ; y++)
			{
				int RGB = 0 ;
				
				RGB += Pixel[3*height*width + 3*x*height + 3*y ] * 0x10000*255 ;  	// = (double)((RGB & 0x00FF0000) / 0x10000)/(double)255 ; 
				RGB += Pixel[3*height*width + 3*x*height + 3*y + 1] * 0x100*255 ; 					//=(double)((RGB &0x0000FF00) / 0x100)/(double)255 ;
				RGB += Pixel[3*height*width + 3*x*height + 3*y + 2] * 255 ;					//=(double)(RGB &0x000000FF) / (double)255 ; 
				
				bild.setRGB(x, y, RGB);
				
			}
		}
//		gibt das Bild zurück 
		return bild ; 
	}
	
	public int getwidth()
	{
		return width; 
	}
	public int getheight()
	{
		return height; 
	}
	public int getX() {
		return X;
	}
	public void setX(int x) {
//		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
//		Y = y;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
