package Main;

import java.io.File;
import java.util.List;

import org.jgap.FitnessFunction;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;

import GeneticAlgorithm.GAConfig;
import GeneticAlgorithm.WorkEfficiencyFitnessFunction;
import Media.MediaType;

/**
 * Class, that initializes and runs Genetic algorithm simulation.
 * @author Alex
 */
public class GeneticAlgorithm {
	public static void main(String[] args) throws InvalidConfigurationException {

		File scenarioFile = new File(args[0]); // the scenario dir
		String batchFile = "C:/Users/Alex/workspace/SoftwareSim/batch_external/batch_params.xml";
		int MAX_ALLOWED_EVOLUTIONS = Main.scenario.maxAllowedEvolutions;
		
		for (String teamPath : Main.scenario.teamPaths) {
			for (String projectPath : Main.scenario.projectPaths) {
				for (String mediasPath : Main.scenario.mediaPaths) {
					List<MediaType> mt = Main.scenario.getMedias(mediasPath);

					Main.conf = new GAConfig("", "", mt);
					Main.conf.projectPath = projectPath;
					Main.conf.teamPath = teamPath;

					FitnessFunction myFunc = new WorkEfficiencyFitnessFunction(
							scenarioFile, batchFile);
					Main.conf.setFitnessFunction(myFunc);
					Genotype population = Genotype
							.randomInitialGenotype(Main.conf);

					for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
						population.evolve();

						IChromosome bestSolutionSoFar = population
								.getFittestChromosome();
						System.out
								.println("The best solution has a fitness value of "
										+ bestSolutionSoFar.getFitnessValue());
					}

					IChromosome bestSolutionSoFar = population
							.getFittestChromosome();
					String out = String.format(
							">>> currentFitness %s val1 %s val2 %s val3 %s",
							bestSolutionSoFar.getFitnessValue(),
							bestSolutionSoFar.getGene(0).getAllele()
					// , bestSolutionSoFar.getGene(1).getAllele(),
					// bestSolutionSoFar.getGene(2).getAllele()
							);
					System.out.println(out);
				}
			}
		}
	}
}