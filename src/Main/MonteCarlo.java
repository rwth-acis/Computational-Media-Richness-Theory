package Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DataLoader.DataMediator;
import DataLoader.Results;
import Media.AMedia;
import Media.Email;
import Media.FaceToFace;
import Media.KnowledgeBase;
import Media.MediaType;
import Media.Phone;
import Simulation.Project;
import Simulation.SoftweareSimBuilder;
import Simulation.Team;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.environment.RunState;
import repast.simphony.util.collections.IndexedIterable;

/**
 * Class, that initializes and runs Monte Carlo simulation.
 * @author Alex
 */
public class MonteCarlo {
	private static List<List<Integer>> communicationFrequency;

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
					List<MediaType> mt = Main.scenario.getMedias(mediasPath);
					Results r = new Results();
					// MonteCarlo.setFrequency(mt.size());
					for (int runs = 0; runs < Main.scenario.maxAllowedEvolutions; runs++) {

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
							// int i = MonteCarlo.getFrequency(z);
							int i = (int) (Math.random() * 30);
							switch (mt.get(z)) {
							case EMAIL:
								Medias[z] = new Email(i);
								break;
							case PHONE:
								Medias[z] = new Phone(i);
								break;
							case FACETOFACE:
								Medias[z] = new FaceToFace(i);
								break;
							case KNOWLEDGEBASE:
								Medias[z] = new KnowledgeBase(i);
								break;
							default:
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
						r.writeToCSVFile(dm);
						System.out.println(r.toString());
						runner.stop(); // execute any actions
										// scheduled
										// at run end
						runner.cleanUpRun();
						System.out.println(">>> sim# " + runs);
					}					
				}
			}
		}
		runner.cleanUpBatch(); // run after all runs complete
		System.out.println(">>> end");
	}

	/**
	 * Initialize list with frequencies.
	 * 
	 * @param mediasCount
	 */
	@SuppressWarnings("unused")
	private static void setFrequency(int mediasCount, int runs) {
		MonteCarlo.communicationFrequency = new ArrayList<List<Integer>>();

		int size = 30;
		int z = runs / mediasCount;
		for (int j = 0; j < mediasCount; j++) {
			ArrayList<Integer> list = new ArrayList<Integer>(z);

			if (z >= size) {
				int t = z / size;
				for (int i = 0; i < t; i++) {
					for (int k = 0; k < size; k++) {
						list.add(k);
					}
				}
				for (int k = 0; k < z % size; k++) {
					list.add((int) (Math.random() * size));
				}

			} else if (z < size) {
				for (int i = 0; i < z; i++) {
					list.add(i);
				}
			}

			communicationFrequency.add(list);
		}
	}

	/**
	 * Get frequency from predefined list. No repeat.
	 * 
	 * @param fromList
	 * @return
	 */
	@SuppressWarnings("unused")
	private static int getFrequency(int fromList) {
		List<Integer> list = MonteCarlo.communicationFrequency.get(fromList);
		int index = -1;
		Random rand = new Random();
		if (list.size() > 0) {
			index = rand.nextInt(list.size());
			list.remove(index);
		}
		return index;
	}

}
