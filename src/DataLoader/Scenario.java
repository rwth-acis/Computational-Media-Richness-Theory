package DataLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import repast.simphony.random.RandomHelper;
import softwareSim.Junior;
import softwareSim.Pair;
import softwareSim.Project;
import softwareSim.Task;
import softwareSim.Team;
import softwareSim.Worker;

public class Scenario {

	private String scenarioPath;
	private String teamPath;
	private String projectPath;

	public Scenario(String _filePath) {
		this.scenarioPath = _filePath;
		this.teamPath = "";
		this.projectPath = "";
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
		Project project = this.getProject();
		Team team = new Team(project.Complexity());
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
		int tasksCount = 1000;// (Integer) params.getValue("human_count");
		for (int i = 0; i < tasksCount; i++) {
			int taskComplexity = RandomHelper.nextIntFromTo(2, 2);
			Task t = new Task(taskComplexity);
			project.Tasks.add(t);
		}
		return project;
	}
}
