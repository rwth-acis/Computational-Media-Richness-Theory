package GeneticAlgorithm;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.FitnessEvaluator;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.event.GeneticEvent;
import org.jgap.event.GeneticEventListener;
import org.jgap.event.IEventManager;
import org.jgap.impl.*;
import org.jgap.util.ICloneable;

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

			Gene[] sampleGenes = new Gene[4];
			sampleGenes[0] = new IntegerGene(this, 0, 3 * 10); // Quarters
			sampleGenes[1] = new IntegerGene(this, 0, 2 * 10); // Dimes
			sampleGenes[2] = new IntegerGene(this, 0, 1 * 10); // Nickels
			sampleGenes[3] = new IntegerGene(this, 0, 4 * 10); // Pennies
			IChromosome sampleChromosome = new Chromosome(this, sampleGenes);
			this.setSampleChromosome(sampleChromosome);

			this.setPopulationSize(10);
			this.addNaturalSelector(new BestChromosomesSelector(this), false);
			this.setRandomGenerator(new StockRandomGenerator());
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
			this.setFitnessEvaluator(new FitnessEvaluator() {

				@Override
				public boolean isFitter(IChromosome a_chrom1,
						IChromosome a_chrom2) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isFitter(double a_fitness_value1,
						double a_fitness_value2) {
					return a_fitness_value1 > a_fitness_value2;
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
