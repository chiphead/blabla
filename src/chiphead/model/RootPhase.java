package chiphead.model;

public class RootPhase {
	private Integer rootPhaseId;
	private String rootPhaseName;
	
	@Override 
	public String toString() {
	return this.rootPhaseId + "#" + this.rootPhaseName; 
	}

	public Integer getRootPhaseId() {
		return rootPhaseId;
	}

	public void setRootPhaseId(Integer rootPhaseId) {
		this.rootPhaseId = rootPhaseId;
	}

	public String getRootPhaseName() {
		return rootPhaseName;
	}

	public void setRootPhaseName(String rootPhaseName) {
		this.rootPhaseName = rootPhaseName;
	}
}
