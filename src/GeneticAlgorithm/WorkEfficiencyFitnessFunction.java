package GeneticAlgorithm;

import java.io.File;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import DataLoader.DataMediator;
import DataLoader.Results;
import Main.MyBatchRunner;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.environment.RunState;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.collections.IndexedIterable;
import softwareSim.Junior;
import softwareSim.Project;
import softwareSim.SoftweareSimBuilder;
import softwareSim.Task;
import softwareSim.Team;
import softwareSim.Worker;

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
	protected double evaluate(IChromosome chromosome) {
		MyBatchRunner runner = new MyBatchRunner();

		try {
			runner.load(scenarioFile); // load the repast scenario
		} catch (Exception e) {
			e.printStackTrace();
		}

		double endAt = 200000.0; // some arbitrary end time
		int minActionsCount = 4; // dummy count of actions left in the end

		runner.runInitialize(batchFile); // initialize the run

		RunEnvironment.getInstance().endAt(endAt);
	
		// add DataMediator to the context
		Context<DataMediator> c = RunState.getInstance().getMasterContext();
		IndexedIterable<DataMediator> ii = c.getObjects(DataMediator.class);
		DataMediator dm = (DataMediator) ii.get(0);
		dm.SetChromosome(chromosome);

		Project project = new Project();
		int tasksCount = 1000;// (Integer) params.getValue("human_count");
		for (int i = 0; i < tasksCount; i++) {
			int taskComplexity = RandomHelper.nextIntFromTo(2,2);
			Task t = new Task(taskComplexity);
			project.Tasks.add(t);
		}
		
		Team team = new Team(project.Complexity());
		int workerCount = 7;// (Integer) params.getValue("human_count");
		for (int i = 0; i < workerCount; i++) {
			// int eperienceLevel = RandomHelper.nextIntFromTo(1, 10);
			Worker w = new Junior(i, project);
			team.addWorker(w);
		}
		
		// add workers
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

/*		String out = String.format(
				">>> currentFitness %s val1 %s val2 %s val3 %s",
				ga.currentFitness, ga.currentChromosome.getGene(0).getAllele(),
				ga.currentChromosome.getGene(1).getAllele(),
				ga.currentChromosome.getGene(2).getAllele());
		System.out.println(out);
		*/
		
		Results.getInstance().writeToCSVFile(dm);
		
		dm.currentFitness = 0;

		runner.stop(); // execute any actions scheduled at run end
		runner.cleanUpRun();
		runner.cleanUpBatch(); // run after all runs complete

		// System.out.println(">>> GA instance" + GA.getInstance().toString());

		return fittness;
	}
}
