package Media;

import Simulation.VariablesSettings;

public class Phone extends AMedia {
	public Phone(int _communicationFrequency){
		this.type = MediaType.PHONE;
		this.communicationFrequency = _communicationFrequency;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 0.6;
		this.initialProbabilityOfSuccessfulDiscuss = 0.6;
		this.decrease = this.initialProbabilityOfSuccessfulDiscuss / VariablesSettings.numberOfKnowledgeAreasNeed;
	}
	
	public Phone(){
		this.type = MediaType.PHONE;
		this.communicationFrequency = 0;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 0.6;
		this.initialProbabilityOfSuccessfulDiscuss = 0.6;
		this.decrease = this.initialProbabilityOfSuccessfulDiscuss / VariablesSettings.numberOfKnowledgeAreasNeed;
	}
}