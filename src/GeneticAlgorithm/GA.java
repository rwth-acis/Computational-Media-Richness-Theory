package GeneticAlgorithm;

import org.jgap.IChromosome;



public class GA {

	public IChromosome currentChromosome;
    public double currentFitness;

    static GA instance;
    
	public static GA getInstance() {
		if (instance == null)
		    instance = new GA();
		return instance;
	    }
}
