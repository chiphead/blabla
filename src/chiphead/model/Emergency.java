package chiphead.model;

import java.sql.Date;

public class Emergency {
	
	private Integer emerNo;
	private Date regDate;
	private Date emerDate;
	private String emerContent;
	private String influence;
	private String analytics;
	private String code;
	private String level;
	private String operators;
	private String projNo;
	private String projName;
	private String projManager;
	private String responsible;
	private String system;
	private String remark;	
	
	@Override 
	public String toString() {
	return 	this.emerNo + "#" + this.regDate + "#" + 
			this.emerDate + "#" + this.emerContent + "#" + 
			this.influence + "#" + this.analytics + "#" + 
			this.code + "#" + this.level + "#" + this.operators + "#" + 
			this.projNo + "#" + this.projName + "#" + this.projManager + "#" + 
			this.responsible + "#" + this.system + "#" + this.remark;
	}

	public Integer getEmerNo() {
		return emerNo;
	}

	public void setEmerNo(Integer emerNo) {
		this.emerNo = emerNo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getEmerDate() {
		return emerDate;
	}

	public void setEmerDate(Date emerDate) {
		this.emerDate = emerDate;
	}

	public String getEmerContent() {
		return emerContent;
	}

	public void setEmerContent(String emerContent) {
		this.emerContent = emerContent;
	}

	public String getInfluence() {
		return influence;
	}

	public void setInfluence(String influence) {
		this.influence = influence;
	}

	public String getAnalytics() {
		return analytics;
	}

	public void setAnalytics(String analytics) {
		this.analytics = analytics;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
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

	public String getProjManager() {
		return projManager;
	}

	public void setProjManager(String projManager) {
		this.projManager = projManager;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}	
}
