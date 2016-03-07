package Media;

import Simulation.VariablesSettings;

public class KnowledgeBase extends AMedia {
	public KnowledgeBase(int _communicationFrequency){
		this.type = MediaType.KNOWLEDGEBASE;
		this.communicationFrequency = _communicationFrequency;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 0.6;
		this.initialProbabilityOfSuccessfulDiscuss = 0.6;
		this.decrease = this.initialProbabilityOfSuccessfulDiscuss / VariablesSettings.numberOfKnowledgeAreasNeed;
	}
	
	public KnowledgeBase(){
		this.type = MediaType.KNOWLEDGEBASE;
		this.communicationFrequency = 0;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 0.6;
		this.initialProbabilityOfSuccessfulDiscuss = 0.6;
		this.decrease = this.initialProbabilityOfSuccessfulDiscuss / VariablesSettings.numberOfKnowledgeAreasNeed;
	}
}
