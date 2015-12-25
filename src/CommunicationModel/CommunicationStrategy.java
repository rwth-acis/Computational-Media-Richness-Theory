package CommunicationModel;

import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunState;
import repast.simphony.util.collections.IndexedIterable;
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

		@SuppressWarnings("unchecked")
		Context<DataMediator> c = RunState.getInstance().getMasterContext();
		IndexedIterable<DataMediator> ii = c.getObjects(DataMediator.class);
		DataMediator dm = (DataMediator) ii.get(0);
		AMedia[] medias = dm.Medias;

		communicationEffect.init(medias);
		
		int mediaIndex = 0;		
		for(AMedia media : medias) {			
			int value = media.communicationFrequency;
			for(int i = 0; i<value;i++){
				communicationEffect.communicate(CommunicationStrategy.selectMedia(mediaIndex));
			}
			mediaIndex++;
		}
	}

	/**
	 * Function for media selection.
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
			throw new RuntimeException("CommunicationStrategy: Media index out of bounds");
		}	
		
	}
}
