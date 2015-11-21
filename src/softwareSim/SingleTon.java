package softwareSim;
import GeneticAlgorithm.GA;


public enum SingleTon {

		   SOLE;
		   
		   //all class stuff
		public GA getit(){
			return GA.getInstance();
		}
}
