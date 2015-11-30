package Main;
import java.io.Console;

import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessEvaluator;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.audit.ChainedMonitors;
import org.jgap.audit.Evaluator;
import org.jgap.audit.FitnessImprovementMonitor;
import org.jgap.audit.IEvolutionMonitor;
import org.jgap.audit.PermutingConfiguration;
import org.jgap.audit.TimedMonitor;
import org.jgap.event.GeneticEvent;
import org.jgap.event.GeneticEventListener;
import org.jgap.event.IEventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.RandomGeneratorForTesting;
import org.jgap.impl.StockRandomGenerator;
import org.jgap.impl.WeightedRouletteSelector;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.runtime.RepastBatchMain;
import GeneticAlgorithm.GA;
import GeneticAlgorithm.GAConfig;

/*
 import cern.jet.math.Bessel;
 import cern.jet.random.VonMises;
 import cern.jet.random.engine.RandomEngine;
 import repast.simphony.engine.environment.RunEnvironment;
 import repast.simphony.batch.InstanceRunner;
 import repast.simphony.random.RandomHelper;
 import repast.simphony.runtime.RepastBatchMain;
 import repast.simphony.util.FileUtils;
 import swarm_sim.learning.GA;
 import swarm_sim.perception.AngleSegment;
 import swarm_sim.perception.Scan;
 import swarm_sim.perception.Scan.AttractionType;
 import swarm_sim.perception.Scan.GrowingDirection;
 import swarm_sim.perception.Scan.ScanInput;
 import swarm_sim.perception.ScanMoveDecision;
 */
/*
 public class Main extends FitnessFunction {


 public static void main(String[] args) {


 try {

 //args = new String[1];
 //args[1] = "batch/batch_params.xml";
 //args[0] = "-params";
 //args[1] ="C:/Users/Alex/workspace/SoftwareSim/batch/batch_params.xml";
 //args[0] ="C:/Users/Alex/workspace/SoftwareSim/SoftwareSim.rs";
 //C:/Users/Alex/workspace/SoftwareSim/SoftwareSim.rs
 //C:\Users\Alex\workspace\JZombies_Demo\JZombies_Demo.rs
 GA ga = SingleTon.SOLE.getit();
 //ga.data = 1;
 RepastBatchMain.main(args);
 int d = ga.data;
 System.out.println(">>> d: "+d);



 } catch (Exception e) {
 e.printStackTrace();
 }

 }

 @Override
 protected double evaluate(IChromosome a_subject) {
 // TODO Auto-generated method stub
 return 0;
 }
 }
 */

import GeneticAlgorithm.WorkEfficiencyFitnessFunction;

import java.io.File;
import java.util.List;
import java.util.Vector;

public class Main {

	private static final int MAX_ALLOWED_EVOLUTIONS = 1000;

	public static void main(String[] args) throws InvalidConfigurationException {

		File scenarioFile = new File(args[0]); // the scenario dir
		String batchFile = "C:/Users/Alex/workspace/SoftwareSim/batch_external/batch_params.xml";
		// ---------------------------------------------------

		GAConfig conf = new GAConfig();
		
		FitnessFunction myFunc = new WorkEfficiencyFitnessFunction(0,
				scenarioFile, batchFile);
		conf.setFitnessFunction(myFunc);
		Genotype population = Genotype.randomInitialGenotype( conf );
		
		for( int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++ )
		{
		    population.evolve();
		    
		    IChromosome bestSolutionSoFar = population.getFittestChromosome();
			System.out.println("The best solution has a fitness value of "
					+ bestSolutionSoFar.getFitnessValue());		      
		}
		/*
		Genotype population = Genotype.randomInitialGenotype(conf);
		long startTime = System.currentTimeMillis();
		// Configure monitors to let the evolution stop when defined criteria
		// are met.
		// ------------------------------------------------------------------
		List monitors = new Vector();
		monitors.add(new TimedMonitor(6));
		monitors.add(new FitnessImprovementMonitor(1, 3, 5.0d));
		IEvolutionMonitor monitor = new ChainedMonitors(monitors, 2);
		// Start the evolution.
		// --------------------
		List<String> messages = population.evolve(monitor);
		if (messages.size() > 0) {
			for (String msg : messages) {
				System.out.println("Message from monitor: " + msg + "\n");
			}
		}
		// Evaluate the result.
		// --------------------
		long endTime = System.currentTimeMillis();
		System.out.println("Total evolution time: " + (endTime - startTime)
				+ " ms");
				*/
		
		
		/*
		PermutingConfiguration pconf = new PermutingConfiguration(conf);
	    pconf.addGeneticOperatorSlot(new CrossoverOperator(conf));
	    //pconf.addGeneticOperatorSlot(new MutationOperator(conf));
	    pconf.addNaturalSelectorSlot(new BestChromosomesSelector(conf));
	    //pconf.addNaturalSelectorSlot(new WeightedRouletteSelector(conf));
	    pconf.addRandomGeneratorSlot(new StockRandomGenerator());
	    RandomGeneratorForTesting rn = new RandomGeneratorForTesting();
	    rn.setNextDouble(0.7d);
	    rn.setNextInt(2);
	   // pconf.addRandomGeneratorSlot(rn);
	   // pconf.addRandomGeneratorSlot((RandomGenerator) new GaussianRandomGenerator(null));
	    pconf.addFitnessFunctionSlot(new WorkEfficiencyFitnessFunction(0, scenarioFile, batchFile));
	    Evaluator eval = new Evaluator(pconf);
	    
	    
	    int permutation = 0;
	    while (eval.hasNext()) {
	      // Create random initial population of Chromosomes.
	      // ------------------------------------------------
	      Genotype population = Genotype.randomInitialGenotype(eval.next());
	      for (int run = 0; run < 10; run++) {
	        // Evolve the population. Since we don't know what the best answer
	        // is going to be, we just evolve the max number of times.
	        // ---------------------------------------------------------------
	        for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
	          population.evolve();
	          // Add current best fitness to chart.
	          // ----------------------------------
	          double fitness = population.getFittestChromosome().getFitnessValue();
	          if (i % 3 == 0) {
	            String s = String.valueOf(i);
//	            Number n = eval.getValue("Fitness " + permutation, s);
//	            double d;
//	            if (n != null) {
//	              // calculate historical average
//	              d = n.doubleValue() + fitness/(run+1);
//	            }
//	            else {
//	              d = fitness;
//	            }
	            eval.setValue(permutation, run, fitness, "" + permutation, s);
	            eval.storeGenotype(permutation, run, population);
//	            eval.setValue(permutation,run,fitness, new Integer(0), s);
	          }
	        }
	      } 
	      try {
		// Display the best solution we found.
		// -----------------------------------
		IChromosome bestSolutionSoFar = population.getFittestChromosome();
		System.out.println("The best solution has a fitness value of "
				+ bestSolutionSoFar.getFitnessValue());
	      } catch (Exception e){
	    	  System.out.println(e);
	      }
	    }
	    */
	     
	}
}