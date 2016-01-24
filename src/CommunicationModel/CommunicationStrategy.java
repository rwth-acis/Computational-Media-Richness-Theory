package CommunicationModel;

import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunState;
import repast.simphony.util.collections.IndexedIterable;
import softwareSim.Pair;
import softwareSim.Team;
import softwareSim.Worker;
import DataLoader.DataMediator;
import Media.AMedia;
import Media.Email;
import Media.FaceToFace;
import Media.Phone;

public class CommunicationStrategy {

	static Logger log = Logger.getLogger(LoggingMXBean.class.getName());

	/**
	 * Function for communication process between workers.
	 * 
	 * @param askWorkerId
	 *            - id of the worker who make an request;
	 */
	public static void communicate(int askWorkerId,
			CommunicationEffects communicationEffect) {
		// log.info("Communicate");

		// TODO: Get list of available connections.
		// TODO: Add logic with whom to communicate, depending on experience
		// level. And call by them communicate() function.

		Team team = CommunicationStrategy.getTeam();

		@SuppressWarnings("unchecked")
		Context<DataMediator> c = RunState.getInstance().getMasterContext();
		IndexedIterable<DataMediator> ii = c.getObjects(DataMediator.class);
		DataMediator dm = (DataMediator) ii.get(0);
		AMedia[] medias = dm.Medias;

		int mediaIndex = 0;
		for (AMedia media : medias) {
			int value = media.communicationFrequency;
			for (int i = 0; i < value; i++) {
				int withId = CommunicationStrategy.communicateWith(Team
						.neighborList(askWorkerId, team.adjacencyList));

				if (withId != -1) {
					Worker w = team.getWorker(withId);
					List<Integer> discussedTopics = w.answer(media);

					communicationEffect.communicate(
							CommunicationStrategy.selectMedia(mediaIndex),
							discussedTopics);
				}
			}
			mediaIndex++;
		}
	}

	/**
	 * Function for media selection.
	 * 
	 * @return Selected MediaType.
	 */
	public static AMedia selectMedia(int index) {
		// TODO: Add logic of communication media selection.

		switch (index) {
		case 0:
			return new Email();
		case 1:
			return new Phone();
		case 2:
			return new FaceToFace();
		default:
			throw new RuntimeException(
					"CommunicationStrategy: Media index out of bounds");
		}
	}

	/**
	 * @param adjacencyList
	 *            - list of the direct connections.
	 * @return Return id of the worker, with whom current worker will
	 *         communicate.
	 */
	private static int communicateWith(
			List<Pair<Integer, Integer>> adjacencyList) {
		int withId = -1;

		if (adjacencyList.size() > 0) {
			withId = (int) (Math.random() * adjacencyList.size());
		}
		return withId;
	}

	private static void typeOfTheCommunication() {
		// questionAsk
		// questionAnswer
		// sendInfo
		// recieveInfo
	}

	/**
	 * Get team.
	 * 
	 * @return team for current simulation.
	 */
	private static Team getTeam() {
		@SuppressWarnings("unchecked")
		Context<Team> c = RunState.getInstance().getMasterContext();
		IndexedIterable<Team> ii = c.getObjects(Team.class);
		Team team = (Team) ii.get(0);
		return team;
	}
}
