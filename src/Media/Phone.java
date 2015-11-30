package Media;

public class Phone extends AMedia {
	public Phone(){
		this.name = MediaType.PHONE;
		AMedia.setDuration(0);
		AMedia.setAmountOfInformationTransfered(2);
	}
}
