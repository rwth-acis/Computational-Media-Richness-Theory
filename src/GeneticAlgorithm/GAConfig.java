package GeneticAlgorithm;

import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.impl.*;
import org.jgap.util.ICloneable;

@SuppressWarnings("serial")
public class GAConfig extends Configuration implements ICloneable {
	public GAConfig() {
		this("", "");
	}

	public GAConfig(String a_id, String a_name) {
		super(a_id, a_name);
		try {
			setBreeder(new GABreeder());
			setRandomGenerator(new StockRandomGenerator());
			setEventManager(new EventManager());
			BestChromosomesSelector bestChromsSelector = new BestChromosomesSelector(
					this, 0.90d);
			bestChromsSelector.setDoubletteChromosomesAllowed(false); // ←
																		// changed
																		// to
																		// false
																		// here
			addNaturalSelector(bestChromsSelector, false);
			setMinimumPopSizePercent(0);
			//
			setSelectFromPrevGen(1.0d);
			setKeepPopulationSizeConstant(true);
			setFitnessEvaluator(new DefaultFitnessEvaluator());
			//setFitnessFunction(new WorkEfficiencyFitnessFunction());
			setChromosomePool(new ChromosomePool());
			addGeneticOperator(new CrossoverOperator(this, 0.5d)); // ←
																	// changed
																	// from 0.35
																	// to 0.5
																	// here
			addGeneticOperator(new MutationOperator(this, 4)); // ← changed
																// from 12 to 4
																// here
		} catch (InvalidConfigurationException e) {
			throw new RuntimeException(
					"Fatal error: DefaultConfiguration class could not use its "
							+ "own stock configuration values. This should never happen. "
							+ "Please report this as a bug to the JGAP team.");
		}
	}

	public Object clone() {
		return super.clone();
	}
}
