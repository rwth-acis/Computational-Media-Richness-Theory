package Main;

import org.jgap.InvalidConfigurationException;

import DataLoader.JsonSerializer;
import DataLoader.Scenario;
import GeneticAlgorithm.GAConfig;

public class Main {

	public static GAConfig conf;
	public static Scenario scenario;

	public static void main(String[] args) throws InvalidConfigurationException {
		// scenario path from args
		Main.scenario = (Scenario) JsonSerializer.deserialize(args[1],
				Scenario.class);
		
		if (Main.scenario.simulationType.equals("MonteCarlo")) {
			//run MonteCarlo simulation
			MonteCarlo.main(args);
		} else if (Main.scenario.simulationType.equals("GA")) {
			//run GA simulation
			GeneticAlgorithm.main(args);
		} else {
			System.out.println("Simulation type is not recognized!");
		}
	}
}