package Technik;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.neuroph.core.data.BufferedDataSet;
import org.neuroph.core.data.DataSet;

import Training.TrainingsSetConverter;

public class RouletteDaten {
	private Rectangle MainView ;
	
//	private String RouletteName ;
	private static File GrundVerzeichnis = new File("src/Projekte") ;
	private File ProjektName ;
	private File VideoFiles ; 
	private File Properties ; 
	
	private String PropName = "prop.txt" ;
	
	public static int[] Gesammelt = new int[38] ;
	public static int[] ZahlenScheibeGU = {0,26,3,35,12,28,7,29,18,22,9,31,14,20,1,33,16,24,5,10,23,8,30,11,36,13,27,6,34,17,25,2,21,4,19,15,32}; 
	
	//Daten über Trainingsdaten
	public static int output = 40 , input , bufferSize; 
	// DatenSet für KNN 
	private DataSet allData; 
	
	public RouletteDaten(String Projekt , Rectangle Rect)
	{
		//ProjektPath setzen
		this.ProjektName = new File(this.GrundVerzeichnis.getPath() + "/" + Projekt) ; 
		//DatenPath setzen
		setVideoFiles(new File( this.getGrundVerzeichnis() +"/"+ Projekt + "/VideoFiles"));
		setProperties(new File(this.getGrundVerzeichnis() +"/"+ Projekt + "/Properties")); 
		//DatenPaths erzeugen
		this.getVideoFiles().mkdirs() ; 
		this.getProperties().mkdirs(); 
		this.setProperties(new File(getProperties().getPath() + "/" + PropName));
		//MainView initialisieren
		this.MainView = Rect ; 
	}
	public RouletteDaten(String Projekt) throws FileNotFoundException
	{
		this.loadProjekt(Projekt);
	}
	public void loadProjekt(String Projekt) throws FileNotFoundException
	{
		this.ProjektName = new File(this.GrundVerzeichnis.getPath() + "/" + Projekt) ; 
		if(this.ProjektName.exists())
		{
			setVideoFiles(new File(this.getGrundVerzeichnis() + "/" + Projekt + "/VideoFiles"));
//			System.out.println("Video Files" + this.getVideoFiles().getPath());
			setProperties(new File(this.getGrundVerzeichnis() + "/" + Projekt + "/Properties" + "/" + this.PropName)); 
			
			Scanner Sc = new Scanner(this.getProperties()) ;
			String rectP = Sc.nextLine() ; 
			
			input = Integer.parseInt(rectP.substring(rectP.indexOf("I:") + 2 , rectP.indexOf("O:"))) ;
			output = Integer.parseInt(rectP.substring(rectP.indexOf("O:")+2 , rectP.length())) ; 
			
			
			int x = Integer.parseInt(rectP.substring(rectP.indexOf("X:") + 2 , rectP.indexOf("Y"))   ); 
			int y = Integer.parseInt(rectP.substring(rectP.indexOf("Y:") + 2 , rectP.indexOf("W"))   );
			int width = Integer.parseInt(rectP.substring(rectP.indexOf("W:") + 2 , rectP.indexOf("H"))   );
			int height = Integer.parseInt(rectP.substring(rectP.indexOf("H:") + 2 , rectP.indexOf("EMV") )   );
			this.setMainview(new Rectangle(x,y,width,height));
		}
		this.loadAllTrainingsData(); 
	}
	public void saveProjekt() throws IOException
	{
		if(this.ProjektName != null)
		{
			if(!this.Properties.exists())try { Properties.createNewFile() ;} catch (IOException e) {e.printStackTrace();} 
			
			String MainViewP = "MV:X:" + this.MainView.x + "Y:" + this.MainView.y + "W:" + this.MainView.width + "H:" + this.MainView.height + "EMV" ;
			String Input = "" ; 
			if(bufferSize != 0)Input = "I:" + this.bufferSize * ( 3* MainView.width*MainView.height) + (this.bufferSize - 1);
			String Output = "O:" + this.output ; 
			
			
			FileWriter FW = new FileWriter(this.Properties);
			FW.write(MainViewP + Input + Output);
			FW.close();
		}
	}
	private void loadAllTrainingsData()
	{
		for(int i = 0 ; i<this.VideoFiles.listFiles().length ; i++)
		{
			if(i == 0)
			{
				this.allData = DataSet.load(this.VideoFiles.listFiles()[i].getPath()) ; 
			}
			else
			{
				DataSet buf = DataSet.load(this.VideoFiles.listFiles()[i].getPath());
				for(int y = 0 ; y < buf.getRows().size() ; y++)
				{
					this.allData.addRow(buf.getRowAt(y)) ; 
				}
			}
//			System.out.println(this.allData.getRows().size());
		}
		
		System.out.println("Daten wurden geladen.");
		
	}
	public void addTrainingsSet(DataSet data)
	{
		
		for(int i = 0 ; i<data.size() ; i++)
		{
			for(int y = 0 ; y<data.getInputSize() ; y++)
			{
				if(data.getRowAt(i).getInput()[y] != data.getRowAt(i + 1).getInput()[y])
				{
					System.out.println("Reihe unterscheidet sich in row: " + y + "");
				}
						
			}
		}
		
		
		data.saveAsTxt(this.VideoFiles.getPath() + "/Video" + this.VideoFiles.listFiles().length + ".tset" , ";");
		
		data.clear();
		System.out.println("Video wurde Gespeichert,  RestDateien:" + data.size());
		this.loadAllTrainingsData(); 
	}
	public void setBufferSize(int i)
	{
		this.bufferSize = i ; 
	}
	public File getProjektName()
	{
		return this.ProjektName ; 
	}
	
	public void setMainview(Rectangle rect)
	{
		this.MainView = rect ;
	}
	public Rectangle getMainView()
	{
		return this.MainView; 
	}
	public File getVideoFiles() {
		return VideoFiles;
	}
	private void setVideoFiles(File videoFiles) {
		VideoFiles = videoFiles;
	}
	public File getProperties() {
		return Properties;
	}
	private void setProperties(File properties) {
		Properties = properties;
	}
	public static File getGrundVerzeichnis() {
		return GrundVerzeichnis;
	}
	private void setGrundVerzeichnis(File grundVerzeichnis) {
		GrundVerzeichnis = grundVerzeichnis;
	}
	public String[] getStatusStrings()
	{
		String[] outNames = new String[40];
		
		for(int i = 0 ; i <= 36 ; i++)
		{
			outNames[i] = "" + i ; 
		}
		
		outNames[37] = "Kugel liegt" ; 
		outNames[38] = "Dealer hat Kugel" ; 
		outNames[39] = "Kugel Läuft" ; 
		
		return outNames ; 
	}
	public BufferedImage getImage(int nbr)
	{
//		System.out.println(this.MainView.height);
//		System.out.println( this.MainView.width);
//		System.out.println(this.allData.size());
//		System.out.println( " WTF :: " + this.allData.getRowAt(5));
		return TrainingsSetConverter.getIMGFromDS(this.allData.get(nbr), this.MainView.width, this.MainView.height);
	}
	public int getDataSetSize()
	{
		return this.allData.size(); 
	}
}
