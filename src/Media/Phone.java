package Media;

public class Phone extends AMedia {
	public Phone(int _communicationFrequency){
		this.name = MediaType.PHONE;
		this.communicationFrequency = _communicationFrequency;
		AMedia.setDuration(0);
		AMedia.setAmountOfInformationTransfered(2);
	}
	
	public Phone(){
		this.name = MediaType.PHONE;
		this.communicationFrequency = 0;
		AMedia.setDuration(0);
		AMedia.setAmountOfInformationTransfered(2);
	}
}
