package GeneticAlgorithm;

import java.util.TreeMap;

import org.jgap.BaseGene;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;

public class AdjacencyListGene extends BaseGene implements Gene,
		java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5352798470359832067L;
	private TreeMap<Integer, Integer> adjacencyList;

	public AdjacencyListGene(Configuration a_configuration)
			throws InvalidConfigurationException {
		super(a_configuration);

		this.adjacencyList = new TreeMap<>();
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void applyMutation(int index, double a_percentage) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPersistentRepresentation()
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setAllele(Object a_newValue) {
		this.adjacencyList = (TreeMap<Integer, Integer>) a_newValue;
	}

	@Override
	public void setToRandomValue(RandomGenerator a_numberGenerator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValueFromPersistentRepresentation(String a_representation)
			throws UnsupportedOperationException,
			UnsupportedRepresentationException {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object getInternalValue() {
		return adjacencyList;
	}

	@Override
	protected Gene newGeneInternal() {
		try {
			return new AdjacencyListGene(getConfiguration());
		} catch (InvalidConfigurationException ex) {
			throw new IllegalStateException(ex.getMessage());
		}
	}

	/** 
	 * @return Count of the workers in the list.
	 */
	public int count() {
		return this.adjacencyList.size();
	}
}
