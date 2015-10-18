/**
 * 
 */
package softwareSim;

import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

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


/**
 * @author Alex
 *
 */
public class SoftweareSimBuilder implements ContextBuilder<Object> {

	private Project project;
	
	@Override
	public Context<Object> build(Context<Object> context) {
		context.setId("SoftwareSim");
		
		//make schedule
		RunEnvironment runEnv = RunEnvironment.getInstance();
		ISchedule schedule = runEnv.getCurrentSchedule();
		schedule.schedule(ScheduleParameters.createRepeating(0, 5,
		ScheduleParameters.FIRST_PRIORITY), this,
		"checkIfSimFinished");
		
		
		Logger log = Logger.getLogger(LoggingMXBean.class.getName());

		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder
				.createContinuousSpaceFactory(null);
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace(
				"space", context, new RandomCartesianAdder<Object>(),
				new repast.simphony.space.continuous.WrapAroundBorders(), 50,
				50);

		//INIT program
		project = new Project();
		int tasksCount = 200;// (Integer) params.getValue("human_count");
		for (int i = 0; i < tasksCount; i++) {
			int taskComplexity = RandomHelper.nextIntFromTo(1, 100);
			Task t = new Task(taskComplexity);
			project.Tasks.add(t);
			
			context.add(t); //add agent to the context
		}
		context.add(project);

		log.info("Tasks: " + Integer.toString(project.Tasks.size()));

		Team team = new Team();
		
		int workerCount = 10;// (Integer) params.getValue("human_count");
		for (int i = 0; i < workerCount; i++) {
			//int eperienceLevel = RandomHelper.nextIntFromTo(1, 10);			
			Worker w = new Junior(i, project);
			team.addWorker(w);
			
			context.add(w); //add agent to the context
		}

		if (RunEnvironment.getInstance().isBatch()) {
			RunEnvironment.getInstance().endAt(20);
		}

		return context;
	}

	public void checkIfSimFinished() {

		if (project.Tasks.size() <= 0) {
			RunEnvironment.getInstance().endRun();
		}
	}
}
