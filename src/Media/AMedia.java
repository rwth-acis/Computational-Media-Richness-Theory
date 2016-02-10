package Media;

/**
 *
 * @author Alex Represents different media types.
 */
public abstract class AMedia {
	public MediaType type;
	
	/** Store communication frequency */
	public int communicationFrequency;
	
	/** 
	 * Value between 0 and 1, where 1 = 100%
	 */
	public double probabilityOfSuccessfulDiscuss;

	/** Richness of the media. Here amount of information transfered. */
	public double richness(int frequency) {
		return Math.log10(frequency);
		//return amountOfInformationTransfered;
	};

	/**
	 * Negative influence per communication.
	 */
	public double negativeInfluence;
}