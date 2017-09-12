package Technik;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.List;

public class BildUZahl {
	public static final int Müll = 37 ; 
	private DataBuffer pic ; 
	public BufferedImage Bild; 
	public List<BildUZahl> Bilder = new ArrayList(); 
	public int Wert = 100;
	
	BildUZahl(BufferedImage bild , int Wert)
	{
		if(Wert <= 38 && Wert >= 0)
		{
//			System.out.println("Is die selbe0");
		setBild(bild);
		setWert(Wert);
		}
		else 
		{
			System.out.println(Wert + " ist ungültig. Max. " + 37);
		}
	}
	
	public void setBild(BufferedImage bild)
	{
		if(Bild == null)
			{
			this.Bild = bild ; 
			pic = Bild.getData().getDataBuffer(); 
			}
		else System.out.println( Wert + ": Diese Zahl wurde schon erkannt.");
	}
	public void setWert(int Wert)
	{
		if(this.Wert == 100) this.Wert = Wert ; 
		else System.out.println("Zahl: " + Wert + " ist schon Definiert.");
	}
	
	public boolean Vergleich(BufferedImage Screen)
	{
		DataBuffer Vergleich = Screen.getData().getDataBuffer(); 
		DataBuffer Vergleich1 = Bild.getData().getDataBuffer(); 
		if(pic.getSize() == Vergleich.getSize())
		{
			int total = Vergleich.getSize() , diff = 0 ; 
			
			for(int i = 0 ; i < total; i++)
			{
//				System.out.println(Vergleich.getElem(i) + "  " + Vergleich.getSize());
				if(Vergleich.getElem(i) != Vergleich1.getElem(i)) diff++ ; 
			}
//			System.out.println("" + diff);
			if(diff <= 25) return true ; 
			else return false ; 
				
		}
		else 
		{
			System.out.println("Irgendwas Stimmt an Fits nicht.");
		}
		return false;
	}
	
}
