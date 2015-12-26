/**
 * 
 */
package softwareSim;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Alex Entity, that represents project.
 */
public class Project {

	public List<Task> Tasks;
	public double deviation;

	public Project() {
		this.deviation = 0;
		this.Tasks = new ArrayList<Task>();
	}

	/**
	 * Calculate complexity of the project.
	 */
	public int Complexity() {
		int complexity = 0;
		for (Task task : Tasks) {
			complexity = complexity + task.complexity;
		}
		return complexity;
	}

	/**
	 * Serialize data to JSON string.
	 * @return
	 */
	public String serialize() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(this);
		return jsonOutput;
	}

	/**
	 * Deserialize from JSON.
	 * @param bufferReader
	 * @return
	 */
	@SuppressWarnings("finally")
	public static Project deserialize(BufferedReader bufferReader) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(bufferReader, Project.class);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			return null;
		}
	}
}
