package Brain;

import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.Layer;
import org.neuroph.core.Neuron;
import org.neuroph.nnet.comp.neuron.InputNeuron;

public class Brain {
	private List<InputNeuron> inNeurons = new ArrayList();
	private List<Neuron> Neuronen = new ArrayList();
	private List<Layer> Schichten = new ArrayList();
	
	public Brain(int input , int output )
	{
		
	}
	
	private void createInputLayer(int input)
	{
		Schichten.add(new Layer()) ;
		for(int i = 0 ; i < input; i++)
		{
			inNeurons.add(new InputNeuron()) ; 
			Schichten.get(i).addNeuron(inNeurons.get(i));
		}
	}
	private void createOutputLayer(int output)
	{
		Schichten.add(new Layer()) ; 
	}
}
