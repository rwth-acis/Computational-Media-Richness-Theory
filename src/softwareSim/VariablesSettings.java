package softwareSim;

/**
 * Class for storing global settings.
 * @author Alex
 *
 */
public class VariablesSettings {

	static VariablesSettings instance;

	public static VariablesSettings getInstance() {
		if (instance == null)
			instance = new VariablesSettings();
		return instance;
	}

	/**
	 * Productivity of the agent. The same measure as task complexity.
	 * @param type
	 * @return
	 */
	public static double getAgentProductivity(AgentTypes type) {
		switch (type) {
		case JUNIOR:
			return 1;
		case MIDDLE:
			return 2;
		case SENIOR:
			return 3;

		default:
			return 1;
		}
	}

	/**
	 * Probability of the agent to face a problem during the work.
	 * @param type
	 * @return
	 */
	public static double getAgentProblemOccurRate(AgentTypes type) {
		switch (type) {
		case JUNIOR:
			return 0.3;
		case MIDDLE:
			return 0.2;
		case SENIOR:
			return 0.1;

		default:
			return 0.3;
		}
	}

	/**
	 * Shows how many % of the productivity will left after problem occurs.
	 * @param type
	 * @return
	 */
	public static double getAgentProductivityDecreaseRate(AgentTypes type) {
		switch (type) {
		case JUNIOR:
			return 0.5;
		case MIDDLE:
			return 0.5;
		case SENIOR:
			return 0.5;

		default:
			return 0.5;
		}
	}
	
	/**
	 * Get how much help could be given by agent.
	 * @param type
	 * @return
	 */
	public static double getAgentHelpRate(AgentTypes type) {
		switch (type) {
		case JUNIOR:
			return 0.05;
		case MIDDLE:
			return 0.1;
		case SENIOR:
			return 0.15;

		default:
			return 0.05;
		}
	}
	
	/**
	 * Get how many knowledge areas are agent skilled in.
	 * @param type
	 * @return
	 */
	public static int numberOfKnowledgeAreas(AgentTypes type){
		switch (type) {
		case JUNIOR:
			return 1;
		case MIDDLE:
			return 2;
		case SENIOR:
			return 3;

		default:
			return 1;
		}
	}
	
	/**
	 * Number of knowledge areas need to solve task.
	 */
	public static int numberOfKnowledgeAreasNeed = 10;
}
