package chiphead.model;

public class Level {
	private Integer levelId;
	private String levelName;
	
	@Override 
	public String toString() {
	return this.levelId + "#" + this.levelName; 
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

}
