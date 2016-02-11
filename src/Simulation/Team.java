package Simulation;

import java.util.ArrayList;
import java.util.List;

public class Team {

	/**
	 * Unique team id.
	 */
	public String id;
	
	public List<Pair<Integer, Integer>> adjacencyList; // sorted tree
	
	public List<Worker> workers;
	/**
	 * Productivity of the team for a given project without communication.
	 * Returns in how many steps project will be ended.
	 */
	public transient double Productivity;

	public Team(int projectComplexity, String _id) {
		this.workers = new ArrayList<Worker>();
		this.Productivity = initialProductivity(projectComplexity);
		this.id = _id;
	}

	public void addWorker(Worker worker) {
		this.workers.add(worker);
	}

	public void deleteWorker(Worker worker) {
		this.workers.remove(worker);
	}

	/**
	 * Productivity of the team for a given project without communication.
	 * Returns in how many steps project will be ended.
	 * 
	 * @param projectComplexity
	 * @return
	 */
	private double initialProductivity(int projectComplexity) {

		double collectiveProductivity = 0;
		for (Worker w : workers) {
			collectiveProductivity = collectiveProductivity
					+ w.productivity
					//* (1 - w.problemsOccurRate)
					//+ (w.productivityDecreaseRate * w.productivity * w.problemsOccurRate)
					;
		}

		if(collectiveProductivity == 0){
			return 0;
		}
		return projectComplexity / collectiveProductivity;
	}

	/**
	 * Recalculate productivity of the team for a given project without
	 * communication. Returns in how many steps project will be ended.
	 * 
	 * @param projectComplexity
	 * @return
	 */
	public void recalculateProductivity(int projectComplexity) {
		this.Productivity = initialProductivity(projectComplexity);
	}

	/**
	 * Check does somebody still works.
	 * 
	 * @return
	 */
	public boolean allWorkIsEnded() {

		for (Worker w : workers) {
			if (w.isBusy)
				return false;
		}
		return true;
	}

	/**
	 * Return list of pairs where first value is id of requested worker and the second value is id of directly connected worker.
	 * @param whoId - id of requested worker
	 * @param adjacencyList - list of connections
	 * @return
	 */
	public static List<Pair<Integer, Integer>>  neighborList(int whoId, List<Pair<Integer, Integer>> adjacencyList){
		List<Pair<Integer, Integer>> neighborList = new ArrayList<Pair<Integer, Integer>>();
		
		for (int i = 0; i < adjacencyList.size(); i++) {
			if(adjacencyList.get(i).getLeft() == whoId)
			{
				neighborList.add(adjacencyList.get(i));
			}
		}
		return neighborList;
	}
	
	/**
	 * Get worker by id.
	 * @param id
	 * @return
	 */
	public Worker getWorker(int id){
		for (Worker w : workers) {
			if(w.id == id){
				return w;
			}
		}
		return null;
	}
}