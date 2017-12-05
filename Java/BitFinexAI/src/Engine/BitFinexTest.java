package Engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Bitfinex.BitfinexAPI;
import Brain.HirnManager;
import Technik.Downloader;

public class BitFinexTest {

	public static void main(String[] args) throws IOException 
	{
//		List<String> test = new ArrayList() ; 
		
//		test.add("tBTCUSD") ; 
		
		BitfinexAPI bapi = new BitfinexAPI(false);
		
//		bapi.repairFileDown(bapi.getAllCurrencys());
		
//		bapi.getAllCurrencys(false);
		
//		for(int i = 0 ; i < bapi.getAllCurrencys(false).size() ; i++)
//		{
//			bapi.isDateiConstant(new File(bapi.getKursFolder().getPath() + "/" +  bapi.getAllCurrencys(false).get(i) + "/n1510604640000"  ), BitfinexAPI.GoDown );
//		}
		
//		System.out.println( bapi.getAllCurrencys(false).get(0));
//		bapi.isDateiConstant(new File(bapi.getKursFolder().getPath() + "/" +  bapi.getAllCurrencys(false).get(1)+ "/n1510604640000"  ) , BitfinexAPI.GoDown) ; 
		
		HirnManager test = new HirnManager() ; 
		test.test(null);
		
//		bapi.Download("tBTCUSD", Downloader.GoDown);
		
//		bapi.isOperating() ; 
		
//		List<String> Symbols = new ArrayList(); 
//		Symbols = bapi.getAllCurrencys() ; 
		
//		bapi.deleteUpperDats(Symbols);
//		BitfinexAPI.isDateiConstant("tLTCUSD" , BitfinexAPI.GoUp) ; 
		
		
	}

}
