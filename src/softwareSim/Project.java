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
		
		
}
