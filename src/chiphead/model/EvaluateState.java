package chiphead.model;

public class EvaluateState {
	private Integer stateId;
	private String stateName;
	
	@Override 
	public String toString() {
	return this.stateId + "#" + this.stateName; 
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}
