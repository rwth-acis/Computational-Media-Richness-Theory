package Media;

/**
 *
 * @author Alex Represents different media types.
 */
public abstract class AMedia {
	public MediaType name;
	
	/** Store communication frequency */
	public int communicationFrequency;
	
	public static double amountOfInformationTransfered;

	/** Richness of the media. Here amount of information transfered. */
	public static double richness(int frequency) {
		return Math.log10(frequency);
		//return amountOfInformationTransfered;
	};

	private static int duration;

	public static int getDuration() {
		return duration;
	}

	public static void setDuration(int duration) {
		AMedia.duration = duration;
	}

	public static void setAmountOfInformationTransfered(double amount) {
		AMedia.amountOfInformationTransfered = amount;
	}

}
