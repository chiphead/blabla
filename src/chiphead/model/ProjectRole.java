package chiphead.model;

public class ProjectRole {
	private Integer roleId;
	private String roleName;
	
	@Override 
	public String toString() {
	return this.roleId + "#" + this.roleName; 
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
