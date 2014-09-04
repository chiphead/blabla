package chiphead.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Operation {
	private Integer operNo;
	private Date regDate;
	private Integer operType;
	private Timestamp operStartTime;
	private Timestamp operEndTime;
	private String operation;
	private Integer level;
	private String operators;
	//private Integer system;
	private String system;
	//private Integer module;
	private String belongPersons;
	private String affectedBusiness;
	private String reason;
	private String suggestion;
	private String remark;
	private Integer checkState;

	@Override 
	public String toString() {
	return this.operNo + "#" + this.regDate + "#" + 
			this.operType + "#" + this.operStartTime + "#" +
			this.operEndTime + "#" + this.operation + "#" +
			this.level + "#" +
			this.operators + "#" + this.system + "#" +
			/*this.module + "#" + */
			this.belongPersons + "#" + this.affectedBusiness + "#" +
			this.reason + "#" + this.suggestion + "#" + 
			this.remark + "#" + this.checkState;
	}

	public Integer getOperNo() {
		return operNo;
	}

	public void setOperNo(Integer operNo) {
		this.operNo = operNo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public Timestamp getOperStartTime() {
		return operStartTime;
	}

	public void setOperStartTime(Timestamp operStartTime) {
		this.operStartTime = operStartTime;
	}

	public Timestamp getOperEndTime() {
		return operEndTime;
	}

	public void setOperEndTime(Timestamp operEndTime) {
		this.operEndTime = operEndTime;
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

	/*
	public Integer getSystem() {
		return system;
	}

	public void setSystem(Integer system) {
		this.system = system;
	}

	public Integer getModule() {
		return module;
	}

	public void setModule(Integer module) {
		this.module = module;
	}
	*/
	

	public String getReason() {
		return reason;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
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

	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
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
	
}
