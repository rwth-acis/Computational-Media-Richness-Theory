package GeneticAlgorithm;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.DeltaFitnessEvaluator;
import org.jgap.FitnessEvaluator;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.event.EventManager;
import org.jgap.event.GeneticEvent;
import org.jgap.event.GeneticEventListener;
import org.jgap.event.IEventManager;
import org.jgap.impl.*;
import org.jgap.util.ICloneable;

import DataLoader.Results;

@SuppressWarnings("serial")
public class GAConfig extends Configuration implements ICloneable {

	public GAConfig() {
		this("", "");
	}

	public GAConfig(String a_id, String a_name) {
		super(a_id, a_name);
		try {

			/*
			 * setBreeder(new GABreeder()); setRandomGenerator(new
			 * StockRandomGenerator()); setEventManager(new EventManager());
			 * BestChromosomesSelector bestChromsSelector = new
			 * BestChromosomesSelector( this, 0.90d);
			 * bestChromsSelector.setDoubletteChromosomesAllowed(false); // ←
			 * // changed // to // false // here
			 * addNaturalSelector(bestChromsSelector, false);
			 * setMinimumPopSizePercent(0); // setSelectFromPrevGen(1.0d);
			 * setKeepPopulationSizeConstant(true); setFitnessEvaluator(new
			 * DefaultFitnessEvaluator()); //setFitnessFunction(new
			 * WorkEfficiencyFitnessFunction()); setChromosomePool(new
			 * ChromosomePool()); addGeneticOperator(new CrossoverOperator(this,
			 * 0.5d)); // ← // changed // from 0.35 // to 0.5 // here
			 * addGeneticOperator(new MutationOperator(this, 4)); // ← changed
			 * // from 12 to 4 // here
			 */

			this.setPreservFittestIndividual(true);

			Gene[] mediaUsageFrequencyPerStepGenes = new Gene[3];
			mediaUsageFrequencyPerStepGenes[0] = new IntegerGene(this, 0, 3 * 10); // Email
			mediaUsageFrequencyPerStepGenes[1] = new IntegerGene(this, 0, 3 * 10); // Phone
			mediaUsageFrequencyPerStepGenes[2] = new IntegerGene(this, 0, 3 * 10); // F-t-f

			IChromosome sampleChromosome = new Chromosome(this, mediaUsageFrequencyPerStepGenes);
			this.setSampleChromosome(sampleChromosome);

			this.setPopulationSize(6);
			this.addNaturalSelector(new BestChromosomesSelector(this), false);
			this.setRandomGenerator(new RandomGenerator() {
				
				@Override
				public long nextLong() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public int nextInt(int a_ceiling) {
					// TODO Auto-generated method stub
					int ret = (int) (Math.random()* (a_ceiling - 1));
					return ret;
				}
				
				@Override
				public int nextInt() {
					// TODO Auto-generated method stub
					return (int)(Math.random()*Integer.MAX_VALUE);
				}
				
				@Override
				public float nextFloat() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public double nextDouble() {
					// TODO Auto-generated method stub
					return Math.random();
				}
				
				@Override
				public boolean nextBoolean() {
					// TODO Auto-generated method stub
					return false;
				}
			}  );
			
			//new StockRandomGenerator();
			this.addGeneticOperator(new MutationOperator(this, 4));
			this.setEventManager(new IEventManager() {

				@Override
				public void removeEventListener(String a_eventName,
						GeneticEventListener a_eventListenerToRemove) {
					// TODO Auto-generated method stub

				}

				@Override
				public void fireGeneticEvent(GeneticEvent a_eventToFire) {
					// TODO Auto-generated method stub

				}

				@Override
				public void addEventListener(String a_eventName,
						GeneticEventListener a_eventListenerToAdd) {
					// TODO Auto-generated method stub

				}
			});
			this.addGeneticOperator(new CrossoverOperator(this));
			this.setFitnessEvaluator(new DeltaFitnessEvaluator() {
				@Override
				public boolean isFitter(double a_fitness_value1,
						double a_fitness_value2) {
					return a_fitness_value1 < a_fitness_value2;
				}
			});

		} catch (Exception e) {
			throw new RuntimeException(
					"Fatal error: DefaultConfiguration class could not use its "
							+ "own stock configuration values. This should never happen. "
							+ "Please report this as a bug to the JGAP team.");
		}

	}

	public Object clone() {
		return super.clone();
	}
}
