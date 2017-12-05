package Technik;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import Bitfinex.Candle;

public class CandleReader {
	private static final int Up = 1 , Down = -1 ; 
	
	private long Candles ; 
	
	int direction = 0; 
	RandomAccessFile Reader ; 
	File file ; 
	
	public CandleReader(File file )
	{
		this.file = file ; 
		direction = this.getDirection(file) ; 
		
		try {
			
			this.Reader = new RandomAccessFile(file , "r") ;
			this.Candles = this.Reader.length() / Candle.lengh ; 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public long CandlesInFile()
	{
		return this.Candles ; 
	}
	
	public Candle getCandle(long Candlnbr)
	{
		try {
			if(direction == this.Up && Candlnbr < this.Candles )
			{
				Reader = new RandomAccessFile(file , "r") ;
				long nbr = Candlnbr ; 
				
				Reader.seek(nbr * Candle.lengh);
				float[] bufFloat = new float[4] ;  
				long bufMTS = Reader.readLong() ; 
				
				
				for(int i = 1 ; i < Candle.lengh ; i++)
				{
					Reader.seek(i + nbr*Candle.lengh );
					
					bufFloat[i-1] = Reader.readFloat() ; 
				}
				
				Candle bufCandle = new Candle(bufMTS , bufFloat[0] , bufFloat[1] , bufFloat[2] , bufFloat[3] , bufFloat[4]  ) ; 
				
				Reader.close(); 
				return bufCandle ; 
			}

			if(direction == this.Down && Candlnbr < this.Candles )
			{
				
				Reader = new RandomAccessFile(file , "r") ;
				long nbr = Candlnbr ; 
				
				Reader.seek(Reader.length() - nbr * Candle.lengh);
				long bufMTS = Reader.readLong() ; 
				float[] bufFloat = new float[4] ;  
				
				
				for(int i = 1 ; i < Candle.lengh ; i++)
				{
					Reader.seek(Reader.length() - nbr*Candle.lengh + i );
					
					bufFloat[i-1] = Reader.readFloat() ; 
				}
				
				Candle bufCandle = new Candle(bufMTS , bufFloat[0] , bufFloat[1] , bufFloat[2] , bufFloat[3] , bufFloat[4]  ) ; 
				
				Reader.close(); 
				return bufCandle ;
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null ; 
	}
	
	private int getDirection(File file)
	{
		try {
			Reader = new RandomAccessFile(file , "r") ;
			
			Reader.seek(0);
			long i = Reader.readLong() ; 
			Reader.seek(Candle.lengh);
			long y = Reader.readLong() ; 
			
			Reader.close();
			
			if(i < y)
			{
				return this.Up ; 
			}
			else
			{
				return this.Down ; 
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return 0 ; 
	}
	
	
	

}
