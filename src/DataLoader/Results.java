package DataLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jgap.Chromosome;
import org.jgap.Gene;
import org.jgap.IChromosome;

import GeneticAlgorithm.GA;
import softwareSim.VariablesSettings;

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

		String basePath = new File("Simulation " + ft.format(dNow) + ".csv")
				.getAbsolutePath();
		// System.out.println(basePath);
		this.resultFilePath = basePath;
	}

	/**
	 * Write results to the CSV file.
	 * 
	 * @param chromosome
	 */
	public void writeToCSVFile(GA ga) {
		try {
			IChromosome chromosome = ga.currentChromosome;
			this.writer = new FileWriter(this.resultFilePath, true);// "E:\\CountryGSON.json"
			String s = "";

			s += ga.currentFitness + ",";
			int i = 1;
			for (Gene gene : chromosome.getGenes()) {
				Class<? extends Gene> cl = gene.getClass();
				s += gene.getAllele().toString();
				if(i < chromosome.getGenes().length){
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
