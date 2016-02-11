package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

import org.jgap.BaseGene;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;

import Simulation.Worker;

public class WorkersGene extends BaseGene implements Gene, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2643353341939435307L;
	public List<Worker> workers;

	public WorkersGene(Configuration a_configuration)
			throws InvalidConfigurationException {
		super(a_configuration);

		this.workers = new ArrayList<Worker>();
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void applyMutation(int index, double a_percentage) {
		// do nothing because of readonly mode
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
		this.workers = (List<Worker>) a_newValue;
	}

	@Override
	public void setToRandomValue(RandomGenerator a_numberGenerator) {
		// do nothing because of readonly mode
	}

	@Override
	public void setValueFromPersistentRepresentation(String a_representation)
			throws UnsupportedOperationException,
			UnsupportedRepresentationException {
		// TODO Auto-generated method stub
	}

	@Override
	protected Object getInternalValue() {
		return this.workers;
	}

	@Override
	protected Gene newGeneInternal() {
		try {
			return new WorkersGene(getConfiguration());
		} catch (InvalidConfigurationException ex) {
			throw new IllegalStateException(ex.getMessage());
		}
	}
	
	/**
	 * @return Count of the workers.
	 */
	public int count(){
		return this.workers.size();
	}
}