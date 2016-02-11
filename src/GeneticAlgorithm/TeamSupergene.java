package GeneticAlgorithm;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.supergenes.AbstractSupergene;
import org.jgap.supergenes.Supergene;

public class TeamSupergene extends AbstractSupergene {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2242192022803802552L;

	public TeamSupergene(Configuration a_conf, Gene[] a_genes)
			throws InvalidConfigurationException {
		super(a_conf, a_genes);
		// TODO Auto-generated constructor stub
	}	

    /* Now just check nickels mod 2 = pennies mod 2: */
   public boolean isValid(Gene [] genes, Supergene s)
   {/*
	   AdjacencyMatrix am = new AdjacencyMatrix() {
		
		@Override
		public int rows() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public String matrixToString() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public DenseDoubleMatrix1D getRow(int row) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getMatrixLabel() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<String> getLabels() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public double get(int row, int col) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public int columns() {
			// TODO Auto-generated method stub
			return 0;
		}
	};*/
        WorkersGene workers = (WorkersGene)  genes[0];
        AdjacencyListGene al  = (AdjacencyListGene) genes[1];
        boolean valid = workers.count() == al.count();//nickels.intValue() % 2 == pennies.intValue() % 2;
        return valid;
   }
}