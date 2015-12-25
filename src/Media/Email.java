package Media;


public final class Email extends AMedia {
	public Email(int _communicationFrequency){
		this.name = MediaType.EMAIL;
		this.communicationFrequency = _communicationFrequency;
		AMedia.setDuration(0);
		AMedia.setAmountOfInformationTransfered(1);
	}
	
	public Email(){
		this.name = MediaType.EMAIL;
		this.communicationFrequency = 0;
		AMedia.setDuration(0);
		AMedia.setAmountOfInformationTransfered(1);
	}
}
