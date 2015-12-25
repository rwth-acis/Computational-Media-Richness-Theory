package DataLoader;

import org.jgap.Gene;
import org.jgap.IChromosome;

import Media.AMedia;
import Media.Email;
import Media.FaceToFace;
import Media.Phone;

/**
 * Class for data storing, that will be used by the Agent based simulation. 
 * Contains data, that received from Genetic algorithm OR Monte Carlo simulation.
 * Makes Agent based simulation not dependent from type of the simulation (GA or MC).
 * @author Alex
 *
 */
public class DataMediator {
	private boolean useGA;
	public AMedia[] Medias; 
	
	private IChromosome currentChromosome;
	public double currentFitness;
	
	public void SetChromosome(IChromosome chromosome){
		this.useGA = true;

		this.Medias = new AMedia[chromosome.size()];
		for(int i = 0; i<chromosome.size(); i++){
			this.Medias[i] = castMedia(i);
			this.Medias[i].communicationFrequency = (int) chromosome.getGene(i).getAllele();
		}
	}
	
	public void SetMedias(int[] MediaFrequencies){
		this.useGA = false;
		
		this.Medias = new AMedia[MediaFrequencies.length];
		for(int i = 0; i<MediaFrequencies.length; i++){
			this.Medias[i] = castMedia(i);
			this.Medias[i].communicationFrequency = MediaFrequencies[i];
		}
	}

	public DataMediator() {
		// TODO Auto-generated constructor stub
	}
	
	private AMedia castMedia(int index) {
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
		/*
		switch (media.name) {
		case EMAIL:
			return new Email();
		case PHONE:
			return new Phone();
		case FACETOFACE:
			return new FaceToFace();
		default:
			throw new RuntimeException("CommunicationStrategy: Media index out of bounds");
		}
		*/
	}

}
