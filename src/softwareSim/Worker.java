/**
 * 
 */
package softwareSim;

import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

import Media.Email;
import Media.MediaType;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
//import repast.simphony.space.continuous.NdPoint;
import repast.simphony.util.ContextUtils;

/**
 * @author Alex
 * Class, that represents worker in the team.
 * workSpeed - how much worker do per time tick;
 */
public class Worker {

	public int id;
	
	protected AgentTypes experience;
	protected double productivity;
	protected double productivityDecreaseRate;
	protected double problemsOccurRate;
	protected double helpRate; //x medium help index
	
	
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
		if (!isBusy) {

			this.currentTask = selectTask(this.currentProject);
			this.currentProject.Tasks.remove(this.currentTask);
			
			if (this.currentTask != null) {
				if (this.currentTask.complexity > this.productivity) {

					int helpLevel = communicate(this.id);
					
					this.currentTask.percentNotDone = (int) (this.currentTask.percentNotDone
							- this.productivity - helpLevel);
					this.isBusy = true;

					if (this.currentTask.percentNotDone <= 0) {
						this.isBusy = false;

						Object obj = this.currentTask;
						Context<Object> context = ContextUtils.getContext(obj);
						try {
							context.remove(obj);
							log.info("Removed: "+obj.toString());
						} catch (Exception e) {
							log.info("Task is null "+e.toString());
						}

					}
				}
			}
		} else {
			this.currentTask.percentNotDone = (int) (this.currentTask.percentNotDone
					- this.productivity);

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
	
	/** Function for communication process between workers. 
	 * @param askWorkerId - id of the worker who make an request; 
	 * @return Level of help received. */
	private int communicate(int askWorkerId){
		MediaType selectedMedia = selectMedia();
		//TODO: Get list of available connections.
		//TODO: Add logic with whom to communicate, depending on experience level.
		
		return 0; //
	}
	
	/** Function for media selection.
	 * @return Selected MediaType.
	 */
	private MediaType selectMedia(){
		//TODO: Add logic of communication media selection.
		MediaType m = Email.name;
		return MediaType.EMAIL;
	}
	
	/** Function for communication process between workers. 
	 * Constructed for future functionality.  */
	private boolean answer(){
		
		return false;
	}

}
