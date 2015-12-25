package softwareSim;

import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

import CommunicationModel.CommunicationEffects;
import CommunicationModel.CommunicationStrategy;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.ContextUtils;


/**
 * Class, that represents worker in the team.
 * @author Alex 
 */
public class Worker {

	public int id; //unique id of the worker

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
	private double leftProductivity; // pr. left from previous step

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

		double helpRecieved = this.communicationEffect.calculateEffect();// communicate();

		// if agent not busy
		if (!isBusy) {
			this.currentTask = selectTask(this.currentProject);
			this.currentProject.Tasks.remove(this.currentTask); //exclude project from tasks
		}
		
			if (this.currentTask != null) {
				this.currentTask.percentNotDone -= this.leftProductivity;
				this.leftProductivity = 0; // reset left pr.
				
				if (this.currentTask.complexity > this.productivity) {

					boolean isProblemOccured = RandomHelper.nextDoubleFromTo(0,
							1) < this.problemsOccurRate ? true : false;

					/* if (isProblemOccured) {
					 //run with problem
					  this.currentTask.percentNotDone = calculatePersentNotDone(
							this.productivityDecreaseRate, helpRecieved);
					} else {
						//run without problem
						 this.currentTask.percentNotDone = calculatePersentNotDone(1, helpRecieved);
					}
					*/

					this.currentTask.percentNotDone = calculatePersentNotDone(
							this.productivityDecreaseRate, helpRecieved);

					this.isBusy = true;

					if (this.currentTask.percentNotDone <= 0) {
						this.isBusy = false;

						Object obj = this.currentTask;
						@SuppressWarnings("unchecked")
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
		// TODO: * medium help index
	/*	double p = this.currentTask.percentNotDone
				- (this.productivity * _productivityDecreaseRate + _helpRecieved);
		*/
		double e = this.productivity * _productivityDecreaseRate * _helpRecieved;
		
		double p = this.currentTask.percentNotDone - e;
		//if(this.communicationEffect.communicationFrequency == 20 )
		//log.info(String.format(">>> worker: %s %s %s %s %s",p, this.productivity, _productivityDecreaseRate, _helpRecieved, this.currentTask.percentNotDone));
		return p;
	}
}
