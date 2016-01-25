package Main;

import java.io.File;
import java.util.Random;

import DataLoader.DataMediator;
import DataLoader.Results;
import Media.AMedia;
import Media.Email;
import Media.FaceToFace;
import Media.Phone;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.environment.RunState;
import repast.simphony.util.collections.IndexedIterable;

public class MonteCarlo {
	public static void main(String[] args){
		File scenarioFile = new File(args[0]); // the scenario dir
		String batchFile = "C:/Users/Alex/workspace/SoftwareSim/batch_external/batch_params.xml";
		
		MyBatchRunner runner = new MyBatchRunner();

		try {
			runner.load(scenarioFile); // load the repast scenario
		} catch (Exception e) {
			e.printStackTrace();
		}

		double endAt = 200000.0; // some arbitrary end time
		int minActionsCount = 4; // dummy count of actions left in the end

		for (int k = 0; k < 31; k++) 
		for (int j = 0; j < 31; j++) 
		for (int i = 0; i < 31; i++) {			
			runner.runInitialize(batchFile); // initialize the run

			Context<DataMediator> c = RunState.getInstance().getMasterContext();
			IndexedIterable<DataMediator> ii = c.getObjects(DataMediator.class);
			DataMediator dm = (DataMediator) ii.get(0);
			
			AMedia[] Medias = new AMedia[3];
			Medias[0] = new Email(i); //(int) (Math.random()*30);
			Medias[1] =  new Phone(j); //(int) (Math.random()*30);
			Medias[2] = new FaceToFace(k); //(int) (Math.random()*30);
			
			dm.setData(Medias, null, null);
			RunEnvironment.getInstance().endAt(endAt);

			while (runner.getActionCount() > minActionsCount) { // loop until last action is
													// left
				if (runner.getModelActionCount() == 0) {
					runner.setFinishing(true);
				}
				runner.step(); // execute all scheduled actions at next tick			
			}
			Results.getInstance().writeToCSVFile(dm);
			runner.stop(); // execute any actions scheduled at run end
			runner.cleanUpRun();
			System.out.println(">>> sim# "+i);
		}
		runner.cleanUpBatch(); // run after all runs complete

		System.out.println(">>> end");
	}
}
