package Media;

public class KnowledgeBase extends AMedia {
	public KnowledgeBase(int _communicationFrequency){
		this.type = MediaType.KNOWLEDGEBASE;
		this.communicationFrequency = _communicationFrequency;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 1;
	}
	
	public KnowledgeBase(){
		this.type = MediaType.KNOWLEDGEBASE;
		this.communicationFrequency = 0;
		this.negativeInfluence = 1;
		this.probabilityOfSuccessfulDiscuss = 1;
	}
}
