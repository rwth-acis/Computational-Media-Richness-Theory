package Media;

import Simulation.VariablesSettings;

public class FaceToFace extends AMedia {
	public FaceToFace(int _communicationFrequency){
		this.type = MediaType.FACETOFACE;
		this.communicationFrequency = _communicationFrequency;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 0.9;
		this.initialProbabilityOfSuccessfulDiscuss = 0.9;
		this.decrease = this.initialProbabilityOfSuccessfulDiscuss / VariablesSettings.numberOfKnowledgeAreasNeed;
	}
	
	public FaceToFace(){
		this.type = MediaType.FACETOFACE;
		this.communicationFrequency = 0;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 0.9;
		this.initialProbabilityOfSuccessfulDiscuss = 0.9;
		this.decrease = this.initialProbabilityOfSuccessfulDiscuss / VariablesSettings.numberOfKnowledgeAreasNeed;
	}	
}
