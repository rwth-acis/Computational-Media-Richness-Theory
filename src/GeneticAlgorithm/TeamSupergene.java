package GeneticAlgorithm;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.IntegerGene;
import org.jgap.supergenes.AbstractSupergene;
import org.jgap.supergenes.Supergene;

public class TeamSupergene extends AbstractSupergene {

	public TeamSupergene(Configuration a_conf, Gene[] a_genes)
			throws InvalidConfigurationException {
		super(a_conf, a_genes);
		// TODO Auto-generated constructor stub
	}
	
	

    /* Now just check nickels mod 2 = pennies mod 2: */
   public boolean isValid(Gene [] genes, Supergene s)
   {
        WorkersGene nickels = (WorkersGene)  genes[0];
        IntegerGene pennies  = (IntegerGene) genes[1];
        boolean valid = true;//nickels.intValue() % 2 == pennies.intValue() % 2;
        return valid;
   }

}
