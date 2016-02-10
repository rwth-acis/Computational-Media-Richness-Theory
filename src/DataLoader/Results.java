package DataLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import Media.AMedia;


public class Results {
	private String resultFilePath;
	private FileWriter writer;

	static Results instance;

	public static Results getInstance() {
		if (instance == null)
			instance = new Results();
		return instance;
	}

	public Results(String _resultFilePath) {
		this.resultFilePath = _resultFilePath;
	}

	public Results() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat(
				"E yyyy.MM.dd 'at' hh.mm.ss a zzz");

		// System.out.println("Current Date: " + ft.format(dNow));

		String basePath = new File("simulation_results\\Simulation "
				+ ft.format(dNow) + ".csv").getAbsolutePath();
		// System.out.println(basePath);
		this.resultFilePath = basePath;
	}

	/**
	 * Write results to the CSV file.
	 * 
	 * @param DataMediator
	 */
	public void writeToCSVFile(DataMediator dm) {
		try {
			AMedia[] medias = dm.Medias;
			this.writer = new FileWriter(this.resultFilePath, true);
			String s = "";

			s += dm.currentFitness + ",";
			int i = 1;
			for (AMedia media : medias) {
				//Class<? extends AMedia> cl = media.getClass();
				//s += media.toString();
				s += media.communicationFrequency;
				if (i < medias.length) {
					s += ",";
				}
				i++;
			}

			writer.write(s + System.lineSeparator());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeHeader(DataMediator dm){
		try {
			AMedia[] medias = dm.Medias;
			this.writer = new FileWriter(this.resultFilePath, true);
			String s = "";

			s += dm.currentFitness + ",";
			int i = 1;
			for (AMedia media : medias) {
				//Class<? extends AMedia> cl = media.getClass();
				//s += media.toString();
				s += media.communicationFrequency;
				if (i < medias.length) {
					s += ",";
				}
				i++;
			}

			writer.write(s + System.lineSeparator());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
