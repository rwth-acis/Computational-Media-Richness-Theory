package CommunicationModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunState;
import repast.simphony.util.collections.IndexedIterable;
import softwareSim.Worker;
import DataLoader.DataMediator;
import Media.AMedia;

/**
 * Store effects of the communication (both negative and positive) for each
 * agent for each step.
 * 
 * @author Alex
 *
 */
public class CommunicationEffects {

	private int actualAskFrequency[];
	private int actualAnswerFrequency[];
	
	public AMedia[] medias;
	
	private Worker worker;
	private List<Integer> discussedTopics;
	
	public CommunicationEffects(Worker worker) {
		this.worker = worker;
		this.discussedTopics = new ArrayList<Integer>();
		
		@SuppressWarnings("unchecked")
		Context<DataMediator> c = RunState.getInstance().getMasterContext();
		IndexedIterable<DataMediator> ii = c.getObjects(DataMediator.class);
		DataMediator dm = (DataMediator) ii.get(0);
		AMedia[] medias = dm.Medias;

		this.init(medias);
	}

	/** Effect could be positive and negative double value. */
	public double calculateEffect() {
		double influence = 0;
		
		influence = positiveInfluence();
		
		for(int i = 0; i<this.actualAskFrequency.length;i++){
			/* Here negative effect is linear - represents effect of the interruptions
			 * during the work.
			 */
			double negativeInfluence = ((double)actualAskFrequency[i] + (double)actualAnswerFrequency[i]);
			
			influence -= negativeInfluence;
		}
		
		return influence;
	}

	/** Call this function on every ask request of the agent. 
	 * @param discussedTopics */
	public void communicate(AMedia media, List<Integer> discussedTopics) {		
		switch(media.name){
		case EMAIL:
			this.actualAskFrequency[0]++;
			break;
		case PHONE:
			this.actualAskFrequency[1]++;
			break;
		case FACETOFACE:
			this.actualAskFrequency[2]++;
			break;
		}
		
		// add discussed topics to the list
		for(int i = 0; i < discussedTopics.size(); i++){
			this.discussedTopics.add(discussedTopics.get(i));
		}
	}
	
	/** Call this function on every answer of the agent. */
	public void answer1(AMedia media) {		
		switch(media.name){
		case EMAIL:
			this.actualAnswerFrequency[0]++;
			break;
		case PHONE:
			this.actualAnswerFrequency[1]++;
			break;
		case FACETOFACE:
			this.actualAnswerFrequency[2]++;
			break;
		}		
	}

	/**
	 * 
	 * @param workerId
	 * @param communicationEffect
	 * @param media
	 * @return
	 */
	public List<Integer> answer(AMedia media){	
		// populate discussed topics
		double amountOfDiscussedTopics = media.probabilityOfSuccessfulDiscuss;
		int length = this.worker.knowledgeAreas.length;
		
		//int[] tempDiscussedTopics = new int[amountOfDiscussedTopics];
		List<Integer> tempDiscussedTopics = new ArrayList<Integer>();
		int i = 0;
		while(i<length){
			int id = (int) (Math.random() * length);
			int topicId = this.worker.knowledgeAreas[id];

				//if topic is not discussed
				if(!tempDiscussedTopics.contains(topicId))
				{
					tempDiscussedTopics.add(topicId);
				}
			i++;				
		}
		
		// negative effect
		this.answer1(media);
		return tempDiscussedTopics;
	}
	
	/** Reset all data. Call this function the end of the every doJob() step. */
	public void clear() {
		this.actualAskFrequency = new int[this.actualAskFrequency.length];
		this.actualAnswerFrequency = new int[this.actualAnswerFrequency.length];
		this.discussedTopics = new ArrayList<Integer>();
	}
	
	/**
	 * Initialize data.
	 * @param _medias
	 */
	public void init(AMedia[] _medias){
		this.medias = _medias;		
		this.discussedTopics = new ArrayList<Integer>();
		this.actualAskFrequency = new int[medias.length];
		this.actualAnswerFrequency = new int[medias.length];
		for(int i = 0; i<this.actualAskFrequency.length;i++){
			this.actualAskFrequency[i] = 0;
			this.actualAnswerFrequency[i] = 0;
		}
	}
	
	/**
	 * Return amount of discussed topics, that are in the list of the needed topics.
	 * @return
	 */
	private int positiveInfluence(){
		int count  = 0;
		for (int topic : this.discussedTopics) {
			if(Arrays.binarySearch(this.worker.knowledgeAreasNeed, topic)>0){
				count++;
			}
		}
		return count;
	}
}
