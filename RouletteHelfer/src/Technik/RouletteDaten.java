package Technik;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class RouletteDaten {
	public static final int ZahlenMax = 36 ; 
	
	public static Image Zahlenscheibe ; 
	public static int[] Gesammelt = new int[38] ;
	public static int[] ZahlenScheibeGU = {0,26,3,35,12,28,7,29,18,22,9,31,14,20,1,33,16,24,5,10,23,8,30,11,36,13,27,6,34,17,25,2,21,4,19,15,32}; 
	
	List<Rectangle> KugelLaufbahn = new ArrayList(); 
	
	private File Pic ; 
	private Rectangle Zahlen = new Rectangle(0,0,0,0), 
					  Kugel = new Rectangle(0,0,0,0),
					  Scheibe = new Rectangle(0,0,0,0),
					  VollBild = new Rectangle(0,0,0,0); 
	
	BufferedImage aktZahl ;
	
	List<BildUZahl> AlleZahlen = new ArrayList(); 
	
	public RouletteDaten() {
		Pic = new File("src/Bilder/Rad.JPEG"); 
		try {Zahlenscheibe = ImageIO.read(Pic);} catch (IOException e) {e.printStackTrace();}
		for (int i = 0 ; i<= 37 ;i++)
		{
			Gesammelt[1] = 0 ; 
		}
	} 
	//Räume initialisieren
	public void setZahlenRaum (Rectangle Zahlen)
	{
//		System.out.println("testdsssssssssssssssssssssssssssssssssssssssssssssssss");
		this.Zahlen.setBounds(Zahlen);
	}
	public void setKugelRaum (Rectangle Kugel)
	{
		this.Kugel.setBounds(Kugel); 
	}
	public void setVollBild(Rectangle rect)
	{
		this.VollBild.setBounds(rect);
	}
	public Rectangle getVollBild()
	{
		return this.VollBild; 
	}
	public void setScheibeRaum(Rectangle Scheibe)
	{
		this.Scheibe.setBounds(Scheibe);
//		System.out.println(this.getKugelRaum() + "  " + this.getZahlenRaum() + "  " + this.getScheibeRaum());
	}
	public Rectangle getZahlenRaum()
	{
		return Zahlen; 
	}
	public Rectangle getKugelRaum()
	{
		return Kugel; 
	}
	public Rectangle getScheibeRaum()
	{
		return Scheibe; 
	}
	
	
	
	
	//Zahlen Verarbeitung
	public void setAktZahl(BufferedImage picaktZahl)
	{
//		System.out.println("Is die selbe0");
		this.aktZahl = picaktZahl;  
	}
	public Image getAktZahl()
	{
		return (Image)this.aktZahl ; 
	}
	public BildUZahl doesZahlfit()
	{
		for(int i = 0 ; i < AlleZahlen.size() ; i++)
		{
			if(AlleZahlen.get(i).Vergleich(aktZahl)) 
			{
//				System.out.println("Is die selbe0");
				return AlleZahlen.get(i);
			}
		}
		return null; 
	}
	public boolean SaveZahlAs(int Wert)
	{
		if(Wert <= RouletteDaten.ZahlenMax + 1 && Wert >= 0)
		{
		AlleZahlen.add(new BildUZahl(aktZahl , Wert));
		return true ; 
		}
		else
		{
			System.out.println(Wert + " ist Ungültig. Max: " + RouletteDaten.ZahlenMax);
			return false ; 
		}
	}
	
	public void addKugelLauf(Rectangle rec)
	{
		this.KugelLaufbahn.add(rec);
	}
	public Rectangle getKugelLauf(int i)
	{
		return KugelLaufbahn.get(i);
	}
}
