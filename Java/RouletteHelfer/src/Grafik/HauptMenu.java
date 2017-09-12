package Grafik;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Technik.RouletteDaten;

public class HauptMenu extends superContent implements ActionListener{
	
	JButton neu = new JButton("Neu"); 
	
	public HauptMenu(String Name, RouletteDaten Daten) {
		super(Name, Daten);
		this.setLayout(new GridLayout(3 , 1));
		this.addContent(); 
	}

	@Override
	public void MakeFrame() {
		this.Owner.setSize(400 , 400);
		this.Owner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.Owner.add(this);
		this.Owner.setVisible(true);
	}

	@Override
	public void addContent() {
		this.neu.addActionListener(this);
		this.add(neu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Neu") this.Owner.EM.NextProgramm(Owner.EM.Initialisieren);
	}

}
