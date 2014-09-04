package chiphead.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Innovation {
	private Integer innoNo;
	private Date regDate;
	private Integer innoType;
	private String proposers;
	private Integer system;
	private String module;
	private String suggestion;
	private String reason;
	private String addSuggestion;
	private String conclusion;
	private String executors;
	private String effect;

	@Override 
	public String toString() {
	return this.innoNo + "#" + this.regDate + "#" + 
			this.innoType + "#" + this.proposers + "#" +
			this.system + "#" + this.module + "#" +
			this.suggestion + "#" + this.reason + "#" +
			this.addSuggestion + "#" + this.conclusion + "#" +
			this.executors + "#" + this.effect;
	}

	public Integer getInnoNo() {
		return innoNo;
	}

	public void setInnoNo(Integer innoNo) {
		this.innoNo = innoNo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Integer getInnoType() {
		return innoType;
	}

	public void setInnoType(Integer innoType) {
		this.innoType = innoType;
	}

	public String getProposers() {
		return proposers;
	}

	public void setProposers(String proposers) {
		this.proposers = proposers;
	}

	public Integer getSystem() {
		return system;
	}

	public void setSystem(Integer system) {
		this.system = system;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAddSuggestion() {
		return addSuggestion;
	}

	public void setAddSuggestion(String addSuggestion) {
		this.addSuggestion = addSuggestion;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public String getExecutors() {
		return executors;
	}

	public void setExecutors(String executors) {
		this.executors = executors;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}
	
}
