package GeneticAlgorithm;

import java.io.File;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import DataLoader.Results;
import Main.MyBatchRunner;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.environment.RunState;
import repast.simphony.util.collections.IndexedIterable;

@SuppressWarnings("serial")
public class WorkEfficiencyFitnessFunction extends FitnessFunction {

	private double bestProductivity;
	private File scenarioFile;
	private String batchFile;

	public WorkEfficiencyFitnessFunction(double _bestProductivity,
			File _scenarioFile, String _batchFile) {
		this.bestProductivity = _bestProductivity;
		this.scenarioFile = _scenarioFile;
		this.batchFile = _batchFile;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub

		MyBatchRunner runner = new MyBatchRunner();

		try {
			runner.load(scenarioFile); // load the repast scenario
		} catch (Exception e) {
			e.printStackTrace();
		}

		double endAt = 100000.0; // some arbitrary end time
		int minActionsCount = 4; // dummy count of actions left in the end

		runner.runInitialize(batchFile); // initialize the run

		RunEnvironment.getInstance().endAt(endAt);

		// add chromosome to the context
		@SuppressWarnings("unchecked")
		Context<GA> c = RunState.getInstance().getMasterContext();
		IndexedIterable<GA> ii = c.getObjects(GA.class);
		GA ga = (GA) ii.get(0);
		ga.currentChromosome = arg0;

		while (runner.getActionCount() > minActionsCount) { // loop until last
															// action is left
			if (runner.getModelActionCount() == 0) {
				runner.setFinishing(true);
			}
			runner.step(); // execute all scheduled actions at next tick
		}

		double fittness = ga.currentFitness;

		String out = String.format(
				">>> currentFitness %s val1 %s val2 %s val3 %s",
				ga.currentFitness, ga.currentChromosome.getGene(0).getAllele(),
				ga.currentChromosome.getGene(1).getAllele(),
				ga.currentChromosome.getGene(2).getAllele());
		System.out.println(out);
/*		*/
		
		Results.getInstance().writeToCSVFile(ga);
		
		ga.currentFitness = 0;

		runner.stop(); // execute any actions scheduled at run end
		runner.cleanUpRun();
		runner.cleanUpBatch(); // run after all runs complete

		// System.out.println(">>> GA instance" + GA.getInstance().toString());

		return fittness;
	}
}
