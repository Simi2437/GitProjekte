package Brain;

import java.io.File;
import java.util.List;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public class KursDataSet extends DataSet {
	
	private int minutsback = 0 , hoursback = 0 , daysback = 0 , weeksback = 0 , monthsback = 0 ; 
	private File TrainingDat ; 
	
	public KursDataSet(int inputSize) {
		super(inputSize);
	}
	
	
	public KursDataSet(int minutsback , int hoursback , int daysback , int weeksback , int monthsback , File TrainingDat)
	{
		super( minutsback*6 + hoursback*6 + daysback*6 + weeksback*6 + monthsback*6  ,  minutsback*6 + hoursback*6 + daysback*6 + weeksback*6 + monthsback*6   ) ; 

		this.minutsback = minutsback ; 
		this.hoursback = hoursback ; 
		this.daysback = daysback ; 
		this.weeksback = weeksback ; 
		this.monthsback = monthsback ; 
		this.TrainingDat = TrainingDat ; 
	}
	
	public DataSetRow getRowAt(int i)
	{
		System.out.println("Die Trainingfunktion will nur die Reihe an " + i );
		return null ; 
	}
	public List<DataSetRow> getRows()
	{
		System.out.println("Die Trainingsfunktion will die Liste ... ");
		return null ; 
	}
	
}
