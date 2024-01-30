package dev.aspectious.NeuralNetz;

public class Nlink {
	private float weight;
	public Neuron prev;
	public Neuron next;
	
	public Nlink(Neuron from, Neuron to) {
		prev = from;
		next = to;
		weight = (float)((Math.random() * 2)-1);
	}
	
	public Nlink() {
		weight = (float)((Math.random() * 2)-1);
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float newWeight) {
		this.weight = newWeight;
	}
}
