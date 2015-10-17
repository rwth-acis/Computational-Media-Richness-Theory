package softwareSim;

import GeneticAlgorithm.GA;

public class VariablesSettings {

	static VariablesSettings instance;

	public static VariablesSettings getInstance() {
		if (instance == null)
			instance = new VariablesSettings();
		return instance;
	}

	public static double getAgentProductivity(AgentTypes type) {
		switch (type) {
		case JUNIOR:
			return 0.1;
		case MIDDLE:
			return 0.2;
		case SENIOR:
			return 0.3;

		default:
			return 0.1;
		}
	}

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

	public static double getAgentProductivityDecreaseRate(AgentTypes type) {
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

}
