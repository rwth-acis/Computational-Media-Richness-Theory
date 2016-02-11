package DataLoader;

import java.util.List;

import org.jgap.Gene;
import org.jgap.IChromosome;

import GeneticAlgorithm.AdjacencyListGene;
import GeneticAlgorithm.MediasSupergene;
import GeneticAlgorithm.TeamSupergene;
import GeneticAlgorithm.WorkersGene;
import Media.AMedia;
import Media.Email;
import Media.FaceToFace;
import Media.MediaType;
import Media.Phone;
import Simulation.Pair;
import Simulation.Team;
import Simulation.Worker;

/**
 * Class for data storing, that will be used by the Agent based simulation. 
 * Contains data, that received from Genetic algorithm OR Monte Carlo simulation.
 * Makes Agent based simulation not dependent from type of the simulation (GA or MC).
 * @author Alex
 *
 */
public class DataMediator {
	public AMedia[] Medias; 
	public Team team;
	
	public double currentFitness;
	
	/**
	 * List of all possible topics/knowledge.
	 */
	public int[] allTopics;
	
	public DataMediator() {
		this.currentFitness = 0;
		
		//set number of available topics
		this.allTopics = new int[100];		
		for(int i = 0; i < allTopics.length; i++) {
			allTopics[i] = i;
        }
	}
	
	/**
	 * Initialize data from the chromosome. Used in the GA.
	 * @param chromosome
	 */
	public void SetChromosome(IChromosome chromosome, List<MediaType> mediaTypes){
		MediasSupergene ms = (MediasSupergene) chromosome.getGene(0);
		Gene[] mediaGenes = ms.getGenes();
		
		this.Medias = new AMedia[mediaGenes.length];
		for(int i = 0; i<mediaGenes.length; i++){
			this.Medias[i] = castMedia(mediaTypes.get(i));
			this.Medias[i].communicationFrequency = (int) mediaGenes[i].getAllele();
		}
		
		TeamSupergene ts = (TeamSupergene) chromosome.getGene(1);
		Gene[] teamGenes = ts.getGenes();
		
		@SuppressWarnings("unchecked")
		List<Worker> wl = (List<Worker>) ((WorkersGene) teamGenes[0]).getAllele();

		@SuppressWarnings("unchecked")
		List<Pair<Integer, Integer>> tm = (List<Pair<Integer, Integer>>) ((AdjacencyListGene) teamGenes[1]).getAllele();

		this.castToTeam(wl, tm);
	}
	
	/**
	 * Use direct set of the data. Used in Monte Carlo simulation.
	 * @param MediaFrequencies
	 * @param tm
	 * @param wl
	 */
	public void setData(AMedia[] Medias, List<Pair<Integer, Integer>> tm, List<Worker> wl){
		this.Medias = Medias;		
		this.castToTeam(wl, tm);
	}

	/**
	 * Create media from type.
	 * @param mediaType
	 * @return
	 */
	private AMedia castMedia(MediaType mediaType) {
		switch (mediaType) {
		case EMAIL:
			return new Email();
		case PHONE:
			return new Phone();
		case FACETOFACE:
			return new FaceToFace();
		default:
			throw new RuntimeException("CommunicationStrategy: Media index out of bounds");
		}
	}
	
	private void castToTeam(List<Worker> wl, List<Pair<Integer, Integer>> tm){
		// TODO add project complexity
		this.team = new Team(0, "");		
		this.team.adjacencyList = tm;
		this.team.workers = wl;
	}
}
