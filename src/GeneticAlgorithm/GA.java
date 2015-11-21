package GeneticAlgorithm;

import MyBatchRunner;

import org.jgap.IChromosome;

/** Config for genetic algorithm */
public class GA {

	public IChromosome currentChromosome;
	public double currentFitness;
	public MyBatchRunner runner;
	
	static GA instance = null;

	public int data;

	public static GA getInstance() {
		if (instance == null) {
			synchronized (GA.class) {
				if (instance == null) {
					instance = new GA();
				}
			}
		}
		return instance;
	}

	protected GA() {
		this.data = 0;
	}
}
