package Media;


public final class Email extends AMedia {
	public Email(){
		this.name = MediaType.EMAIL;
		AMedia.setDuration(0);
		AMedia.setAmountOfInformationTransfered(1);
	}
}
