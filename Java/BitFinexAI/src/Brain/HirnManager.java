package Brain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.Perceptron;

public class HirnManager implements Runnable {
	File HirnFolder = new File("bin/Hirne") ; 
	File BabyHirnFolder = new File(HirnFolder.getPath() + "/Baby" ) ; 
	File TradingHirnFolder = new File(HirnFolder.getPath() + "/Trader" ) ; 
	
	File TestData ; 
	
	MultiLayerPerceptron test ; 
	KursDataSet testdata ; 
	
	List<File> HirnDat = new ArrayList(); 
	
	public HirnManager()
	{
		if(!this.HirnFolder.exists())
		{
			this.HirnFolder.mkdirs() ; 
		}
		if(!this.BabyHirnFolder.exists())
		{
			this.BabyHirnFolder.mkdirs() ; 
		}
		if(!this.TradingHirnFolder.exists())
		{
			this.TradingHirnFolder.mkdirs() ; 
		}
	}
	
//	in: 1 h minutenweise , 2h , 3h , 1T , 2T , 1W , 1M
//	out: 
	
	public MultiLayerPerceptron makefullconectetBrain(int... neuronsInLayers  )
	{
		
		MultiLayerPerceptron hirn = new MultiLayerPerceptron(2,2,2) ; 
		System.out.println("funktioniert");
		
		return hirn ; 
	}
	
	public void makeTrainingsset(File Kurstest , int minutsback , int hoursback , int daysback , int weeksback , int monthback)
	{
		this.TestData = Kurstest ; 
		
		testdata = new KursDataSet( minutsback ,  hoursback ,  daysback ,  weeksback ,  monthback ,  Kurstest) ;

		System.out.println("funktioniert");
	}
	
	public void test( File Kurs )
	{
		makeTrainingsset( Kurs , 0 ,0,0,0,0) ; 
		this.test = makefullconectetBrain(testdata.getInputSize() , 2 , testdata.getOutputSize()) ; 

		System.out.println("funktioniert");
		test.learn(testdata);
	}
	
	@Override
	public void run() 
	{
		
	}
	
}
