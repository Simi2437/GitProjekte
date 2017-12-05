package Brain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Bitfinex.Candle;
import Technik.CandleReader;

public class CandleHandler {
	
	private CandleReader FileReader ; 

	private int  LookBackFor = 0 ; 
	
//	private long bufferSize ; 
	
	private boolean Direction ; 
	
	private long MaxCandles ; 
	private long UntersteKerze = 0 ; 
	private long ObersteKerze = 0  ; 
	
	private long CandlesInBuf = 0 ; 
	
	List<Candle> bufCandles = new ArrayList() ;
	
	public CandleHandler( File file , int LookBackfor , boolean Direction )
	{
		FileReader = new CandleReader(file) ; 
		
		this.Direction = Direction ; 
		
		MaxCandles = FileReader.CandlesInFile(); 
		
		UntersteKerze = 0 ; 
		
		this.LookBackFor = LookBackfor ; 
		
		this.loadBufCandles(LookBackfor);
		
	}
	
	public boolean hasNext()
	{
		if(this.Direction == CandleDataSet.lookback)
		{
			if(this.ObersteKerze >= this.FileReader.CandlesInFile()) return false ; 
			else return true ; 
		}
		else
		{
			if(this.ObersteKerze >= this.FileReader.CandlesInFile()) return false ; 
			else return true ; 
		}
	}
	
	private boolean loadBufCandles( int i )
	{

		if(this.Direction == CandleDataSet.lookback)
		{
			if(this.ObersteKerze + i <= FileReader.CandlesInFile())
			{	
				for(int y = 0 ; y < i ; y++)
				{
					bufCandles.add(FileReader.getCandle(y + ObersteKerze)) ; 
					this.CandlesInBuf++ ; 
				}
				this.ObersteKerze += (i - 1) ; 
				this.shortBuffer();
				return true ; 
			}
			else
			{
				System.out.println("keine Kerzen mehr übrig");
				return false ; 
			}
		}
		else
		{
			if(this.ObersteKerze + i + this.LookBackFor<= FileReader.CandlesInFile())
			{	
				for(int y = 0 ; y < i ; y++)
				{
					bufCandles.add(FileReader.getCandle(y + ObersteKerze + this.LookBackFor)) ; 
					this.CandlesInBuf++ ; 
				}
				this.ObersteKerze += (i - 1) ; 
				this.shortBuffer();
				return true ; 
			}
			else
			{
				System.out.println("keine Kerzen mehr übrig");
				return false ; 
			}
		}
	}
	private boolean loadBufCandle()
	{
		if(this.Direction == CandleDataSet.lookback)
		{
			if(ObersteKerze <= this.FileReader.CandlesInFile())
			{
				bufCandles.add(FileReader.getCandle(ObersteKerze)) ; 
				this.CandlesInBuf++ ; 
				this.ObersteKerze++  ; 
				this.shortBuffer();
				return true ; 
			}
			else
			{
				System.out.println("keine Kerzen mehr übrig");
				return false ; 
			}
		}
		else
		{
			if(ObersteKerze + this.LookBackFor <= this.FileReader.CandlesInFile())
			{
				bufCandles.add(FileReader.getCandle(ObersteKerze)) ; 
				this.CandlesInBuf++ ; 
				this.ObersteKerze++  ; 
				this.shortBuffer();
				return true ; 
			}
			else
			{
				System.out.println("keine Kerzen mehr übrig");
				return false ; 
			}
		}
	}
	
