package DataLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import repast.simphony.random.RandomHelper;
import softwareSim.Junior;
import softwareSim.Pair;
import softwareSim.Project;
import softwareSim.Task;
import softwareSim.Team;
import softwareSim.Worker;

/**
 * Object, that stores scenario 
 * @author Alex
 *
 */
public class Scenario {

	private transient String scenarioPath;
	public List<String> teamPaths;
	public List<String> projectPaths;
	
	/**
	 * Type of the simulation. GA or MonteCarlo.
	 */
	public String simulationType;
	
	/**
	 * Maximum runs in simulation per project.
	 */
	public int runs;
	
	public Scenario(String _filePath) {
		this.scenarioPath = _filePath;
		this.teamPaths = new ArrayList<String>();
		this.projectPaths = new ArrayList<String>();
	}

	public Team getTeam() {
		/*
		 * Team teamObj = null; try {
		 * System.out.println("Reading JSON from a file"); //"E:\\file.json"
		 * BufferedReader br = new BufferedReader(new FileReader(file)); //
		 * convert the json string back to object teamObj =
		 * Team.deserialize(br); } catch (IOException e) { e.printStackTrace();
		 * } return teamObj;
		 */
		
		/*
		Project project = this.getProject();
		Team team = new Team(project.Complexity(), "");
		int workerCount = 7;// (Integer) params.getValue("human_count");
		for (int i = 0; i < workerCount; i++) {
			// int eperienceLevel = RandomHelper.nextIntFromTo(1, 10);
			Worker w = new Junior(i);
			team.addWorker(w);
		}

		team.adjacencyList = new ArrayList<Pair<Integer, Integer>>();
		for (int id = 0; id < workerCount; id++) {
			int withId;
			for (int i = 0; i < 3; i++) {
				withId = (int) (Math.random() * workerCount);
				team.adjacencyList.add(new Pair<Integer, Integer>(id, withId));
			}
		}
		return team;
		*/

		//JsonSerializer.serialize(team);
		/**/
		Team t = (Team) JsonSerializer.deserialize(".//simulation_data//team_1.json", Team.class);
		return t;
	}

	public Project getProject() {
		/*
		 * Project projectObj = null; try {
		 * System.out.println("Reading JSON from a file"); //"E:\\file.json"
		 * BufferedReader br = new BufferedReader(new FileReader(file)); //
		 * convert the json string back to object projectObj =
		 * Project.deserialize(br); } catch (IOException e) {
		 * e.printStackTrace(); } return projectObj;
		 */
		Project project = new Project();
		int tasksCount = 500;// (Integer) params.getValue("human_count");
		for (int i = 0; i < tasksCount; i++) {
			int taskComplexity = RandomHelper.nextIntFromTo(2, 2);
			Task t = new Task(taskComplexity);
			project.Tasks.add(t);
		}
		return project;
	}
	
	/**
	 * Help function for object deserialization.
	 * @param br
	 * @return
	 */
	public static Scenario deserialize(BufferedReader br) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(br, Scenario.class);
		} catch (Exception e) {
			System.out.println(e);
		} 
		return null;
	}
}
