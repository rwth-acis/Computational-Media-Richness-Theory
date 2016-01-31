package GeneticAlgorithm;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DeltaFitnessEvaluator;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.RandomGenerator;
import org.jgap.event.GeneticEvent;
import org.jgap.event.GeneticEventListener;
import org.jgap.event.IEventManager;
import org.jgap.impl.*;
import org.jgap.util.ICloneable;

import com.google.gson.reflect.TypeToken;

import DataLoader.JsonSerializer;
import DataLoader.Scenario;
import Media.MediaType;

@SuppressWarnings("serial")
public class GAConfig extends Configuration implements ICloneable {

	public GAConfig() {
		this("", "");
	}

	/**
	 * List of medias relative to the medias in the genes.
	 */
	public List<MediaType> MediaTypes = new ArrayList<MediaType>();;
	
	/**
	 * Set list of medias relative to the medias in the genes.
	 * @param mediaTypes
	 */
	public void setMedias(List<MediaType> mediaTypes){
		this.MediaTypes = mediaTypes;
	}
	
	public GAConfig(String a_id, String a_name) {
		super(a_id, a_name);
		try {
			this.setPreservFittestIndividual(true);

			Gene[] genes = new Gene[2];
			
			//TODO change genes from scenario
			//mediaUsageFrequencyPerStepGenes
			Gene[] mediaGenes = new Gene[3];			
			mediaGenes[0] = new IntegerGene(this, 0, 3 * 10); // Email
			mediaGenes[1] = new IntegerGene(this, 0, 3 * 10); // Phone
			mediaGenes[2] = new IntegerGene(this, 0, 3 * 10); // F-t-f

			Scenario scenario = Main.Main.scenario;
			List<MediaType> mt = scenario.getMedias("");
			this.setMedias(mt);
			
			genes[0] = new MediasSupergene(this, mediaGenes);
			genes[1] = new TeamSupergene(this, new Gene[] {
			        new WorkersGene(this),
			        new AdjacencyListGene(this)
			    });
			
			IChromosome sampleChromosome = new Chromosome(this, genes);
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
