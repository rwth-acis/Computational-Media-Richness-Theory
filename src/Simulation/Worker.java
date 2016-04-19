package Simulation;

import CommunicationModel.CommunicationEffects;
import CommunicationModel.CommunicationStrategy;
import DataLoader.DataMediator;
import Media.AMedia;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunState;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.collections.IndexedIterable;

/**
 * Class, that represents worker in the team.
 * 
 * @author Alex
 */
public class Worker {

	public int id; // unique id of the worker

	protected AgentTypes experience = AgentTypes.DEFAULT;
	protected transient double productivity;
	//protected transient double productivityDecreaseRate;
	//protected transient double problemsOccurRate;
	//protected transient double helpRate; // * medium help index
	protected transient boolean isBusy;
	
	private transient CommunicationEffects communicationEffect;
	private transient Task currentTask;
	private transient Project currentProject;
	private transient double leftProductivity; // pr. left from previous step
	private transient boolean isInitialized;

	/**
	 * Agent have few knowledge areas, where it has some knowledge.
	 */
	//public transient int[] knowledgeAreas;

	/**
	 * Agent have a need in some knowledge, to solve a task.
	 */
	//public transient int knowledgeAreasNeed;

	public transient DataMediator dataMediator;

	public Worker(int _id) {
		this.id = _id;
		this.isInitialized = false;
	}

	/**
	 * Function for main worker functionality - do job with help of the
	 * calculated communication effects.
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 1)
	public void doJob() {		
		//calculate effects
		double ne = this.communicationEffect.effect();
		
		// if agent not busy
		if (!isBusy) {
			this.currentTask = selectTask(this.currentProject);
		}

		if (this.currentTask != null) {
			this.currentTask.complexity -= this.leftProductivity;
			this.leftProductivity = 0; // reset left pr.

			if (this.currentTask.partsNotDone > this.productivity) {

				// boolean isProblemOccured = RandomHelper.nextDoubleFromTo(0,
				// 1) < this.problemsOccurRate ? true : false;

				/*
				 * if (isProblemOccured) { //run with problem
				 * this.currentTask.percentNotDone = calculatePersentNotDone(
				 * this.productivityDecreaseRate, helpRecieved); } else { //run
				 * without problem this.currentTask.percentNotDone =
				 * calculatePersentNotDone(1, helpRecieved); }
				 */

				this.currentTask.complexity = calculatePersentNotDone(ne);

				this.isBusy = true;

				if (this.currentTask.complexity <= 0) {
					this.isBusy = false;
					/*
					 * Object obj = this.currentTask;
					 * 
					 * @SuppressWarnings("unchecked") Context<Object> context =
					 * ContextUtils.getContext(obj); try { // remove task from
					 * tasks list context.remove(obj); // log.info("Removed: " +
					 * obj.toString()); } catch (Exception e) {
					 * log.info("Task is null " + e.toString()); }
					 */

					if (this.currentTask.complexity < 0) {
						this.leftProductivity = -this.currentTask.complexity;
					}
				}
			}
		}

		this.communicationEffect.clear();
	}

	/**
	 * Calculate communication effects. At first always calculated
	 * communications for step and then do job with help of the calculated
	 * values.
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 2)
	public void communicate() {
		CommunicationStrategy.communicate(this.id, this.communicationEffect);
	}

	/**
	 * Set random knowledge areas, on each step.
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 3)
	public void setKnowledgeAreas() {
		if (!this.isInitialized) {
			this.initialize();
			this.isInitialized = true;
		}
		
		/*for (int i = 0; i < this.knowledgeAreas.length; i++) {
			this.knowledgeAreas[i] = (int) (Math.random() * this.dataMediator.allTopics.length);
		}*/
		
		this.communicationEffect = new CommunicationEffects(this);
	}

	/**
	 * Select next task for agent.
	 * 
	 * @param project
	 * @return
	 */
	private Task selectTask(Project project) {
		if (!project.Tasks.isEmpty()) {
			int id = RandomHelper.nextIntFromTo(0, project.Tasks.size() - 1);
			Task t = project.Tasks.get(id);
			project.Tasks.remove(id); // exclude task from project
			return t;
		}
		return null;
	}

	/**
	 * Function for communication process between workers.
	 * 
	 * @param media
	 * @return
	 */
	public int answer(AMedia media) {
		return this.communicationEffect.answer(media);
	}

	/**
	 * Help function. Calculates percent not done from the task.
	 * 
	 * @param effect
	 * @return percent not done from the task
	 */
	private double calculatePersentNotDone(double effect) {
		double e = this.productivity + this.productivity * effect;

		double p = this.currentTask.complexity - e;
		return p;
	}

	/**
	 * Function to be called only once.
	 */
	private void initialize() {
		this.isBusy = false;
		this.currentProject = null;		
		this.leftProductivity = 0;
		
		//this.knowledgeAreas = new int[VariablesSettings
		//		.numberOfKnowledgeAreas(this.experience)];
		//this.knowledgeAreasNeed = VariablesSettings.numberOfKnowledgeAreasNeed;

		@SuppressWarnings("unchecked")
		Context<DataMediator> c = RunState.getInstance().getMasterContext();
		IndexedIterable<DataMediator> ii = c.getObjects(DataMediator.class);
		this.dataMediator = (DataMediator) ii.get(0);
		
		@SuppressWarnings("unchecked")
		Context<Project> co = RunState.getInstance().getMasterContext();
		IndexedIterable<Project> pii = co.getObjects(Project.class);
		this.currentProject = (Project) pii.get(0);
		
		experience = AgentTypes.DEFAULT;
		productivity = VariablesSettings
				.getAgentProductivity(this.experience);
		/*
		productivityDecreaseRate = VariablesSettings
				.getAgentProductivityDecreaseRate(this.experience);
		problemsOccurRate = VariablesSettings
				.getAgentProblemOccurRate(this.experience);
		helpRate = VariablesSettings
				.getAgentHelpRate(this.experience);
				*/
	}
}
