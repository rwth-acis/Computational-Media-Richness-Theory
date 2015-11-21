package GeneticAlgorithm;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

@SuppressWarnings("serial")
public class WorkEfficiencyFitnessFunction extends FitnessFunction {

	private double bestProductivity;

	public WorkEfficiencyFitnessFunction(double _bestProductivity) {
		this.bestProductivity = _bestProductivity;
	}

	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub
		
		
		return 0;
	}
}
