package GeneticAlgorithm;


import org.jgap.Chromosome;
import org.jgap.IChromosome;

import Main.MyBatchRunner;

/** Config for genetic algorithm */
public class GA {

	public IChromosome currentChromosome;
	public double currentFitness;

	public GA() {
		this.currentFitness = 0;
	}
}
