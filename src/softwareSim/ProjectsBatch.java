package softwareSim;


import java.util.ArrayList;
import java.util.List;

public class ProjectsBatch {

	public List<Project> ProjectsList;
	
	public ProjectsBatch(){
		this.ProjectsList = new ArrayList<Project>();
	}
	
	/**
	 * Load projects from datafile.
	 * @return
	 */
	public boolean loadProjects(){
		return true;
	}
}