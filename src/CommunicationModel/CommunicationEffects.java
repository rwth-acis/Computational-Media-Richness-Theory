package CommunicationModel;

import Media.AMedia;

/**
 * Store effects of the communication (both negative and positive) for each
 * agent for each step.
 * 
 * @author Alex
 *
 */
public class CommunicationEffects {

	private int communicationFrequency;

	private double positiveInfluence;
	private double negativeInfluence;

	public CommunicationEffects() {
		this.positiveInfluence = 0;
		this.negativeInfluence = 0;
		this.communicationFrequency = 0;
	}

	/** Effect could be positive and negative double value. */
	public double calculateEffect() {
		this.calculateNegativeInfluence();

		return this.positiveInfluence - this.negativeInfluence;
	}

	/** Call this function on every communication of the agent. */
	public void communicate(AMedia media) {
		this.communicationFrequency++;
		this.positiveInfluence += AMedia.richness();
	}

	/** Reset all data. Call this function the end of the every doJob() step. */
	public void clear() {
		this.positiveInfluence = 0;
		this.negativeInfluence = 0;
		this.communicationFrequency = 0;
	}

	/**
	 * Here negative effect is linear - represents effect of the interruptions
	 * during the work.
	 */
	private void calculateNegativeInfluence() {
		this.negativeInfluence = communicationFrequency / 5;
	}
}
