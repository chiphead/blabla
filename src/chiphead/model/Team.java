package chiphead.model;

public class Team {
	private Integer teamId;
	private String teamName;
	
	@Override 
	public String toString() {
	return this.teamId + "#" + this.teamName; 
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
}
