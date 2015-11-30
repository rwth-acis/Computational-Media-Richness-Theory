/**
 * 
 */
package softwareSim;

import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

import CommunicationModel.CommunicationEffects;
import GeneticAlgorithm.CommunicationStrategy;
import GeneticAlgorithm.GA;
import Media.AMedia;
import Media.Email;
import Media.MediaType;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
//import repast.simphony.space.continuous.NdPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.collections.IndexedIterable;

/**
 * @author Alex Class, that represents worker in the team. workSpeed - how much
 *         worker do per time tick;
 */
public class Worker {

	public int id;

	protected AgentTypes experience = AgentTypes.DEFAULT;
	protected double productivity = VariablesSettings
			.getAgentProductivity(this.experience);
	protected double productivityDecreaseRate = VariablesSettings
			.getAgentProductivityDecreaseRate(this.experience);
	protected double problemsOccurRate = VariablesSettings
			.getAgentProblemOccurRate(this.experience);
	protected double helpRate = VariablesSettings
			.getAgentHelpRate(this.experience); // * medium help index

	private CommunicationEffects communicationEffect;

	public boolean isBusy;
	private Task currentTask;
	private Project currentProject;
	private double leftProductivity;// pr. left from previous step

	Logger log = Logger.getLogger(LoggingMXBean.class.getName());

	public Worker(int _id, Project _project) {
		this.id = _id;
		this.isBusy = false;
		this.currentProject = _project;
		this.communicationEffect = new CommunicationEffects();
		this.leftProductivity = 0;
	}

	/**
	 * Function for main worker functionality - do job with help of the
	 * calculated communication effects.
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 1)
	public void doJob() {

		double helpRecieved = this.communicationEffect.calculateEffect()
				+ this.leftProductivity;// communicate();

		// if agent not busy
		if (!isBusy) {
			this.currentTask = selectTask(this.currentProject);
			this.currentProject.Tasks.remove(this.currentTask);

			if (this.currentTask != null) {
				if (this.currentTask.complexity > this.productivity) {

					boolean isProblemOccured = RandomHelper.nextDoubleFromTo(0,
							1) < this.problemsOccurRate ? true : false;

					// if (isProblemOccured) {

					this.currentTask.percentNotDone = calculatePersentNotDone(
							this.productivityDecreaseRate, helpRecieved);

					this.isBusy = true;

					if (this.currentTask.percentNotDone <= 0) {
						this.isBusy = false;

						Object obj = this.currentTask;
						Context<Object> context = ContextUtils.getContext(obj);
						try {
							// remove task from tasks list
							context.remove(obj);
							// log.info("Removed: " + obj.toString());
						} catch (Exception e) {
							log.info("Task is null " + e.toString());
						}

					}
				}
			}
		} else {
			// this.currentTask.percentNotDone = (int)
			// (this.currentTask.percentNotDone - this.productivity);
			this.leftProductivity = 0; // reset left pr.

			boolean isProblemOccured = RandomHelper.nextDoubleFromTo(0, 1) < this.problemsOccurRate ? true
					: false;
			// if (isProblemOccured) {

			this.currentTask.percentNotDone = calculatePersentNotDone(
					this.productivityDecreaseRate, helpRecieved);

			if (this.currentTask.percentNotDone <= 0) {
				this.isBusy = false;

				Object obj = this.currentTask;
				Context<Object> context = ContextUtils.getContext(obj);
				try {
					// remove task from tasks list
					context.remove(obj);
					// log.info("Removed: " + obj.toString());
				} catch (Exception e) {
					log.info("Task is null " + e.toString());
				}

				if (this.currentTask.percentNotDone < 0) {
					this.leftProductivity = -this.currentTask.percentNotDone;
				}

			}
		}

		this.communicationEffect.clear();
	}

	/**
	 * Calculate communication effects. First step is always calculate
	 * communications for step and then do job with help of the calculated
	 * values.
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 2)
	public void communicate() {
		CommunicationStrategy.communicate(this.id, this.communicationEffect);
	}

	private Task selectTask(Project project) {
		if (!project.Tasks.isEmpty()) {
			int id = RandomHelper.nextIntFromTo(0, project.Tasks.size() - 1);
			return project.Tasks.get(id);
		}

		return null;
	}

	/**
	 * Function for communication process between workers. Constructed for
	 * future functionality.
	 */
	@SuppressWarnings("unused")
	private boolean answer() {
		return false;
	}

	/**
	 * Help function. Calculates percent not done from the task.
	 * 
	 * @param _productivityDecreaseRate
	 * @param _helpRecieved
	 * @return
	 */
	private double calculatePersentNotDone(double _productivityDecreaseRate,
			double _helpRecieved) {
		// return this.currentTask.percentNotDone - 1;
		// TODO: * medium help index
		double p = this.currentTask.percentNotDone
				- (this.productivity * _productivityDecreaseRate + _helpRecieved);
		// log.info(">>> percent not done: "+p);
		return p;/**/
	}
}
