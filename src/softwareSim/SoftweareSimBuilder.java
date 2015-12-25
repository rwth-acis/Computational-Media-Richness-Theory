package softwareSim;

import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;
import DataLoader.DataMediator;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;

/**
 * @author Alex
 *
 */
public class SoftweareSimBuilder implements ContextBuilder<Object> {

	private Project project;
	private Team team;
	private Context<Object> thisContext;

	Logger log = Logger.getLogger(LoggingMXBean.class.getName());
	ISchedule schedule;

	@Override
	public Context<Object> build(Context<Object> context) {
		// System.out.println(">>> started");
		context.setId("SoftwareSim");

		this.thisContext = context;

		// make schedule
		RunEnvironment runEnv = RunEnvironment.getInstance();
		schedule = runEnv.getCurrentSchedule();

		schedule.schedule(ScheduleParameters.createRepeating(0, 1,
				ScheduleParameters.LAST_PRIORITY), this, "checkIfSimFinished");

		schedule.schedule(ScheduleParameters
				.createAtEnd(ScheduleParameters.FIRST_PRIORITY), this,
				"endAction");

		/*
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder
				.createContinuousSpaceFactory(null);
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace(
				"space", context, new RandomCartesianAdder<Object>(),
				new repast.simphony.space.continuous.WrapAroundBorders(), 50,
				50);
				*/

		dm = new DataMediator();
		context.add(dm);

		context.add(this); // Add SoftwareSimBuilder to access functions over context

		if (RunEnvironment.getInstance().isBatch()) {
			RunEnvironment.getInstance().endAt(200000);
		}

		return context;
	}

	/** DataMediator for global data exchange. */
	public DataMediator dm;

	/**
	 * Add agents to the context.
	 * @param _team
	 * @param _project
	 */
	public void addAgents(Team _team, Project _project) {
		this.project = _project;
		this.team = _team;
		
		for (Task t : _project.Tasks) {
			this.thisContext.add(t); // add agent to the context
		}
		
		for (Worker w : _team.workers) {
			this.thisContext.add(w); // add agent to the context
		}
		
		this.thisContext.add(_project); // add agent to the context		
	}

	/** End simulation. */
	public void checkIfSimFinished() {
		if (project.Tasks.size() <= 0 && team.allWorkIsEnded()) {
			RunEnvironment.getInstance().endRun();
		}
	}

	/** Print info, when simulation is finished. */
	public void endAction() {
		RunEnvironment runenv = RunEnvironment.getInstance();
		//String out = String.format(">>> Took %d ticks", (int) runenv
		//		.getCurrentSchedule().getTickCount());
		// System.out.println(out);

		this.dm.currentFitness = runenv.getCurrentSchedule().getTickCount();
	}
}
