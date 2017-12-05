package Bitfinex;

import java.text.DateFormat;
import java.util.Date;

public class Candle {
	public static final int lengh = 6 ; 
	long MTS ;
	float[] Feld = new float[5];  
	
	public Candle(long MTS , float open , float close , float high , float low , float volume)
	{
		this.setMTS(MTS);
		this.setopen(open);
		this.setclose(close);
		this.sethigh(high);
		this.setlow(low);
		this.setvolume(volume);
	}
	
	public void setMTS(long MTS)
	{
		this.MTS = MTS ; 
	}
	public long getMTS()
	{
		return this.MTS ; 
	}
	public void setopen(float open)
	{
		this.Feld[0] = open ; 
	}
	public float getopen()
	{
		return this.Feld[0] ; 
	}
	
	public void setclose(float close)
	{
		this.Feld[1] = close ; 
	}
	public float getclose()
	{
		return this.Feld[1];
	}	
	public void sethigh(float high)
	{
		this.Feld[2] = high ; 
	}
	public float gethigh()
	{
		return this.Feld[2];
	}	
	public void setlow(float low)
	{
		this.Feld[3] = low ; 
	}
	public float getlow()
	{
		return this.Feld[3];
	}	
	public void setvolume(float volume)
	{
		this.Feld[4] = volume ; 
	}
	public float getvolume()
	{
		return this.Feld[4] ; 
	}
	public String toString()
	{

        DateFormat shortDf = DateFormat.getDateInstance(DateFormat.SHORT);
		return "" + shortDf.format(this.getDate()) + "  " + this.MTS + "  " + this.getopen() + "   " + this.getclose() + "  " + this.gethigh() + "  " + this.getlow() + "  " + this.getvolume() ; 
	}
	public Date getDate()
	{
		return new Date(this.MTS) ; 
	}
}
