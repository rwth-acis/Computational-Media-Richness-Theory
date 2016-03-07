package CommunicationModel;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunState;
import repast.simphony.util.collections.IndexedIterable;
import DataLoader.DataMediator;
import Media.AMedia;
import Simulation.Pair;
import Simulation.Team;
import Simulation.Worker;

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

		// TODO: Add logic with whom to communicate, depending on experience
		// level. And call by them communicate() function.

		Team team = CommunicationStrategy.getTeam();

		@SuppressWarnings("unchecked")
		Context<DataMediator> c = RunState.getInstance().getMasterContext();
		IndexedIterable<DataMediator> ii = c.getObjects(DataMediator.class);
		DataMediator dm = (DataMediator) ii.get(0);
		AMedia[] medias = dm.Medias;

		for (AMedia media : medias) {
			int value = media.communicationFrequency;
			for (int i = 0; i < value; i++) {
				int withId = CommunicationStrategy.communicateWith(Team
						.neighborList(askWorkerId, team.adjacencyList));

				if (withId != -1) {
					Worker w = team.getWorker(withId);
					communicationEffect.ask(media.type);
					int discussedTopics = w.answer(communicationEffect.medias[communicationEffect.getMediaIndex(media.type)]);

					communicationEffect.answerRecieved(discussedTopics, media.type);
				}
			}
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

	@SuppressWarnings("unused")
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
