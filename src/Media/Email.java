package Media;


public final class Email extends AMedia {
	public Email(int _communicationFrequency){
		this.type = MediaType.EMAIL;
		this.communicationFrequency = _communicationFrequency;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 0.5;
	}
	
	public Email(){
		this.type = MediaType.EMAIL;
		this.communicationFrequency = 0;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 0.5;
	}
}