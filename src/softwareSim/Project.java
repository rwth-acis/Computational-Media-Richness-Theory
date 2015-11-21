/**
 * 
 */
package softwareSim;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex
 * Entity, that represents project.
 */
public class Project {

		public List<Task> Tasks;
		public double deviation;
		
		public Project(){
			this.deviation = 0;
			this.Tasks = new ArrayList<Task>();
		}
		
		/**
		 * Calculate complexity of the project.
		 */
		public int Complexity(){
			int complexity = 0;
			for (Task task : Tasks) {
				complexity = complexity + task.complexity;
			}
			return complexity;
		}
}
