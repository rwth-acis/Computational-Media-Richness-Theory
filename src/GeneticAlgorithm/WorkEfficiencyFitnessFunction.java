package GeneticAlgorithm;

import java.io.File;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import Main.MyBatchRunner;
import repast.simphony.engine.environment.RunEnvironment;

@SuppressWarnings("serial")
public class WorkEfficiencyFitnessFunction extends FitnessFunction {

	private double bestProductivity;
	private File scenariofile;
	private String batchFile;

	public WorkEfficiencyFitnessFunction(double _bestProductivity,
			File _scenariofile, String _batchFile) {
		this.bestProductivity = _bestProductivity;
		this.scenariofile = _scenariofile;
		this.batchFile = _batchFile;
	}

	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub

		MyBatchRunner runner = new MyBatchRunner();

		try {
			runner.load(scenariofile); // load the repast scenario
		} catch (Exception e) {
			e.printStackTrace();
		}

		double endAt = 2000.0; // some arbitrary end time
		int minActionsCount = 3; // dummy count of actions left in the end

		runner.runInitialize(batchFile); // initialize the run

		RunEnvironment.getInstance().endAt(endAt);

		while (runner.getActionCount() > minActionsCount) { // loop until last
															// action is left
			if (runner.getModelActionCount() == 0) {
				runner.setFinishing(true);
			}
			runner.step(); // execute all scheduled actions at next tick
		}

		runner.stop(); // execute any actions scheduled at run end
		runner.cleanUpRun();

		runner.cleanUpBatch(); // run after all runs complete

		// TODO close

		return 0;
	}
}
