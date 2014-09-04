package chiphead.model;

import java.math.BigInteger;

public class PersonProject {
	private BigInteger empProjId;
	private Integer empYstId;
	private String projNo;
	private Integer roleId;
	
	@Override 
	public String toString() {
	return this.empProjId + "#" + this.empYstId + "#" + 
			this.projNo + "#" + this.roleId; 
	}

	public BigInteger getEmpProjId() {
		return empProjId;
	}

	public void setEmpProjId(BigInteger empProjId) {
		this.empProjId = empProjId;
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
