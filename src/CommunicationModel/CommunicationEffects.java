package CommunicationModel;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunState;
import repast.simphony.util.collections.IndexedIterable;
import softwareSim.Worker;
import DataLoader.DataMediator;
import Media.AMedia;
import Media.MediaType;

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

	@SuppressWarnings("unused")
	private Worker worker;
	private int discussedTopics;

	public CommunicationEffects(Worker worker) {
		this.worker = worker;
		this.discussedTopics = 0;

		@SuppressWarnings("unchecked")
		Context<DataMediator> c = RunState.getInstance().getMasterContext();
		IndexedIterable<DataMediator> ii = c.getObjects(DataMediator.class);
		DataMediator dm = (DataMediator) ii.get(0);
		AMedia[] medias = dm.Medias;

		this.init(medias);
	}

	/** Negative influence. */
	public double negativeEffect() {
		/*
		 * Here negative effect is linear - represents effect of the
		 * interruptions during the work.
		 */
		double influence = 0;

		for (int i = 0; i < this.actualAskFrequency.length; i++) {

			double negativeInfluence = ((double) actualAskFrequency[i]/* + (double) actualAnswerFrequency[i]*/);

			influence += negativeInfluence;
		}

		influence = - influence / 60;

		return influence;
	}

	/**
	 * Call this function on every ask request of the agent.
	 * 
	 * @param discussedTopics
	 */
	public void communicate(MediaType media, int _discussedTopics) {
		switch (media) {
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
		this.discussedTopics += _discussedTopics;
	}

	/** Call this function on every answer of the agent. */
	private void answerHelpFunc(AMedia media) {
		switch (media.name) {
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
	 * During answer agent can answer or not not some amount of topics, with
	 * probability, depending on media.
	 * 
	 * @param media
	 * @return
	 */
	public int answer(AMedia media) {

		int tempDiscussedTopics = 0;
		if (Math.random() <= media.probabilityOfSuccessfulDiscuss) {
			tempDiscussedTopics = 1;
		}

		// negative effect
		this.answerHelpFunc(media);
		return tempDiscussedTopics;
	}

	/** Reset all data. Call this function in the end of the every doJob() step. */
	public void clear() {
		this.actualAskFrequency = new int[this.actualAskFrequency.length];
		this.actualAnswerFrequency = new int[this.actualAnswerFrequency.length];
		this.discussedTopics = 0;
	}

	/**
	 * Initialize data.
	 * 
	 * @param _medias
	 */
	public void init(AMedia[] _medias) {
		this.medias = _medias;
		this.discussedTopics = 0;
		this.actualAskFrequency = new int[medias.length];
		this.actualAnswerFrequency = new int[medias.length];
		for (int i = 0; i < this.actualAskFrequency.length; i++) {
			this.actualAskFrequency[i] = 0;
			this.actualAnswerFrequency[i] = 0;
		}
	}

	/**
	 * Return from 0 to 1, where 1 is 100%
	 * 
	 * @return
	 */
	public double positiveEffect() {
		if (this.worker.knowledgeAreasNeed <= this.discussedTopics) {
			return 1;
		}
		// % of needed topics
		return this.discussedTopics / this.worker.knowledgeAreasNeed;
	}
}
