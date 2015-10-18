package softwareSim;

public class Junior extends Worker{
	public Junior(int _id, Project _project) {
		super(_id, _project);
		this.experience = AgentTypes.JUNIOR;
	}
}
