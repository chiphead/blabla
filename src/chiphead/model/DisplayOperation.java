package chiphead.model;

public class DisplayOperation {
	private String checkState;//原Integer
	private String operNo;//原Integer
	private String regDate;//原Date
	private String operType;//原Integer
	private String operTime;
	private String operation;
	private String level;//原Integer
	private String operators;
	private String system;
	//private String module;
	private String belongPersons;
	private String affectedBusiness;
	private String reason;
	private String suggestion;
	private String remark;

	@Override 
	public String toString() {
	return this.operNo + "#" + this.regDate + "#" + 
			this.operType + "#" + this.operTime + "#" +
			this.operation + "#" + this.level + "#" +
			this.operators + "#" + this.system + "#" +
			/*this.module + "#" + */this.belongPersons + "#" + 
			this.affectedBusiness + "#" + this.reason + "#" +
			this.suggestion + "#" + this.remark + "#" + 
			this.checkState;
	}

	
	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public String getBelongPersons() {
		return belongPersons;
	}


	public void setBelongPersons(String belongPersons) {
		this.belongPersons = belongPersons;
	}


	public String getAffectedBusiness() {
		return affectedBusiness;
	}


	public void setAffectedBusiness(String affectedBusiness) {
		this.affectedBusiness = affectedBusiness;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getOperNo() {
		return operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	/*
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	*/

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

}
