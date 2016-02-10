package Media;

public class FaceToFace extends AMedia {
	public FaceToFace(int _communicationFrequency){
		this.type = MediaType.FACETOFACE;
		this.communicationFrequency = _communicationFrequency;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 1;
	}
	
	public FaceToFace(){
		this.type = MediaType.FACETOFACE;
		this.communicationFrequency = 0;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 1;
	}
}
