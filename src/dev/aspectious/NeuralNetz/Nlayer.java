package dev.aspectious.NeuralNetz;

public class Nlayer {
	public Neuron[] neurons;
	public Nlink[] PrevHop;
	public Nlink[] nextHop;
	public boolean isOutputLayer = false;
	public double bias;
	
	public Nlayer(int NeuronCount) {
		this.bias = 0.0d;
		neurons = new Neuron[NeuronCount];
		for (int i=0; i<neurons.length; i++) {
			neurons[i] = new Neuron();
		}
	}
	public Nlayer(int NeuronCount, boolean isOutput) {
		this.bias = 0.0d;
		neurons = new Neuron[NeuronCount];
		for (int i=0; i<neurons.length; i++) {
			neurons[i] = new Neuron();
		}
	}
}
