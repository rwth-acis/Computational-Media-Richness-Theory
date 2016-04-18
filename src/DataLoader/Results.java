package DataLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Media.AMedia;
import Media.MediaType;


public class Results {
	private String resultFilePath;
	private FileWriter writer;

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
	
	/**
	 * Create header - first line in the csv file
	 * @param mt
	 */
	public void writeHeader(List<MediaType> medias){
		try {
			
			this.writer = new FileWriter(this.resultFilePath, true);
			String s = "Efficiency,";
			
			int i = 1;
			for (MediaType media : medias) {				
				s += media.toString();
				if (i < medias.size()) {
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
