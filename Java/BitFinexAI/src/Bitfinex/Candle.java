package Bitfinex;

public class Candle {
	
	int MTS ;
	float[] Feld = new float[4];  
	
	Candle(int MTS , float open , float close , float high , float low , float volume)
	{
		this.setMTS(MTS);
		this.setopen(open);
		this.setclose(close);
		this.sethigh(high);
		this.setlow(low);
		this.setvolume(volume);
	}
	
	public void setMTS(int MTS)
	{
		this.MTS = MTS ; 
	}
	public int getMTS()
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
}
