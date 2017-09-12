package Technik;

public class EventManager {
	
	public static final int MainFrame = 0 , Initialisieren = 1 , Anlernen = 2 , ZahlWahl = 3 , Automatic = 4 ;  
	private boolean hasChanged = true; 
	private int Nr = 0 ; 
	
	public String[] Programm = { "MainFrame" , "Neu" , "Anlernen" , "ZahlWahl" , "Automatic" , "Initialisieren"} ; 
	
	public EventManager()
	{
		Nr = MainFrame ; 
	}
	
	public void NextProgramm(int nr)
	{
		Nr = nr ; 
		hasChanged = true ; 
	}
	
	public int getProgramm()
	{
		return Nr ; 
	}
	public boolean getChanged()
	{
		return hasChanged ; 
	}
	public void Changed()
	{
		this.hasChanged = false ; 
	}
	
}
