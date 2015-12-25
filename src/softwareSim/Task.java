/**
 * 
 */
package softwareSim;

/**
 * @author Alex
 *
 */
public class Task {
	public int complexity;
	public double percentNotDone;
	public double deviation;
	
	public Task(int taskComplexity) {
		this.complexity = taskComplexity;
		this.percentNotDone = 100 * this.complexity;
	}
}


