package dev.aspectious.NeuralNetz;

import java.util.*;

public class NetworkMGR {
	private Nlayer inputs;
	private Nlayer[] hidden;
	private Nlayer outputs;
	
	public NetworkMGR(Nlayer INPUTS, Nlayer[] HIDDEN, Nlayer OUTPUTS, boolean LoadedWeights) {
		this.inputs = INPUTS;
		this.hidden  = HIDDEN;
		this.outputs = OUTPUTS;
		
		System.out.println("[INFO] Initializing Neural Network...");
		if (LoadedWeights == false) initializeDefault();
		if (LoadedWeights == true) initializeLoaded();
		System.out.println("[INFO] Network Initialized.");
	}
	
	public void initializeLoaded() {
		// Initialize all connections between INPUT layer and HIDDEN layer 1;
				int count = 0;
				for (Neuron n1 : inputs.neurons) {
					for (Neuron n2 : hidden[0].neurons) {
						inputs.nextHop[count].prev = n1;
						inputs.nextHop[count].next = n2;
						count++;
					}
				}
				hidden[0].PrevHop = inputs.nextHop;
				
				// Initialize all connections between all HIDDEN layers;
				for (int i=0; i<hidden.length-1; i++) {
					if (i==0)hidden[0].PrevHop = inputs.nextHop;
					else  hidden[i].PrevHop = hidden[i-1].nextHop;
					
					count = 0;
					for (Neuron n1 : hidden[i].neurons) {
						for (Neuron n2 : hidden[i+1].neurons) {
							hidden[i].nextHop[count].prev = n1;
							hidden[i].nextHop[count].next = n2;
							count++;
						}
					}
					hidden[i+1].PrevHop = hidden[i].nextHop;
				}
				
				
				
				
				// Initialize all connections between last HIDDEN layer and OUTPUT layer;
				
				
				// Initialize all connections between all HIDDEN layers;
				count = 0;
				for (Neuron n1 : hidden[hidden.length-1].neurons) {
					for (Neuron n2 : outputs.neurons) {
						hidden[hidden.length-1].nextHop[count].prev = n1;
						hidden[hidden.length-1].nextHop[count].next = n2;
						count++;
					}
				}
				outputs.PrevHop = hidden[hidden.length-1].nextHop;
				
	}
	
	public void initializeDefault() {
		
		// Initialize all connections between INPUT layer and HIDDEN layer 1;
		ArrayList<Nlink> layer1_2 = new ArrayList<Nlink>(); 
		for (Neuron n1 : inputs.neurons) {
			for (Neuron n2 : hidden[0].neurons) {
				layer1_2.add(new Nlink(n1,n2));
			}
		}
		Nlink[] fin1_2 = new Nlink[layer1_2.size()];
		for (int i=0; i<layer1_2.size(); i++) {fin1_2[i] = layer1_2.get(i);}
		inputs.nextHop = fin1_2;
		
		
		
		// Initialize all connections between all HIDDEN layers;
		for (int i=0; i<hidden.length-1; i++) {
			if (i==0)hidden[0].PrevHop = fin1_2;
			else  hidden[i].PrevHop = hidden[i-1].nextHop;
			
			ArrayList<Nlink> layer = new ArrayList<Nlink>(); 
			for (Neuron n1 : hidden[i].neurons) {
				for (Neuron n2 : hidden[i+1].neurons) {
					layer.add(new Nlink(n1,n2));
				}
			}
			Nlink[] finH = new Nlink[layer.size()];
			for (int j=0; j<layer.size(); j++) {finH[j] = layer.get(j);}
			hidden[i].nextHop = finH;
		}
		
		
		
		
		// Initialize all connections between last HIDDEN layer and OUTPUT layer;
		ArrayList<Nlink> layerO = new ArrayList<Nlink>(); 
		for (Neuron n1 : hidden[hidden.length-1].neurons) {
			for (Neuron n2 : outputs.neurons) {
				layerO.add(new Nlink(n1,n2));
			}
		}
		Nlink[] finO = new Nlink[layerO.size()];
		for (int i=0; i<layerO.size(); i++) {finO[i] = layerO.get(i);}
		hidden[hidden.length-1].nextHop = finO;
		
		
		
	}
	
	
	private static Nlink[] filterNeuronLinks(Nlink[] links, Neuron n) {
		ArrayList<Nlink> filteredLinks = new ArrayList<Nlink>();
		for (Nlink link : links) {
			if (link.next == n) {
				filteredLinks.add(link);
			}
		}
		Nlink[] rets = new Nlink[filteredLinks.size()];
		for (int i=0; i<rets.length; i++) {
			rets[i] =  filteredLinks.get(i);
		}
		return rets;
	}

	
	
	
	
	// Runs a set of values through the network
	public double[] runCycle(double[] input) {
		System.out.println("[INFO] Cycling network...");
		for (int i=0; i<inputs.neurons.length; i++) {
			inputs.neurons[i].setValue((float)input[i]);
		}
		
		for (int i=0; i<hidden[0].neurons.length; i++) {
			Nlink[] links = filterNeuronLinks(inputs.nextHop, hidden[0].neurons[i]);
			float sum = 0.0f;
			for (Nlink link : links) {
				sum += (link.prev.getValue() * link.getWeight());
			}
			hidden[0].neurons[i].setValue(sigmoid((float)(sum - hidden[0].bias)));
		}
		
		for (int j=0; j<hidden.length-1; j++) {
			for (int i=0; i<hidden[j+1].neurons.length; i++) {
				Nlink[] links = filterNeuronLinks(hidden[j].nextHop, hidden[j+1].neurons[i]);
				float sum = 0.0f;
				for (Nlink link : links) {
					sum += (link.prev.getValue() * link.getWeight());
				}
				hidden[j+1].neurons[i].setValue(sigmoid((float)(sum - hidden[j+1].bias)));
			}
		}
		
		for (int i=0; i<outputs.neurons.length; i++) {
			Nlink[] links = filterNeuronLinks(hidden[hidden.length-1].nextHop, outputs.neurons[i]);
			float sum = 0.0f;
			for (Nlink link : links) {
				sum += (link.prev.getValue() * link.getWeight());
			}
			outputs.neurons[i].setValue(sigmoid((float)(sum - outputs.bias)));
		}
		
		double[] outs = new double[outputs.neurons.length];
		for (int i=0; i<outs.length; i++) {
			outs[i] = (double)outputs.neurons[i].getValue();
		}
		System.out.println("[INFO] Network Cycle Complete.");
		return outs;
	}
	
	
	
	
	
	
	public Nlayer[] getLayers() {
		return new Nlayer[] {inputs,hidden[0],hidden[1],outputs};
	}
	
	private static float sigmoid(float value) {
		return (float) (1.0f/(1 + Math.pow(Math.E,(double)(value * -1.0f))));
	}
}
