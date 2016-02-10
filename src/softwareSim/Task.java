package softwareSim;

/**
 * Class, that represents task in the project.
 * @author Alex
 */
public class Task {
	/**
	 * Complexity of the task.
	 */
	public double complexity;
	/**
	 * Support value, that used to calculate does task is done.
	 */
	public double partsNotDone;
	//public double deviation;
	
	public Task(double taskComplexity) {
		this.complexity = taskComplexity;
		this.partsNotDone = taskComplexity;
	}
}