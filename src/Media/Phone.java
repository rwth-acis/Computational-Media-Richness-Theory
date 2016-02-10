package Media;

public class Phone extends AMedia {
	public Phone(int _communicationFrequency){
		this.type = MediaType.PHONE;
		this.communicationFrequency = _communicationFrequency;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 0.7;
	}
	
	public Phone(){
		this.type = MediaType.PHONE;
		this.communicationFrequency = 0;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 0.7;
	}
}