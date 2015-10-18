/**
 * 
 */
package softwareSim;

import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

import GeneticAlgorithm.CommunicationStrategy;
import Media.Email;
import Media.MediaType;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
//import repast.simphony.space.continuous.NdPoint;
import repast.simphony.util.ContextUtils;

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

	public boolean isBusy;
	private Task currentTask;
	private Project currentProject;

	Logger log = Logger.getLogger(LoggingMXBean.class.getName());

	public Worker(int _id, Project _project) {
		this.id = _id;
		this.isBusy = false;
		this.currentProject = _project;
	}

	/** Function for main worker functionality - do job. */
	@ScheduledMethod(start = 1, interval = 1)
	public void doJob() {
		// if agent not busy
		if (!isBusy) {

			this.currentTask = selectTask(this.currentProject);
			this.currentProject.Tasks.remove(this.currentTask);

			if (this.currentTask != null) {
				if (this.currentTask.complexity > this.productivity) {

					boolean isProblemOccured = RandomHelper.nextDoubleFromTo(0,
							1) < this.problemsOccurRate ? true : false;

					// TODO: * medium help index
					if (isProblemOccured) {
						double helpRecieved = communicate();
						this.currentTask.percentNotDone = calculatePersentDone(
								this.productivityDecreaseRate, helpRecieved);
					} else {
						this.currentTask.percentNotDone = calculatePersentDone(
								1, 0);
					}

					this.isBusy = true;

					if (this.currentTask.percentNotDone <= 0) {
						this.isBusy = false;

						Object obj = this.currentTask;
						Context<Object> context = ContextUtils.getContext(obj);
						try {
							context.remove(obj);
							log.info("Removed: " + obj.toString());
						} catch (Exception e) {
							log.info("Task is null " + e.toString());
						}

					}
				}
			}
		} else {
			// this.currentTask.percentNotDone = (int)
			// (this.currentTask.percentNotDone - this.productivity);

			boolean isProblemOccured = RandomHelper.nextDoubleFromTo(0, 1) < this.problemsOccurRate ? true
					: false;

			// TODO: * medium help index
			if (isProblemOccured) {
				double helpRecieved = communicate();
				this.currentTask.percentNotDone = calculatePersentDone(
						this.productivityDecreaseRate, helpRecieved);
			} else {
				this.currentTask.percentNotDone = calculatePersentDone(1, 0);
			}

			if (this.currentTask.percentNotDone <= 0) {
				this.isBusy = false;

				Object obj = this.currentTask;
				Context<Object> context = ContextUtils.getContext(obj);
				try {
					context.remove(obj);
					log.info("Removed: " + obj.toString());
				} catch (Exception e) {
					log.info("Task is null " + e.toString());
				}

			}
		}
	}

	private Task selectTask(Project project) {
		if (!project.Tasks.isEmpty()) {
			int id = RandomHelper.nextIntFromTo(0, project.Tasks.size() - 1);
			return project.Tasks.get(id);
		}

		return null;
	}

	/**
	 * Function for communication process between workers.
	 * @return Level of help received.
	 */
	private double communicate() {		
		return CommunicationStrategy.communicate(this.id);
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
	 * Help function. Calculates percent done from the task.
	 * @param _productivityDecreaseRate
	 * @param _helpRecieved
	 * @return
	 */
	private double calculatePersentDone(double _productivityDecreaseRate,
			double _helpRecieved) {
		// TODO: * medium help index
		return this.currentTask.percentNotDone
				- (this.productivity * _productivityDecreaseRate + _helpRecieved);
	}

}
