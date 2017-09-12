package Technik;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

public class Converter {
	public static final int RouletteScheibenTest = 0 ;
//	static final int R = 0 , G = 1 , B = 2 ; 
	static final int RA = 1 , GA = 2 , BA = 3  , SW = 4 , WS = 5; 
//	static long RotAnteil = 0 , GrünAnteil = 0 , BlauAnteil = 0;
	static short[] FarbLeser = new short[6] ;
//	static int[] FarbLeser = new int[3]; 
	static VideoAlgorythmus CVideo = new VideoAlgorythmus(); 
	static VideoObject Bild ; 
	static DataBuffer Buf ;
	static int Width = 0 ;
	static int Height = 0; 
	
	public static void ConvertVideo(VideoAlgorythmus Video , int Methode )
	{
		for(int i = 0 ; i < Video.getFrames() ; i++)
		{
			Bild = new VideoObject(ConvertPic((BufferedImage)Video.getVO(i).getImage() , Methode) , Video.getVO(i).getTime());
		}
	}
	
	public static BufferedImage ConvertPic(BufferedImage Bild , int Methode)
	{ 
		Width = Bild.getWidth() ; 
		Height = Bild.getHeight() ; 
		Buf = Bild.getData().getDataBuffer() ; 
		
		for(int Unten = 0 ; Unten < Height ; Unten++)
		{
			for(int Rechts = 0 ; Rechts < Width  ; Rechts++)
			{
				int Pixel = Rechts + Width * Unten ; 
//				System.out.printf("    " +"%02X" , Buf.getElem((Pixel))); 
			}
			System.out.println("");
		}
		
		return Bild;
	}
	
	public static void ReadColorIn(int Buf)
	{
		FarbLeser[RA] = (short) ((Buf & 0x00FF0000) / 0x10000);
		FarbLeser[GA] = (short) ((Buf &0x0000FF00) / 0x100);
		FarbLeser[BA] = (short) ((Buf& 0x000000FF));
	}
	
	public static String FarbeWsl()
	{
		if(FarbLeser[RA] > FarbLeser[GA] && FarbLeser[RA] > FarbLeser[BA]) return "   Computer sagt Rot ?" ;
		if(FarbLeser[RA] < FarbLeser[GA] && FarbLeser[GA] > FarbLeser[BA]) return "   Computer sagt Grün ?" ;
		if(FarbLeser[BA] > FarbLeser[GA] && FarbLeser[RA] < FarbLeser[BA]) return "   Computer sagt Blau ?" ;
		else return "Funktioniert nicht" ; 
	}
	public static int FarberkennungZiffernBlatt()
	{
		if(FarbLeser[RA] < 50 && FarbLeser[GA] < 50 && FarbLeser[BA] < 50) 
			{
			System.out.println("Schwarz");
			return SW ; 
			}
		if(FarbLeser[RA] > 150 && FarbLeser[GA] < 150 && FarbLeser[BA] < 150)
		{
			System.out.println("Weis");
			return WS ;
		}
		if(FarbLeser[RA] >  FarbLeser[GA] && FarbLeser[RA] > FarbLeser[BA])
		{
			System.out.println("Rot");
			return RA ; 
		}
		else return SW ; 
	}
	
	public static int getPixelFarbe(BufferedImage Bild , int x , int y)
	{
		Width = Bild.getWidth() ; 
		Height = Bild.getHeight() ; 
		Buf = Bild.getData().getDataBuffer() ; 
	
		
		int Pixel = x + Width * y ;

		System.out.printf( "Int: %02X", Buf.getElem(Pixel) );
		System.out.print(" Rot: " + FarbLeser[RA]);
		System.out.print(" Grün: " + FarbLeser[GA]);
		System.out.print(" Blau: " + FarbLeser[BA]);
		System.out.println("______________");
		ReadColorIn(Buf.getElem(Pixel));
		FarberkennungZiffernBlatt();
		Buf.setElem(Pixel , Buf.getElem(Pixel) & 0x00FF0000 ) ;

//		System.out.printf( " %02X", FarbLeser[RotAnteil]);
//		System.out.printf( " %02X", Buf.getElem(Pixel));
		System.out.println();
		return Buf.getElem(Pixel); 
	}
	
}
