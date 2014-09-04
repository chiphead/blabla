package chiphead.model;


public class DisplayProject {
	private String evaluateState;
	private String updateTime;
	private String reqNo;
	private String projNo;
	private String projName;
	private String projType;//projects中Integer
	private String masterSlave;
	private String projManager;
	private String curPhase;//当前阶段，projects表中没有
	private String curState;//projects中Integer
	private String riskQuestion;
	private String designer;//设计人员，逗号分隔
	private String developer;//开发人员，逗号分隔
	private String auditor;//评审人员，逗号分隔
	private String startDate;//projects中Date
	private String actualInstallDate;//projects中Date
	private String planEndDate;//projects中Date
	private String actualEndDate;//projects中Date
	private String actualDays;//projects中Integer
	private String overdueMark;//Y/N/""
	private String overdueReason;
	private String planWorkAmount;//projects中Double
	private String actualWorkAmount;//projects中Double
	private String functionAmount;//projects中Double
	private String productivity;//projects中Double
	private String smokeNum;//projects中Integer
	private String stType;//projects中Integer
	private String stBugDensity;//projects中Double
	private String nc;
	private String designAudit;
	private String codeAudit;
	private String installAudit;
	private String experience;
	private String problemAnalysis;
	private String remark;
	
	@Override 
	public String toString() {
	return this.evaluateState + "#" + this.updateTime + "#" + 
			this.reqNo + "#" +
			this.projNo + "#" + this.projName + "#" +
			this.projType + "#" + "#" + this.masterSlave + 
			this.projManager + "#" + this.curPhase + "#" + this.curState + "#" + 
			this.riskQuestion + "#" + this.designer + "#" +
			this.developer + "#" + this.auditor + "#" + this.startDate + "#" +
			this.actualInstallDate + "#" + this.planEndDate + "#" + this.actualEndDate + "#" +
			this.actualDays + "#" + this.overdueMark + "#" + 
			this.overdueReason + "#" + 
			this.planWorkAmount + "#" + this.actualWorkAmount + "#" + 
			this.functionAmount + "#" + this.productivity + "#" + 
			this.smokeNum + "#" + this.stType + "#" + 
			this.stBugDensity +"#" + this.nc + "#" + this.designAudit + "#" +
			this.codeAudit + "#" + this.installAudit + "#" +
			this.experience + "#" + this.problemAnalysis + "#" +
			this.remark;
	}
	
	public String getOverdueMark() {
		return overdueMark;
	}

	public void setOverdueMark(String overdueMark) {
		this.overdueMark = overdueMark;
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
	public String getProjType() {
		return projType;
	}
	public void setProjType(String projType) {
		this.projType = projType;
	}
	public String getMasterSlave() {
		return masterSlave;
	}
	public void setMasterSlave(String masterSlave) {
		this.masterSlave = masterSlave;
	}
	public String getProjManager() {
		return projManager;
	}
	public void setProjManager(String projManager) {
		this.projManager = projManager;
	}
	public String getCurPhase() {
		return curPhase;
	}
	public void setCurPhase(String curPhase) {
		this.curPhase = curPhase;
	}
	public String getCurState() {
		return curState;
	}
	public void setCurState(String curState) {
		this.curState = curState;
	}
	public String getRiskQuestion() {
		return riskQuestion;
	}
	public void setRiskQuestion(String riskQuestion) {
		this.riskQuestion = riskQuestion;
	}
	public String getDesigner() {
		return designer;
	}
	public void setDesigner(String designer) {
		this.designer = designer;
	}
	public String getDeveloper() {
		return developer;
	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}
	public String getActualEndDate() {
		return actualEndDate;
	}
	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}
	public String getActualDays() {
		return actualDays;
	}
	public void setActualDays(String actualDays) {
		this.actualDays = actualDays;
	}
	public String getPlanWorkAmount() {
		return planWorkAmount;
	}
	public void setPlanWorkAmount(String planWorkAmount) {
		this.planWorkAmount = planWorkAmount;
	}
	public String getActualWorkAmount() {
		return actualWorkAmount;
	}
	public void setActualWorkAmount(String actualWorkAmount) {
		this.actualWorkAmount = actualWorkAmount;
	}
	public String getFunctionAmount() {
		return functionAmount;
	}
	public void setFunctionAmount(String functionAmount) {
		this.functionAmount = functionAmount;
	}
	public String getProductivity() {
		return productivity;
	}
	public void setProductivity(String productivity) {
		this.productivity = productivity;
	}
	public String getSmokeNum() {
		return smokeNum;
	}
	public void setSmokeNum(String smokeNum) {
		this.smokeNum = smokeNum;
	}
	public String getStType() {
		return stType;
	}
	public void setStType(String stType) {
		this.stType = stType;
	}
	public String getStBugDensity() {
		return stBugDensity;
	}
	public void setStBugDensity(String stBugDensity) {
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

	public String getEvaluateState() {
		return evaluateState;
	}

	public void setEvaluateState(String evaluateState) {
		this.evaluateState = evaluateState;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getActualInstallDate() {
		return actualInstallDate;
	}

	public void setActualInstallDate(String actualInstallDate) {
		this.actualInstallDate = actualInstallDate;
	}

	public String getOverdueReason() {
		return overdueReason;
	}

	public void setOverdueReason(String overdueReason) {
		this.overdueReason = overdueReason;
	}
	
}
