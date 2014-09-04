package chiphead.model;

public class Phase {
	private Integer phaseId;
	private String phaseName;
	
	@Override 
	public String toString() {
	return this.phaseId + "#" + this.phaseName; 
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}
}
