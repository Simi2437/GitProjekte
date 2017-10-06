package Fenster;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DSRepresenter extends JPanel implements ActionListener, ChangeListener{
	
	private int ButtonWidth = 50; 
	private BufferedImage img ;
	
	boolean[] outputStat ;
	private String [] OutputNamen; 
	
	JSlider FrNbr ;
	private int Framenbr = 0 ; 
	
	JPanel content = new JPanel() ; 
	JPanel ButtonPanel1 = new JPanel();
	JPanel ButtonPanel2 = new JPanel();
	JPanel ButtonPanel3 = new JPanel();
	
	public DSRepresenter(int picWidth , int picHeight , String[] OutputNamen , int Frames)
	{
		this.OutputNamen = OutputNamen ; 
		outputStat = new boolean[OutputNamen.length] ; 
		
		this.setLayout(new BorderLayout());
		
		//Bild und Button Layout
		JPanel BUB = new JPanel();
		BUB.setLayout(new BoxLayout(BUB , BoxLayout.X_AXIS));
		
		JPanel Bild = new JPanel(); 
		Bild.setPreferredSize(new Dimension(picWidth , picHeight));
		BUB.add(Bild);
		
		ButtonPanel1.setLayout(new BoxLayout(ButtonPanel1 , BoxLayout.Y_AXIS));
		BUB.add(ButtonPanel1) ;  
		this.add(BUB, BorderLayout.CENTER);

		ButtonPanel2.setLayout(new BoxLayout(ButtonPanel2 , BoxLayout.Y_AXIS));
		BUB.add(ButtonPanel2) ;  
		this.add(BUB, BorderLayout.CENTER);
		
		ButtonPanel3.setLayout(new BoxLayout(ButtonPanel3 , BoxLayout.Y_AXIS));
		this.addOutputs(OutputNamen);
		BUB.add(ButtonPanel3) ;  
		this.add(BUB, BorderLayout.CENTER);
		
		//JSlider Einfügen
		FrNbr = new JSlider( 0 , Frames , 0) ; 
		FrNbr.setMajorTickSpacing(1);
		FrNbr.setPaintTicks(true);
		FrNbr.addChangeListener(this);
		this.add(FrNbr , BorderLayout.SOUTH); 
		
		this.repaint();
	}
	public void setIMG(BufferedImage img)
	{
		this.img = img ; 
		this.repaint(); 
	}
	public void addOutputs(String[] args)
	{
		for(int i = 0 ; i < args.length ; i++)
		{
			if(i<=args.length/3)addButtonTo(args[i] , ButtonPanel1);
			if(i>args.length/3 && i<=args.length*2/3)addButtonTo(args[i] , ButtonPanel2);
			if(i>args.length/3)addButtonTo(args[i] , ButtonPanel3);
		}
	}
	private void addButtonTo(String string, JPanel buttonPanel2) 
	{
		JButton button = new JButton(string) ; 
		button.addActionListener(this);
		buttonPanel2.add(button);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		for(int i = 0 ; i<OutputNamen.length ; i++)
		{
			if(e.getActionCommand() == OutputNamen[i])
			{
				outputStat[i] = true ; 
			}
		}
		
	}
	public int getChoosenFrame()
	{
		return this.Framenbr ; 
	}
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		this.Framenbr = FrNbr.getValue() ; 
		this.getParent().getParent().getParent().getParent().requestFocus();
	}
}
