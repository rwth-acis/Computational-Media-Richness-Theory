/**
 * 
 */
package softwareSim;

import java.io.Console;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

import GeneticAlgorithm.GA;

import com.sun.media.Log;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.graph.DirectedJungNetwork;
import repast.simphony.util.ContextUtils;

/**
 * @author Alex
 *
 */
public class SoftweareSimBuilder implements ContextBuilder<Object> {

	private Project project;
	private Team team;
	Logger log = Logger.getLogger(LoggingMXBean.class.getName());
	ISchedule schedule;

	@Override
	public Context<Object> build(Context<Object> context) {
		// System.out.println(">>> started");

		context.setId("SoftwareSim");

		// make schedule
		RunEnvironment runEnv = RunEnvironment.getInstance();
		schedule = runEnv.getCurrentSchedule();
		
		schedule.schedule(ScheduleParameters.createRepeating(0, 1,
		  ScheduleParameters.LAST_PRIORITY), this, "checkIfSimFinished");
	

		schedule.schedule(ScheduleParameters
				.createAtEnd(ScheduleParameters.FIRST_PRIORITY), this,
				"endAction");

		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder
				.createContinuousSpaceFactory(null);
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace(
				"space", context, new RandomCartesianAdder<Object>(),
				new repast.simphony.space.continuous.WrapAroundBorders(), 50,
				50);

		// INIT program
		project = new Project();
		int tasksCount = 20;// (Integer) params.getValue("human_count");
		for (int i = 0; i < tasksCount; i++) {
			int taskComplexity = RandomHelper.nextIntFromTo(1000000, 10000000);
			Task t = new Task(taskComplexity);
			project.Tasks.add(t);

			context.add(t); // add agent to the context
		}
		context.add(project);

		log.info("Tasks: " + Integer.toString(project.Tasks.size()));

		team = new Team(project.Complexity());

		int workerCount = 7;// (Integer) params.getValue("human_count");
		for (int i = 0; i < workerCount; i++) {
			// int eperienceLevel = RandomHelper.nextIntFromTo(1, 10);
			Worker w = new Junior(i, project);
			team.addWorker(w);

			context.add(w); // add agent to the context
		}

		
		 if (RunEnvironment.getInstance().isBatch()) {
		 RunEnvironment.getInstance().endAt(10000); }
		 

		return context;
	}

	public void checkIfSimFinished() {
		if (project.Tasks.size() <= 0 && team.allWorkIsEnded()) {
			RunEnvironment.getInstance().endRun();
		}
	}

	public void endAction() {
		RunEnvironment runenv = RunEnvironment.getInstance();
		String out = String.format(">>> Took %d ticks", (int) runenv
				.getCurrentSchedule().getTickCount());
		System.out.println(out);	
	}
}
