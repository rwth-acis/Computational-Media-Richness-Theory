package softwareSim;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Team {

	public TreeMap<Integer, Integer> adjacencyList; //sorted tree
	public List<Worker> workers;
	
	public Team(){
		this.workers = new ArrayList<Worker>();
	}
	
	public void addWorker(Worker worker){
		this.workers.add(worker);
	}
	
	public void deleteWorker(Worker worker){
		this.workers.remove(worker);
	}
}
