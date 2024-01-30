package dev.aspectious.NeuralNetz;

public class Neuron {
	private float value;
	
	public Neuron() {
		value = (float) Math.random();
	}
	public Neuron(float value) {
		this.value = value;
	}
	
	public float getValue() { return this.value;}
	public void setValue(float value) { this.value = value; }
}
