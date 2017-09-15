package Training;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public class LernVid {
	
	private boolean firstrun = true ; 
	
	private String name = "fünf";
	
	private double[] UPM = new double[1]; 
	private int TempTime = 0 ; 
	private int ChangeTime = 4000 ; 
	private int Dauerms ;
	private int width , height ; 
	
	private double[][] Screen ; 
	private double[] Old ; 
	private double[] DataNeu  ;
	private double[] gesammt ; 
	
	private int KX , KY ,x ,y , KR; 
	private int Delay = 20 ; 
	
	private DataSet training ; 
	
	public LernVid(int sek , int width , int height)
	{
		this.Dauerms = sek * 1000 ; 
		this.width = width ; 
		this.height = height ; 
		training = new DataSet(2*width*height+1 /*, 1*/);
//		DataNeu = new double[width*height];
//		Old = new double[width*height];
//		gesammt = new double[2*width*height+ 1];
//		this.Screen = new double[width][height];
		
		DataNeu = this.make1dfilldArray(DataNeu, width*height , 0);
		Old = this.make1dfilldArray(Old , width*height , 0);
		gesammt =this.make1dfilldArray(gesammt, 2 * width*height + 1, 0);
		Screen = this.make2dfilldArray(Screen, width, height, 0);
		
		training.setLabel(name);
		
		makeVideo();
	}

	private void makeVideo() {
		
		KR = (int) (this.width/2 - (Math.random() * this.width / 2 )) ; 
		x = this.width/2 ; 
		y = this.height/2 ; 
		UPM[0] =  (double) (120 - Math.random() * 100) ; 
		
		for(int t = 0 ; t < Dauerms ; t += Delay)
		{
			TempTime += Delay ; 

			KX = (int) ( x + KR * Math.sin( (2 * Math.PI * UPM[0]) * ((float) t / 1000 / 60 ))) ; 
			KY = (int) ( y + KR * Math.cos( (2 * Math.PI * UPM[0]) * ((float) t / 1000 / 60 ))) ;
			
			Screen[KX][KY] = 1 ; 
			
			for(int xt = 0 ; xt < this.width ; xt++)
			{
				for(int yt = 0 ; yt < this.height ; yt++)
				{
					DataNeu[xt*this.height + yt] = Screen[xt][yt] ; 
//					System.out.println(""+ (xt*this.height + yt));
				}
			}
			
			
			
			if(firstrun) 
			{
				Old = DataNeu ; 
				firstrun = false ; 
			}
			else
			{
				for(int i = 0 ; i < DataNeu.length ; i++)
				{
					gesammt[i] = DataNeu[i] ; 
//					System.out.println("" + i + "-- nr --" + gesammt[i] + "--input");
				}
				for(int y = 0 ; y < Old.length ; y++)
				{
//					System.out.println("" + (this.DataNeu.length + y )+ "-- nr --" + gesammt[y] + "--input");
					gesammt[DataNeu.length + y] = Old[y] ; 
				}
				
				gesammt[Old.length + DataNeu.length] = Delay ; 
//				System.out.println("" + (this.DataNeu.length + Old.length)+ "-- nr --" + gesammt[DataNeu.length + Old.length] + "--input");
				
				training.addRow(new DataSetRow(gesammt/*, UPM*/));
				System.out.println(" hääää   -- " + UPM[0]);
			}
			
			Screen[KX][KY] = 0 ;
			
			
			if(TempTime == ChangeTime)
			{
				TempTime = 0 ; 
				KR = (int) (this.width/2 - (Math.random() * this.width / 2 )) ; 
			}
		}
		
		
		training.save("src/TrainingsDaten/" + name + ".tset");
		
	}

	private double[] make1dfilldArray(double [] array , int lenght , double wert)
	{
		array = new double[lenght];
		
		for(int z = 0 ; z < lenght ; z++)
		{
			array[z] = wert ; 
		}
		return array;
	}
	private double[][] make2dfilldArray(double [][] array , int width , int height , double wert)
	{
		array = new double[width][height];
		
		for(int z = 0 ; z < width ; z++)
		{
			for(int y = 0 ; y < height ; y++)
			{
				array[z][y] = 0  ; 
			}
		}
		return array;
	}
}
