package Simulation;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity, that represents project.
 * @author Alex 
 */
public class Project {

	/**
	 * Unique id of the project.
	 */
	public String id;
	
	/**
	 * List of tasks.
	 */
	public List<Task> Tasks;
	//public double deviation;

	public Project() {
		//this.deviation = 0;
		this.Tasks = new ArrayList<Task>();
	}

	/**
	 * Calculate complexity of the project.
	 */
	public double Complexity() {
		double complexity = 0;
		for (Task task : Tasks) {
			complexity = complexity + task.complexity;
		}
		return complexity;
	}
}
