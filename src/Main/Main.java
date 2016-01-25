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
import DataLoader.Results;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

	private static final int MAX_ALLOWED_EVOLUTIONS = 100;
	public static GAConfig conf;
	
	public static void main(String[] args) throws InvalidConfigurationException {

		boolean useMC = false;
		if (useMC) {
			MonteCarlo.main(args);
		} else {
			File scenarioFile = new File(args[0]); // the scenario dir
			String batchFile = "C:/Users/Alex/workspace/SoftwareSim/batch_external/batch_params.xml";
			// ---------------------------------------------------

			Main.conf = new GAConfig();
			FitnessFunction myFunc = new WorkEfficiencyFitnessFunction(0,
					scenarioFile, batchFile);
			Main.conf.setFitnessFunction(myFunc);
			Genotype population = Genotype.randomInitialGenotype(Main.conf);

			for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
				population.evolve();

				IChromosome bestSolutionSoFar = population
						.getFittestChromosome();
				System.out.println("The best solution has a fitness value of "
						+ bestSolutionSoFar.getFitnessValue());

			}

			IChromosome bestSolutionSoFar = population.getFittestChromosome();
			String out = String.format(
					">>> currentFitness %s val1 %s val2 %s val3 %s",
					bestSolutionSoFar.getFitnessValue(), bestSolutionSoFar
							.getGene(0).getAllele()
			// , bestSolutionSoFar.getGene(1).getAllele(),
			// bestSolutionSoFar.getGene(2).getAllele()
					);
			System.out.println(out);

		}
	}
}