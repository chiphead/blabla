package chiphead.model;

public class StType {
	private Integer stId;
	private String stName;
	
	@Override 
	public String toString() {
	return this.stId + "#" + this.stName; 
	}

	public Integer getStId() {
		return stId;
	}

	public void setStId(Integer stId) {
		this.stId = stId;
	}

	public String getStName() {
		return stName;
	}

	public void setStName(String stName) {
		this.stName = stName;
	}

}
