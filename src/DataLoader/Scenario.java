package DataLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import softwareSim.Team;

public class Scenario {

	private String filePath;

	public Scenario(String _filePath) {
		this.filePath = _filePath;
	}

	public Team getTeam(String file) {
		Team teamObj = null;
		try {
			System.out.println("Reading JSON from a file");
			//"E:\\file.json"
			BufferedReader br = new BufferedReader(new FileReader(file));
			// convert the json string back to object
			teamObj = Team.deserialize(br);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return teamObj;
	}
	
	
}
