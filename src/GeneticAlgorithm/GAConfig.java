package GeneticAlgorithm;

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
import Media.MediaType;

@SuppressWarnings("serial")
public class GAConfig extends Configuration implements ICloneable {

	/**
	 * List of medias relative to the medias in the genes.
	 */
	public List<MediaType> MediaTypes = new ArrayList<MediaType>();
	
	public String projectPath;		
	public String teamPath;
	
	public GAConfig(String a_id, String a_name, List<MediaType> mt) {
		super(a_id, a_name);
		try {
			this.setPreservFittestIndividual(true);

			//two genes: medias and team
			Gene[] genes = new Gene[2];
			
			//change genes from scenario
			this.MediaTypes = mt;
			
			//mediaUsageFrequencyPerStepGenes
			Gene[] mediaGenes = new Gene[mt.size()];
			for(int i = 0; i < mt.size(); i++){
				mediaGenes[i] = new IntegerGene(this, 0, 3 * 10); // add media to the gene
			}
			
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
