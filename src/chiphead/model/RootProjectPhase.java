package chiphead.model;

import java.math.BigInteger;

public class RootProjectPhase {
	private BigInteger rootProjPhaseId;
	private String projNo;
	private Integer rootPhaseId;
	private Integer year;
	private Integer month;
	private Integer weekNo;

	
	@Override 
	public String toString() {
	return this.rootProjPhaseId + "#" + 
			this.projNo + "#" +this.rootPhaseId + "#" + 
			this.year + "#" + this.month + "#" +
			this.weekNo;
	}


	public BigInteger getRootProjPhaseId() {
		return rootProjPhaseId;
	}


	public void setRootProjPhaseId(BigInteger rootProjPhaseId) {
		this.rootProjPhaseId = rootProjPhaseId;
	}


	public String getProjNo() {
		return projNo;
	}


	public void setProjNo(String projNo) {
		this.projNo = projNo;
	}


	public Integer getRootPhaseId() {
		return rootPhaseId;
	}


	public void setRootPhaseId(Integer rootPhaseId) {
		this.rootPhaseId = rootPhaseId;
	}


	public Integer getYear() {
		return year;
	}


	public void setYear(Integer year) {
		this.year = year;
	}


	public Integer getMonth() {
		return month;
	}


	public void setMonth(Integer month) {
		this.month = month;
	}


	public Integer getWeekNo() {
		return weekNo;
	}


	public void setWeekNo(Integer weekNo) {
		this.weekNo = weekNo;
	}
}
