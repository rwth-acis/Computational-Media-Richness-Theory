
import java.io.Console;

import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.runtime.RepastBatchMain;
import softwareSim.SingleTon;
import GeneticAlgorithm.GA;
import GeneticAlgorithm.GAConfig;

/*
import cern.jet.math.Bessel;
import cern.jet.random.VonMises;
import cern.jet.random.engine.RandomEngine;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.batch.InstanceRunner;
import repast.simphony.random.RandomHelper;
import repast.simphony.runtime.RepastBatchMain;
import repast.simphony.util.FileUtils;
import swarm_sim.learning.GA;
import swarm_sim.perception.AngleSegment;
import swarm_sim.perception.Scan;
import swarm_sim.perception.Scan.AttractionType;
import swarm_sim.perception.Scan.GrowingDirection;
import swarm_sim.perception.Scan.ScanInput;
import swarm_sim.perception.ScanMoveDecision;
*/
/*
public class Main extends FitnessFunction {


	public static void main(String[] args) {

		
		try {

			//args = new String[1];
			//args[1] = "batch/batch_params.xml";
			//args[0] = "-params";
			//args[1] ="C:/Users/Alex/workspace/SoftwareSim/batch/batch_params.xml";
			//args[0] ="C:/Users/Alex/workspace/SoftwareSim/SoftwareSim.rs";
			//C:/Users/Alex/workspace/SoftwareSim/SoftwareSim.rs
			//C:\Users\Alex\workspace\JZombies_Demo\JZombies_Demo.rs
			GA ga = SingleTon.SOLE.getit();
			//ga.data = 1;
			RepastBatchMain.main(args);
			int d = ga.data;
			System.out.println(">>> d: "+d);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected double evaluate(IChromosome a_subject) {
		// TODO Auto-generated method stub
		return 0;
	}
}
*/



import java.io.File;

public class Main {

	public static void main(String[] args){
		
		GA ga = GA.getInstance();
		System.out.println(ga.toString());
		
		File file = new File(args[0]); // the scenario dir
		String batchFile = "C:/Users/Alex/workspace/SoftwareSim/batch_external/batch_params.xml";
		MyBatchRunner runner = new MyBatchRunner();

		try {
			runner.load(file);     // load the repast scenario
		} catch (Exception e) {
			e.printStackTrace();
		}

		double endTime = 2000.0;  // some arbitrary end time

		// Run the sim a few times to check for cleanup and init issues.
		for(int i=0; i<2; i++){

			runner.runInitialize(batchFile);  // initialize the run

			RunEnvironment.getInstance().endAt(endTime);

			while (runner.getActionCount() > 0){  // loop until last action is left
				if (runner.getModelActionCount() == 0) {
					runner.setFinishing(true);
				}
				runner.step();  // execute all scheduled actions at next tick

			}

			runner.stop();          // execute any actions scheduled at run end
			runner.cleanUpRun();
		}
		runner.cleanUpBatch();    // run after all runs complete
		
		//TODO close
	}
}