	public Candle getCandle(long StartAt, int lookback )
	{
		if(this.Direction == CandleDataSet.lookback)
		{
			if( StartAt <= this.FileReader.CandlesInFile())
			{
				//			Prüfung ob der StartWert über der obersten Kerze liegt
				if((StartAt ) > this.ObersteKerze )
				{
					while(( StartAt - 1) > this.ObersteKerze)
					{
						this.loadBufCandle();
					}
				}


				//			Prüfung ob der StartWert und der zurücksehende wert nicht zuweit drunter liegen
				if(StartAt - lookback <= this.UntersteKerze)
				{
					System.out.println("Sie wollten zu weit zurück schauen. Unterste Grenze: " + this.UntersteKerze + " Ihr Angefragter untester Wert " + ( StartAt - lookback ));
					return null ; 
				}

				long start = StartAt - this.UntersteKerze ; 
				long middle = start ; 
				long end = StartAt - this.UntersteKerze - lookback ; 

				float lowest = 0 ; 
				float highest = 0; 
				float close = 0 ; 
				float open = 0; 
				float volume = 0 ; 

				boolean wasRight = true ; 

				//			Kerze zum Starten 
				while(lowest == 0 || highest == 0 || close == 0 || open == 0 )
				{
					if(wasRight)
					{
						close = this.bufCandles.get((int) (start)).getclose() ; 
						lowest = this.bufCandles.get((int) (start)).getlow() ; 
						highest = this.bufCandles.get((int) (start)).gethigh() ; 
						open = this.bufCandles.get((int) (end)).getopen(); 
						volume += this.bufCandles.get((int) start).getvolume(); 
					}
					else
					{
						if(close == 0 ) close = this.bufCandles.get((int) start).getopen(); 
						if(open == 0 ) open = this.bufCandles.get((int) end).getclose(); 
						if(lowest == 0 || highest == 0) 
						{
							lowest = this.bufCandles.get((int) middle).getlow(); 
							highest = this.bufCandles.get((int) middle).gethigh(); 
							volume += this.bufCandles.get((int) middle).getvolume() ;
						}
					}


					if(open == 0 || close ==0 || lowest == 0 || highest == 0 ) 
					{
						end-- ; 
						middle-- ; 
						start++ ; 
						wasRight = false ; 
					}
				}
				//			intervall abarbeiten 
				while(end < middle )
				{
					if(this.bufCandles.get((int) middle).getlow() < lowest) lowest = this.bufCandles.get((int) middle).getlow() ; 
					if(this.bufCandles.get((int) middle).gethigh() > highest) highest = this.bufCandles.get((int) middle).gethigh(); 
					middle-- ; 
				}

				return new Candle(0 , open , close , highest , lowest , volume ) ; 

			}
			else
			{
				System.out.println("Es gibt keine Kerze über: " + this.FileReader.CandlesInFile() + " Ihr angefragter Wert: " + StartAt);
			}
		}
		else
		{
			if( StartAt + this.LookBackFor<= this.FileReader.CandlesInFile())
			{
				//			Prüfung ob der StartWert über der obersten Kerze liegt
				if((StartAt ) > this.ObersteKerze+this.LookBackFor )
				{
					while(( StartAt - 1) > this.ObersteKerze+this.LookBackFor)
					{
						this.loadBufCandle();
					}
				}


				//			Prüfung ob der StartWert und der zurücksehende wert nicht zuweit drunter liegen
				if(StartAt + lookback <= this.ObersteKerze + this.LookBackFor)
				{
					System.out.println("Sie wollten zu weit zurück schauen. Unterste Grenze: " + this.UntersteKerze + " Ihr Angefragter untester Wert " + ( StartAt - lookback ));
					return null ; 
				}

				long start = StartAt - this.UntersteKerze ; 
				long middle = start ; 
				long end = StartAt - this.UntersteKerze + lookback ; 

				float lowest = 0 ; 
				float highest = 0; 
				float close = 0 ; 
				float open = 0; 
				float volume = 0 ; 

				boolean wasRight = true ; 

				//			Kerze zum Starten 
				while(lowest == 0 || highest == 0 || close == 0 || open == 0 )
				{
					if(wasRight)
					{
						close = this.bufCandles.get((int) (start)).getclose() ; 
						lowest = this.bufCandles.get((int) (start)).getlow() ; 
						highest = this.bufCandles.get((int) (start)).gethigh() ; 
						open = this.bufCandles.get((int) (end)).getopen(); 
						volume += this.bufCandles.get((int) start).getvolume(); 
					}
					else
					{
						if(close == 0 ) close = this.bufCandles.get((int) middle).getopen(); 
						if(open == 0 ) open = this.bufCandles.get((int) start).getclose(); 
						if(lowest == 0 || highest == 0) 
						{
							lowest = this.bufCandles.get((int) middle).getlow(); 
							highest = this.bufCandles.get((int) middle).gethigh(); 
							volume += this.bufCandles.get((int) middle).getvolume() ;
						}
					}


					if(open == 0 || close ==0 || lowest == 0 || highest == 0 ) 
					{
						end++ ; 
						middle++ ; 
						start-- ; 
						wasRight = false ; 
					}
				}
				//			intervall abarbeiten 
				while(end > middle )
				{
					if(this.bufCandles.get((int) middle).getlow() < lowest) lowest = this.bufCandles.get((int) middle).getlow() ; 
					if(this.bufCandles.get((int) middle).gethigh() > highest) highest = this.bufCandles.get((int) middle).gethigh(); 
					middle-- ; 
				}

				return new Candle(0 , open , close , highest , lowest , volume ) ; 

			}
			else
			{
				System.out.println("Es gibt keine Kerze über: " + this.FileReader.CandlesInFile() + " Ihr angefragter Wert: " + StartAt);
			}
			
		}
		return null ; 
	}
	
	private void shortBuffer()
	{
		for(int i = 0 ; i < this.bufCandles.size() - this.LookBackFor ; i++)
		{
			this.bufCandles.remove(0) ; 
			this.CandlesInBuf-- ; 
			this.UntersteKerze++ ; 
		}
	}
	
	public long NbrOfCandles()
	{
		return this.MaxCandles ; 
	}
}
