package Engine;

import java.io.IOException;

import Bitfinex.BitfinexAPI;

public class BitFinexTest {

	public static void main(String[] args) throws IOException 
	{
		
		BitfinexAPI bapi = new BitfinexAPI();
		
		bapi.isOperating() ; 
		bapi.getAllCurrencys() ; 
		
	}

}
