package Media;

import Simulation.VariablesSettings;


public final class Email extends AMedia {
	public Email(int _communicationFrequency){
		this.type = MediaType.EMAIL;
		this.communicationFrequency = _communicationFrequency;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 0.3;
		this.initialProbabilityOfSuccessfulDiscuss = 0.3;
		this.decrease = this.initialProbabilityOfSuccessfulDiscuss / VariablesSettings.numberOfKnowledgeAreasNeed;
	}
	
	public Email(){
		this.type = MediaType.EMAIL;
		this.communicationFrequency = 0;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 0.3;
		this.initialProbabilityOfSuccessfulDiscuss = 0.3;
		this.decrease = this.initialProbabilityOfSuccessfulDiscuss / VariablesSettings.numberOfKnowledgeAreasNeed;
	}
}