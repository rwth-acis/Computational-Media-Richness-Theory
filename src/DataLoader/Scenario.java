package DataLoader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Media.MediaType;
import Simulation.Project;
import Simulation.Team;

import com.google.gson.reflect.TypeToken;

/**
 * Object, that stores scenario 
 * @author Alex
 *
 */
public class Scenario {

	public List<String> teamPaths;
	public List<String> projectPaths;
	public List<String> mediaPaths;
	
	/**
	 * Type of the simulation. GA or MonteCarlo.
	 */
	public String simulationType;
	
	/**
	 * Maximum steps during one project. Some arbitrary end time, that shouldn't be exceeded.
	 */
	public int maxAllowedSteps;
	
	/**
	 * Maximum allowed evolutions - Maximum runs of the simulations per project.
	 */
	public int maxAllowedEvolutions;
	
	public Scenario() {
		this.simulationType = "GA";
		this.maxAllowedSteps = 0;
		this.maxAllowedEvolutions = 0;
		this.teamPaths = new ArrayList<String>();
		this.projectPaths = new ArrayList<String>();
		this.mediaPaths = new ArrayList<String>();		
	}

	public Team getTeam(String teamPath) {
		
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
		/*
		String s = JsonSerializer.serialize(team);
		System.out.print(s);
		*/
		Team t = (Team) JsonSerializer.deserialize(teamPath, Team.class);
		return t;
	}

	public Project getProject(String projectPath) {
		/*
		Project project = new Project();
		int tasksCount = 500;// (Integer) params.getValue("human_count");
		for (int i = 0; i < tasksCount; i++) {
			int taskComplexity = RandomHelper.nextIntFromTo(2, 2);
			Task t = new Task(taskComplexity);
			project.Tasks.add(t);
		}*/
		/*
		String s = JsonSerializer.serialize(project);
		System.out.print(s);
		*/
		Project project = (Project) JsonSerializer.deserialize(projectPath, Project.class);
		return project;
	}
	
	/**
	 * Deserialize medias from JSON file.
	 * @param path
	 * @return
	 */
	public List<MediaType> getMedias(String path) {
		/*
		List<MediaType> mt = new ArrayList<MediaType>();
		mt.add(MediaType.EMAIL);
		mt.add(MediaType.PHONE);
		mt.add(MediaType.FACETOFACE);
		this.setMedias(mt);
		
		String s = JsonSerializer.serialize(this.MediaTypes);
		*/
		Type listType = new TypeToken<ArrayList<MediaType>>() {}.getType();
		@SuppressWarnings("unchecked")
		List<MediaType> mt = (List<MediaType>) JsonSerializer.deserialize(path, listType);
		return mt;
	}
}
