package Main;

import org.jgap.FitnessFunction;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import DataLoader.JsonSerializer;
import DataLoader.Scenario;
import GeneticAlgorithm.GAConfig;
import GeneticAlgorithm.WorkEfficiencyFitnessFunction;

import java.io.File;

public class Main {

	private static int MAX_ALLOWED_EVOLUTIONS;
	public static GAConfig conf;
	public static Scenario scenario;

	public static void main(String[] args) throws InvalidConfigurationException {
		// scenario path from args
		Main.scenario = (Scenario) JsonSerializer.deserialize(args[1], Scenario.class);
		MAX_ALLOWED_EVOLUTIONS = Main.scenario.maxAllowedEvolutions;

		if (Main.scenario.simulationType.equals("MonteCarlo")) {
			MonteCarlo.main(args);
		} else if (Main.scenario.simulationType.equals("GA")) {
			File scenarioFile = new File(args[0]); // the scenario dir
			String batchFile = "C:/Users/Alex/workspace/SoftwareSim/batch_external/batch_params.xml";
			// ---------------------------------------------------

			Main.conf = new GAConfig();
			FitnessFunction myFunc = new WorkEfficiencyFitnessFunction(
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

		} else {
			System.out.println("Simulation type is not recognized!");
		}
	}
}