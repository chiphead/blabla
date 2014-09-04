package chiphead.model;

import java.math.BigInteger;

public class ProjectPhase {
	private BigInteger projPhaseId;
	private Integer empYstId;
	private String projNo;
	private Integer phaseId;
	private Integer year;
	private Integer month;
	private Integer weekNo;
	private Double devoteHours;
	private Integer checkState;

	
	@Override 
	public String toString() {
	return this.projPhaseId + "#" + this.empYstId + "#" + 
			this.projNo + "#" +this.phaseId + "#" + 
			this.year + "#" + this.month + "#" +
			this.weekNo + "#" + this.devoteHours + "#" +
			this.checkState;
	}


	public BigInteger getProjPhaseId() {
		return projPhaseId;
	}


	public void setProjPhaseId(BigInteger projPhaseId) {
		this.projPhaseId = projPhaseId;
	}


	public Integer getEmpYstId() {
		return empYstId;
	}


	public void setEmpYstId(Integer empYstId) {
		this.empYstId = empYstId;
	}

	public String getProjNo() {
		return projNo;
	}


	public void setProjNo(String projNo) {
		this.projNo = projNo;
	}


	public Integer getPhaseId() {
		return phaseId;
	}


	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
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


	public Double getDevoteHours() {
		return devoteHours;
	}


	public void setDevoteHours(Double devoteHours) {
		this.devoteHours = devoteHours;
	}


	public Integer getCheckState() {
		return checkState;
	}


	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}
}
