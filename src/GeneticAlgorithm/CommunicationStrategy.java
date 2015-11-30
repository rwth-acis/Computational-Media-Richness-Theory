package GeneticAlgorithm;

import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

import org.jgap.Chromosome;
import org.jgap.Gene;
import org.jgap.IChromosome;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunState;
import repast.simphony.util.collections.IndexedIterable;
import softwareSim.VariablesSettings;
import CommunicationModel.CommunicationEffects;
import Media.AMedia;
import Media.Email;
import Media.FaceToFace;
import Media.MediaType;
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
		Context<GA> c = RunState.getInstance().getMasterContext();
		IndexedIterable<GA> ii = c.getObjects(GA.class);
		GA ga = (GA) ii.get(0);
		IChromosome chr = ga.currentChromosome;
		Gene[] genes = chr.getGenes();

		int mediaIndex = 0;
		for (Gene gene : genes) {			
			int value = (Integer) gene.getAllele();
			communicationEffect.communicate(selectMedia(mediaIndex));
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
			return new Email();
		}
	}

}
