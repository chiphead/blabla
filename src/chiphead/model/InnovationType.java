package chiphead.model;

public class InnovationType {
	private Integer typeId;
	private String typeName;
	
	@Override 
	public String toString() {
	return this.typeId + "#" + this.typeName; 
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
