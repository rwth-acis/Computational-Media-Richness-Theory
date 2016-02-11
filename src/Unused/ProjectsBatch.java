package Unused;


import java.util.ArrayList;
import java.util.List;

import Simulation.Project;

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