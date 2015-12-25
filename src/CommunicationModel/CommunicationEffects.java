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

	private int actualCommunicationFrequency[];
	public AMedia[] medias;
	
	public CommunicationEffects() {}

	/** Effect could be positive and negative double value. */
	public double calculateEffect() {
		double influence = 0;
		for(int i = 0; i<this.actualCommunicationFrequency.length;i++){
			/* Here negative effect is linear - represents effect of the interruptions
			 * during the work.
			 */
			double negativeInfluence = ((double)actualCommunicationFrequency[i]) / 20;
			double positiveInfluence = medias[i].richness(this.actualCommunicationFrequency[i]);
			
			influence += (positiveInfluence- negativeInfluence)*medias[i].amountOfInformationTransfered;
		}
		
		return influence;
	}

	/** Call this function on every communication of the agent. */
	public void communicate(AMedia media) {		
		switch(media.name){
		case EMAIL:
			this.actualCommunicationFrequency[0]++;
			break;
		case PHONE:
			this.actualCommunicationFrequency[1]++;
			break;
		case FACETOFACE:
			this.actualCommunicationFrequency[2]++;
			break;
		}		
	}

	/** Reset all data. Call this function the end of the every doJob() step. */
	public void clear() {
		this.actualCommunicationFrequency = new int[this.actualCommunicationFrequency.length];
	}
	
	/**
	 * Initialize data.
	 * @param _medias
	 */
	public void init(AMedia[] _medias){
		this.medias = _medias;
		
		this.actualCommunicationFrequency = new int[medias.length];
		for(int i = 0; i<this.actualCommunicationFrequency.length;i++){
			this.actualCommunicationFrequency[i] = 0;	
		}
	}
}
