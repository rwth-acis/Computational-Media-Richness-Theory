package Media;

/**
 *
 * @author Alex Represents different media types.
 */
public abstract class AMedia {
	/**
	 * Type of the media.
	 */
	public MediaType type;
	
	/** Store communication frequency */
	public int communicationFrequency;
	
	/** 
	 * Value between 0 and 1, where 1 = 100%; this value could change during the simulation step
	 */
	public double probabilityOfSuccessfulDiscuss;
	
	/**
	 * This value is constant.
	 */
	public double initialProbabilityOfSuccessfulDiscuss;
	
	/**
	 * Help value. Decrease of the probabilityOfSuccessfulDiscuss per communication.
	 */
	public double decrease;

	/**
	 * Negative influence per communication.
	 */
	public double negativeInfluence;
}