package Main;

import java.io.File;
import java.util.List;

import org.jgap.impl.IntegerGene;

import DataLoader.DataMediator;
import DataLoader.Results;
import Media.AMedia;
import Media.Email;
import Media.FaceToFace;
import Media.MediaType;
import Media.Phone;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.environment.RunState;
import repast.simphony.util.collections.IndexedIterable;
import softwareSim.Project;
import softwareSim.SoftweareSimBuilder;
import softwareSim.Team;

public class MonteCarlo {
	public static void main(String[] args) {
		File scenarioFile = new File(args[0]); // the scenario dir
		String batchFile = "C:/Users/Alex/workspace/SoftwareSim/batch_external/batch_params.xml";

		MyBatchRunner runner = new MyBatchRunner();

		try {
			runner.load(scenarioFile); // load the repast scenario
		} catch (Exception e) {
			e.printStackTrace();
		}

		double endAt = Main.scenario.maxAllowedSteps; // some arbitrary end time
		int minActionsCount = 6; // dummy count of actions left in the end

		for (String teamPath : Main.scenario.teamPaths) {
			for (String projectPath : Main.scenario.projectPaths) {
				for (String mediasPath : Main.scenario.mediaPaths) {
					for (int runs = 0; runs < Main.scenario.maxAllowedEvolutions; runs++) {
						List<MediaType> mt = Main.scenario
								.getMedias(mediasPath);

						// (int)
						// (Math.random()*30);
						int i = 10;
						int j = 10;
						int k = 10;
						// initialize the run
						runner.runInitialize(batchFile);

						@SuppressWarnings("unchecked")
						Context<DataMediator> c = RunState.getInstance()
								.getMasterContext();
						IndexedIterable<DataMediator> ii = c
								.getObjects(DataMediator.class);
						DataMediator dm = (DataMediator) ii.get(0);

						AMedia[] Medias = new AMedia[mt.size()];
						for (int z = 0; z < mt.size(); z++) {
							switch (mt.get(z)) {
							case EMAIL:
								Medias[z] = new Email(i);
								break;
							case PHONE:
								Medias[z] = new Phone(j);
								break;
							case FACETOFACE:
								Medias[z] = new FaceToFace(k);
								break;
							}
						}

						Team team = Main.scenario.getTeam(teamPath);
						dm.setData(Medias, team.adjacencyList, team.workers);
						RunEnvironment.getInstance().endAt(endAt);

						@SuppressWarnings("unchecked")
						Context<SoftweareSimBuilder> s = RunState.getInstance()
								.getMasterContext();
						IndexedIterable<SoftweareSimBuilder> ii2 = s
								.getObjects(SoftweareSimBuilder.class);
						SoftweareSimBuilder ssb = (SoftweareSimBuilder) ii2
								.get(0);
						Project project = Main.scenario.getProject(projectPath);
						ssb.addAgents(team, project);

						// loop until last action is left
						while (runner.getActionCount() > minActionsCount) {

							if (runner.getModelActionCount() == 0) {
								runner.setFinishing(true);
							}
							runner.step(); // execute all scheduled
											// actions at next tick
						}
						Results.getInstance().writeToCSVFile(dm);
						runner.stop(); // execute any actions
										// scheduled
										// at run end
						runner.cleanUpRun();
						System.out.println(">>> sim# " + i);
					}
					runner.cleanUpBatch(); // run after all runs complete
				}
			}
		}
		System.out.println(">>> end");
	}
}
