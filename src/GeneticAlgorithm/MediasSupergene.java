package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.supergenes.AbstractSupergene;
import org.jgap.supergenes.Supergene;

import Media.MediaType;

/**
 * Encapsulates set of media genes, no logic inside.
 * @author Alex
 */
public class MediasSupergene extends AbstractSupergene {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MediasSupergene(Configuration a_conf, Gene[] a_genes)
			throws InvalidConfigurationException {
		super(a_conf, a_genes);
		// TODO Auto-generated constructor stub
		
		
	}

	public boolean isValid(Gene[] genes, Supergene s) {
		// suppose set of genes is always valid because media genes are independent
		boolean valid = true;
		return valid;
	}
}
