package GeneticAlgorithm;

import java.io.File;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import DataLoader.DataMediator;
import DataLoader.Results;
import DataLoader.Scenario;
import Main.MyBatchRunner;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.environment.RunState;
import repast.simphony.util.collections.IndexedIterable;
import softwareSim.Project;
import softwareSim.SoftweareSimBuilder;
import softwareSim.Team;


public class WorkEfficiencyFitnessFunction extends FitnessFunction {

	private static final long serialVersionUID = 1L;
	
	private File scenarioFile;
	private String batchFile;
	private Scenario scenario;
	
	public WorkEfficiencyFitnessFunction(File _scenarioFile, String _batchFile) {
		this.scenarioFile = _scenarioFile;
		this.batchFile = _batchFile;
		this.scenario = Main.Main.scenario;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected double evaluate(IChromosome chromosome) {
		MyBatchRunner runner = new MyBatchRunner();

		try {
			runner.load(scenarioFile); // load the repast scenario
		} catch (Exception e) {
			e.printStackTrace();
		}

		double endAt = this.scenario.maxAllowedSteps; // some arbitrary end time, that shouldn't be exceeded 
		int minActionsCount = 6; // dummy count of actions left in the end

		runner.runInitialize(batchFile); // initialize the run

		RunEnvironment.getInstance().endAt(endAt);
	
		// add DataMediator to the context
		Context<DataMediator> c = RunState.getInstance().getMasterContext();
		IndexedIterable<DataMediator> ii = c.getObjects(DataMediator.class);
		DataMediator dm = (DataMediator) ii.get(0);
		dm.SetChromosome(chromosome, Main.Main.conf.MediaTypes);

		// add workers and project
		
		Project project = scenario.getProject();		
		Team team = scenario.getTeam();
		
		Context<SoftweareSimBuilder> s = RunState.getInstance().getMasterContext();
		IndexedIterable<SoftweareSimBuilder> ii2 = s.getObjects(SoftweareSimBuilder.class);
		SoftweareSimBuilder ssb = (SoftweareSimBuilder) ii2.get(0);
		ssb.addAgents(team, project);
		
		while (runner.getActionCount() > minActionsCount) { // loop until last
															// action is left
			if (runner.getModelActionCount() == 0) {
				runner.setFinishing(true);
			}
			runner.step(); // execute all scheduled actions at next tick
		}

		double fittness = dm.currentFitness;

		String out = String.format(
				">>> currentFitness %s ",
				fittness);
		System.out.println(out);
		/**/
		
		Results.getInstance().writeToCSVFile(dm);
		
		dm.currentFitness = 0;

		runner.stop(); // execute any actions scheduled at run end
		runner.cleanUpRun();
		runner.cleanUpBatch(); // run after all runs complete

		// System.out.println(">>> GA instance" + GA.getInstance().toString());

		return fittness;
	}
}
