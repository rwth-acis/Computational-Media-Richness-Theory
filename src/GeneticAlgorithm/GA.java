package GeneticAlgorithm;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jgap.Chromosome;
import org.jgap.IChromosome;

import DataLoader.Results;
import Main.MyBatchRunner;

/** Config for genetic algorithm */
public class GA {

	public IChromosome currentChromosome;
	public double currentFitness;
	
	public GA() {
		this.currentFitness = 0;
		this.currentChromosome = null;
	}
}
