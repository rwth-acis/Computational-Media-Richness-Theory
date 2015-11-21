package GeneticAlgorithm;

import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

import softwareSim.VariablesSettings;
import Media.Email;
import Media.MediaType;

public class CommunicationStrategy {

	static Logger log = Logger.getLogger(LoggingMXBean.class.getName());
	
	static CommunicationStrategy instance;

	public static CommunicationStrategy getInstance() {
		if (instance == null)
			instance = new CommunicationStrategy();
		return instance;
	}
	
	/**
	 * Function for communication process between workers.
	 * 
	 * @param askWorkerId
	 *            - id of the worker who make an request;
	 * @return Level of help received.
	 */
	public static double communicate(int askWorkerId) {
		//log.info("Communicate");
		MediaType selectedMedia = selectMedia();
		// TODO: Get list of available connections.
		// TODO: Add logic with whom to communicate, depending on experience
		// level.

		return 0; //
	}

	/**
	 * Function for media selection.
	 * 
	 * @return Selected MediaType.
	 */
	private static MediaType selectMedia() {
		// TODO: Add logic of communication media selection.
		MediaType m = Email.name;
		return MediaType.EMAIL;
	}

}
