package CommunicationModel;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunState;
import repast.simphony.util.collections.IndexedIterable;
import DataLoader.DataMediator;
import Media.AMedia;
import Media.Email;
import Media.FaceToFace;
import Media.KnowledgeBase;
import Media.MediaType;
import Media.Phone;
import Simulation.Worker;

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
	private int discussedTopics[];

	public CommunicationEffects(Worker worker) {
		this.worker = worker;

		@SuppressWarnings("unchecked")
		Context<DataMediator> c = RunState.getInstance().getMasterContext();
		IndexedIterable<DataMediator> ii = c.getObjects(DataMediator.class);
		DataMediator dm = (DataMediator) ii.get(0);
		AMedia[] medias = dm.Medias;

		this.init(medias);
	}

	/**
	 * 
	 * 
	 * @return % - ratio of the discussed to the amount of needed topics.
	 */
	public double positiveEffect() {
		return this.discussedTopicsSum();
	}

	/** Negative influence. */
	public double negativeEffect() {
		/*
		 * Here negative effect is nonlinear - represents effect of the
		 * interruptions during the work.
		 */
		double influence = 0;
		AMedia[] medias = worker.dataMediator.Medias;

		for (int i = 0; i < this.actualAskFrequency.length; i++) {

			double negativeInfluence = 0.1;
			for(int j = 1; j<actualAskFrequency[i]; j++){
				negativeInfluence = negativeInfluence + medias[i].probabilityOfSuccessfulDiscuss * j;
			}
			/*
			double negativeInfluence = (double) (actualAskFrequency[i])
					* medias[i].probabilityOfSuccessfulDiscuss * 2 / 3
					+ actualAnswerFrequency[i]
					* medias[i].probabilityOfSuccessfulDiscuss * 1 / 3;
*/
			influence += negativeInfluence;
		}

		influence = influence / 2.4;

		return influence;
	}

	/**
	 * Return % of productivity
	 * 
	 * @return
	 */
	public double effect() {
		double ne = this.negativeEffect();
		double pe = this.positiveEffect();

		return (pe - ne) / 10;
	}

	/**
	 * Call this function on every ask request of the agent.
	 * 
	 * @param discussedTopics
	 */
	public void ask(MediaType media) {
		int index = this.getMediaIndex(media);
		this.actualAskFrequency[index]++;
	}

	/**
	 * During answer agent can answer or not, with probability, depending on
	 * media.
	 * 
	 * @param media
	 * @return
	 */
	public int answer(AMedia media) {

		int tempDiscussedTopics = 0;
		if (Math.random() <= media.probabilityOfSuccessfulDiscuss) {
			tempDiscussedTopics = 1;
			media.probabilityOfSuccessfulDiscuss = media.probabilityOfSuccessfulDiscuss - media.decrease;
		}

		// negative answer effect
		// KNOWLEDGEBASE has no negative answer request, because it uses no
		// worker, to recieve help.
		if (media.type != MediaType.KNOWLEDGEBASE) {
			this.actualAnswerFrequency[this.getMediaIndex(media.type)]++;
		}
		return tempDiscussedTopics;
	}

	/** Reset all data. Call this function in the end of the every doJob() step. */
	public void clear() {
		this.actualAskFrequency = new int[this.actualAskFrequency.length];
		this.actualAnswerFrequency = new int[this.actualAnswerFrequency.length];
		this.discussedTopics = new int[this.actualAnswerFrequency.length];
	}

	/**
	 * Initialize data.
	 * 
	 * @param _medias
	 */
	public void init(AMedia[] _medias) {
		this.medias = _medias;
		this.medias = new AMedia[_medias.length];
		int i = 0;

		// init new medias
		for (AMedia m : _medias) {
			switch (m.type) {
			case FACETOFACE:
				this.medias[i] = new FaceToFace();
				break;
			case PHONE:
				this.medias[i] = new Phone();
				break;
			case EMAIL:
				this.medias[i] = new Email();
				break;
			case KNOWLEDGEBASE:
				this.medias[i] = new KnowledgeBase();
				break;
			default:
				throw new Error("Undefined media!");
			}
			i++;
		}

		this.actualAskFrequency = new int[medias.length];
		this.actualAnswerFrequency = new int[medias.length];
		this.discussedTopics = new int[medias.length];
		/*for (int j = 0; j < this.actualAskFrequency.length; j++) {
			// TODO check that array is init-d by zeros
			
			 * this.actualAskFrequency[i] = 0; this.actualAnswerFrequency[i] =
			 * 0; this.discussedTopics[i] = 0;
		} */
	}

	/**
	 * Help function that returns index (position of media by type) in the
	 * array.
	 * 
	 * @param mediatype
	 * @return index of the media in the array.
	 */
	private int getMediaIndex(MediaType mediatype) {
		int i = 0;
		for (AMedia m : this.medias) {
			if (m.type.equals(mediatype)) {
				return i;
			}
			i++;
		}
		throw new Error("Cannot find media!");
	}

	private double discussedTopicsSum() {
		double sum = 0;
		for (int i = 0; i < this.discussedTopics.length; i++) {
			sum += this.discussedTopics[i];
		}
		return sum;
	}

	public void answerRecieved(int _discussedTopics, MediaType media) {
		int index = this.getMediaIndex(media);
		// add discussed topics
		this.discussedTopics[index] += _discussedTopics;
	}
}