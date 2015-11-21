package softwareSim;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Team {

	public TreeMap<Integer, Integer> adjacencyList; //sorted tree
	public List<Worker> workers;
	/**
	 * Productivity of the team for a given project without communication. Returns in how many steps project will be ended.
	 */
	public double Productivity;
	
	public Team(int projectComplexity){
		this.workers = new ArrayList<Worker>();
		this.Productivity = initialProductivity(projectComplexity);
	}
	
	public void addWorker(Worker worker){
		this.workers.add(worker);
	}
	
	public void deleteWorker(Worker worker){
		this.workers.remove(worker);
	}
	
	/**
	 * Productivity of the team for a given project without communication. Returns in how many steps project will be ended.
	 * @param projectComplexity
	 * @return
	 */
	private double initialProductivity(int projectComplexity){
		
		double collectiveProductivity = 0;
		for(Worker w : workers){
			collectiveProductivity = collectiveProductivity + w.productivity * (1 - w.problemsOccurRate) + (w.productivityDecreaseRate * w.productivity * w.problemsOccurRate);
		}
	
		return projectComplexity / collectiveProductivity;
	}
	
	/**
	 * Recalculate productivity of the team for a given project without communication. Returns in how many steps project will be ended.
	 * @param projectComplexity
	 * @return
	 */
	public void recalculateProductivity(int projectComplexity){
		this.Productivity = initialProductivity(projectComplexity);
	}
	
	
	/**
	 * Check does somebody still works.
	 * @return
	 */
	public boolean allWorkIsEnded(){
		
		for(Worker w : workers ){
			if(w.isBusy)
				return false;
		}
		
		return true;
	} 
}
