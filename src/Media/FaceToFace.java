package Media;

public class FaceToFace extends AMedia {
	public FaceToFace(int _communicationFrequency){
		this.name = MediaType.FACETOFACE;
		this.communicationFrequency = _communicationFrequency;
		AMedia.setDuration(0);
		AMedia.setAmountOfInformationTransfered(3);
	}
	
	public FaceToFace(){
		this.name = MediaType.FACETOFACE;
		this.communicationFrequency = 0;
		AMedia.setDuration(0);
		AMedia.setAmountOfInformationTransfered(3);
	}
}
