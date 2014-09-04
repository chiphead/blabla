package chiphead.model;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

public class Project {
	private BigInteger projId;
	private Timestamp updateTime;
	private String reqNo;
	private String projNo;
	private String projName;
	private Integer projType;
	private Integer masterSlave;
	private String projCharge;
	private String projManager;
	private Integer curState;
	private String riskQuestion;
	private Date startDate;
	private String actualInstallDate;
	private Date planEndDate;
	private Date actualEndDate;
	private Integer actualDays;
	private String overdueReason;
	private Double planWorkAmount;
	private Double actualWorkAmount;
	private Double functionAmount;
	private Double productivity;
	private Integer smokeNum;
	private Integer stType;
	private Double stBugDensity;
	private String nc;
	private String designAudit;
	private String codeAudit;
	private String installAudit;
	private String experience;
	private String problemAnalysis;
	private String remark;
	private Integer evaluateState;
	
	@Override 
	public String toString() {
	return this.projId + "#" + this.updateTime + "#" + this.reqNo + "#" + 
			this.projNo + "#" + this.projName + "#" + this.projType + "#" +
			this.masterSlave + "#" + this.projCharge + "#" + 
			this.projManager + "#" + this.curState + "#" + 
			this.riskQuestion + "#" + this.startDate + "#" + 
			this.actualInstallDate + "#" + this.planEndDate + "#" + 
			this.actualEndDate + "#" +this.actualDays + "#" + 
			this.overdueReason + "#" + 
			this.planWorkAmount + "#" +
			this.actualWorkAmount + "#" + this.functionAmount + "#" + 
			this.productivity + "#" + this.smokeNum + "#" + 
			this.stType + "#" + this.stBugDensity + "#" +
			this.nc + "#" + this.designAudit + "#" +
			this.codeAudit + "#" + this.installAudit + "#" +
			this.experience + "#" + this.problemAnalysis + "#" +
			this.remark + "#" + this.evaluateState;
	}

	public BigInteger getProjId() {
		return projId;
	}

	public void setProjId(BigInteger projId) {
		this.projId = projId;
	}

	public String getProjNo() {
		return projNo;
	}

	public void setProjNo(String projNo) {
		this.projNo = projNo;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public Integer getProjType() {
		return projType;
	}

	public void setProjType(Integer projType) {
		this.projType = projType;
	}
	
	public Integer getMasterSlave() {
		return masterSlave;
	}
	
	public void setMasterSlave(Integer masterSlave) {
		this.masterSlave = masterSlave;
	}

	public String getProjCharge() {
		return projCharge;
	}

	public void setProjCharge(String projCharge) {
		this.projCharge = projCharge;
	}

	public String getProjManager() {
		return projManager;
	}

	public void setProjManager(String projManager) {
		this.projManager = projManager;
	}

	public Integer getCurState() {
		return curState;
	}

	public void setCurState(Integer curState) {
		this.curState = curState;
	}

	public String getRiskQuestion() {
		return riskQuestion;
	}

	public void setRiskQuestion(String riskQuestion) {
		this.riskQuestion = riskQuestion;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public Integer getActualDays() {
		return actualDays;
	}

	public void setActualDays(Integer actualDays) {
		this.actualDays = actualDays;
	}

	public Double getPlanWorkAmount() {
		return planWorkAmount;
	}

	public void setPlanWorkAmount(Double planWorkAmount) {
		this.planWorkAmount = planWorkAmount;
	}

	public Double getActualWorkAmount() {
		return actualWorkAmount;
	}

	public void setActualWorkAmount(Double actualWorkAmount) {
		this.actualWorkAmount = actualWorkAmount;
	}

	public Double getFunctionAmount() {
		return functionAmount;
	}

	public void setFunctionAmount(Double functionAmount) {
		this.functionAmount = functionAmount;
	}

	public Double getProductivity() {
		return productivity;
	}

	public void setProductivity(Double productivity) {
		this.productivity = productivity;
	}

	public Integer getSmokeNum() {
		return smokeNum;
	}

	public void setSmokeNum(Integer smokeNum) {
		this.smokeNum = smokeNum;
	}

	public Integer getStType() {
		return stType;
	}

	public void setStType(Integer stType) {
		this.stType = stType;
	}

	public Double getStBugDensity() {
		return stBugDensity;
	}

	public void setStBugDensity(Double stBugDensity) {
		this.stBugDensity = stBugDensity;
	}

	public String getNc() {
		return nc;
	}

	public void setNc(String nc) {
		this.nc = nc;
	}

	public String getDesignAudit() {
		return designAudit;
	}

	public void setDesignAudit(String designAudit) {
		this.designAudit = designAudit;
	}

	public String getCodeAudit() {
		return codeAudit;
	}

	public void setCodeAudit(String codeAudit) {
		this.codeAudit = codeAudit;
	}

	public String getInstallAudit() {
		return installAudit;
	}

	public void setInstallAudit(String installAudit) {
		this.installAudit = installAudit;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getProblemAnalysis() {
		return problemAnalysis;
	}

	public void setProblemAnalysis(String problemAnalysis) {
		this.problemAnalysis = problemAnalysis;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getEvaluateState() {
		return evaluateState;
	}

	public void setEvaluateState(Integer evaluateState) {
		this.evaluateState = evaluateState;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getOverdueReason() {
		return overdueReason;
	}

	public void setOverdueReason(String overdueReason) {
		this.overdueReason = overdueReason;
	}

	public String getActualInstallDate() {
		return actualInstallDate;
	}

	public void setActualInstallDate(String actualInstallDate) {
		this.actualInstallDate = actualInstallDate;
	}

}
